package queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node head;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item must not be null");
        if (head == null) {
            head = new Node(item);
        } else {
            final Node oldHead = head;
            head = new Node(item);
            head.next = oldHead;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException("Queue is empty.");
        int index = StdRandom.uniform(size);
        Node prev;
        Node curr;
        if (index == 0) {
            curr = head;
            head = head.next;
            curr.next = null;
        } else {
            prev = head;
            curr = head.next;
            index--;
            while (index > 0) {
                prev = curr;
                curr = curr.next;
                index--;
            }
            prev.next = curr.next;
            curr.next = null;
        }
        size--;
        return curr.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException("Queue is empty.");
        int index = StdRandom.uniform(size);
        Node curr = head;
        while (index > 0) {
            curr = curr.next;
            index--;
        }
        return curr.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(head, size);
    }
    private class Node {

        Item item;
        Node next;

        public Node(final Item item) {
            this.item = item;
        }
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int taken;
        private Item[] items;

        public RandomizedQueueIterator(final Node head, final int size) {
            Node curr = head;
            items = (Item[]) new Object[size];
            int index = 0;
            while (curr != null) {
                items[index++] = curr.item;
                curr = curr.next;
            }
            this.taken = 0;
        }

        public boolean hasNext() {
            return taken != items.length;
        }

        public Item next() {
            if (size == taken) throw new NoSuchElementException("No more elements");
            int index = StdRandom.uniform(size);
            while (items[index % items.length] == null) {
                index++;
            }
            Item item = items[index % items.length];
            items[index % items.length] = null;
            taken++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        final RandomizedQueue<String> queue = new RandomizedQueue<String>();
        final String[] items = {"A", "B", "C", "D", "E"};
        for (final String s : items) {
            queue.enqueue(s);
        }
        StdOut.printf("Expected size: 5, Actual: %d%n", queue.size());
        for (String s : queue) {
            StdOut.printf("Item: %s%n", s);
        }
        StdOut.printf("Dequed: %s%n", queue.dequeue());
        StdOut.printf("Dequed: %s%n", queue.dequeue());
        StdOut.printf("Dequed: %s%n", queue.dequeue());

        StdOut.printf("Expected size: 2, Actual: %d%n", queue.size());
        for (String s : queue) {
            StdOut.printf("Item: %s%n", s);
        }
        StdOut.printf("Expected Not Empty, Actual: %s%n", queue.isEmpty());
        StdOut.printf("Sampled: %s%n", queue.sample());
        StdOut.printf("Expected size: 2, Actual: %d%n", queue.size());
        StdOut.printf("Dequed: %s%n", queue.dequeue());
        StdOut.printf("Dequed: %s%n", queue.dequeue());
        StdOut.printf("Expected Empty, Actual: %s%n", queue.isEmpty());
    }

}