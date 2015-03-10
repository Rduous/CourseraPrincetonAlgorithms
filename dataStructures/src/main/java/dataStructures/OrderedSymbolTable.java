package dataStructures;

import java.util.Collection;



public interface OrderedSymbolTable<K, V>   {

	void insert(K key, V value);
	
	V get(K key);
	
	void remove(K key);
	
	/**
	 * @return the lowest key, or null if empty
	 */
	K min();
	
	/**
	 * @return the highest key, or null if empty
	 */
	K max();
	
	/**
	 * @param key
	 * @return the largest key that is <= "key"
	 */
	K floor(K key);
	
	/**
	 * @param key
	 * @return the smallest key that is >= "key"
	 */
	K ceiling(K key);
	
	/**
	 * @param key
	 * @return the number of keys less than "key"
	 */
	int rank(K key);
	
	/**
	 * @param n
	 * @return the key with rank n
	 */
	K select(int n);
	
	boolean isEmpty();
	
	Collection<K> keysInOrder();
}
