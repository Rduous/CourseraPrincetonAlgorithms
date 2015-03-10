package dataStructures.symbolTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dataStructures.OrderedSymbolTable;


public class RedBlackBST<K extends Comparable<K>,V> implements OrderedSymbolTable<K, V> {

	private static boolean RED = true;
	private static boolean BLACK = false;
	
	Node<K,V>  root = null;
	
	private int size = 0;
	
	public void insert(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Cannot insert with null key");
		}
		if (value == null) {
			throw new NullPointerException("Cannot insert a null value");
		}
		root = insert(key, value, root);
		size++;
	}

	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("Cannot get with null key");
		}
		Node<K,V> x = root;
		while (x != null) {
			int c = key.compareTo(x.key);
			if (c < 0) { x = x.left;}
			else if (c > 0) { x = x.right; }
			else { return x.value; }
		}
		return null;
	}
	
	

	public void remove(K key) {
		// TODO Auto-generated method stub
		
	}

	public K min() {
		// TODO Auto-generated method stub
		return null;
	}

	public K max() {
		// TODO Auto-generated method stub
		return null;
	}

	public K floor(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	public K ceiling(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	public int rank(K key) {
		// TODO Auto-generated method stub
		return 0;
	}

	public K select(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Collection<K> keysInOrder() {
		Collection<K> keys = new ArrayList<K>();
		preorder(keys, root);
		return keys;
	}

	private void preorder(Collection<K> keys, Node<K, V> node) {
		if (node == null) {
			return;
		}
		if (node.left != null) {
			preorder(keys, node.left);
		}		
		keys.add(node.key);
		if (node.right != null) {
			preorder(keys, node.right);
		}
	}

	private Node<K, V> insert(K key, V value, Node<K, V> node) {
		if (node == null) {
			return new Node<K, V>(key, value);
		}
		int c = key.compareTo(node.key);
		if (c < 0) {
			node.left = insert(key, value, node.left);
		} else if (c > 0) {
			node.right = insert(key, value, node.right);
		} else {
			node.value = value;
		}
		if (isRed(node.right) && !isRed(node.left)) { node = rotateLeft(node); }
		if (isRed(node.left) && isRed(node.left.left) ) { node = rotateRight(node); }
		if (isRed(node.left) && isRed(node.right)) {flipColors(node);}
		
		return node;
	}
	
	private Node<K, V> rotateRight(Node<K,V> h) {
		assert isRed(h.left);
		Node<K,V> x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}
	
	private Node<K,V> rotateLeft(Node<K,V> h) {
		assert isRed(h.right);
		Node<K,V> x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}
	
	private void flipColors(Node<K,V> h) {
		assert !isRed(h);
		assert isRed(h.left);
		assert isRed(h.right);
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}
	
	private boolean isRed(Node<K,V> n) {
		return n != null && n.color == RED;
	}
	
	private Node<K,V> search(K key, Node<K,V> node) {
		if (node == null) {
			return null;
		}
		int c = key.compareTo(node.key);
		if (c < 0) {
			return search(key, node.left);
		} else if (c > 0) {
			return search(key, node.right);
		} else {
			return node;
		}
	}

	static class Node<Key, Value>  {
		boolean color = true;
		Key key;
		Value value;
		Node<Key, Value> left = null;
		Node<Key, Value> right = null;
		public Node(Key k, Value v) {
			key = k;
			value = v;
		}
		
	}
	
	
}
