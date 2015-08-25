/*************************************************************************
 *  Compilation:  javac Q3BSTdbg.java
 *  Execution:    java Q3BSTdbg
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/32bst/tinyST.txt  
 *
 *  A symbol table implemented with a binary search tree.
 * 
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java Q3BSTdbg < tinyST.txt
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

import java.util.NoSuchElementException;
import java.util.Comparator;

public class Q3BSTdbg<Key extends Comparable<Key>, Value> {
    private int putLevel = 0;
    private int getLevel = 0;
    private Node root;             // root of Q3BSTdbg
    private Comparator<Key> comparator;
    private Interval1Ddbg zeroInt1D;
    private double currEndpoint;

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree
        private double endpoint;

        public Node(Key key, Value val, int N, double e) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.endpoint = e;
            StdOut.printf("Node: %s %s\n", val, key);
        }

        public int compareTo(Key x) {
            return comparator.compare(this.key, x);
        }
    }

    // Constructor
    public Q3BSTdbg(Comparator<Key> cmp) {
        this.comparator = cmp;
        this.zeroInt1D = new Interval1Ddbg(0.0, 0.0);
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in Q3BSTdbg
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in Q3BSTdbg rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

   /***********************************************************************
    *  Search Q3BSTdbg for given key, and return associated value if found,
    *  return null if not found
    ***********************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with the given key, or null if no such key exists
    public Value get(Key key) {
        getLevel = -1;
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        getLevel++;
        if (x == null) return null;
        // int cmp = key.compareTo(x.key);
        int cmp = x.compareTo(key);
        StdOut.printf("key:%s val:%s cmp:%d level %d\n",
            x.key, x.val, cmp, getLevel);
        if (cmp > 0) {
            StdOut.println("put: go left");
            return get(x.left, key);
        }
        else if (cmp < 0) {
            StdOut.println("get: go right");
            return get(x.right, key);
        }
        else              return x.val;
    }

   /***********************************************************************
    *  Insert key-value pair into Q3BSTdbg
    *  If key already exists, update with new value
    ***********************************************************************/
    public void put(Key key, Value val) {
        putLevel = -1;
        currEndpoint = 0.0;
        if (zeroInt1D.getClass() == key.getClass()) {
            currEndpoint = ((Interval1Ddbg)key).right();
            StdOut.println("put: currEndpoint " + currEndpoint);
        }

        if (val == null) { delete(key); return; }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val) {
        putLevel++;
        if (x == null) {
            StdOut.printf("new: %d ", putLevel);
            return new Node(key, val, 1, currEndpoint);
        }
        // int cmp = key.compareTo(x.key);
        int cmp = x.compareTo(key);
        if (cmp > 0) { 
            StdOut.println("put: go left");
            x.left  = put(x.left,  key, val);
        }
        else if (cmp < 0){
            StdOut.println("put: go right");
            x.right = put(x.right, key, val); 
        }
        else {            x.val   = val;
            StdOut.printf("put: %s %s level %d", x.val, key, putLevel);
        }
        if (x.endpoint < currEndpoint) {
            x.endpoint = currEndpoint;
            StdOut.printf("put: %s %s level %d", x.val, key, putLevel);
            StdOut.println(" endpoint " + x.endpoint);
        }
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

   /***********************************************************************
    *  Delete
    ***********************************************************************/

    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
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
        if (isEmpty()) 
            throw new NoSuchElementException("Symbol table underflow");
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


    // height of this Q3BSTdbg (one-node tree has height 0)
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
    *  Check integrity of Q3BSTdbg data structure
    *************************************************************************/
    private boolean check() {
        if (!isQ3BSTdbg())            StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) 
                          StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isQ3BSTdbg() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since
    //       order is strict
    private boolean isQ3BSTdbg() {
        return isQ3BSTdbg(root, null, null);
    }

    // is the tree rooted at x a Q3BSTdbg with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isQ3BSTdbg(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isQ3BSTdbg(x.left, min, x.key) && isQ3BSTdbg(x.right, x.key, max);
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
       /*
        Q3BSTdbg<String, Integer> st = new Q3BSTdbg<String, Integer>();
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
