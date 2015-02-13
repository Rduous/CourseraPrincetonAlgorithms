import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class DequeTest {

    private static final String firstString = "a string";
    private static final String secondString = "another string";
    private Deque<String> deque;

    @Before
    public void setup() {
        deque = new Deque<String>();
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNullpointerOnNullAdd() {
        deque.addLast(null);
    }

    @Test
    public void TestNoNPEOnNonNullAddLast() {
        deque.addLast(firstString);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsNPEOnNullAddFirst() {
        deque.addFirst(null);
    }

    @Test
    public void TestNoNPEOnNonNullAddFirst() {
        deque.addFirst(firstString);
    }

    @Test(expected = NoSuchElementException.class)
    public void testThrowsNoSuchEltExceptionOnRemoveLastWhenEmpy() {
        deque.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void testThrowsNoSuchEltExceptionOnRemoveFirstWhenEmpy() {
        deque.removeFirst();
    }

    @Test
    public void testReturnsAStringOnRemoveLast() {
        deque.addLast(firstString);
        assertEquals(firstString, deque.removeLast());
    }

    @Test
    public void testReturnsAStringOnRemoveFirst() {
        deque.addLast(firstString);
        assertEquals(firstString, deque.removeFirst());
    }

    @Test
    public void testReturnsAStringOnRemoveLastWithAddFirst() {
        deque.addFirst(firstString);
        assertEquals(firstString, deque.removeLast());
    }

    @Test
    public void testReturnsAStringOnRemoveFirstWithAddFirst() {
        deque.addFirst(firstString);
        assertEquals(firstString, deque.removeFirst());
    }

    @Test
    public void testReturnsSecondStringOnRemoveLast() {
        deque.addLast(firstString);
        deque.addLast(secondString);
        assertEquals(secondString, deque.removeLast());
    }

    @Test
    public void testReturnsFirstStringOnRemoveFirst() {
        deque.addLast(firstString);
        deque.addLast(secondString);
        assertEquals(firstString, deque.removeFirst());
    }

    @Test
    public void testReturnsSecondStringOnRemoveFirstWithAddFirst() {
        deque.addFirst(firstString);
        deque.addFirst(secondString);
        assertEquals(secondString, deque.removeFirst());
    }

    @Test
    public void testReturnsFirstStringOnRemoveLastWithAddFirst() {
        deque.addFirst(firstString);
        deque.addFirst(secondString);
        assertEquals(firstString, deque.removeLast());
    }

    @Test
    public void testEmptyDequeIsEmpty() {
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testNonEmptyQueueIsNotEmpty() {
        deque.addLast(firstString);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testRemoveFirstActuallyRemoves() {
        deque.addLast(firstString);
        deque.removeFirst();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testRemoveLastActuallyRemoves() {
        deque.addLast(firstString);
        deque.removeLast();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testADequeIsNonEmptyOnlyOneStringHasBeenRemoved() {
        deque.addLast(firstString);
        deque.addLast(secondString);
        deque.removeFirst();
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testCanRetrieveStringsInOrderWithAddLastRemoveFirst() {
        deque.addLast(firstString);
        deque.addLast(secondString);
        assertEquals(firstString, deque.removeFirst());
        assertEquals(secondString, deque.removeFirst());
    }

    @Test
    public void testCanRetrieveStringsInOrderWithAddFirstRemoveLast() {
        deque.addFirst(firstString);
        deque.addFirst(secondString);
        assertEquals(firstString, deque.removeLast());
        assertEquals(secondString, deque.removeLast());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastCleansUpForwardReferences() {
        deque.addLast(firstString);
        deque.addLast(secondString);
        deque.removeLast();
        deque.removeFirst();
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastCleansUpForwardReferences2() {
        deque.addFirst(firstString);
        deque.addFirst(secondString);
        deque.removeLast();
        deque.removeFirst();
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstCleansUpBackReferences() {
        deque.addLast(firstString);
        deque.addLast(secondString);
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstCleansUpBackReferences2() {
        deque.addFirst(firstString);
        deque.addFirst(secondString);
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
    }

    @Test
    public void testCanRetrieveItemsInOrderWithComboOfOperations() {
        deque.addLast(firstString);
        deque.addFirst(secondString);
        assertEquals(firstString, deque.removeLast());
        assertEquals(secondString, deque.removeFirst());
    }

    @Test
    public void testCanRetrieveItemsInOrderWithComboOfOperations2() {
        deque.addFirst(firstString);
        deque.addLast(secondString);
        assertEquals(firstString, deque.removeFirst());
        assertEquals(secondString, deque.removeLast());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemoveThrowsError() {
        deque.iterator().remove();
    }

    @Test
    public void testIteratorDoesNotHaveNextWithEmptyDeque() {
        assertFalse(deque.iterator().hasNext());
    }

    @Test
    public void testIteratorHasNextWithNonEmptyQueue() {
        deque.addFirst(firstString);
        assertTrue(deque.iterator().hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorThrowsExceptionOnNextWhenEmpty() {
        deque.iterator().next();
    }

    @Test
    public void testIteratorReturnsAnItemWhenNotEmpty() {
        deque.addFirst(firstString);
        assertEquals(firstString, deque.iterator().next());
    }

    @Test
    public void testIteratorReturnsStringsInOrder() {
        deque.addLast(firstString);
        deque.addLast(secondString);
        Iterator<String> iter = deque.iterator();
        assertEquals(firstString, iter.next());
        assertEquals(secondString, iter.next());
    }

    // just for peace of mind
    @Test
    public void testAddingAndRetrievingThreeItems() {
        String thirdString = "yet another string";
        deque.addLast(firstString);
        deque.addLast(secondString);
        deque.addLast(thirdString);
        assertEquals(firstString, deque.removeFirst());
        assertEquals(secondString, deque.removeFirst());
        assertEquals(thirdString, deque.removeFirst());
    }

    @Test
    public void testSizeIsZeroWhenEmpty() {
        assertEquals(0, deque.size());
    }

    @Test
    public void testSizeGrowsWithAddMethods() {
        deque.addFirst(firstString);
        assertEquals(1, deque.size());
        deque.addLast(secondString);
        assertEquals(2, deque.size());
    }

    @Test
    public void testSizeShrinksWithRemoveMethods() {
        deque.addFirst(firstString);
        deque.addLast(secondString);
        deque.removeLast();
        assertEquals(1, deque.size());
        deque.removeFirst();
        assertEquals(0, deque.size());
    }

}
