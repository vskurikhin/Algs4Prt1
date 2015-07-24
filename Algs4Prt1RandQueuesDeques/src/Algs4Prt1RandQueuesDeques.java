/* Algs4Prt1RandQueuesDeques.java */
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

import java.util.Iterator;

public class Algs4Prt1RandQueuesDeques {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Deque<Integer> dq = new Deque<Integer>();
        StdOut.printf("dq.isEmpty() = %b\n", dq.isEmpty());
        dq.addFirst(1);
        StdOut.printf("dq.isEmpty() = %b\n", dq.isEmpty());
        StdOut.printf("dq.removeFirst() = %d\n", dq.removeFirst());
        dq.addFirst(3);
        StdOut.printf("dq.isEmpty() = %b\n", dq.isEmpty());
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
/* _if_ 
            StdOut.printf("OK: %s\n", item);
 _endif_ */
            if (null != item) {
                rq.enqueue(item);
            }
        }
        Iterator<String> iter1 = rq.iterator();
        while (iter1.hasNext()) {
            StdOut.println(iter1.next());
        }
        if (args.length != 1) {
            return;
        }
        int N = Integer.parseInt(args[0]);
        for (int i = 0; i < N; ++i) {
            StdOut.println(rq.dequeue());
        }
        Iterator<String> iter2 = rq.iterator();
        while (iter2.hasNext()) {
            StdOut.println(iter2.next());
        }
/* _if_ 
        StdOut.printf("dq.size = %d\n", rq.size());
        rq.printAllNodesDown();
 _endif_ */
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
