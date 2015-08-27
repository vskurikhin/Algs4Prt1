/* Algs4Prt1KdTrees.java */
/* $Date$
 * $Id$
 * $Version: 0.1$
 * $Revision: 1$
 */

import java.util.regex.*;

/**
 *
 * @author skurvikn
 */
public class Algs4Prt1KdTrees {

    private final static KdTree tree = new KdTree();

    private static Point2D insertPoint(double x, double y) {
        Point2D p = new Point2D(x, y);
        tree.insert(p);
        return p;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
/*
        Point2D a = insertPoint(0.1, 0.5);
        Point2D b = insertPoint(0.2, 0.5);
        Point2D c = insertPoint(0.1, 0.3);
        Point2D d = insertPoint(0.7, 0.9);

        StdOut.println(tree.contains(a));
        StdOut.println(tree.contains(b));
        StdOut.println(tree.contains(c));
        StdOut.println(tree.contains(d));

        Point2D p = insertPoint(0.7, 0.2);
        Point2D q = insertPoint(0.5, 0.4);
        Point2D r = insertPoint(0.2, 0.3);
        Point2D s = insertPoint(0.4, 0.7);
        Point2D t = insertPoint(0.9, 0.6);
        Point2D x = insertPoint(0.9, 0.6);

        StdOut.printf("tree.size() = %d\n", tree.size());
        Point2D nearest = tree.nearest(new Point2D(0.7, 0.2));
        StdOut.println(p.equals(nearest));
        nearest = tree.nearest(new Point2D(0.51, 0.4));
        StdOut.printf("%s == %s\n", q, nearest);
        StdOut.println(q.equals(nearest));
*/
        // TODO code application logic here
        /*
        Pattern p0 = Pattern.compile("^\\s*");
        Pattern p1 = Pattern.compile("\\s+");
        KdTree kdt = new KdTree();
        RectHV rect = new RectHV(-1, -1, 1, 1);
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String[] parts0 = p0.split(StdIn.readLine());
            String[] parts1 = p1.split(parts0[parts0.length - 1]);
            double x = Double.parseDouble(parts1[0]);
            double y = Double.parseDouble(parts1[1]);
            Point2D p = new Point2D(x, y);
            kdt.insert(p);
        }
        StdOut.println(kdt.contains(new Point2D(0, 0)));
        StdOut.println(kdt.contains(new Point2D(20, 37)));
        for (Point2D p : kdt.range(rect)) {
            StdOut.println(p.toString());
        }
<<<<<<< HEAD
        Point2D nearest = kdt.nearest(new Point2D(2.0, 1.0));
        StdOut.printf("== %s\n", nearest);
/*
=======
        */

>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            kdtree.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            // StdDraw.setPenRadius(.03);
            // StdDraw.setPenColor(StdDraw.RED);
            // brute.nearest(query).draw();
            // StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenRadius(.02);
            StdDraw.setPenColor(StdDraw.YELLOW);
            kdtree.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
        }
<<<<<<< HEAD
        */
=======
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
