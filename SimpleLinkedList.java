package edu.csupomona.cs.cs240.prog_assgmnt_2;

/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * Class contains a Node class that creates a node object 
 * and stores elements.
 *
 * Edgar Ruiz 009634885
 * 
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class SimpleLinkedList<K extends Comparable<K>, V> implements Serializable {

	private Node head = null;
	
	/**
	 * Will be used for the size of the list.
	 */
	protected int counter = 0;
	
	/**
	 * Takes in a new element with a key and value and adds
	 * element to the end of the list. Returns true when a new element
	 * has been added to the list.
	 */
	public boolean add(K key, V value, String grade) {
		if (head == null) {
			Node nn = new Node(key, value, grade);
			head = nn;
		} else {
			Node node = head;

			while (node.next != null) {
				node = node.next;
			}

			node.next = new Node(key, value, grade);
		}
		
		++counter;
		return true;
	}

	
	/**
	 * First checks to see if list is empty.
	 * If not empty, element associated with 
	 * the key will be removed and subtracts from counter.
	 */
	public V remove(K key) {
		V result = null;
		Node current = head;

		if (head == null) {
			return null;
		}

		if (current != null) {
			if (current.key.compareTo(key) == 0) {
				result = current.value;
				head = current.next;
			} else {
				while (current.next != null) {
					if (current.next.key.compareTo(key) == 0) {
						result = current.next.value;
						current.next = current.next.next;
						break;
					}
					current = current.next;
				}
			}
		}
		--counter;
		return result;
	}
	
	/**
	 * Deletes an element from index 'n'. Checks to see if index is 
	 * not greater than the size of the array. When element is removed, 
	 * decreases the counter variable and returns the result.
	 */
	public V remove(int n) {
		Node node = head;
		Node nextNode = node.next;
		Node result = null;

		if (n > counter) {
			return null;
		}

		if (n == 1) {
			head = node.next;
			result = node;
		}

		if (n != 1) {
			int i = 1;
			while (i < n - 1) {
				node = node.next;
				nextNode = nextNode.next;
				++i;
			}
			result = nextNode;
			node.next = nextNode.next;
			nextNode = null;
		}
		--counter;
		return result.value;
	}
	
	/**
	 * Remove method with no parameters will remove the element at the front
	 * of the list. Will point to the next node after the first element is
	 * removed.
	 */
	public V remove() {
		Node result = head;
		if (result != null) {
			head = head.next;
		}
		--counter;
		return result.value;
	}
	
	/**
	 * Finds the value associated with the key 
	 * and returns the value.
	 */
	public V lookup(K key) {
		V result = null;
		Node current = head;

		while (current != null) {
			if (current.key.compareTo(key) == 0) {
				result = current.value;
			}
			current = current.next;
		}
		return result;
	}
	
	/**
	 * Returns the number of elements in the list.
	 */
	public int size() {
		return counter;
	}
	
	/**
	 * Takes in an index 'n' and returns the value 
	 * in the given position.
	 */
	public V get(int n) {
		Node node = head;
		if (n > counter) {
			return null;
		}

		int i = 0;
		while (i < n) {
			node = node.next;
			++i;
		}

		return node.value;
	}

	/**
	 * Returns the string representation of the 
	 * elements in the list.
	 */
	public String toString() {
		String result = "";
		Node current = head;

		if (current == null) {
			return "Empty List";
		}

		result += current;

		while (current.next != null) {
			current = current.next;
			result += current;
		}

		return result;
	}

	/**
	 * Key and value are in a node. Each node has a protected
	 * variable called 'next' that will point to the next Node.
	 * 
	 * 
	 */
	private class Node implements Serializable{
		protected K key;
		protected V value;
		protected String grade;
		protected Node next;

		Node(K k, V v, String g) {
			key = k;
			value = v;
			grade = g;
			next = null;
		}
		
		public String toString() {
			return "(" + key + ", " + value + ", " + grade + ") -- ";
		}
	}

}
