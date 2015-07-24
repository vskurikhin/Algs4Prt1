/******************************************************************************
 *  Compilation:  javac Algs4_Prt1_Percolation.java
 *  Execution:  java Algs4_Prt1_Percolation < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Test Percolation algorithm.
 *
 *****************************************************************************/

/**
 *
 * @author Victor Skurikhin
 */
import java.awt.Font;
import java.lang.Runtime;

public class Algs4_Prt1_Percolation {

    // delay in miliseconds (controls animation speed)
    private static final int DELAY = 10;
    private static final int MAXCOUNT = 1000;

    // draw N-by-N percolation system
    public static void draw(Percolation perc, int N) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-.05*N, 1.05*N);
        StdDraw.setYscale(-.05*N, 1.05*N);   // leave a border to write text
        StdDraw.filledSquare(N/2.0, N/2.0, N/2.0);

        // draw N-by-N grid
        int opened = 0;
        for (int row = 1; row <= N; row++) {
            for (int col = 1; col <= N; col++) {
                if (perc.isFull(row, col)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    opened++;
                }
                else if (perc.isOpen(row, col)) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                    opened++;
                }
                else
                    StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
            }
        }

        // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(.25*N, -N*.025, opened + " open sites");
        if (perc.percolates()) StdDraw.text(.75*N, -N*.025, "percolates");
        else                   StdDraw.text(.75*N, -N*.025, "does not percolate");

    }

	public static void printRunTimeMemory(Runtime rt) {
		System.out.printf("Runtime.freeMemory() = %d\n", rt.freeMemory());
		System.out.printf("Runtime.totalMemory() = %d\n", rt.totalMemory());
	}

    public static class Node {
        public Double item;
        public Node next;
        public Node prev;
        public Node() {}
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        ShellSrt.sort(a);
        ShellSrt.show(a);
        // InsertionSrt.sort(a);
        // InsertionSrt.show(a);
        // SelectionSrt.sort(a);
        // SelectionSrt.show(a);

        /*
        Node prev = null;
        Node next = null;
        Runtime runTime = Runtime.getRuntime();
		printRunTimeMemory(runTime);
        for (int i = 0; i < MAXCOUNT; ++i) {
            prev = next;
            next = new Node();
            StdOut.printf("i:%04d:\n", i);
            if (next != null)
                next.prev = prev;
		    printRunTimeMemory(runTime);
        }
		printRunTimeMemory(runTime);
        */
        if (args.length != 9) {
            return;
        }
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system
        // StdOut.printf("N = %d\n", N);

        // turn on animation mode
        StdDraw.show(0);

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(N);
        draw(perc, N);
        StdDraw.show(DELAY);
        // int idx01 = 0;
        while (!in.isEmpty()) {
            // StdOut.printf("idx01 = %d\n", idx01++);
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            draw(perc, N);
            StdDraw.show(DELAY);
            /*
            perc.dumpVars();
            if (0 == idx01) {
                boolean res01 = perc.isFull(18, 1);
                StdOut.printf("res01 = %b\n", res01);
                if (true == res01) {
                    break;
                }
            }
            */
        }
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[1]),
                                                   Integer.parseInt(args[2]));
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", "
                                                    + ps.confidenceHi());
        //////////////////////////////////////////////////////////////////////
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
// EOF
