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
public class UnboundedList<K extends Comparable<K>,V> extends SimpleBoundedList<K,V> {

	/** Default constructor of unbounded list.
	  * 
	  */
	public UnboundedList(int bound) {
		super(bound);
	}

	/** Calls super class method add and adds a value to the end of the list. If list is full, nothing is added.
	  * 
	  */
	public boolean add(K key, V value) {
		ensureCapacity();
		super.add(key, value);
		
		return true;
	}

	/** Creates array double the size of list and contains elements from list. Used to hve more storage for 
	  * values in list.
	  */
	private void ensureCapacity() {
		if (this.size() == this.values.length) {
			Object[] newArray = new Object[values.length * 2];
			
			int current = start;
			
			for (int i = 0; i < values.length; ++i) {
				newArray[i] = values[i];
				current = (current + 1) % values.length;
			}
			start = 0;
			nextEmpty = (values.length - 1);
			this.values = newArray;
		}
		
	}

}

