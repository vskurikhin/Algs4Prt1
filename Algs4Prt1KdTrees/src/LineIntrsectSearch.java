/*************************************************************************
 *  Compilation:  javac LineIntrsectSearch.java
 *  Execution:    java LineIntrsectSearch
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/32bst/tinyST.txt  
 *
 *  A symbol table implemented with a binary search tree.
 * 
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java LineIntrsectSearch < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 *************************************************************************/
import java.util.regex.*;
import java.util.NoSuchElementException;

public class LineIntrsectSearch<Key extends Comparable<Key>, Value> {
    private Node root;             // root of LineIntrsectSearch

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Value endVal;
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in LineIntrsectSearch
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in LineIntrsectSearch rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

   /***********************************************************************
    *  Search LineIntrsectSearch for given key, and return associated value
    *  if found, return null if not found
    ***********************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with the given key, or null if no such key exists
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

   /***********************************************************************
    *  Insert key-value pair into LineIntrsectSearch
    *  If key already exists, update with new value
    ***********************************************************************/
    public void put(Key key, Value val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void put(Key key, Value val, Value endVal) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val, endVal);
        assert check();
    }

    private Node put(Node x, Key key, Value val, Value endVal) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val, endVal);
        else if (cmp > 0) x.right = put(x.right, key, val, endVal);
        else            { x.val   = val; x.endVal = endVal; }
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

   /***********************************************************************
    *  Delete
    ***********************************************************************/

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else { 
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        } 
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    } 


   /***********************************************************************
    *  Min, max, floor, and ceiling
    ***********************************************************************/
    public Key min() {
        if (isEmpty()) return null;
        return min(root).key;
    } 

    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    public Key max() {
        if (isEmpty()) return null;
        return max(root).key;
    } 

    private Node max(Node x) { 
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        else return x.key;
    } 

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp <  0) return floor(x.left, key);
        Node t = floor(x.right, key); 
        if (t != null) return t;
        else return x; 
    } 

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) return null;
        else return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) { 
            Node t = ceiling(x.left, key); 
            if (t != null) return t;
            else return x; 
        } 
        return ceiling(x.right, key); 
    } 

   /***********************************************************************
    *  Rank and selection
    ***********************************************************************/
    public Key select(int k) {
        if (k < 0 || k >= size()) return null;
        Node x = select(root, k);
        return x.key;
    }

    // Return key of rank k. 
    private Node select(Node x, int k) {
        if (x == null) return null; 
        int t = size(x.left); 
        if      (t > k) return select(x.left,  k); 
        else if (t < k) return select(x.right, k-t-1); 
        else            return x; 
    } 

    public int rank(Key key) {
        return rank(key, root);
    } 

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    } 

   /***********************************************************************
    *  Range count and range search.
    ***********************************************************************/
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { 
        if (x == null) return; 
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) keys(x.left, queue, lo, hi); 
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key); 
        if (cmphi > 0) keys(x.right, queue, lo, hi); 
    } 

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }


    // height of this LineIntrsectSearch (one-node tree has height 0)
    public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    // level order traversal
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

  /*************************************************************************
    *  Check integrity of LineIntrsectSearch data structure
    *************************************************************************/
    private boolean check() {
        if (!isLineIntrsectSearch()) StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isLineIntrsectSearch() && isSizeConsistent() &&
               isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since
    // order // is strict
    private boolean isLineIntrsectSearch() {
        return isLineIntrsectSearch(root, null, null);
    }

    // is the tree rooted at x a LineIntrsectSearch with all keys strictly
    // between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isLineIntrsectSearch(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isLineIntrsectSearch(x.left, min, x.key) &&
               isLineIntrsectSearch(x.right, x.key, max);
    } 

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.N != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }


   /***************************************************************************
    *  Test client
    ***************************************************************************/
    public static void main(String[] args) { 
        MinPQ<String> pq = new MinPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readLine();
            pq.insert(item);
        }
        LineIntrsectSearch<Integer, String> ty = new LineIntrsectSearch<Integer,
                                                                     String>();
        LineIntrsectSearch<Integer, String> tx = new LineIntrsectSearch<Integer,
                                                                     String>();
        Pattern p1 = Pattern.compile("^\\s*");
        Pattern p2 = Pattern.compile("\\s+");
        while (!pq.isEmpty()) {
            String[] parts1;
            String[] parts2;
            parts1 = p1.split(pq.delMin().toString());

            parts2 = p2.split(parts1[parts1.length - 1]);
            /*
            for (int i = 0; i < parts2.length; ++i) {
                StdOut.printf("parts[%d] = %s\n", i , parts2[i]);
            }
            */
            String name = parts2[4];
            int x1 = Integer.parseInt(parts2[0]);
            int y1 = Integer.parseInt(parts2[1]);
            int x2 = Integer.parseInt(parts2[2]);
            int y2 = Integer.parseInt(parts2[3]);
            for (Integer x : tx.keys(0, x1)) {
                StdOut.printf("on %d for delete %s\n", x1, tx.get(x));
            }
            if (name.equals("A")) {
                StdOut.println("Result:");
                for (Integer y : ty.keys()) {
                    StdOut.printf("%s ", ty.get(y));
                }
                StdOut.println();
            }
            if (x1 == x2) {
                StdOut.printf("%s (%2d,%2d) -> (%2d,%2d)  [ vertical   ]\n",
                              name, x1, y1, x2, y2 );
                for (Integer y : ty.keys(y1, y2)) {
                    StdOut.printf("intersection %s with %s\n", name, ty.get(y));
                }
            } else {
                ty.put(y1, name);
                tx.put(x2, name);
                StdOut.printf("%s (%2d,%2d) -> (%2d,%2d)  [ horizontal ]\n",
                              name, x1, y1, x2, y2 );
            }
        }
        /*
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        for (String s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));

        StdOut.println();

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
        */
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
