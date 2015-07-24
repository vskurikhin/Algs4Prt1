/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author skurvikn
 */
public class GenericMysteryBox<Item> {
        public Node first;
        public class Node {
            public Item item;
            public Node next;
            public Node prev;
        }
        public GenericMysteryBox() {}
}
