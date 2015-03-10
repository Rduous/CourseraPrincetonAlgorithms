package dataStructures.symbolTable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import dataStructures.OrderedSymbolTable;
import dataStructures.OrderedSymbolTableTest;
import dataStructures.symbolTable.BinarySearchTree.Node;

public class BinarySearchTreeTest extends OrderedSymbolTableTest {

	@Override
	protected OrderedSymbolTable<String, Integer> getSymbolTable() {
		return new BinarySearchTree<String, Integer>();
	}

	@Test
	public void testWorstCaseHeight() {
		for (int i = 0; i < KEY_LIST.length; i++) {
			table.insert(KEY_LIST[i], i);
		}
		int height = 0;
		Node<String, Integer> n = ((BinarySearchTree<String, Integer>) table).root;
		while (n.right != null) {
			height++;
			n = n.right;
		}
		assertEquals(KEY_LIST.length - 1, height);
	}

	@Test
	public void testRandomInsertionHeight() {
		List<String> keys = new ArrayList();
		for (int i = 0; i < 200; i++) {
			keys.add( String.valueOf(i));
		}
		Collections.shuffle(keys);
		for (int i = 0; i < keys.size(); i++) {
			table.insert(keys.get(i),i);
		}

		Node<String, Integer> n = ((BinarySearchTree<String, Integer>) table).root;
		int height = countMaxHeight(n, 0);
		assertTrue((2.5*Math.log(200) / Math.log(2) ) > height);
	}

	private int countMaxHeight(Node<String, Integer> node, int i) {
		if (node == null) {
			return i - 1;
		}

		return Math.max(countMaxHeight(node.left, i + 1), countMaxHeight(node.right, i + 1));
	}

}
