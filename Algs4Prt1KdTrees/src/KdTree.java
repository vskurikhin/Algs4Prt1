/* KdTree.java */
/**
 * @author skurvikn
 *
 */

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
            StdOut.printf("new: Node p %s\n", p.toString());
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
                StdOut.printf("insert: level %d ", level);
                return new Node(p);
        }
        int cmp;
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(x.p, p);
        } else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
        }
        if (cmp > 0) {
            StdOut.println("put: go left");
            x.left  = put(x.left,  p, level);
        } else if (cmp < 0) {
            StdOut.println("put: go right");
            x.right = put(x.right, p, level);
        } else {
            StdOut.println("put: hmm... equal?");
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
            StdOut.printf("contains: level %d FALSE\n", level);
            return false;
        }
        int cmp;
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(x.p, p);
        } else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
        }
        if (cmp > 0) {
            StdOut.println("contains: go left");
            return contains(x.left,  p, level);
        } else if (cmp < 0) {
            StdOut.println("contains: go right");
            return contains(x.right, p, level);
        } else {
            return true;
        }
    }

    // public ... insert(...)
    // public ... draw(...)
    // public ... nearest(...)
/*
    private enum Orientation {
        public Orientation next() {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        public Node(Point2D p) {
            this.p = p;
        }
    }
    public KdTree() {
    public boolean isEmpty() {
    public int size() {
    public void insert(Point2D p) {
    private Node put(Node x, Point2D p, Orientation orientation) {
    private int compare(Point2D p, Point2D q, Orientation orientation) {
    public boolean contains(Point2D p) {
    private boolean contains(Node x, Point2D p, Orientation orientation) {
    public void draw() {
    private void draw(Node x, Orientation orientation) {
    public Iterable<Point2D> range(RectHV rect) {
    private void findPoints(Queue<Point2D> queue, RectHV rect, Node x) {
    public Point2D nearest(Point2D p) {
    private Point2D findNearest(Node x, Point2D p, Point2D nearest,
*/
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
