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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Pattern p0 = Pattern.compile("^\\s*");
        Pattern p1 = Pattern.compile("\\s+");
        KdTree kdt = new KdTree();
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
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
