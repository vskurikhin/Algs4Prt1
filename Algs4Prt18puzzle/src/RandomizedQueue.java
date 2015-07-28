/* RandomizedQueue.java */
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

/*
 *
 * @author skurvikn
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.lang.reflect.Array;

public class RandomizedQueue<Item> implements Iterable<Item> {
/* _if_ 
    private final String delimeter = "=======";
    private final static int MAX_INT_VAL = 536870912;
 _endif_ */
    private int iSize;
    private static class Node<Item> {
        private Item item;
/* _if_ 
        private Node<Item> next;
        private Node<Item> prev;
 _endif_ */
    }
    private Node<Item> nodeFirst;
    private Node<Item> nodeLast;
    private Node<Item>[] arrNodes;
    private int iArrNodesSize;

    // construct an empty randomized queue
 /* _if_ 
    @SuppressWarnings("unchecked")
 _endif_ */
    public RandomizedQueue() {
        nodeFirst = null;
        nodeLast = null;
        iSize = 0;
        iArrNodesSize = 2;
        arrNodes = (Node<Item>[]) new Node[iArrNodesSize];
    }

    // is the queue empty?
    public boolean isEmpty() { return (null == nodeFirst); }

    // return the number of items on the queue
    public int size() { return iSize; }

    // resize the underlying array
    private void resize(int max) {
        iArrNodesSize = max;
/* _if_ 
        @SuppressWarnings("unchecked")
 /* _endif_ */
        Node<Item>[] arrTemp = (Node<Item>[]) new Node[max];
        int j = 0;
        for (int i = 0; i < arrNodes.length; ++i) {
            arrTemp[j] = arrNodes[i];
            if (null != arrTemp[j]) {
                j++;
            }
            if (j == max)
                break;
        }
        arrNodes = null;
        arrNodes = arrTemp;
    }

    // add the item
    public void enqueue(Item item) {
        if (null == item) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> nodeTemp = nodeLast;
        if (isEmpty()) {
            nodeLast = new Node<Item>();
            nodeFirst = nodeLast;
/* _if_ 
            nodeLast.prev = null;
 _endif_ */
        } else {
            nodeLast = new Node<Item>();
/* _if_ 
            nodeLast.prev = nodeTemp;
            nodeTemp.next = nodeLast;
 _endif_ */
        }
        nodeLast.item = item;
/* _if_ 
        nodeLast.next = null;
 _endif_ */
        if (arrNodes.length == iSize) {
            resize(2*arrNodes.length);
        }
        arrNodes[iSize] = nodeLast;
        iSize++;
    }

    private int selectRandom() {
        int iRandom = StdRandom.uniform(iSize);
        int i = iRandom;
        int j = iRandom;
/* _if_ 
            StdOut.printf("iSize = %d, iRandom = %d\n", iSize, iRandom);
 _endif_ */
        while (null == arrNodes[iRandom] && iSize > 0) {
            if (i < (arrNodes.length - 1)) {
                ++i;
                if (null != arrNodes[i]) {
                    iRandom = i;
                    break;
                }
            }
            if (j > 0) {
                --j;
                if (null != arrNodes[j]) {
                    iRandom = j;
                    break;
                }
            }
        }
        return iRandom;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        int iRandom = selectRandom();
/* _if_ 
            StdOut.printf("iSize = %d, iRandom = %d\n", iSize, iRandom);
 _endif_ */
        Node<Item> nodeTemp = arrNodes[iRandom];
        Item itemTemp = nodeTemp.item;
/* _if_ 
        if (null != nodeTemp.next) {
            nodeTemp.next.prev = nodeTemp.prev;
        }
        if (null != nodeTemp.prev) {
            nodeTemp.prev.next = nodeTemp.next;
        }
 _endif_ */
        if (iRandom >= 0) {
            nodeTemp = null;
            arrNodes[iRandom] = arrNodes[iSize - 1];
            arrNodes[iSize - 1] = null;
        }
        iSize--;
        if (iSize > 0 && iSize == arrNodes.length/4) {
            resize(arrNodes.length/2);
        }
        if (0 == iSize) {
            nodeFirst = null;
            nodeLast = null;
        }
        return itemTemp;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        int iRandom = selectRandom();
        return arrNodes[iRandom].item;
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<itrItem> implements Iterator<itrItem> {
        private int index;
        private int[] arrOrder;
        private Node<itrItem>[] array;

        public ListIterator(Node<itrItem>[] first) {
            array = first;
            index = 0;
            if (iSize < 1) return;
            arrOrder = new int[iSize];
            for (int i = 0; i < iSize; ++i) {
                arrOrder[i] = i;
            }
            StdRandom.shuffle(arrOrder);
        }

        public boolean hasNext() { return index < iSize; }
        public void remove()     { throw new UnsupportedOperationException(); }

        public itrItem next() {
            if (!hasNext()) throw new NoSuchElementException();
            itrItem item = array[arrOrder[index++]].item;
            return item;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(arrNodes);
    }


/* _if_ 
    public void printAllNodesDown() {
        boolean whileTrigger = false;
        Node<Item> e = nodeFirst;
        while (e != null) {
            StdOut.println(delimeter);
            StdOut.printf("e.pntr: %b\n", e);
            StdOut.printf("e.item: \"%s\"\n", e.item);
            StdOut.printf("e.next: %s\n",
                          (null != e.next ? "linked" : "nolink"));
            StdOut.printf("e.prev: %s\n",
                          (null != e.prev ? "linked" : "nolink"));
            e = e.next;
            whileTrigger = true;
        }
        if (whileTrigger) {
            StdOut.println(delimeter);
        }
    }
 _endif_ */
    // unit testing
/* _if_ 
    public static void main(String[] args) {
        int upperBound = 16;
        if (args.length > 0) {
            upperBound = Integer.parseInt(args[0]);
        }
        int N = StdRandom.uniform(upperBound);
        StdOut.println("==== UNIT TEST 0 Random ====");
        StdOut.printf("N = %d\n", N);
        RandomizedQueue<Integer> rq0 = new RandomizedQueue<Integer>();
        for (int i = 0; i < N; ++i) {
            int t = StdRandom.uniform(5);
            switch (t) {
                case 0: // enqueue(item)
                    StdOut.println("=== begin enqueue(item) ===");
                    int v = StdRandom.uniform(65535);
                    rq0.enqueue(v);
                    StdOut.printf("    rq.enqueue(%d)\n", v);
                    StdOut.println("=== end enqueue(item) ===");
                    break;                   
                case 1: // ListIterator
                    StdOut.println("=== begin ListIterator ===");
                    for (Integer I : rq0) {
                        StdOut.printf("    rq Iterate: %d\n", I);
                    }
                    StdOut.println("=== end ListIterator ===");
                    break;                   
                case 2: // dequeue()
                    StdOut.println("=== begin dequeue() ===");
                    StdOut.printf("rq.dequeue() = %d\n", rq0.dequeue());
                    StdOut.println("=== end dequeue() ===");
                    break;
                case 3: // isEmpty()
                    StdOut.println("=== begin isEmpty() ===");
                    StdOut.printf("    rq.isEmpty() = %b\n", rq0.isEmpty());
                    StdOut.println("=== end isEmpty() ===");
                    break;
                case 4: // size()
                    StdOut.println("=== begin size() ===");
                    StdOut.printf("    rq.size() = %d\n", rq0.size());
                    StdOut.println("=== end size() ===");
                    break;
            }            
        }
        StdOut.println("==== UNIT TEST 1 Stress ====");
        RandomizedQueue<Integer> rq1 = new RandomizedQueue<Integer>();
        StdOut.println("=== begin isEmpty() ===");
        StdOut.printf("    rq.isEmpty() = %b\n", rq1.isEmpty());
        StdOut.println("=== end isEmpty() ===");
        StdOut.println("=== begin size() ===");
        StdOut.printf("    rq.size() = %d\n", rq0.size());
        StdOut.println("=== end size() ===");
        StdOut.println("=== begin enqueue(item) ===");
        for (int i = 1; i < MAX_INT_VAL; ++i) { // Integer.MAX_VALUE
            int v = StdRandom.uniform(65535);
            rq1.enqueue(v);
            StdOut.printf("    rq.enqueue(%d)\n", v);
        }
        StdOut.println("=== end enqueue(item) ===");
        StdOut.println("=== begin size() ===");
        StdOut.printf("    rq.size() = %d\n", rq0.size());
        StdOut.println("=== end size() ===");
        StdOut.println("=== begin ListIterator ===");
        for (Integer I : rq1) {
            StdOut.printf("    rq Iterate: %d\n", I);
        }
        StdOut.println("=== end ListIterator ===");
        StdOut.println("=== begin dequeue() ===");
        for (int i = 0; i < MAX_INT_VAL; ++i) { // Integer.MAX_VALUE
            StdOut.printf("rq.dequeue() = %d\n", rq1.dequeue());
        }
        StdOut.println("=== end dequeue() ===");
        StdOut.println("=== begin size() ===");
        StdOut.printf("    rq.size() = %d\n", rq0.size());
        StdOut.println("=== end size() ===");
        StdOut.println("=== begin isEmpty() ===");
        StdOut.printf("    rq.isEmpty() = %b\n", rq1.isEmpty());
        StdOut.println("=== end isEmpty() ===");
    }
 _endif_ */
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
