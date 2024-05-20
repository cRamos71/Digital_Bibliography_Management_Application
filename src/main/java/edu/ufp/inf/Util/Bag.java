//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.ufp.inf.Util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item>, Serializable {
    private Node<Item> first = null;
    private int n = 0;

    public Bag() {
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.n;
    }

    public void add(Item item) {
        Node<Item> oldfirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldfirst;
        ++this.n;
    }

    public Iterator<Item> iterator() {
        return new LinkedIterator(this.first);
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag();

        while(!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("size of bag = " + bag.size());
        Iterator var4 = bag.iterator();

        while(var4.hasNext()) {
            String s = (String)var4.next();
            StdOut.println(s);
        }

    }

    private static class Node<Item> implements Serializable {
        private Item item;
        private Node<Item> next;

        private Node() {
        }
    }

    private class LinkedIterator implements Iterator<Item>, Serializable {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            this.current = first;
        }

        public boolean hasNext() {
            return this.current != null;
        }

        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                Item item = this.current.item;
                this.current = this.current.next;
                return item;
            }
        }
    }
}
