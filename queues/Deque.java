import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;  // number of elements on Dequeue
    private Node<Item> first;  // beginning of queue
    private Node<Item> last;  // end of queue

    private static class Node<Item> {
        private Item item;  // the item in the node
        private Node<Item> next;  // reference to next item
        private Node<Item> previous;  // reference to previous item
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("argument is null");
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.previous = null;
        if (isEmpty()) {
            last = first;
            first.next = null;
        }
        else {
            oldfirst.previous = first;
            first.next = oldfirst;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("argument is null");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
            last.previous = null;
        }
        else {
            oldlast.next = last;
            last.previous = oldlast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.previous = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Dequeu is empty");
        Item item = last.item;
        last = last.previous;
        n--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;  // node containing current item

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(1);
        dq.addFirst(2);
        dq.addFirst(3);
        StdOut.println("size=" + dq.size());
        StdOut.println("remove:" + dq.removeLast());
        StdOut.println("remove:" + dq.removeFirst());
        StdOut.println("size=" + dq.size());
        dq.addLast(4);
        StdOut.println("size=" + dq.size());
        StdOut.println("remove:" + dq.removeLast());
        StdOut.println(dq.isEmpty());
        StdOut.println("size=" + dq.size());
        for (int i : dq) {
            StdOut.println(i);
        }
    }
}
