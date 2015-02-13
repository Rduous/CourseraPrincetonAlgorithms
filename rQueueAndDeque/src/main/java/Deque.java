

import java.rmi.activation.UnknownObjectException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	
	Node firstNode = null;
	Node lastNode = null;

	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		Node node = new Node(item);
		node.next = firstNode;
		if (isEmpty()) {
			lastNode = node;
		} else {
			firstNode.prev = node;
		}
		firstNode = node;
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		Node node = new Node(item);
		node.prev = lastNode;
		if (isEmpty()) {
			firstNode = node;
		} else {
			lastNode.next = node;
		}
		lastNode = node;
	}

	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Node oldLast = lastNode;
		lastNode = oldLast.prev;
		if (lastNode == null) {
			firstNode = null;
		} else {
			lastNode.next = null;
		}
		return oldLast.item;
	}

	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Node oldFirst = firstNode;
		firstNode = oldFirst.next;
		if (firstNode != null) {
			firstNode.prev = null;
		}
		return oldFirst.item;
	}

	public boolean isEmpty() {
		return firstNode == null;
	}
	
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new DequeIterator();
	}
	
	private class Node {
		Item item;
		Node next;
		Node prev;
		public Node(Item item) {
			this.item = item;
		}
	}
	
	private class DequeIterator implements Iterator<Item> {

		Node next;
		
		public DequeIterator() {
			next = firstNode;
		}
		
		public boolean hasNext() {
			return next != null;
		}

		public Item next() {
			if (! hasNext()) {
				throw new NoSuchElementException();
			}
			Node prev = next;
			next = prev.next;
			return prev.item;
		}
		
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
		
	}

}
