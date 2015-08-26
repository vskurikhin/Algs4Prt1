/* KdTree.java */
/**
 * @author skurvikn
 *
 */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int N = 0;
    // private Comparator<Point2D> oddComparator;
    // private Comparator<Point2D> evenComparator;
    // private int level = 0;

    private static class Node {
        private Point2D p;
        private Node left;
        private Node right;
        public Node(Point2D p) {
            this.p     = p;
            this.left  = null;
            this.right = null;
        }
    }

    private Node root;

    public KdTree() {
        this.root = null;
        Point2D zero = new Point2D(0.0, 0.0);
        // oddComparator = zero.Y_ORDER;
        // evenComparator = zero.X_ORDER;
    }

    public boolean isEmpty() {
        return (0 == this.N);
    }

    public int size() {
        return this.N;
    }

    public void insert(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        int level = -1;
        root = put(root, p, -1);
        this.N++;
    }

    private Node put(Node x, Point2D p, int level) {
        level++;
        if (null == x) {
            return new Node(p);
        }
        int cmp;
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(x.p, p);
        } else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
        }
        if (cmp > 0) {
            x.left  = put(x.left,  p, level);
        } else if (cmp < 0) {
            x.right = put(x.right, p, level);
        } else {
        }
        return x;
    }

    public boolean contains(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        int level = -1;
        return contains(root, p, -1);
    }

    private boolean contains(Node x, Point2D p, int level) {
        level++;
        if (null == x) {
            return false;
        }
        int cmp;
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(x.p, p);
        } else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
        }
        if (cmp > 0) {
            return contains(x.left,  p, level);
        } else if (cmp < 0) {
            return contains(x.right, p, level);
        } else {
            return true;
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        int level = -1;
        Queue<Point2D> queue = new Queue<Point2D>();
        if (null == rect) {
            throw new NullPointerException();
        }
        range(rect, queue, root, level);
        return queue;
    }

    private void range(RectHV rect, Queue<Point2D> queue, Node x, int level) {
        if (null == x) return;
        level++;
        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
            range(rect, queue, x.left,  level);
            range(rect, queue, x.right, level);
            level--;
            return;
        }
        int cmp = 0;
        Point2D lb = new Point2D(rect.xmin(), rect.ymin());
        Point2D ru = new Point2D(rect.xmax(), rect.ymax());
        if (0 == (level % 2)) {
            if (Point2D.X_ORDER.compare(x.p, ru) > 0) {
                cmp = 1; // go left
            } else if (Point2D.X_ORDER.compare(x.p, lb) < 0) {
                cmp = 2; // go right
            } else if (Point2D.X_ORDER.compare(x.p, lb) > 0) {
                cmp = 3; // go left and go right
            }
        } else {
            if (Point2D.Y_ORDER.compare(x.p, ru) > 0) {
                cmp = 1; // go left
            } else if (Point2D.Y_ORDER.compare(x.p, lb) < 0) {
                cmp = 2; // go right
            } else if (Point2D.Y_ORDER.compare(x.p, lb) > 0) {
                cmp = 3; // go left and go right
            }
        }
        if (1 == cmp) {
            range(rect, queue, x.left,  level);
        } else if (2 == cmp) {
            range(rect, queue, x.right, level);
        } else if (3 == cmp) {
            range(rect, queue, x.left,  level);
            range(rect, queue, x.right, level);
        }
        level--;
    }

    public Point2D nearest(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        int level = -1;
        return nearest(root, p, root.p, Double.MAX_VALUE, level);
    }

    private Point2D nearest(Node x, Point2D p, Point2D n, double m, int level) {
        level++;
        if (null == x)
            throw new NullPointerException();
        int cmp;
        double minDist = m;
        Point2D closest = n;
        double dist = p.distanceTo(x.p);
        if (dist < minDist) {
            minDist = dist;
            closest = x.p;
        }
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(x.p, p);
        } else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
        }
        if (cmp > 0 && null != x.left) {
            return nearest(x.left, p, closest, minDist, level);
        } else if (cmp < 0 && null != x.right) {
            return nearest(x.right, p, closest, minDist, level);
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
        level++;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        RectHV rectLeft  = new RectHV(rect.xmin(), rect.ymin(), 
                                      rect.xmax(), rect.ymax());
        RectHV rectRight = new RectHV(rect.xmin(), rect.ymin(), 
                                      rect.xmax(), rect.ymax());
        if (0 == (level % 2)) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.p.x(), rect.ymin(), x.p.x(), rect.ymax());
            rectLeft  = new RectHV(rect.xmin(), rect.ymin(), 
                                   x.p.x(), rect.ymax());
            rectRight = new RectHV(x.p.x(), rect.ymin(), 
                                   rect.xmax(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(rect.xmin(), x.p.y(), rect.xmax(), x.p.y());
            rectLeft  = new RectHV(rect.xmin(), rect.ymin(), 
                                   rect.xmax(), x.p.y());
            rectRight = new RectHV(rect.xmin(), x.p.y(), 
                                   rect.xmax(), rect.ymax());
        }
        draw(x.left,  rectLeft,  level);
        draw(x.right, rectRight, level);
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
