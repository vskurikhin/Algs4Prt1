import java.util.Comparator;
import java.util.Iterator;

public class Solver {

    // private Board board;
    private int moves;
    private SearchNode lastNode;

    private class SearchNode {
        public Board board;
        public int moves;
        public SearchNode prev;
    }

    private class SearchNodeCmp implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int aPri = a.board.manhattan() + a.moves;
            int bPri = b.board.manhattan() + b.moves;
            // return (aPri - bPri);
            if (aPri < bPri)
                return -1;
            if (aPri > bPri)
                return 1;
            return 0;
        }
    }

    private MinPQ<SearchNode> initNodes(Board b) {
        SearchNodeCmp snCmp = new SearchNodeCmp();
        MinPQ<SearchNode> snpq = new MinPQ<>(1, snCmp);
        SearchNode sn = new SearchNode();
        sn.board = b;
        sn.moves = 0;
        sn.prev = null;
        snpq.insert(sn);
        return snpq;
    }

    private void addNeighbors(MinPQ<SearchNode> nodes, SearchNode currNode) {
        for (Board e : currNode.board.neighbors()) {
            if (currNode.prev == null || !e.equals(currNode.prev.board)) {
                SearchNode neighborNode = new SearchNode();
                neighborNode.board = e;
                neighborNode.moves = currNode.moves + 1;
                neighborNode.prev = currNode;
                nodes.insert(neighborNode);
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
          throw new java.lang.NullPointerException();

        this.moves = -1;
        this.lastNode = null;

        MinPQ<SearchNode> nodes = initNodes(initial);

        while (!nodes.isEmpty()) {
            SearchNode next = nodes.delMin();
            /* debug
            StdOut.println("Solver delMin():");
            StdOut.println(next.board);
            */
            if (next.board.isGoal()) {
                this.lastNode = next;
                this.moves = next.moves;
                return;
            } else {
                addNeighbors(nodes, next);
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return (this.moves != -1);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    private class SolutionItr implements Iterator<Board> {
        private Stack<Board> solution;
        private SolutionItr() {
            solution = new Stack<Board>();
            SearchNode curr = lastNode;
            while (curr.prev != null) {
                solution.push(curr.board);
                curr = curr.prev;
            }
        }
        public boolean hasNext() {
            return (!solution.isEmpty());
        }
        public Board next() {
            return solution.pop();
        }
        public void remove() {}
    }

    private class SolutionIterable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            return new SolutionItr();
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        return new SolutionIterable();
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
