/* Point.java */
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
 * <b>The problem</b>. Given a set of N distinct points in the plane, 
 * draw every (maximal) line segment that connects a subset of 4 
 * or more of the points.
 */

/**
 * <b>Point data type</b>. Create an immutable data type Point that represents 
 * a point in the plane by implementing the following API:
 */

import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope to this point
    /**
     * The <tt>SLOPE_ORDER</tt> comparator should compare points by the slopes 
     * they make with the invoking point (x0, y0).
     * <p>
     * Formally, the point <em>(x1, y1)</em> 
     * is less than the point <em>(x2, y2)</em> if and only if the slope 
     * <em>(y1 − y0) / (x1 − x0)</em> is less than the slope
     * <em>(y2 − y0) / (x2 − x0)</em>.
     * Treat horizontal, vertical, and degenerate line segments as in 
     * the <tt>slopeTo()</tt> method.
     */
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    private class SlopeOrder implements Comparator<Point> {
        // YOUR DEFINITION HERE
        public int compare(Point p1, Point p2) { 
            /* YOUR CODE HERE */
            return 0;
        }
    }

    private final int x; // x coordinate
    private final int y; // y coordinate

    // construct the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // draw this point
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }
   
    // draw the line segment from this point to that point
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
   
    // string representation
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // is this point lexicographically smaller than that point?
    // comparing y-coordinates and breaking ties by x-coordinates
    /**
     * The <tt>compareTo()</tt> method should compare points by their 
     * y-coordinates, breaking ties by their x-coordinates.
     * <p>
     * Formally, the invoking point 
     * <em>(x0, y0)</em> is less than the argument point <em>(x1, y1)</em>
     * if and only if either <em>y0 < y1</em> or if <em>y0 = y1</em> 
     * and <em>x0 < x1</em>.
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        return 0;
    }
   
    // the slope between this point and that point
    /** 
     * The <tt>slopeTo()</tt> method should return the slope between 
     * the invoking point <em>(x0, y0)</em> and the argument point 
     * <em>(x1, y1)</em>, which is given by the formula 
     * <em>(y1 − y0) / (x1 − x0)</em>.
     * <p>
     * Treat the slope of a horizontal line segment as positive zero; 
     * treat the slope of a vertical line segment as positive infinity; 
     * treat the slope of a degenerate line segment 
     * (between a point and itself) as negative infinity.
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        return 0.0;
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
