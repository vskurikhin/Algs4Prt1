/* Board.java */

import java.util.Iterator;
// import edu.princeton.cs.algs4.Queue;
// import edu.princeton.cs.algs4.StdRandom;

/**
 * <b>We make a key observatioi</b>: To solve the puzzle from a given search
 * node on the priority queue, the total number of moves we need to make
 * (including those already made) is at least its priority, using either
 * the Hamming or Manhattan priority function. (For Hamming priority, this
 * is true because each block that is out of place must move at least once
 * to reach its goal position. For Manhattan priority, this is true because
 * each block must move its Manhattan distance from its goal position.
 * Note that we do not count the blank square when computing the Hamming or
 * Manhattan priorities.) Consequently, when the goal board is dequeued, we
 * have discovered not only a sequence of moves from the initial board to
 * the goal board, but one that makes the fewest number of moves.
 * (Challenge for the mathematically inclined: prove this fact.)
 */
public class Board {
    private int[][] blocks;
    private int N;

    // helper Zero position on board data type
    private int izero;

    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * @param blocks an array of board combination
     */
    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (0 == blocks[i][j]) {
                    packZero(i, j);
                }
            }
    }

    private void packZero(int row, int col) {
        this.izero = row;
        this.izero <<= 8;
        this.izero += col;
    }

    private int getZeroRow() {
        return (this.izero >> 8);
    }

    private int getZeroCol() {
        return (this.izero - (getZeroRow() << 8));
    }

    // board dimension N
    public int dimension() { return this.N; }

    /**
     * @param i a row position
     * @param j a col position
     * @return true if element on position i,j is wrong
     */
    private boolean wrongPosition(int i, int j) {
        return this.blocks[i][j] != ((this.N * i + j + 1) % (this.N*this.N));
    }

    /**
     * @param i a row position
     * @param j a col position
     * @return true if element on position i,j is wrong and not zero
     */
    private boolean notZeroWrongPosition(int i, int j) {
        if (0 == this.blocks[i][j])
            return false;
        return wrongPosition(i, j);
    }

    /**
     * <i>Hamming priority function</i>. The number of blocks in the wrong
     * position, plus the number of moves made so far to get to the search
     * node. Intuitively, a search node with a small number of blocks in
     * the wrong position is close to the goal, and we prefer a search node
     * that have been reached using a small number of moves.
     * @return hamming priority (number of blocks out of place)
     */
    public int hamming() {
        int result = 0;
        /* debug
        int n = 0;
         */
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                if (notZeroWrongPosition(i, j)) {
                    result++;
                }
        return result;
    }

    /**
     * <i>Manhattan priority function</i>. The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance) from the blocks to their
     * goal positions, plus the number of moves made so far to get to the
     * search node.
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < this.N; ++i)
            for (int j = 0; j < this.N; ++j) {
                if (notZeroWrongPosition(i, j)) {
                   int colMove = this.blocks[i][j] % this.N;
                   int rowMove = this.blocks[i][j] / this.N;
                   if (colMove == 0) {
                       colMove = this.N - 1;
                       rowMove--;
                   } else
                       colMove--;
                   result += Math.abs(rowMove - i) + Math.abs(colMove - j);
                }
            }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int n = 1;
        for (int i = 0; i < this.N; ++i)
            for (int j = 0; j < this.N; ++j) {
                if (blocks[i][j] != (n % (this.N*this.N))) {
                    return false;
                }
                n++;
            }
        return true;
    }

    // a board that is obtained by exchanging two adjacent blocks in
    // the same row
    public Board twin() {
        Board twin = new Board(this.blocks);
        while (true) {
            int i = StdRandom.uniform(this.N);
            int j = StdRandom.uniform(this.N);
            int k = j;
            if (j == this.N - 1) {
                --k;
            } else {
                ++k;
            }
            if (blocks[i][j] != 0 && blocks[i][k] != 0) {
                int t = twin.blocks[i][j];
                twin.blocks[i][j] = blocks[i][k];
                twin.blocks[i][k] = t;
                break;
            }
        }
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board b = (Board) y;
        if (b.dimension() != this.N)
            return false;
        for (int i = 0; i < this.N; ++i)
            for (int j = 0; j < this.N; ++j) {
                if (this.blocks[i][j] != b.blocks[i][j])
                    return false;
            }
        return true;
    }

    private class NeighborIterator implements Iterator<Board> {
        private Queue<Board> neighbors;

        public NeighborIterator() {
            neighbors = new Queue<Board>();
            if (getZeroRow() > 0) {
                moveZero(-1, 0);
            }
            if (getZeroCol() < N - 1) {
                moveZero(0, 1);
            }
            if (getZeroRow() < N - 1) {
                moveZero(1, 0);
            }
            if (getZeroCol() > 0) {
                moveZero(0, -1);
            }
        }

        private void moveZero(int down, int right) {
            Board b = new Board(blocks);
            int oldRow = getZeroRow();
            int oldCol = getZeroCol();
            int newRow = oldRow + down;
            int newCol = oldCol + right;
            b.blocks[oldRow][oldCol] = b.blocks[newRow][newCol];
            b.blocks[newRow][newCol] = 0;
            b.packZero(newRow, newCol);
            neighbors.enqueue(b);
        }

        public boolean hasNext() {
            return (!neighbors.isEmpty());
        }

        public Board next() { return neighbors.dequeue(); }

        public void remove() { }
    }

    // all neighboring boards
    private class NeighborIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            NeighborIterator iter = new NeighborIterator();
            return iter;
        }
    }

    public Iterable<Board> neighbors() {
        Iterable<Board> iter = new NeighborIterable();
        return iter;
    }

    // string representation of this board 
    // (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // String result = "";
        // result += this.N + "\n";
        sb.append(this.N).append("\n");
        for (int i = 0; i < this.N; ++i) {
            // String s1 = "";
            int j;
            for (j = 0; j < this.N - 1; ++j) {
                // s1 += this.blocks[i][j] + " ";
                sb.append(this.blocks[i][j]).append(" ");
            }
            // result += s1 + this.blocks[i][j] + "\n";
            sb.append(this.blocks[i][j]).append("\n");
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        return;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
