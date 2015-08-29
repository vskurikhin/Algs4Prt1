/* Deque.java */
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

/**
 *
 * @author skurvikn
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
/* _if_ */
        private final String delimeter = "=======";
/* _endif_ */
    private int iSize;
    private Node<Item> nodeFirst;
    private Node<Item> nodeLast;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        nodeFirst = null;
        nodeLast  = null;
        iSize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() { return (nodeFirst == null && nodeLast == null); }

    // return the number of items on the deque
    public int size() { return iSize; }

    // add the item to the front
    public void addFirst(Item item) {
        if (null == item) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> nodeTemp = nodeFirst;
        if (isEmpty()) {
            nodeFirst = new Node<Item>();
            nodeLast = nodeFirst;
            nodeFirst.next = null;
        } else {
            nodeFirst = new Node<Item>();
            nodeFirst.next = nodeTemp;
            nodeTemp.prev = nodeFirst;
        }
        nodeFirst.item = item;
        nodeFirst.prev = null;
        iSize++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (null == item) {
            throw new java.lang.NullPointerException();
        }
        Node<Item> nodeTemp = nodeLast;
        if (isEmpty()) {
            nodeLast = new Node<Item>();
            nodeFirst = nodeLast;
            nodeLast.prev = null;
        } else {
            nodeLast = new Node<Item>();
            nodeLast.prev = nodeTemp;
            nodeTemp.next = nodeLast;
        }
        nodeLast.item = item;
        nodeLast.next = null;
        iSize++;
    }


    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item itemTemp = nodeFirst.item;
        if (null != nodeFirst.next) {
            nodeFirst.next.prev = null;
        }
        nodeFirst = nodeFirst.next;
        iSize--;
        if (0 == iSize) {
            nodeFirst = null;
            nodeLast = null;
        }
        return itemTemp; 
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item itemTemp = nodeLast.item;
        if (null != nodeLast.prev) {
            nodeLast.prev.next = null;
        }
        nodeLast = nodeLast.prev;
        iSize--;
        if (0 == iSize) {
            nodeFirst = null;
            nodeLast = null;
        }
        return itemTemp; 
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(nodeFirst);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Itm> implements Iterator<Itm> {
        private Node<Itm> current;

        public ListIterator(Node<Itm> first) {
            current = first;
        }

        public boolean hasNext() { return current != null; }
        public void remove()     { throw new UnsupportedOperationException(); }

        public Itm next() {
            if (!hasNext()) throw new NoSuchElementException();
            Itm item = current.item;
            current = current.next; 
            return item;
        }
    }

    // unit testing
    // public static void main(String[] args) {}

/* _if_ */
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
/* _endif_ */
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
