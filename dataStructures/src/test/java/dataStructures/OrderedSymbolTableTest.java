package dataStructures;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public abstract class OrderedSymbolTableTest {

	protected OrderedSymbolTable<String, Integer> table;

	protected static String[] KEY_LIST = { "A", "B", "C", "D", "E", "F", "G", "H" };

	@Before
	public void setup() {
		table = getSymbolTable();
	}

	protected abstract OrderedSymbolTable<String, Integer> getSymbolTable();

	@Test
	public void testEmptyTableIsEmpty() {
		assertTrue("Table should start out empty", table.isEmpty());
	}

	@Test
	public void testPutMakesTableNotEmpty() {
		table.insert("key", 0);
		assertFalse("Table should not be empty after put", table.isEmpty());
	}

	@Test
	public void testPutThenRemoveLeavesEmptyTable() {
		table.insert("key", 0);
		table.remove("key");
		assertTrue("Table should be empty after equal numbers of put and remove", table.isEmpty());
	}

	@Test(expected = NullPointerException.class)
	public void testPuttingNullKeyThrowsError() {
		table.insert(null, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testPuttingNullValueThrowsError() {
		table.insert("key", null);
	}

	@Test
	public void testGetFromEmptyTableReturnsNull() {
		assertNull(table.get("key"));
	}

	@Test(expected = NullPointerException.class)
	public void testGetWithNullKeyThrowsError() {
		table.get(null);
	}

	@Test
	public void testCanGetAfterPut() {
		table.insert("key", 0);
		assertEquals(Integer.valueOf(0), table.get("key"));
	}

	@Test
	public void testCannotGetAfterPutAndRemove() {
		table.insert("key", 0);
		table.remove("key");
		assertNull(table.get("key"));
	}

	@Test
	public void testEmptyTableReturnsEmptyKeys() {
		Collection<String> keys = table.keysInOrder();
		assertEquals(0, keys.size());
	}

	@Test
	public void testKeysContainsPutItem() {
		table.insert("key", 0);
		Collection<String> keys = table.keysInOrder();
		assertTrue(keys.contains("key"));
	}

	@Test
	public void testDifferentOrdersOfInsertingThreeKeys() {
		int[][] orders = { { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 }, { 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 } };
		for (int i = 0; i < orders.length; i++) {
			table = getSymbolTable();
			insertInOrderAndCheck(orders[i], i);
		}
	}
	
	@Test
	public void testDifferentOrdersOfInserting4Keys() {
		int[][] orders = { { 0, 1, 2, 3 }, { 0, 2, 1, 3 }, { 1, 0, 2, 3 }, { 1, 2, 0, 3 }, { 2, 0, 1, 3 }, { 2, 1, 0, 3 },
				{ 0, 1, 3, 2 }, { 0, 2, 3, 1 }, { 1, 0, 3, 2 }, { 1, 2, 3, 0 }, { 2, 0, 3, 1 }, { 2, 1, 3, 0 },
				{ 0, 3, 1, 2 }, { 0, 3, 2, 1 }, { 1, 3, 0, 2 }, { 1, 3, 2, 0 }, { 2, 3, 0, 1 }, { 2, 3, 1, 0 },
				{ 3, 0, 1, 2 }, { 3, 0, 2, 1 }, { 3, 1, 0, 2 }, { 3, 1, 2, 0 }, { 3, 2, 0, 1 }, { 3, 2, 1, 0 }
		};
		for (int i = 0; i < orders.length; i++) {
			table = getSymbolTable();
			insertInOrderAndCheck(orders[i], i);
		}
	}
	
	public void testDifferentOrdersOfInsertingKeysSpreadOverRange() {
		int[][] orders = { { 0, 3, 6 }, { 0, 6, 3 }, { 3, 0, 6 }, { 3, 6, 0 }, { 6, 0, 3 }, { 6, 3, 0 } };
		for (int i = 0; i < orders.length; i++) {
			table = getSymbolTable();
			insertInOrderAndCheck(orders[i], i);
		}
	}

	private void insertInOrderAndCheck(int[] order, int orderNum) {
		for (int i = 0; i < order.length; i++) {
			table.insert(KEY_LIST[order[i]], i);
		}
		for (int i = 0; i < order.length; i++) {
			String key = KEY_LIST[order[i]];
			assertEquals("Problem getting key " + key + " in order number " + orderNum, Integer.valueOf(i), table.get(key));
		}
		List<String> expectedKeys = Arrays.asList(Arrays.copyOf(KEY_LIST, order.length));
		assertEquals(expectedKeys, table.keysInOrder());
	}
	
	@Test
	public void testDeletion() {
		for (int i = 0; i < KEY_LIST.length; i++) {
			table = getSymbolTable();
			List<String> expectedKeys = new ArrayList<String>(Arrays.asList(KEY_LIST));
			expectedKeys.remove(i);
			for (int j = 0; j < KEY_LIST.length; j++) {
				table.insert(KEY_LIST[j], j);
			}
			table.remove(KEY_LIST[i]);
			assertEquals(expectedKeys, table.keysInOrder());
		}
	}

}
