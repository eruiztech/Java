/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #1
 *
 *
 * @author Edgar Ruiz
 */
package edu.csupomona.cs.cs241.prog_assgmnt_1;

/**
 * @author Edgar Ruiz
 */
public interface Heap<V extends Comparable<V>>{
	
	public static enum MODE {MAX, MIN};

	/**
	 * Adds a new element into the next available spot in heap.
	 * @param value of the new element.
	 */
	public void add(V value);

	/**
	 * Copies elements from heap into an array.
	 * @return Heap as an array.
	 */
	public V[] toArray();

	/**
	 * Removes an element from the top of the heap.
	 * @return top element of the heap.
	 */
	public V remove();
	
	/**
	 * Takes an array and places it into a heap based on
	 * the heap property.
	 * @param array with heap values.
	 */
	public void fromArray(V[] array);

	/**
	 * Returns an array that contains heap values based on
	 * the heap property.
	 * @return array with sorted contents.
	 */
	public V[] getSortedContents();
	
	/**
	 * Returns the mode of the heap.
	 * @return mode of the heap.
	 */
	public MODE getMode();
	
	/**
	 * Sets the mode of the heap.
	 * @param mode to set 
	 */
	public void setMode(MODE mode);

}