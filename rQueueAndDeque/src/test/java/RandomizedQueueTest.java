import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class RandomizedQueueTest {

	private static final String STRING = "a string";
	private static final String STRING2 = "another string";
	private RandomizedQueue<String> queue;
	
	@Before
	public void setup() {
		queue = new RandomizedQueue<String>();
		
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testThrowsErrorOnDequeueWhenEmpty() {
		queue.dequeue();
	}
	
	@Test (expected = NullPointerException.class)
	public void testThrowsErrorWhenNullIsAdded() {
		queue.enqueue(null);
	}
	
	@Test
	public void testIsEmptyIsTrueWhenEmpty() {
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testIsNotEmptyWhenNotEmpty() {
		queue.enqueue(STRING);
		assertFalse(queue.isEmpty());
	}
	
	@Test
	public void testCanRetrieveItemWithDequeue() {
		queue.enqueue(STRING);
		assertEquals(STRING, queue.dequeue());
	}
	
	@Test
	public void testCanRetrieveTwoItemsWithDequeue() {
		queue.enqueue(STRING);
		queue.enqueue(STRING2);
		List<String> returns = new ArrayList<String>();
		returns.add(queue.dequeue());
		returns.add(queue.dequeue());
		assertTrue(returns.contains(STRING));
		assertTrue(returns.contains(STRING2));
	}
	
	@Test
	public void testQueueReturnsInDifferentOrders() {
		boolean orderOneFound = false;
		boolean orderTwoFound = false;
		for (int i = 0; i < 20; i++) {
			List<String> results = new ArrayList<String>();
			queue = new RandomizedQueue<String>();
			queue.enqueue(STRING);
			queue.enqueue(STRING2);
			results.add(queue.dequeue());
			results.add(queue.dequeue());
			if (STRING.equals(results.get(0)) && STRING2.equals(results.get(1))) {
				orderOneFound = true;
			} else if (STRING.equals(results.get(1)) && STRING2.equals(results.get(0))) {
				orderTwoFound = true;
			}
			if (orderOneFound && orderTwoFound) {
				break;
			}
		}
		assertTrue(orderOneFound && orderTwoFound);
	}
	
	@Test
	public void testSizeIsZeroWhenEmpty() {
		assertEquals(0, queue.size());
	}
	
	@Test
	public void testSizeGrowsWithEnqueues() {
		queue.enqueue(STRING);
		assertEquals(1,queue.size());
		queue.enqueue(STRING2);
		assertEquals(2,queue.size());
	}
	
	@Test
	public void testSizeShrinksWithDequeues() {
		queue.enqueue(STRING);
		queue.enqueue(STRING2);
		queue.dequeue();
		assertEquals(1,queue.size());
		queue.dequeue();
		assertEquals(0, queue.size());
	}
	
	@Test
	public void testQueueResizesWhenLotsOfItemsAdded() {
		queue = spy(queue);
		int toAdd = RandomizedQueue.INITIAL_CAPACITY + 1;
		for (int i = 0; i < toAdd; i++) {
			queue.enqueue(Integer.toString(i)); 
		}
		verify(queue).resize();
	}
	
	@Test
	public void testQueueCompressesWhenManyItemsRemoved() {
		queue = spy(queue);
		int toAdd = RandomizedQueue.INITIAL_CAPACITY + 1;
		int toRemove = (int) (RandomizedQueue.INITIAL_CAPACITY * (1 - RandomizedQueue.RESIZE_DOWN_RATIO)) ;
		for (int i = 0; i < toAdd; i++) {
			queue.enqueue(Integer.toString(i)); 
		}
		for (int i = 0; i < toRemove; i++) {
			queue.dequeue();
		}
		
		verify(queue).resize();
		verify(queue).compress();
	}
	
	@Test
	public void testQueueDoesNotCompressWhenFewItemsRemoved() {
		queue = spy(queue);
		int toAdd = RandomizedQueue.INITIAL_CAPACITY + 1;
		int toRemove = (int) (RandomizedQueue.INITIAL_CAPACITY * (1 - RandomizedQueue.RESIZE_DOWN_RATIO)) -1 ;
		for (int i = 0; i < toAdd; i++) {
			queue.enqueue(Integer.toString(i)); 
		}
		for (int i = 0; i < toRemove; i++) {
			queue.dequeue();
		}
		
		verify(queue).resize();
		verify(queue, never()).compress();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testSampleThrowsErrorWhenEmpty() {
		queue.sample();
	}
	
	@Test
	public void testSampleReturnsASingleItem() {
		queue.enqueue(STRING);
		assertEquals(STRING,queue.sample());
	}
	
	@Test
	public void testSampleReturnsInconsistentItems() {
		queue.enqueue(STRING);
		queue.enqueue(STRING2);
		int stringOneCount = 0;
		int stringTwoCount = 0;
		int numSamples = 100;
		for (int i = 0; i < numSamples; i++) {
			String result = queue.sample();
			if (STRING.equals(result)) {
				stringOneCount++;
			}else if (STRING2.equals(result)) {
				stringTwoCount++;
			}
		}
		assertEquals(numSamples, stringOneCount + stringTwoCount);
		assertTrue(stringOneCount > 0);
		assertTrue(stringTwoCount > 0);
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testIteratorThrowsErrorOnRemove() {
		queue.iterator().remove();
	}
	
	@Test
	public void testIteratorDoesNotHaveNextWhenEmpty() {
		assertFalse(queue.iterator().hasNext());
	}
	
	@Test
	public void testIteratorHasNextWhenNotEmpty() {
		queue.enqueue(STRING);
		assertTrue(queue.iterator().hasNext());
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testIteratorNextThrowsErrorWhenEmpty() {
		queue.iterator().next();
	}
	
	@Test
	public void testIteratorReturnsAThing() {
		queue.enqueue(STRING);
		assertEquals(STRING, queue.iterator().next());
	}
	
	@Test(timeout=1000)
	public void testIteratorReturnsAllItems() {
		Set<String> results = new HashSet<String>();
		int toAdd = 20;
		for (int i = 0; i < toAdd; i++) {
			queue.enqueue(Integer.toString(i));
		}
		Iterator<String> iter = queue.iterator();
		while (iter.hasNext()) {
			results.add(iter.next());
		}
		assertEquals(toAdd, results.size());
	}
	
	@Test
	public void testIteratorDoesNotReturnNulls() {
		Set<String> results = new HashSet<String>();
		int toAdd = 25;		
		for (int i = 0; i < toAdd; i++) {
			queue.enqueue(Integer.toString(i));
		}
		int toRemove = 5;
		for (int i = 0; i < toRemove; i++) {
			queue.dequeue();
		}
		Iterator<String> iter = queue.iterator();
		while (iter.hasNext()) {
			String next = iter.next();
			assertNotNull(next);
			results.add(next);
		}
		assertEquals(toAdd - toRemove, results.size());
	}
	
	@Test
	public void testIteratorReturnsInRandomOrder() {
		int toAdd = 25;		
		for (int i = 0; i < toAdd; i++) {
			queue.enqueue(Integer.toString(i));
		}
		Iterator<String> iter = queue.iterator();
		int numSame = 0;
		for (int i = 0; i < toAdd; i++) {
			if (Integer.toString(i).equals( iter.next())) {
				numSame++;
			}
		}
		assertTrue(numSame < 10);
	}
	
	@Test
	public void testIteratorsReturnInDifferentOrders() {
		int toAdd = 25;		
		for (int i = 0; i < toAdd; i++) {
			queue.enqueue(Integer.toString(i));
		}
		List<String> result1 = new ArrayList<String>();
		List<String> result2 = new ArrayList<String>();
		Iterator<String> iter1 = queue.iterator();
		Iterator<String> iter2 = queue.iterator();
		for (int i = 0; i < queue.size(); i++) {
			result1.add(iter1.next());
			result2.add(iter2.next());
		}
		boolean different = false;
		for (int i = 0; i < toAdd; i++) {
			if (! result1.get(i).equals(result2.get(i))) {
				different = true;
				break;
			}
		}
		assertTrue(different);
	}

}
