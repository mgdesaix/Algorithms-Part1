import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {
	
	private Node first;    // beginning of queue
    private Node last;     // end of queue
    private int n;               // number of elements on queue
    
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

	public Deque()  // construct an empty deque
	{
		first = null;
        last  = null;
        n = 0;
	}
	
	public boolean isEmpty() // is the deque empty?
	{
		return first == null || last == null;
	}
	
	public int size() // return the number of items on the deque
	{
		return n;
	}
	
	public void addFirst(Item item) // add the item to the front
	{
		if (item == null) throw new IllegalArgumentException("Null item provided!");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
        if (isEmpty()) last = first;
        else if (oldfirst == last) {
        	last.previous = first;
        	first.next = last;
        }
        else {
        	first.next = oldfirst;
        	oldfirst.previous = first;
        }
        n++;
	}
	
	public void addLast(Item item) // add the item to the end
	{
		if (item == null) throw new IllegalArgumentException("Null item provided!");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldlast;
        if (isEmpty()) first = last;
        else {
        	oldlast.next = last;
        	last.previous = oldlast;
        }
        n++;
	}
	
	public Item removeFirst() // remove and return the item from the front
	{
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
	}
	
	public Item removeLast()  // remove and return the item from the end
	{
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.previous;
        n--;
        if (isEmpty()) first = null;   // to avoid loitering
        return item;
	}
	
	public Iterator<Item> iterator()  {
        return new ListIterator();  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

}
