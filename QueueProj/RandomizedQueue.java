import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] elems;
    private int size;

    private class RandomizedQueueIter implements Iterator<Item> {
        int cnt = 0;
        // random query index sequence
        int[] querySequence;

        public RandomizedQueueIter() {
            querySequence = new int[size];
            for (int i = 0; i < size; i++) {
                querySequence[i] = i;
            }
            StdRandom.shuffle(querySequence);
        }

        @Override
        public boolean hasNext() {
            return cnt < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elems in iterator!");
            }
            return elems[querySequence[cnt++]];
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException("randomized queue iterator doesn't support remove operation!");
        }
    }

    public RandomizedQueue() {
        elems = (Item[]) new Object[2];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item elem) {
        if (elem == null) {
            throw new java.lang.IllegalArgumentException("Do not insert null!");
        }
        expand();
        elems[size++] = elem;
    }

    /* get random index,
     * replace last elem to index,
     * delete last elem */
    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("randomized queue is already empty!");
        }
        int index = StdRandom.uniform(0, size);
        Item elem = elems[index];
        elems[index] = elems[--size];
        elems[size] = null;
        shrink();
        return elem;
    }

    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("randomized queue is empty!");
        }
        int index = StdRandom.uniform(0, size);
        return elems[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIter();
    }

    private void shrink() {
        Item[] tmp;
        if (size < elems.length / 4) {
            tmp = (Item[]) new Object[elems.length / 2];
            for (int i = 0; i < size; i++) {
                tmp[i] = elems[i];
            }
            elems = tmp;
        }

    }

    private void expand() {
        if (size == elems.length) {
            Item[] tmp = (Item[]) new Object[elems.length * 2];
            for (int i = 0; i < size; i++) {
                tmp[i] = elems[i];
            }
            elems = tmp;
        }
    }
}

