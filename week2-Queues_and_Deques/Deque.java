package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        public Item val;
        public Node pre;
        public Node next;

        public Node() {

        }

        public Node(Item val) {
            this.val = val;
        }
    }

    private Node head;
    private Node tail;
    private int count;

    public Deque() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.pre = head;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        link(head, new Node(item), head.next);
        count++;
    }

    private void link(Node a, Node b, Node c) {
        a.next = b;
        b.pre = a;
        b.next = c;
        c.pre = b;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        link(tail.pre, new Node(item), tail);
        count++;
    }

    public Item removeFirst() {
        if (count == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        count--;
        return remove(head.next);
    }

    private Item remove(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        return node.val;
    }

    public Item removeLast() {
        if (count == 0) {
            throw new NoSuchElementException("deque is empty");
        }
        count--;
        return remove(tail.pre);
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int left;
        private Node cur;

        public DequeIterator() {
            left = count;
            cur = head;
        }

        public boolean hasNext() {
            return left != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            left--;
            cur = cur.next;
            return cur.val;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        Deque<Integer> q = new Deque<Integer>();
        for (int i = 0; i < 10; i++) {
            q.addFirst(i);
            q.addLast(i);
        }
        assert (q.count == 20);
        
        Iterator<Integer> iter = q.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        for (int i = 0; i < 10; i++) {
            q.removeFirst();
            q.removeLast();
        }
    }
}