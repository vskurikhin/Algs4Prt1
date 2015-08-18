import java.util.Comparator;
import java.util.Iterator;

/**
 * <b>Best-first search</b>. Now, we describe a solution to the problem that
 * illustrates a general artificial intelligence methodology known as the
 * <a href=
 * "http://en.wikipedia.org/wiki/A*_search_algorithm">A* search algorithm</a>.
 * We define a search node of the game to be a board, the number of moves made
 * to reach the board, and the previous search node. First, insert the initial
 * search node (the initial board, 0 moves, and a null previous search node)
 * into a priority queue. Then, delete from the priority queue the search node
 * with the minimum priority, and insert onto the priority queue all
 * neighboring search nodes (those that can be reached in one move from
 * the dequeued search node). Repeat this procedure until the search node
 * dequeued corresponds to a goal board. The success of this approach hinges
 * on the choice of <i>priority function</i> for a search node. We consider
 * two priority functions:
 */

public class Solver {

    // private Board board;
    private int moves;
    private SearchNode lastNode;

    // helper search node data type
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode prev;
    }

    // find a solution to the initial board (using the A* algorithm)
    /**
     * @param initial
     */
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

    private class SearchNodeCmp implements Comparator<SearchNode> {
        /**
         * @param a a search node
         * @param b a search node
         * @return -1, 0 or 1 <=>
         */
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

    /**
     * @param b a Board
     * @return MinPQ of search node
     */
    private MinPQ<SearchNode> initNodes(Board b) {
        SearchNodeCmp snCmp = new SearchNodeCmp();
        MinPQ<SearchNode> snpq = new MinPQ<SearchNode>(1, snCmp);
        SearchNode sn = new SearchNode();
        sn.board = b;
        sn.moves = 0;
        sn.prev = null;
        snpq.insert(sn);
        return snpq;
    }

    /**
     * @param nodes
     * @param currNode
     */
    private void addNeighbors(MinPQ<SearchNode> nodes, SearchNode currNode) {
        for (Board e : currNode.board.neighbors()) {
            /**
             * A critical optimization. Best-first search has one annoying
             * feature: search nodes corresponding to the same board are
             * enqueued on the priority queue many times. To reduce unnecessary
             * exploration of useless search nodes, when considering
             * the neighbors of a search node, don't enqueue a neighbor if its
             * board is the same as the board of the previous search node.
             */
            if (currNode.prev == null || !e.equals(currNode.prev.board)) {
                SearchNode neighborNode = new SearchNode();
                neighborNode.board = e;
                neighborNode.moves = currNode.moves + 1;
                neighborNode.prev = currNode;
                nodes.insert(neighborNode);
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
            while (curr != null) {
                solution.push(curr.board);
                curr = curr.prev;
            }
            // if (curr != null)
            //    solution.push(curr.board);
        }
        public boolean hasNext() {
            return (!solution.isEmpty());
        }
        public Board next() {
            return solution.pop();
        }
        public void remove() { }
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
