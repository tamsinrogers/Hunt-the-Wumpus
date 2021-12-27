/**
 * File: LinkedList.java
 * Author: Tamsin Rogers
 * Date: 4/28/20
 * CS 231 Project 10
 */
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Scanner; 
import java.util.*; 

public class LinkedList<T> implements Iterable<T>
{
	Node head;									// head node of the list
	int size;									// number of items in the list
	
	/* node objects have a value and a next pointer */						
	private class Node
	{
		private Node next;						// the next pointer of the node
		private T value;						// the value of type T of the node
	
		/* constructor, initializes next to null and the container field to item */
		public Node(T item)	
		{
			next = null;						// initialize next to null
			value = item;						// initialize the container field to item
		}	

		/* returns the value of the container field */
		public T getThing()
		{
			return value;						// return the value of the container field
		}

		/* sets next to the given node */
		public void setNext(Node n)
		{
			next = n;							// set next to the given node
		}

		/* returns the next field */
		public Node getNext()
		{
			return next;						// return the next node
		}
	}

	/* constructor that initializes the fields so it is an empty list */
	public LinkedList()
	{
		size = 0;								// initialize size to 0 (so the list is empty)
	}

	/* resets the fields so the list is empty */
	public void clear()
	{
		size = 0;								// initialize size to 0 (so the list is empty)
		head = null;							// set the head node to null (there is no head node)
	}

	/* returns the size of the list */
	public int size()
	{
		return size;
	}

	/* inserts the item at the beginning of the list */
	public void addFirst(T thing)
	{
		Node firstNode = new Node(thing);		// create a node that has the given thing value
		firstNode.next = head;					// set the next node to the current head node
		head = firstNode;						// set the new head node to the newly created node
		size++;									// increment the size of the linked list
	}

	/* appends the item at the end of the list */
	public void addLast(T thing)
	{
		if(size == 0)							// if the linked list is empty
		{
			head = new Node(thing);				// the head node is the newly added node
		}
	
		else									// if the list already has elements
		{
			Node lastNode = new Node(thing);	// create a new node that has the given thing value
			Node node = head;					// point to the head node
			Node next = node.next;				// point to the node next to the head node
			for(int i=0; i<this.size-1; i++)	// loop through the linked list
			{
				node = next;					// set the head node to the next node
				next = node.next;				// set the current node to the next node
			}
			node.next = lastNode;				// set the final pointed next node to the last node
		}
		size++;									// increment the size of the linked list
	}

	/* inserts the item at the specified position in the list */
	public void add(int index, T item)
	{
		if(size == 0)							// if the linked list is empty
		{
			this.addFirst(item);				// add the new item to the beginning of the list
			return;
		}
		if(index == 0)							// if the given index is 0
		{
			this.addFirst(item);				// add the new item to the beginning of the list
			return;
		}
		Node addNode = new Node(item);			// create a new node
		Node node = head;						// point to the head node
		for(int i=0; i<index-1; i++)			// loop through the linked list
		{
			node = node.getNext();				// set node to the node next to it
		}
		Node adder = node.getNext();			// set the next node to the adder node
		node.next = addNode;					// set the node next to node to addNode
		addNode.setNext(adder);					// set adder to be the node next to addNode
		size++;									// increment the size of the linked list
	}

	/* removes the item at the specified position in the list */
	public T remove(int index)
	{
		Node node = head;						// point to the head node
		Node nxt = head.next;					// set next to the node next to the head node
		if(index == 0)							// if the given index is 0
		{
			T headValue = head.value;			// set headValue to the value of the head node
			head = head.next;					// set the head node to the node next to it
			size--;								// decrement the size of the linked list
			return headValue;					// return the value of the head node
		}
		for(int i=0; i<index-1; i++)			// loop through the linked list
		{
			node = nxt;							// set the head node pointer to the node next to the head node
			nxt = node.next;					// set the nxt node to the node next to it (move them down one)
		}
		node.next = nxt.next;					//set the node next to node to the node next to next (move them down one)
		size--;									// decrement the size of the linked list
		return nxt.value;						// return the T value of the nxt node
	}
	
