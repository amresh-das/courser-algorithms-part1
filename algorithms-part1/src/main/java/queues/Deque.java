package queues;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first;
    private Node last;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(final Item item) {
        if (item == null) throw new IllegalArgumentException("Item must not be null");
        final Node node = new Node(item);
        if (size == 0) {
            last = node;
            first = node;
        } else {
            first.prev = node;
            node.next = first;
            first = node;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Item must not be null");
        final Node node = new Node(item);
        if (size == 0) {
            last = node;
            first = node;
        } else {
            node.prev = last;
            last.next = node;
            last = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException("No elements found in queues.Deque");
        final Node oldFirst = first;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        oldFirst.next = null;
        oldFirst.prev = null;
        size--;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException("No elements found in queues.Deque");
        final Node oldLast = last;
        last = oldLast.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        oldLast.prev = null;
        oldLast.next = null;
        size--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(this);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator(final Deque<Item> deque) {
            current = deque.first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException("No more elements");
            final Node temp = current;
            current = current.next;
            return temp.item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove Not supported");
        }
    }

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(final Item item) {
            this.item = item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        final Deque<String> deque = new Deque<String>();
        deque.addFirst("A");
        deque.addFirst("B");
        deque.addFirst("C");
        deque.addFirst("A");
        deque.addLast("D");
        deque.addLast("E");
        deque.addLast("E");



        StdOut.printf("Expected size: 7, Actual: %d%n", deque.size());
        StdOut.printf("Removed First: %s%n", deque.removeFirst());
        StdOut.printf("Expected size: 6, Actual: %d%n", deque.size());
        StdOut.printf("Removed Last: %s%n", deque.removeLast());
        StdOut.printf("Expected size: 5, Actual: %d%n", deque.size());

        if (!deque.isEmpty()) {
            for (String s : deque) {
                StdOut.print(s);
            }
        }
    }

}