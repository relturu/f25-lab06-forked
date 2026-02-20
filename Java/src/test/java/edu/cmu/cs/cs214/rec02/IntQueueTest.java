package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testClear() {
        // should remove all elements from queue
        mQueue.enqueue(2);
        mQueue.enqueue(3);
        mQueue.enqueue(4);
        mQueue.clear();
        assertTrue(mQueue.size()==0);
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(0);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        if (mQueue.size()==0){
            assertEquals(null, mQueue.peek());
        }
        else{
            assertTrue(mQueue.peek()==0);
        }
    }

    @Test
    public void testPeekNoEmptyQueue() {
        if (mQueue.size()==0){
            assertEquals(null, mQueue.peek());
        }
        else{
            assertTrue(mQueue.peek()>0);
        }
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        for (int i = 0; i < testList.size(); i++){
            if (mQueue.size() ==0){
                assertEquals(null, mQueue.dequeue());
            }
            else{
                int expected = mQueue.peek();
                int actual = mQueue.dequeue(); // is dequeue destructive does it actually mutate the queue
                assertEquals(expected, actual); // how to test the val is expected
                assertEquals(i-1, mQueue.size());
            }
            
        }
    }

    @Test
    public void testEnsureCapacity() {
        // crurently has 3 | 0 1 2 (head)
        // have initial capacity of 10
        mQueue.clear();
        // fills completely 
        for (int i = 0; i < 10; i++){
            mQueue.enqueue(i);
        }
        // dequeue a few to move head forward (creates wrap-around)
        mQueue.dequeue();
        mQueue.dequeue();
        mQueue.dequeue();
        
        // enqueue more to force resize (triggers ensureCapacity with head != 0)
        mQueue.enqueue(10);
        mQueue.enqueue(11);
        mQueue.enqueue(12);
        mQueue.enqueue(13);

        // verify size is correct
        assertEquals(11, mQueue.size());

        // verify order is preserved - dequeue should come out in correct order
        assertEquals(3, (int) mQueue.dequeue()); // first 3 were dequeued, so 3 is next
        assertEquals(4, (int) mQueue.dequeue());
        assertEquals(5, (int) mQueue.dequeue());

    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }



}
