/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #1
 *
 * Array Lists and Linked Lists
 *
 * Edgar Ruiz 009634885
 */
/**
 * This file pedagogical material for the course
 * CS 240: Data Structures and Algorithms
 * taught at California State Polytechnic University - Pomona, and
 * cannot be used without express written consent from the author.
 * 
 * Copyright (c) 2013 - Edwin Rodr&iacute;guez.
 */
package edu.csupomona.cs.cs240.prog_assgmnt_1;

/**
 * @author Edwin Rodr&iacute;guez
 *
 */
public class SimpleLinkedList<K extends Comparable<K>,V> implements List<K, V> {
	
	/** First node in linked list.
	  * 
	  */
	private Node head = null;
	
	/** Last node in the linked list.
	  * 
	  */
	private Node tail = null;

	/** Constructor of Linked list.
	  * 
	  */
	public SimpleLinkedList() {
		head = null;
		tail = null;
	}
	
	/** Adds value to the end of the list. If list is empty, the head and tail are set to the new node.
	  * 
	  */
	public boolean add(K key, V value) {
		if (head == null) { // List is empty
			Node nn = new Node(key, value);
			
			head = nn;
		} else {
			Node node = head;
			
			while (node.next != null) {
				node = node.next;
			}
			
			node.next = new Node(key,value);
		}
		
		return true;
	}

	/** Searches for node containing the key specified. If node with key is found, node is removed and value of node 
	  * is returned. If not found, null will be returned.
	  */
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		Node curNode = head;
		while(curNode != null) {
			if( ((Node)curNode.next.value).key == key){
				Node removed = curNode.next;
				curNode.next = curNode.next.next;
				return ((Node)(removed.value)).value;
			}
			curNode = curNode.next;
		}
		return null;
	}

	/** Removes and returns node in list at specified position.
	  * 
	  */
	public V remove(int n) {
		if(head == null) {
			return null;
		}
		else {
			int inPos = 0;
		
			for( Node curNode = head; curNode != null; curNode = curNode.next, inPos += 1) {
				if(inPos == n) {
					Node removed = head;
					head = head.next;
					return removed.value;
				}
			}
		}
		return null;
	}

	/** Removes current head node from list by setting head's value as next value. Returns value of head 
	  * or null if empty.  
	  */
	public V remove() {
		if(head == null) {
			return null;
		}
		else if(head == tail) {
			Node removed = head;
			head = null;
			tail = null;
			return removed.value;
		}
		else {
			Node removed = head;
			head = head.next;
			return removed.value;
		}
	}

	/** Searches through list for node containing key. Returns value of node containing specified key or else 
	  * null if not found. 
	  */
	@SuppressWarnings("unchecked")
	public V lookup(K key) {
		if( head == null ) {
			return null;
		}
		Node curNode = head;
		
		while(curNode != null) {
			if( ((Node)curNode.next.value).key == key) {
				return curNode.value;
			}
			curNode = curNode.next;
		}
		return null;
	}

	/** Returns size of the list.
	  * 
	  */
	public int size() {
		int size = 0;
		Node curNode = head;
		
		while(curNode.next != null) {
			curNode = curNode.next;
			size++;
		}
		return size;
	}

	/** Finds value in list at specified position (n).
	  * 
	  */
	public V get(int n) {
		if(head == null) {
			return null;
		}
		else {
			int inPos = 0;
			for(Node curNode = head; curNode != null; curNode = curNode.next, inPos += 1) {
				if(inPos == n) {
					return curNode.value;
				}
			}
			return null;
		}
	}
	
	/** Creates array and is added the values of each node. Returns array or null if list is empty.
	  * 
	  */
	public Object[] toArray() {
		int cSize = size();
		int index = 0;
		
		if(head == null){
			return null;
		}
		else {
			Object[] arrayList = new Object[cSize];
			for(Node curNode = head; curNode != null; curNode = curNode.next) {
				arrayList[index] = curNode.next.value;
				index++;
			}
			return arrayList;
		}
	}
	
	/** Returns string representation of linked list. Returns null if list is empty.
	  * 
	  */
	public String toString() {
		if(head ==  null)
			return null;
		else {
			String str = "";
			Node curNode = head;
			
			while(curNode != null) {
				str = str + "(" + curNode.key + "," + curNode.value + ") -- ";
				curNode = curNode.next;
			}
			return str;
		}
	}
	
	/** Defines a node.
	  * 
	  */
	private class Node {
		protected K key;
		protected V value;
		protected Node next;
		
		Node(K k, V v) {
			key = k;
			value = v;
			next = null;
		}
	}

}
