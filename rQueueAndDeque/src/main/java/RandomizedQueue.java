

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	protected static final int INITIAL_CAPACITY = 10;
	protected static final double RESIZE_DOWN_RATIO = 0.25;
	
	private Item[] items = (Item[]) new Object[INITIAL_CAPACITY];
	private int pointer = 0;
	private int size = 0;
	
	public RandomizedQueue() {
		
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		if (needToResize()) {
			resize();
		}
		items[pointer++] = item;
		size++;
	}

	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int toReturn = getRandomItemPointer();
		Item item = items[toReturn];
		items[toReturn] = null;
		size--;
		if (needToCompress()) {
			compress();
		}
		return item;
	}

	private int getRandomItemPointer() {
		int toReturn;
		do { 
			toReturn = StdRandom.uniform(pointer);
		}
		while (items[toReturn] == null);
		return toReturn;
	}

	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return items[getRandomItemPointer()];
	}

	public Iterator<Item> iterator() {
		return new RQIter();
	}

	protected boolean needToResize() {
		return  pointer == items.length; 
	}
	
	protected boolean needToCompress() {
		return items.length > INITIAL_CAPACITY && ((double) size) / items.length < RESIZE_DOWN_RATIO;
	}

	protected void resize() {
		Item[] oldItems = items;
		items = (Item[]) new Object[items.length * 2];
		System.arraycopy(oldItems, 0, items, 0, oldItems.length);
	}
	
	protected void compress() {
		Item[] newItems = (Item[]) new Object[items.length / 2];
		int copied = 0;
		for (int i = 0; copied < size && i < pointer; i++) {
			if (items[i] != null) {
				newItems[copied++] = items[i];
			}
		}
		items = newItems;
		pointer = size + 1;
	}
	
	private class RQIter implements Iterator<Item> {
		
		Item[] items;
		int size;
		int pointer = 0;
		
		public RQIter() {
			items = Arrays.copyOf(RandomizedQueue.this.items, RandomizedQueue.this.pointer);
			StdRandom.shuffle(items);
			size = RandomizedQueue.this.size;
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
