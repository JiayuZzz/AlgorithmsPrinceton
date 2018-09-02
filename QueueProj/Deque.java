import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Node pre;
        Node next;
        Item elem;

        Node(Item e) {
            elem = e;
        }

        Node(Item e, Node p, Node n) {
            pre = p;
            next = n;
            elem = e;
        }
    }

    private class DequeIter implements Iterator<Item> {
        public Node current = head.next;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item elem = current.elem;
            current = current.next;
            return elem;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /* store data in a doubly linked list with a sentinel node */
    public Deque() {
        head = new Node(null);
        tail = head;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("do not add null element!");
        }
        size++;
        head.next = new Node(item, head, head.next);
        if (size == 1) {
            tail = head.next;
            return;
        }
        head.next.next.pre = head.next;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("do not add null element!");
        }
        size++;
        tail.next = new Node(item, tail, null);
        tail = tail.next;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("deque is already empty!");
        }
        size--;
        Item elem = head.next.elem;
        head.next = head.next.next;
        if (size == 0) {
            tail = head;
            return elem;
        }
        head.next.pre = head;
        return elem;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("deque is already empty!");
        }
        size--;
        Item elem = tail.elem;
        tail = tail.pre;
        tail.next = null;
        return elem;
    }

    public Iterator<Item> iterator() {
        return new DequeIter();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        while(!StdIn.isEmpty()){
            deque.addFirst(StdIn.readInt());
            if(!StdIn.isEmpty())
            deque.addLast(StdIn.readInt());
        }
        deque.removeFirst();
        deque.removeLast();
        StdOut.println(deque.size());
        for(int i:deque){
            StdOut.print(i+" ");
        }
        StdOut.println();
        while(!deque.isEmpty()) deque.removeLast();
        StdOut.println(deque.size());
    }
}
