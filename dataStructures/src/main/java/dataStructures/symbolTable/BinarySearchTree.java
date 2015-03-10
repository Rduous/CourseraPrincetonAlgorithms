package dataStructures.symbolTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dataStructures.OrderedSymbolTable;

public class BinarySearchTree<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V> {

	private int size = 0;

	Node<K, V> root = null;

	public BinarySearchTree() {

	}

	public boolean isEmpty() {
		assert size >= 0;
		return size <= 0;
	}

	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("Cannot get a null key");
		}
		Node<K,V> n = search(key, root);
		if (n == null) {
			return null;
		}
		return n.value;
	}

	public void remove(K key) {
		root = remove(key, root);
		size--;
	}

	public void insert(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Cannot insert a null key");
		}
		if (value == null) {
			throw new NullPointerException("Cannot insert a null value");
		}
		root = insert(key, value, root);
		size++;
	}

	public K min() {
		Node<K,V> min = min(root);
		if (min != null) {
			return min.key;
		}
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

	private Node<K, V> insert(K key, V value, Node<K, V> node) {

		if (node == null) {
			node = new Node<K, V>(key, value);
		}
		int c = key.compareTo(node.key);
		if (c < 0) {
			node.left = insert(key, value, node.left);
		} else if (c > 0) {
			node.right = insert(key, value, node.right);
		} else {
			node.value = value;
		}
		return node;
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
	
	private Node<K, V> remove(K key, Node<K, V> node) {
		if (node == null) {
			return null;
		}
		int c = key.compareTo(node.key);
		if (c < 0) {
			node.left = remove(key, node.left);
		} else if ( c > 0 ) {
			node.right = remove(key, node.right);
		} else {
			if (node.right == null) {
				return node.left;
			}
			if (node.left == null) {
				return node.right;
			}
			
			Node<K,V> t = node;
			node = min(t.right);
			removeMin(t.right);
		}
		return node;
	}
	
	private Node<K,V> min(Node<K,V> node) {
		if (node == null) {
			return node;
		}
		if (node.left == null) {
			return node;
		}
		return (min(node.left));
	}
	
	private Node<K,V> removeMin(Node<K,V> node) {
		if (node.left == null) return node.right;
		node.left = removeMin(node.left);
		return node;
		
	}

	static class Node<Key, Value> {

		Key key;
		Value value;
		Node<Key, Value> left = null;
		Node<Key, Value> right = null;

		public Node(Key k, Value v) {
			key = k;
			value = v;
		}
	}

	public Collection<K> keysInOrder() {
		List<K> keys = new ArrayList<K>();
		preorder(keys, root);
		return keys;
	}

	private void preorder(List<K> keys, Node<K, V> node) {
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


}
