/* KdTree.java */
/******************************************************************************
 * @author Victor N. Skurikhin
 *
 *****************************************************************************/

/* _unless_ 
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
 _endunless_ */

/******************************************************************************
 * Programming Assignment 5: Kd-Trees. Write a data type to represent a set
 * of points in the unit square (all points have x- and y-coordinates between
 * 0 and 1) using a <i>2d-tree</i> to support efficient <i>range search</i>
 * (find all of the points contained in a query rectangle) and
 * <i>nearest neighbor</i> search (find a closest point to a query point).
 * 2d-trees have numerous applications, ranging from classifying astronomical
 * objects to computer animation to speeding up neural networks to mining data
 * to image retrieval.
 *****************************************************************************/

/******************************************************************************
 * Corner cases.  Throw a java.lang.NullPointerException if any argument is
 * null. Performance requirements.  Your implementation should support insert()
 * and contains() in time proportional to the logarithm of the number of points
 * in the set in the worst case; it should support nearest() and range() in
 * time proportional to the number of points in the set.
 *****************************************************************************/

/******************************************************************************
 * 2d-tree implementation. Write a mutable data type KdTree.java that uses
 * a 2d-tree to implement the same API (but replace PointSET with KdTree).
 * A <i>2d-tree</i> is a generalization of a BST to two-dimensional keys.
 * The idea is to build a BST with points in the nodes, using the x- and y-
 * coordinates of the points as keys in strictly alternating sequence.
 *****************************************************************************/

public class KdTree {

    // KdTree helper node data type
    private static class Node {
        private Point2D p;          // key 2-d point
        private Node left, right;   // links to left and right subtrees
        private int N;              // number of nodes in subtree
        public Node(Point2D p) {
            this.p     = p;
            this.left  = null;
            this.right = null;
            this.N = 1;
        }
    }

    private Node root;              // root of the KdTree

    // construct an empty KdTree of points
    public KdTree() {
        this.root = null;
    }

    /*************************************************************************
     *  Size methods
     *************************************************************************/

    // return number of 2-d points
    public int size() {
        return size(root);
    }

    // is this KdTree empty?
    public boolean isEmpty() {
        if (null == root) return true;
        return (0 == root.N);
    }

    // number of node in subtree rooted at x; 0 if x is null
    /**
     * @param x     - current node
     */
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    /*************************************************************************
     * Search and insert. The algorithms for search and insert are similar
     * to those for BSTs, but at the root we use the x-coordinate
     * (if the point to be inserted has a smaller x-coordinate than the point
     * at the root, go left; otherwise go right); then at the next level, we
     * use the y-coordinate (if the point to be inserted has a smaller y-
     * coordinate than the point in the node, go left; otherwise go right);
     * then at the next level the x-coordinate, and so forth. 
     *************************************************************************/

    // add the point to the set (if it is not already in the set)
    /**
     * @param p     - point
     */
    public void insert(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        int level = -1;
        root = put(root, p, -1);
    }

