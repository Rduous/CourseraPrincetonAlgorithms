
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    protected static final int INITIAL_CAPACITY = 10;
    protected static final double RESIZE_DOWN_RATIO = 0.25;

    private Item[] items = (Item[]) new Object[INITIAL_CAPACITY];
    private int pointer = 0;

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return pointer == 0;
    }

    public int size() {
        return pointer;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (needToResize()) {
            resize(items.length * 2);
        }
        items[pointer++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int toReturn = StdRandom.uniform(pointer);
        Item item = items[toReturn];
        pointer--;
        items[toReturn] = items[pointer];
        items[pointer] = null;
        if (needToCompress()) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(pointer)];
    }

    public Iterator<Item> iterator() {
        return new RQIter();
    }

    protected boolean needToResize() {
        return pointer == items.length;
    }

    protected boolean needToCompress() {
        return items.length > INITIAL_CAPACITY
                && ((double) pointer) / items.length < RESIZE_DOWN_RATIO;
    }

    protected void resize(int newSize) {
        Item[] oldItems = items;
        items = (Item[]) new Object[newSize];
        System.arraycopy(oldItems, 0, items, 0, pointer);
    }
    private class RQIter implements Iterator<Item> {

        Item[] items;
        int size;
        int pointer = 0;

        public RQIter() {
            items = Arrays.copyOf(RandomizedQueue.this.items,
                    RandomizedQueue.this.pointer);
            StdRandom.shuffle(items);
            size = RandomizedQueue.this.pointer;
        }

        public boolean hasNext() {
            return size != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (items[pointer] == null) {
                pointer++;
            }
            size--;
            return items[pointer++];
        }

        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
}
