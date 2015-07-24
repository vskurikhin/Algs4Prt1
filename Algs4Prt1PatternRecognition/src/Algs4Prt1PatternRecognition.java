/* Algs4Prt1PatternRecognition.java */
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
public class Algs4Prt1PatternRecognition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int n = 50;

        Stack<Integer> stack = new Stack<Integer>();
        while (n > 0) {
           stack.push(n % 2);
           n = n / 2;
        }

        for (int d : stack) {
            StdOut.print(d);
        }
        StdOut.println();
        //
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
