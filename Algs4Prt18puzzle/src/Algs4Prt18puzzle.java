/* Algs4Prt18puzzle.java */
/* $Date$
 * $Id$
 * $Version: 0.1$
 * $Revision: 1$
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skurvikn
 */
public class Algs4Prt18puzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BSTdbg<String, Integer> st = new BSTdbg<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        st.get("84");

        for (String s : st.levelOrder())
            StdOut.print(s + " " );
        StdOut.println();

        for (int i = 0; i < args.length; ++i) {
            StdOut.printf("st.delete(args[%d] = %s)\n", i, args[i]);
            st.delete(args[i]);
        }

        for (String s : st.levelOrder())
            StdOut.print(s + " " );
        StdOut.println();
        /*
        String[] a = StdIn.readAllStrings();
        ShellSrt.sort(a);
        ShellSrt.show(a);
        String[] a = StdIn.readAllStrings();
        SelectionSrt.sort(a);
        SelectionSrt.show(a);
        int N = 101;
        MaxPQ<Integer> pq = new MaxPQ<Integer>();
        for (int i = 0 ; i < N; ++i) {
            pq.insert(i);
        }
        pq.show(); */
        /*
        int N = -1;
        String in[] = new String[10];
        in[++N] = "82";
        in[++N] = "72";
        in[++N] = "59";
        in[++N] = "61";
        in[++N] = "70";
        in[++N] = "34";
        in[++N] = "22";
        in[++N] = "18";
        in[++N] = "35";
        in[++N] = "32";
        MaxPQ<String> pq = new MaxPQ<String>(in);
        StdOut.println("pq.show()");
        pq.show();
        pq.insert("76");
        pq.insert("67");
        pq.insert("84");
        StdOut.println("pq.insert()");
        StdOut.println("pq.insert()");
        StdOut.println("pq.insert()");
        StdOut.println("pq.show()");
        pq.show();
        N = -1;
        in[++N] = "98";
        in[++N] = "95";
        in[++N] = "76";
        in[++N] = "94";
        in[++N] = "83";
        in[++N] = "72";
        in[++N] = "45";
        in[++N] = "12";
        in[++N] = "35";
        in[++N] = "56"; 
        MaxPQ<String> pi = new MaxPQ<String>(in);
        StdOut.println("pi.show()");
        pi.show();
        pi.delMax();
        pi.delMax();
        pi.delMax();
        StdOut.println("pi.delMax()");
        StdOut.println("pi.delMax()");
        StdOut.println("pi.delMax()");
        StdOut.println("pi.show()");
        pi.show();
        */
        // TODO code application logic here
        // String[] a = StdIn.readAllStrings();
        // Quick3waySrt.sort(a);
        // Quick3waySrt.show(a);
        // QuickSrt.sort(a);
        // QuickSrt.show(a);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
