import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    //try: private Node first, last;
    private Node first = null;
    private Node last = null;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        //try deleting this constructor since it can be implicit
    }

    // is the deque empty?
    public boolean isEmpty() {
        //try: return size == 0;
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("The item you are adding is null. Consider trying with another item.");
        if (size > 0) {
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = first;
            first.prev = newFirst;
            first = newFirst;
        } else {
            Node newFirst = new Node();
            newFirst.item = item;
            first = newFirst;
            last = newFirst;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("The item you are adding is null. Consider trying with another item.");
        if (size > 0) {
            Node newLast = new Node();
            newLast.item = item;
            newLast.prev = last;
            last.next = newLast;
            last = newLast;
        } else {
            Node newLast = new Node();
            newLast.item = item;
            first = newLast;
            last = newLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0)
            throw new java.util.NoSuchElementException("There are no more elements to remove. Consider adding elements using the addFirst() or addLast() functions.");
        if (isEmpty()) {
            return null;
        }
        if (size > 1) {
            Item item = first.item;
            first = first.next;
            // avoid loitering
            first.prev = null;
            size--;
            return item;
        }
        Item item = first.item;
        first = null;
        last = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0)
            throw new java.util.NoSuchElementException("There are no more elements to remove. Consider adding elements using the addFirst() or addLast() functions.");
        if (isEmpty()) {
            return null;
        }
        if (size > 1) {
            Item item = last.item;
            last = last.prev;
            // avoid loitering
            last.next = null;
            size--;
            return item;
        }
        Item item = last.item;
        first = null;
        last = null;
        size--;
        return item;
    }


    // memory per iterator: constant
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new dequeIterator();
    }

    private class dequeIterator implements Iterator<Item> {
        Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Removing items is not supported! Please consider the dequeue() function.");
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("There are no more elements to view.");
            Item item = current.item;
            current = current.next;
            return item;
        }


    }

    // unit testing (required)
    public static void main(String[] args) {
        // test each function with a full queue and an empty queue
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(4);
        System.out.println("Printing should show: 4 Actual:" + deque.removeFirst());
        deque.addFirst(5);
        System.out.println("Printing should show: 5 Actual:" + deque.removeLast());

        deque.addFirst(4);
        deque.addFirst(3);
        deque.addLast(8);
        deque.addLast(9);
        deque.addFirst(84);
        // it should be 84, 3, 4, 8, 9
        System.out.println("Printing should show (below): 84, 3, 4, 8, 9 Actual:");
        for (Integer i : deque)
            StdOut.println(i);

        Deque<Integer> deque1 = new Deque<Integer>();
        deque1.addLast(4);
        deque1.addLast(1);
        deque1.addFirst(2);
        // it should be 2, 4, 1

        // print:
        System.out.println("Printing should show: 2 Actual:" + deque1.removeFirst());
        System.out.println("Printing should show: 1 Actual:" + deque1.removeLast());
        System.out.println("Printing should show: 4 Actual:" + deque1.removeLast());


        deque1.addFirst(1);
        deque1.removeFirst();
        // iterate over deque1; it should be empty
        System.out.println("Printing should show nothing Actual:");
        for (Integer i : deque1)
            StdOut.println(i);

        System.out.println("New line:");
        // deque should have elements: 84, 3, 4, 8, 9
        // print:
        System.out.println("Printing should show: 9 Actual:" + deque.removeLast());
        System.out.println("Printing should show: 84 Actual:" + deque.removeFirst());
        System.out.println("Printing should show: 8 Actual:" + deque.removeLast());
        System.out.println("Printing should show: 4 Actual:" + deque.removeLast());

        System.out.println("Printing should show (below): 3 Actual:");
        for (int i : deque)
            StdOut.println(i);

    }

}








