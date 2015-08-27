/* KdTree.java */
/**
 * @author skurvikn
 *
 */

<<<<<<< HEAD
/* _unless_ */
=======
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
<<<<<<< HEAD
/* _endunless_ */
=======
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf

public class KdTree {
    // private Comparator<Point2D> oddComparator;
    // private Comparator<Point2D> evenComparator;
    // private int level = 0;

    private static class Node {
        private Point2D p;
        private Node left;
        private Node right;
        private int N;             // number of nodes in subtree
        public Node(Point2D p) {
            this.p     = p;
            this.left  = null;
            this.right = null;
<<<<<<< HEAD
            this.N = 1;
/* _if_ 
            StdOut.printf("new: Node p %s\n", p.toString());
 _endif_ */
=======
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
        }
    }

    private Node root;

    public KdTree() {
        this.root = null;
        // Point2D zero = new Point2D(0.0, 0.0);
        // oddComparator = zero.Y_ORDER;
        // evenComparator = zero.X_ORDER;
    }

    public boolean isEmpty() {
        if (null == root) return true;
        return (0 == root.N);
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public void insert(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        int level = -1;
/* _if_ 
        StdOut.printf("insert: level %d point %s\n", level, p.toString());
 _endif_ */
        root = put(root, p, -1);
    }

<<<<<<< HEAD
    private int compare2Points(Point2D a, Point2D b, int level) {
=======
    private Node put(Node x, Point2D p, int level) {
        level++;
        if (null == x) {
            return new Node(p);
        }
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
        int cmp;
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(a, b);
            if (0 == cmp) {
                cmp = Point2D.Y_ORDER.compare(a, b);
            }
        } else {
            cmp = Point2D.Y_ORDER.compare(a, b);
            if (0 == cmp) {
                cmp = Point2D.X_ORDER.compare(a, b);
            }
        }
        return cmp;
    }

    private Node put(Node x, Point2D p, int level) {
        int l = level + 1;
        if (null == x) {
/* _if_ 
            StdOut.printf("insert: level %d ", l);
 _endif_ */
            return new Node(p);
        }
        int cmp = compare2Points(x.p, p, l);
        if (cmp > 0) {
<<<<<<< HEAD
/* _if_ 
            StdOut.println("put: go left");
 _endif_ */
            x.left  = put(x.left,  p, l);
        } else if (cmp < 0) {
/* _if_ 
            StdOut.println("put: go right");
 _endif_ */
            x.right = put(x.right, p, l);
        } else {
/* _if_ 
            StdOut.println("put: hmm... equal?");
 _endif_ */
=======
            x.left  = put(x.left,  p, level);
        } else if (cmp < 0) {
            x.right = put(x.right, p, level);
        } else {
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
        }
        x.N = 1 + size(x.left) + size(x.right);

        return x;
    }

    public boolean contains(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        int level = -1;
        return contains(root, p, -1);
    }

    private boolean contains(Node x, Point2D p, int level) {
        int l = level + 1;
        if (null == x) {
<<<<<<< HEAD
/* _if_ 
            StdOut.printf("contains: level %d not found FALSE\n", l);
 _endif_ */
=======
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
            return false;
        }
        int cmp = compare2Points(x.p, p, l);
        if (cmp > 0) {
<<<<<<< HEAD
/* _if_ 
            StdOut.printf("contains: level %d go left\n", l);
 _endif_ */
            return contains(x.left,  p, l);
        } else if (cmp < 0) {
/* _if_ 
            StdOut.printf("contains: level %d go right\n", l);
 _endif_ */
            return contains(x.right, p, l);
=======
            return contains(x.left,  p, level);
        } else if (cmp < 0) {
            return contains(x.right, p, level);
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
        } else {
/* _if_ 
            StdOut.printf("contains: level %d found TRUE\n", l);
 _endif_ */
            // equal?
            return p.equals(x.p);
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
<<<<<<< HEAD
        int l = level + 1;
        if (rect.contains(x.p)) {
/* _if_ 
            StdOut.printf("range: level %d contains %s\n",
                          l, x.p.toString());
 _endif_ */
            queue.enqueue(x.p);
            range(rect, queue, x.left,  l);
            range(rect, queue, x.right, l);
=======
        level++;
        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
            range(rect, queue, x.left,  level);
            range(rect, queue, x.right, level);
            level--;
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
            return;
        }
        int cmp = 0;
        Point2D lb = new Point2D(rect.xmin(), rect.ymin());
        Point2D ru = new Point2D(rect.xmax(), rect.ymax());
<<<<<<< HEAD
        int cmp_ru = compare2Points(x.p, ru, l);
        int cmp_lb = compare2Points(x.p, lb, l);
        if (cmp_ru > 0) {
/* _if_ 
            StdOut.printf("range: level %d X go left x.p %s; ru %s\n",
                          l, x.p.toString(), ru.toString());
 _endif_ */
            cmp = 1; // go left
        } else if (cmp_lb < 0) {
/* _if_ 
            StdOut.printf("range: level %d X go right x.p %s; lb %s\n",
                          l, x.p.toString(), lb.toString());
 _endif_ */
            cmp = 2; // go right
        } else if (cmp_lb > 0) {
/* _if_ 
            StdOut.printf("range: level %d X go left and go right"
                          + " x.p %s; lb %s\n",
                          l, x.p.toString(), lb.toString());
 _endif_ */
            cmp = 3; // go left and go right
        }
        if (1 == cmp) {
            range(rect, queue, x.left,  l);
        } else if (2 == cmp) {
            range(rect, queue, x.right, l);
        } else if (3 == cmp) {
            range(rect, queue, x.left,  l);
            range(rect, queue, x.right, l);
        }
=======
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
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
    }

    public Point2D nearest(Point2D p) {
        if (null == p)
            throw new NullPointerException();
        int level = -1;
        return nearest(root, p, root.p, Double.MAX_VALUE, level);
    }

<<<<<<< HEAD
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

    private Point2D nearest(Node x, Point2D p, Point2D n, double m, int level) {
        int l = level + 1;
        if (null == x)
            throw new NullPointerException();
=======
    private Point2D nearest(Node x, Point2D p, Point2D n, double m, int level) {
        level++;
        if (null == x)
            throw new NullPointerException();
        int cmp;
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
        double minDist = m;
        Point2D closest = n;
        double dist = p.distanceTo(x.p);
        if (dist < minDist) {
            minDist = dist;
            closest = x.p;
        }
<<<<<<< HEAD
        int cmp = compare2Points(x.p, p, l);
        if ((cmp > 0) && (null != x.left)) {
/* _if_ 
            StdOut.printf("nearest: level %d go left minDist %e\n", l, minDist);
 _endif_ */
            closest = nearest(x.left, p, closest, minDist, l);
            minDist = Math.min(minDist, p.distanceTo(closest));
/* _if_ 
            StdOut.printf("nearest: level %d min = minDist %e\n", l, minDist);
 _endif_ */
            if (needCheck(p, x.p, minDist, l) && (null != x.right)) {
/* _if_ 
                StdOut.printf("nearest: level %d go go right\n", l);
 _endif_ */
                closest = nearest(x.right, p, closest, minDist, l);
            }
        } 
        if ((cmp < 0) && (null != x.right)) {
/* _if_ 
            StdOut.printf("nearest: level %d go right\n", l);
 _endif_ */
            closest = nearest(x.right, p, closest, minDist, l);
            minDist = Math.min(minDist, p.distanceTo(closest));
            if (needCheck(p, x.p, minDist, l) && (null != x.left)) {
/* _if_ 
                StdOut.printf("nearest: level %d go go left\n", l);
 _endif_ */
                closest = nearest(x.left, p, closest, minDist, l);
            }
=======
        if (0 == (level % 2)) {
            cmp = Point2D.X_ORDER.compare(x.p, p);
        } else {
            cmp = Point2D.Y_ORDER.compare(x.p, p);
        }
        if (cmp > 0 && null != x.left) {
            return nearest(x.left, p, closest, minDist, level);
        } else if (cmp < 0 && null != x.right) {
            return nearest(x.right, p, closest, minDist, level);
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
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
<<<<<<< HEAD
        int l = level + 1;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
        RectHV rectLeft;
        RectHV rectRight;
        if (0 == (l % 2)) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.p.x(), rect.ymin(), x.p.x(), rect.ymax());
            rectLeft  = new RectHV(rect.xmin(), rect.ymin(),
                                   x.p.x(), rect.ymax());
            rectRight = new RectHV(x.p.x(), rect.ymin(),
=======
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
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
                                   rect.xmax(), rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(rect.xmin(), x.p.y(), rect.xmax(), x.p.y());
<<<<<<< HEAD
            rectLeft  = new RectHV(rect.xmin(), rect.ymin(),
                                   rect.xmax(), x.p.y());
            rectRight = new RectHV(rect.xmin(), x.p.y(),
                                   rect.xmax(), rect.ymax());
        }
        draw(x.left,  rectLeft,  l);
        draw(x.right, rectRight, l);
=======
            rectLeft  = new RectHV(rect.xmin(), rect.ymin(), 
                                   rect.xmax(), x.p.y());
            rectRight = new RectHV(rect.xmin(), x.p.y(), 
                                   rect.xmax(), rect.ymax());
        }
        draw(x.left,  rectLeft,  level);
        draw(x.right, rectRight, level);
>>>>>>> a0a3cbcb85446be64f360ed457a3714b6f77acaf
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}
/*
 * https://class.coursera.org/algs4partI-008/forum/thread?thread_id=1016
My initial implementation of nearest() was a brute force solution that did an exhaustive search of the tree and all subtrees. This, however, failed the timing tests because it was too slow.

Then I was thinking about the lecture on this topic how it said we should search a subtree "if it could contain a closer point". I took this to mean that you should search the subtree whose rectangle contains the query point.

This was much faster. I passed all the timing tests but now I'm failing some of the correctness tests. After thinking about it more and seeing some posts on here, I guess sometimes you have to search the other subtree, too. 

My solution so far looks like this:

if (the point at this node is closer than the champion) {
    set the new champion to be this point
}

if (there is a left subtree and the left subtree's rectangle contains the query point) {
    nearest(left subtree)
}

if (there is a right subtree and the right subtree's rectangle contains the query point) {
    nearest(right subtree)
}

What's the extra logic that I need to make this correct? I've been stuck for several days on this now and could use some help.
0votes received.· flag
Marc Ponchon· 23 days ago 
I have been stuck for a couple a days too on this problem. The way go get it faster is to go first in the good subtree.

To do this, organize your recursive method so that when there are two possible subtrees to go down, you always choose the subtree that is on the same side of the splitting line as the query point as the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.
The key is to choose first the subtree "on the same side of the splitting line as the query point".
You know how to insert a point, and so you should know wich side you have to go at each node across the kdtree.

Hope this help.

https://class.coursera.org/algs4partI-008/forum/thread?thread_id=986 
* 
 */
/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
