/*************************************************************************
 *  Compilation:  javac Q3IntervalST.java
 *  Execution:    java Q3IntervalST
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/32bst/tinyST.txt  
 *
 *  A symbol table implemented with a binary search tree.
 * 
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java Q3IntervalST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 *************************************************************************/

import java.util.regex.*;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Q3IntervalST {
    public static void main(String[] args) {
        Pattern p0 = Pattern.compile("^\\s*");
        Pattern p1 = Pattern.compile("\\s+");
        Interval1Ddbg zero = new Interval1Ddbg(0.0, 0.0);
        Comparator<Interval1Ddbg> cmp = zero.LEFT_ENDPOINT_ORDER;
        Q3BSTdbg<Interval1Ddbg, String> st;
        st = new Q3BSTdbg<Interval1Ddbg, String>(cmp);
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String[] parts0 = p0.split(StdIn.readLine());
            String[] parts1 = p1.split(parts0[parts0.length - 1]);
            double low  = Double.parseDouble(parts1[1]);
            double high = Double.parseDouble(parts1[2]);
            Interval1Ddbg interval = new Interval1Ddbg(low, high);
            st.put(interval, parts1[0]);
        }
/*
        for (String s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));

        StdOut.println();

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s)); */
    }
}
