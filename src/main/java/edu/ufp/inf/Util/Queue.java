package edu.ufp.inf.Util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first = null;
    private Node<Item> last = null;
    private int n = 0;

    public Queue() {
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return this.n;
    }

    public Item peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        } else {
            return this.first.item;
        }
    }

    public void enqueue(Item item) {
        Node<Item> oldlast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        if (this.isEmpty()) {
            this.first = this.last;
        } else {
            oldlast.next = this.last;
        }

        ++this.n;
    }

    public Item dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        } else {
            Item item = this.first.item;
            this.first = this.first.next;
            --this.n;
            if (this.isEmpty()) {
                this.last = null;
            }

            return item;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator var2 = this.iterator();

        while(var2.hasNext()) {
            Item item = (Item) var2.next();
            s.append(item);
            s.append(' ');
        }

        return s.toString();
    }

    public Iterator<Item> iterator() {
        return new LinkedIterator(this.first);
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue();

        while(!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!queue.isEmpty()) {
                StdOut.print((String)queue.dequeue() + " ");
            }
        }

        StdOut.println("(" + queue.size() + " left on queue)");
    }

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;

        private Node() {
        }
    }

    private class LinkedIterator implements Iterator<Item> {
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
