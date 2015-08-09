/*************************************************************************
 *  Compilation:  javac ShellSrt.java
 *  Execution:    java ShellSrt < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/21sort/tiny.txt
 *                http://algs4.cs.princeton.edu/21sort/words3.txt
 *   
 *  Sorts a sequence of strings from standard input using shellsort.
 *
 *  Uses increment sequence proposed by Sedgewick and Incerpi.
 *  The nth element of the sequence is the smallest integer >= 2.5^n
 *  that is relatively prime to all previous terms in the sequence.
 *  For example, incs[4] is 41 because 2.5^4 = 39.0625 and 41 is
 *  the next integer that is relatively prime to 3, 7, and 16.
 *   
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java ShellSrt < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *    
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *  
 *  % java ShellSrt < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *
 *************************************************************************/

/**
 *  The <tt>ShellSrt</tt> class provides static methods for sorting an
 *  array using ShellSrtsort with Knuth's increment sequence (1, 4, 13, 40, ...).
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class ShellSrt {
    private static int countPhase = 0;
    // private static int countSort = 0;
    private static final String PREFIX = "ShellSrt";
    private static final String SUFIX = "Q2";
    private static final String EXT = ".txt";

    // This class should not be instantiated.
    private ShellSrt() { }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        int c  = 0;
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        String filename;

        // 3x+1 increment sequence:  1, 4, 13, 40, 121, 364, 1093, ... 
        int h = 1;
        while (h < N/3) h = 3*h + 1; 

        while (h >= 1) {
            c1++; c++;
            /*
            filename = String.format("ShellSrt.sort." + SUFIX 
                                     + ".c_%05d_c1_%d.c2_%d.c3_%d.txt", 
                                     c, c1, c2, c3);
            showOut(a, filename);
            */
            // h-sort the array
            for (int i = h; i < N; i++) {
                c2++; c++;
                /*
                filename = String.format("ShellSrt.sort." + SUFIX 
                                         + ".c_%05d_c1_%d.c2_%d.c3_%d.txt", 
                                         c, c1, c2, c3);
                showOut(a, filename);
                */
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) {
                    c3++; c++;
                    exch(a, j, j-h);
                    // StdOut.printf("c: %d; h: %d; i: %d; j: %d\n", c, h, i, j);
                    /*
                    filename = String.format("ShellSrt.sort." + SUFIX 
                                             + ".c_%05d_c1_%d.c2_%d.c3_%d.txt", 
                                             c, c1, c2, c3);
                    showOut(a, filename);
                    */
                }
            }
            filename = String.format(PREFIX + ".countPhase%d." + "h%d."
                                     + SUFIX + EXT, countPhase, h);
            showOut(a, filename);
            assert isHsorted(a, h); 
            h /= 3;
        }
        assert isSorted(a);
    }



   /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***********************************************************************
    *  Check if array is sorted - useful for debugging
    ***********************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is the array h-sorted?
    private static boolean isHsorted(Comparable[] a, int h) {
        for (int i = h; i < a.length; i++)
            if (less(a[i], a[i-h])) return false;
        return true;
    }

    // print array to standard output
    public static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    // print array to standard output
    private static void showOut(Comparable[] a, String filename) {
        boolean trigger = false;
        Out out = new Out(filename);
        if (a.length > 0) {
            out.print(a[0]);
            trigger = true;
        }
        for (int i = 1; i < a.length; i++) {
            out.print(" " + a[i]);
            trigger = true;
        }
        if (trigger) {
            out.println();
        }
        out.close();
    }

    private static void showOutln(Comparable[] a, String filename) {
        Out out = new Out(filename);
        for (int i = 0; i < a.length; i++) {
            out.println(a[i]);
        }
        out.close();
    }
    /**
     * Reads in a sequence of strings from standard input; ShellSrtsorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        ShellSrt.sort(a);
        show(a);
    }

}
