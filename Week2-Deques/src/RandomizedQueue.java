import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
	
	 private Item[] q;       // queue elements
	 private int n;          // number of elements on queue
	 private int first;      // index of first element of queue
	 private int last;       // index of next available slot
	 private int sample;
	
	public RandomizedQueue() {
		q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
	}
	
	private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = n;
    }
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
    public void enqueue(Item item) {
    	if (item == null) throw new IllegalArgumentException("Null item provided!");
        // double size of array if necessary and recopy to front of array
        if (n == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        n++;
    }
	
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = sample();
        q[sample] = q[last-1];                            // to avoid loitering
        q[last-1] = null;
        n--;
        last--;
        if (first == q.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == q.length/4) resize(q.length/2); 
        return item;
	}
	
	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		sample = StdRandom.uniform(n);
		return q[sample];
	}
	
	public Iterator<Item> iterator(){
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(i + first) % q.length];
            i++;
            return item;
        }
    }
	
	public static void main(String[] args) {
	
	}

}
