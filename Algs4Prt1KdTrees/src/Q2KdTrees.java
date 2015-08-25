/* Q2KdTrees.java */
/* $Date$
 * $Id$
 * $Version: 0.1$
 * $Revision: 1$
 */

/**
 *
 * @author skurvikn
 */
public class Q2KdTrees {
    private static BSTdbg<Point2D, String> bst;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Point2D zero = new Point2D(0.0, 0.0);
        bst = new BSTdbg<Point2D, String>(zero.X_ORDER, zero.Y_ORDER);
        StdOut.println("===================================");
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String name = StdIn.readString();
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p2d = new Point2D(x, y);
            StdOut.println("add: " + name + " x:" + p2d.x() + " y:" + p2d.y());
            bst.put(p2d, name);
            StdOut.println("===================================");
        }
        StdOut.println("===============================");
        for (Point2D p2d : bst.levelOrder()) {
            StdOut.println(" OK: " + bst.get(p2d) + " x:" + p2d.x() + " y:" + p2d.y());
            StdOut.println("===============================");
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
