/* Fast.java */
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
/**
 * <b>A faster, sorting-based solution</b>. Remarkably, it is possible 
 * to solve the problem much faster than the brute-force solution described 
 * above. 
 * <p>
 * Given a point <i>p</i>, the following method determines whether <i>p</i> 
 * participates in a set of 4 or more collinear points.
 * <ul>
 * <li> Think of <i>p</i> as the origin.
 * <li> For each other point <i>q</i>, determine the slope it makes with
 *      <i>p</i>.
 * <li> Sort the points according to the slopes they makes with <i>p</i>.
 * <li> Check if any 3 (or more) adjacent points in the sorted order have 
 *      equal slopes with respect to <i>p</i>. If so, these points, together 
 *      with <i>p</i>, are collinear.
 * </ul>
 * Applying this method for each of the <i>N</i> points in turn yields an 
 * efficient algorithm to the problem. The algorithm solves the problem 
 * because points that have equal slopes with respect to <i>p</i> are 
 * collinear, and sorting brings such points together. 
 * The algorithm is fast because the bottleneck operation is sorting.
 */
public class Fast {
    public static void main(String[] args) {
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
