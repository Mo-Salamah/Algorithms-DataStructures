import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private int head = 0;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        // initiate length to 1 for better, easier doubleSize() implementation
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("You can't add a null argument to Randomized Queue!");

        if (head == queue.length) {
            queue = doubleSize(queue);
            // head changed after resizing, size did not
            head = size;
            queue[head] = item;
            head++;
            size++;
        } else {
            queue[head] = item;
            size++;
            head++;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new java.util.NoSuchElementException("The RandomizedQueue is empty. There are no items to dequeue.");
        int randomInt = 0;
        Item item = null;
        // choose element at random. We need the loop because the queue has some full and some empty elements in no particular order
        while (item == null) {
            randomInt = StdRandom.uniform(queue.length);
            item = queue[randomInt];
        }
        queue[randomInt] = null;
        // The minimum size a Randomized Queue can take is 1
        if (size - 1 == queue.length / 4 && size != 1) {

            queue = quarterSize(queue);// stopped here
            size--;
            head = size;
            return item;
        } else {
            size--;
            return item;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new java.util.NoSuchElementException("The RandomizedQueue is empty. There are no items to sample.");
        // identical code to choosing an element at random in dequeue()
        int randomInt;
        Item item = null;
        // choose element at random. We need the loop because the queue has some full and some empty elements in no particular order
        while (item == null) {
            randomInt = StdRandom.uniform(queue.length);
            item = queue[randomInt];
        }
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        // size is the number of items currently in the randomizedQueue,
        // so this array is the minimum possible size
        Item[] IteArr = (Item[]) new Object[size];
        int counter = 0;
        int current = 0;
        boolean finished = false;
        RandomizedQueue<Item> IteRandoQue = new RandomizedQueue<Item>();


        public RandomizedQueueIterator() {

            while (size >= 1 && !finished) {
                if (size == 1) finished = true;
                IteArr[counter++] = dequeue();
            }
            // at this stage counter = size (original; before dequeue()s)
            for (int i = 0; i < counter; i++) {
                RandomizedQueue.this.enqueue(IteArr[i]);
            }

        }

        public boolean hasNext() {
            // does equality only suffice here? Will this return false once, and never called again after that?
            return current < IteArr.length;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("There are no more elements to view.");
            return IteArr[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Removing items is not supported! Please consider the dequeue() function.");
        }

    }


    // return array with double the size of the original array
    private Item[] doubleSize(Item[] oldQueue) {
        int oldLength = oldQueue.length;
        // counter is used so that new queue doesn't have empty indicies between items
        int counter = 0;
        Item[] queue = (Item[]) new Object[2 * oldLength];
        // iterate over oldQueue to copy its contents
        for (int i = 0; i < oldLength; i++) {
            if (oldQueue[i] != null) {
                queue[counter] = oldQueue[i];
                counter++;
            }
        }
        // returned queue is twice the size and has no empty indicies where index <= size (the variable)
        return queue;
    }

    private Item[] quarterSize(Item[] oldQueue) {
        int oldLength = oldQueue.length;
        int counter = 0;
        Item[] queue = (Item[]) new Object[oldLength / 4];
        // iterate over oldQueue to copy its contents
        for (int i = 0; i < oldLength; i++) {
            if (oldQueue[i] != null) {
                queue[counter] = oldQueue[i];
                counter++;
            }
        }
        return queue;
    }


    // unit testing (required)
    public static void main(String[] args) {
        // Testing
        RandomizedQueue<Integer> ranQue = new RandomizedQueue<Integer>();

        // testing isEmpty() and size()
        System.out.println("Should show true: " + ranQue.isEmpty());
        System.out.println("Show show 0: " + ranQue.size());

        ranQue.enqueue(1);

        System.out.println("Should show false: " + ranQue.isEmpty());
        System.out.println("Show show 1: " + ranQue.size());

        // further testing enque()
        ranQue.enqueue(2);
        ranQue.enqueue(3);
        ranQue.enqueue(4);

        System.out.println("Should show false: " + ranQue.isEmpty());
        System.out.println("Show show 4: " + ranQue.size());

        // testing iterator()
        // Iterator ite = ranQue.iterator();
        System.out.println("The following should print 1, 2, 3, 4 each in a seprate line: ");
        for (Integer i : ranQue)
            System.out.println(i);

        // testing dequeue()
        System.out.println("Should be a random item with the dequeue: " + ranQue.dequeue());
        System.out.println("Should be a random item with the dequeue: " + ranQue.dequeue());
        System.out.println("Should be a random item with the dequeue: " + ranQue.dequeue());
        System.out.println("Should be a random item with the dequeue: " + ranQue.dequeue());

        System.out.println("Should show true: " + ranQue.isEmpty());
        System.out.println("Show show 0: " + ranQue.size());

        // testing sample()
        ranQue.enqueue(8);
        System.out.println("Should be a random item with the dequeue (8 is the only item): " + ranQue.sample());
        System.out.println("Should be a random item with the dequeue (8 is the only item): " + ranQue.sample());


    }

}













