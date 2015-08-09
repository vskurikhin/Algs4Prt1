import java.util.Iterator;

public class Board {
    private int[][] blocks;
    private int N;

    private class Zero {
        public int i;
        public int j;
        public Zero(int row, int col) { i = row; j = col; }
    }

    private Zero zero;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (0 == blocks[i][j]) {
                    zero = new Zero(i, j);
                }
            }
    }

    // board dimension N
    public int dimension() { return N; }

    // number of blocks out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0, n = 1; i < N; ++i)
            for (int j = 0; j < N; ++j)
                if (blocks[i][j] != (n++ % 9)) {
                    return false;
                }
        return true;
    }

    // a board that is obtained by exchanging two adjacent blocks in
    // the same row
    public Board twin() {
        Board twin = new Board(this.blocks);
        while (true) {
            int i = StdRandom.uniform(N);
            int j = StdRandom.uniform(N);
            int k = j;
            if (j == N - 1) {
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
        Board b = (Board)y;
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j) {
                if (this.blocks[i][j] != b.blocks[i][j])
                    return false;
            }
        return true;
    }

    private class NeighborIterator implements Iterator<Board> {
        private Queue<Board> neighbors;

        private void moveZero(Zero newZero) {
            Board b = new Board(blocks);
            /*
            StdOut.print("NeighborIterator::moveZero:: ");
            StdOut.printf("zero.i = %d, zero.j = %d" + 
                          ", newZero.i = %d, newZero.j = %d\n",
                          zero.i, zero.j, newZero.i, newZero.j); */
            b.blocks[zero.i][zero.j] = b.blocks[newZero.i][newZero.j];
            b.blocks[newZero.i][newZero.j] = 0;
            b.zero.i = newZero.i;
            b.zero.j = newZero.j;
            neighbors.enqueue(b);
        }

        public NeighborIterator() {
            neighbors = new Queue<Board>();
            Zero newZero = new Zero(zero.i, zero.j);
            if (zero.i > 0) {
                newZero.i--;
                moveZero(newZero);
                newZero.i++;
            }
            if (zero.j < N - 1) {
                newZero.j++;
                moveZero(newZero);
                newZero.j--;
            }
            if (zero.i < N - 1) {
                newZero.i++;
                moveZero(newZero);
                newZero.i--;
            }
            if (zero.j > 0) {
                newZero.j--;
                moveZero(newZero);
                newZero.j++;
            }
        }

        public boolean hasNext() {
                return (neighbors.isEmpty() == false);
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
        String result = new String();
        result += N + "\n";
        for (int i = 0; i < N; ++i) {
            int j;
            for (j = 0; j < N - 1; ++j) {
                result += blocks[i][j] + " ";
            }
            result += this.blocks[i][j] + "\n";
        }
        return result;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        StdRandom.setSeed(System.currentTimeMillis());
        int N = 3;
        int[][] blocks = new int[N][N];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;
        Board a = new Board(blocks);
        Board b = new Board(blocks);
        Board c = new Board(blocks);
        c.blocks[1][1] = c.blocks[2][2];
        c.blocks[2][2] = 0;
        StdOut.println("Board a:");
        StdOut.println(a.toString());
        StdOut.println("Board b:");
        StdOut.println(b.toString());
        StdOut.println("Board c:");
        StdOut.println(c.toString());
        if (a.equals(b)) {
            StdOut.println("a equals b");
        } else {
            StdOut.println("a isn't equals b");
        }
        if (a.equals(c)) {
            StdOut.println("a equals c");
        } else {
            StdOut.println("a isn't equals c");
        }
        for (int i = 0, n = 1; i < N; ++i)
            for (int j = 0; j < N; ++j)
                blocks[i][j] = (n++ % 9);
        Board g = new Board(blocks);
        StdOut.println("Board g:");
        StdOut.println(g.toString());
        if (g.isGoal()) {
            StdOut.println("Yes, g it's goal.");
        }

        StdOut.println("Board a:");
        StdOut.println(a.toString());
        StdOut.println("a neighbors:");
        for (Board e : a.neighbors()) {
            StdOut.printf("e:\n" + e.toString());
            StdOut.printf("e.zero.i = %d\n", e.zero.i);
            StdOut.printf("e.zero.j = %d\n", e.zero.j);
            StdOut.printf("e.blocks[%d][%d] = %d\n", e.zero.i, e.zero.j,
                           e.blocks[e.zero.i][e.zero.j]);
            StdOut.println();
        }
    }
}

