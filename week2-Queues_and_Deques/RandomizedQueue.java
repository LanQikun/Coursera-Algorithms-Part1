package week2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int count;

    public RandomizedQueue() {
        arr = (Item[]) new Object[2];
        count = 0;
    }

    private void resize(int size) {
        Item[] newArr = Arrays.copyOf(arr, size);
        arr = newArr;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item shouldn't be null");
        }
        if (count == arr.length) {
            resize(2 * arr.length);
        }
        arr[count++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }

        int index = StdRandom.uniform(count);
        Item item = arr[index];
        arr[index] = arr[count - 1];
        arr[count - 1] = null;
        count--;
        if (count > 0 && count == arr.length / 4) {
            resize(arr.length / 2);
        }
        return item;

    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        return arr[StdRandom.uniform(count)];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private Item[] items;
        private int index;

        public ArrayIterator() {
            items = Arrays.copyOf(arr, count);
            StdRandom.shuffle(items);
            index = count - 1;
        }

        public boolean hasNext() {
            return index >= 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[index--];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for(int i=0; i<10; i++){
            queue.enqueue(i);
        }
        assert(queue.size() == 10);
        
        System.out.println("sample:");
        for(int i=0; i<10; i++){
            System.out.println(queue.sample());
        }

        System.out.println("iter:");
        Iterator<Integer> iter = queue.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
        
        for(int i=0; i<5; i++){
            queue.dequeue();
        }
        assert(queue.size() == 5);
        
            
    }
}