    // helper method for compare two 2-D points
    /**
     * @param a     - 1st point
     * @param b     - 2nd point
     * @param level - current level
     */
    private int compare2Points(Point2D a, Point2D b, int level) {
        int cmp;
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(a, b);
            if (0 == cmp)
                cmp = Point2D.Y_ORDER.compare(a, b);
        } else {
            cmp = Point2D.Y_ORDER.compare(a, b);
            if (0 == cmp)
                cmp = Point2D.X_ORDER.compare(a, b);
        }
        return cmp;
    }

    // helper method for insert point
    /**
     * @param x     - current node
     * @param p     - query point
     * @param level - current level
     */
    private Node put(Node x, Point2D p, int level) {
        int l = level + 1;
        if (null == x)
            return new Node(p);
        int cmp = compare2Points(x.p, p, l);
        if (cmp > 0)
            x.left  = put(x.left,  p, l);
        else if (cmp < 0)
            x.right = put(x.right, p, l);
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    /*************************************************************************
     * The prime advantage. Of a 2d-tree over a BST is that it supports
     * efficient implementation of range search and nearest neighbor search.
     * Each node corresponds to an axis-aligned rectangle in the unit square,
     * which encloses all of the points in its subtree. The root corresponds
     * to the unit square; the left and right children of the root corresponds
     * to the two rectangles split by the x-coordinate of the point at the
     * root; and so forth.
     *************************************************************************/

    // does the set contain point p? 
    /**
     * @param p     - point
     */
    public boolean contains(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        if (isEmpty())
            return false;
        int level = -1;
        return contains(root, p, -1);
    }

    // helper method for contains
    /**
     * @param x     - current node
     * @param p     - query point
     * @param level - current level
     */
    private boolean contains(Node x, Point2D p, int level) {
        int l = level + 1;
        if (null == x)
            return false;
        int cmp = compare2Points(x.p, p, l);
        if (cmp > 0)
            return contains(x.left,  p, l);
        else if (cmp < 0)
            return contains(x.right, p, l);
        else
            return p.equals(x.p);
    }

    // all points that are inside the rectangle
    /**
     * @param rect  - query rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        int level = -1;
        Queue<Point2D> queue = new Queue<Point2D>();
        if (null == rect)
            throw new NullPointerException();
        if (isEmpty())
            return queue;
        range(rect, queue, root, level);
        return queue;
    }

    /*************************************************************************
     * Range search. To find all points contained in a given query rectangle,
     * start at the root and recursively search for points in <i>both</i>
     * subtrees using the following <i>pruning rule</i>: if the query rectangle
     * does not intersect the rectangle corresponding to a node, there is no
     * need to explore that node (or its subtrees).
     * A subtree is searched only if it might contain a point contained in
     * the query rectangle.
     *************************************************************************/
    // helper method for range
    /**
     * @param rect  - query rectangle
     * @param x     - current node
     * @param p     - query point
     * @param level - current level
     */
    private void range(RectHV rect, Queue<Point2D> queue, Node x, int level) {
        if (null == x) return;
        int l = level + 1;
        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
            range(rect, queue, x.left,  l);
            range(rect, queue, x.right, l);
            return;
        }
        Point2D lb = new Point2D(rect.xmin(), rect.ymin());
        Point2D ru = new Point2D(rect.xmax(), rect.ymax());
        int cmp_ru = compare2Points(x.p, ru, l);
        int cmp_lb = compare2Points(x.p, lb, l);
        if (cmp_ru > 0)                     // go left
            range(rect, queue, x.left,  l);
        else if (cmp_lb < 0)                // go right
            range(rect, queue, x.right, l);
        else if (cmp_lb > 0) {              // go left and go right
            range(rect, queue, x.left,  l);
            range(rect, queue, x.right, l);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    /**
     * @param p     - query point
     */
    public Point2D nearest(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        if (isEmpty())
            return null;
        int level = -1;
        return nearest(root, p, root.p, Double.MAX_VALUE, level);
    }

    // helper method for nearest
    private boolean needCheck(Point2D p, Point2D xp, double mdist, int level) {
        if (0 == (level % 2)) {
            if (p.distanceTo(new Point2D(xp.x(), p.y())) < mdist)
                return true;
        } else {
            if (p.distanceTo(new Point2D(p.x(), xp.y())) < mdist)
                return true;
        }
        return false;
    }

    /*************************************************************************
     * Nearest neighbor search. To find a closest point to a given query
     * point, start at the root and recursively search in <i>both</i> subtrees
     * using the following <i>pruning rule</i>: if the closest point discovered
     * so far is closer than the distance between the query point and
     * the rectangle corresponding to a node, there is no need to explore that
     * node (or its subtrees).
     * That is, a node is searched only if it might contain a point that is
     * closer than the best one found so far.
     * The effectiveness of the pruning rule depends on quickly finding a
     * nearby point.
     * To do this, organize your recursive method so that when there are two
     * possible subtrees to go down, you always choose 
     * <i>the subtree that is on the same side of the splitting line as
     * the query point</i> as the first subtree to explore—the closest point
     * found while exploring the first subtree may enable pruning of the second
     * subtree.
     *************************************************************************/
    // helper method for nearest
    /**
     * @param x     - current node
     * @param p     - query point
     * @param n     - current closest point
     * @param level - current level
     */
    private Point2D nearest(Node x, Point2D p, Point2D n, double m, int level) {
        if (null == x)
            throw new NullPointerException();
        int l = level + 1;
        double minDist = m;
        Point2D closest = n;
        double dist = p.distanceTo(x.p);
        if (dist < minDist) {
            minDist = dist;
            closest = x.p;
        }
        int cmp = compare2Points(x.p, p, l);
        if (cmp > 0) {
            boolean otherSideCheck = false;
            if (null != x.left) {
                closest = nearest(x.left, p, closest, minDist, l);
                minDist = Math.min(minDist, p.distanceTo(closest));
                if (null != x.right)
                    otherSideCheck = needCheck(p, x.p, minDist, l);
            } else if (null != x.right)
                otherSideCheck = true;
            if (otherSideCheck)
                closest = nearest(x.right, p, closest, minDist, l);
        } 
        if (cmp < 0) {
            boolean otherSideCheck = false;
            if (null != x.right) {
                closest = nearest(x.right, p, closest, minDist, l);
                minDist = Math.min(minDist, p.distanceTo(closest));
                if (null != x.left)
                    otherSideCheck = needCheck(p, x.p, minDist, l);
            } else if (null != x.left)
                otherSideCheck = true;
            if (otherSideCheck)
                closest = nearest(x.left, p, closest, minDist, l);
        }
        return closest;
    }

    /*
     * draw all of the points to standard draw
     */
    public void draw() {
        int level = -1;
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        draw(root, rect, level);
    }

    private void draw(Node x, RectHV rect, int level) {
        if (x == null) {
            return;
        }
        int l = level + 1;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        RectHV rectLeft;
        RectHV rectRight;
        if (0 == (l % 2)) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(x.p.x(), rect.ymin(), x.p.x(), rect.ymax());
            rectLeft  = new RectHV(rect.xmin(), rect.ymin(),
                                   x.p.x(), rect.ymax());
            rectRight = new RectHV(x.p.x(), rect.ymin(),
                                   rect.xmax(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(rect.xmin(), x.p.y(), rect.xmax(), x.p.y());
            rectLeft  = new RectHV(rect.xmin(), rect.ymin(),
                                   rect.xmax(), x.p.y());
            rectRight = new RectHV(rect.xmin(), x.p.y(),
                                   rect.xmax(), rect.ymax());
        }
        draw(x.left,  rectLeft,  l);
        draw(x.right, rectRight, l);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
