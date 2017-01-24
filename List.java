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
public interface List<K extends Comparable<K>,V> {

	/** data ListData theList <- (* Collection of objects *); 
	  */
	
	/** data size 0 <- (* The number of objects in the List*)
	  *                               
	  */
	
	/** inv 1 : $data is a collection of objects
	  * inv 2 : Order in which objects are stored matters,
	  *         therefore, there's an implicit indexing:
	  *          i0 -> o1, i1 -> o2, ..., in -> on.
	  * inv 3 : The list can be empty.
	  */
	
	/** pre: value exists in list
	  * post: value is in the list and increases size of list by 1. Returns true if
	  * 		 new value is added to list. Returns false if nothing added to list. 
	  */	
	public abstract boolean add(K key,V value);

	/** pre: key is in the list
	  * post: if key is in list, then remove that key and value. If key is not in list,
	  * 		 then nothing is removed and null is returned. 
	  */	
	public abstract V remove(K key);

	/** pre: n is a position in the list and is not a negative number
	  * post: nothing is removed if list is empty and null is returned. If position is 
	  * 		 found, remove value at position n and return value.
	  */	
	public abstract V remove(int n);

	/** pre: list has number of elements.
	  * post: if list is empty, list stays the same and nothing happens. Else, remove
	  *       value at last position in list and return value.
	  */	
	public abstract V remove();

	/** pre: key exists in list
	  * post: if key is in list, return value associated with key. Else, return null.
	  */	
	public abstract V lookup(K key);

	/** pre: list size starts at 0 if empty and increases based on number of elements
	  * 	 on list.
	  * post: return number of elements in list.
	  */
	public abstract int size();

	/** pre: n is not a negative integer and is a position that exists in list.
	  * post: if list is not empty, then value is returned at position n.
	  */
	public abstract V get(int n);

}