	/* replaces the second node with a new node containing newValue, if there is no second node, do nothing */ 
	public void replaceSecondValue(T newValue)
	{
		Node newNode = new Node(newValue);		// create a new node
		Node current = this.head;				// set the current node to the head node
		if (this.size()>1)						// if there is at least one item in the linked list
		{	
			current.getNext().setNext(newNode);	// set the node next to the node next to the current node to the new node
		}
		else									// if the linked list is empty
		{
			return;
		}
	}
	
	/* returns the data in the i-th node in the list */
	public T get(int i)
	{
		Node current = this.head;				// set the current node to the head node
		for(int j = 0; j < i; j++) 				// loop through the linked list
		{
			current = current.next;				// set the current node to the node next to it
		}
		return current.value;					// return the T value of the node at index i
	}
	
	/* returns true if one or more of the nodes in the list has the object obj as its data */
	public boolean contains(T obj)
	{
		for(Node current = this.head; current != null; current = current.next)	
		{
			if(current.getThing().equals(obj))	// if the T value of the current node has the object obj as its data
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		if(this.head == null) 					// if there is no head node (the linked list is empty)
		{
			return "<>";
		}
		String result = "";						// initialize the result string
		Node current = this.head;				// set the current node to the head node
		while(current != null) 					// loop through the linked list
		{
			result += current.getThing().toString() + ", ";
			current = current.next;
		}
		result = "<" + result.substring(0,result.length()-2) + ">";
		return result;							// return the result string that includes all of the nodes
	}
		
	private class LLIterator implements Iterator<T>
	{
		private Node current;					// initialize the current node
	
		/* constructor for the LLIterator given the head of a list */
		public LLIterator(Node head)
		{		
			current = head;						// set the current node to the head node
		}
		
		/* returns true if there are still values to traverse (if the current node reference is not null) */
		public boolean hasNext()
		{
			if(current != null)					// if the current node exists
			{
				return true;
			}
			else								// if the current node does not exist
			{
				return false;
			}
		}
		
		/* returns the next item in the list, which is the item contained in the current node
			moves the traversal along to the next node in the list */
		public T next()
		{
			Node n = current;					// set the node n to the current node
			current = current.next;				// set the current node to the node next to it
			return n.value;						// return the value of the current node
		}
		
		/*public void remove()
		{
			Node node = head;						// point to the head node
			Node nxt = head.next;					// set next to the node next to the head node
			for(int i=0; i<size; i++)				// loop through the linked list
			{
				node = nxt;							// set the head node pointer to the node next to the head node
				nxt = node.next;					// set the nxt node to the node next to it (move them down one)
			}
			node.next = nxt.next;					//set the node next to node to the node next to next (move them down one)
			size--;									// decrement the size of the linked list
			return nxt.value;						// return the T value of the nxt node
		}*/
	}
	
	/* returns a new LLIterator pointing to the head of the list */
	public Iterator<T> iterator() 
	{
    	return new LLIterator(this.head);		
	}
	
	/* returns an ArrayList of the list contents in order */
	public ArrayList<T> toArrayList() 
	{
		ArrayList<T> list = new ArrayList<T>();	// create a new array lsit
		Node node = this.head;					// create a node pointing to the head node
		for(int i=0; i<this.size-1; i++)		// loop through the linked list
			{
				while(node != null)				// if a next node exists (not at the end of the linked list yet)
				{
					list.add(node.getThing());	// add the T value of the current node to the list
					node = node.next;
				}
			}
		return list;							// return the elements of the linked list in order as an array list
	}	
	
	/* returns an ArrayList of the list contents in shuffled order */
	public ArrayList<T> toShuffledList() 
	{
		Random rand = new Random(); 			// initialize random
		ArrayList<T> list = toArrayList();		// create a new array list
		ArrayList<T> shuff = new ArrayList<T>();	
		while(list.size() > 0)
		{
			shuff.add(list.remove(rand.nextInt(list.size())));
		}
		return shuff;							// return the elements of the linked list shuffled in an array list
	}
}