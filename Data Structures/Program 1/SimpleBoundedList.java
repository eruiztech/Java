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
public class SimpleBoundedList<K extends Comparable<K>,V> implements List<K, V> {

	protected Object[] values;
	
	/** index of first object in list.
	  * 
	  */
	protected int start = 0;
	
	/** index of next empty spot in list.
	  * 
	  */
	protected int nextEmpty = 0;
	
	
	private int index = 0;

	/** Constructor that sets start and nextEmpty to 0
	  * 
	  */
	public SimpleBoundedList(int bound) {
		values = new Object[bound];
	}

	/** Adds a value to the list. If list is full, does not add value. 
	  * 
	  */
	public boolean add(K key, V value) {
		boolean modify = false;
		int nextIndex = nextEmpty;

		if (((nextEmpty + 1) % values.length) != start) {
			nextEmpty = (nextEmpty + 1) % values.length;
			modify = true;
		} else if (values[nextEmpty] == null) {
			modify = true;
		}
		
		if (modify)
			values[nextIndex] = new Entry(key,value);
		
		return modify;
	}

	/** Removes and returns value if found within the list. Null is returned if
	  *  value is not found in list.
	  */
	public V remove(K key) {
		V value = lookup(key);
		if(value == null) {
			return null;
		}
		else {
			values[index] = null;
			start = (start + 1) % values.length;
			return value;
		}
	}

	/** Returns value if value containing the specified key is found. If not found, return null
	  * 
	  */
	@SuppressWarnings({ "unchecked" })
	public V lookup(K key) {
		
		if(start == nextEmpty && values[start] == null){
			return null;
		}
		else{
			int last = (nextEmpty + 1) % values.length;
			
			for(index = start; index != last; index = (index + 1) % values.length){
				if(((Comparable<K>) ((Entry)values[index]).key).compareTo(key) == 0) {
					V value = get(index);
					return value;
				}
			}
		}
		return null;
	}

	/** Removes the value at the specified position (n) that is entered.
	  * 
	  */
	public V remove(int n) {
		
		V value = get(n);
		values[n] = null;
		start = (start + 1) % values.length;
		return value;
		
	}

	/** Returns size of the list.
	  * 
	  */
	public int size() {
		int size;
		
		if(values[start] == null && start == nextEmpty) {
			size = 0;
		}
		
		else {
			int count = 0;
			int current = start;
			
			while(current != nextEmpty) {
				++count;
				current = (current + 1) % values.length;
			}
			size = count;
		}
		return size;
	}

	/** Finds the value in the list , starting at position specified.
	  * 
	  */
	public V get(int n) {
		if(values[start] == null && start == nextEmpty) {
			return null;
		}
		else {
			@SuppressWarnings("unchecked")
			V value = (V) ((Entry)values[n]).value;
			return value;
		}
	}
	
	/** Removes value at very top of the list.
	  * 
	  */
	public V remove() {
	
		if(values[start] == null && start == nextEmpty) {
			return null;
		}
		else {
			@SuppressWarnings("unchecked")
			V value = (V) ((Entry)values[start]).value;
			values[start] = null;
			start = (start + 1) % values.length;				
			return value;
		}
	}

	/** Array is created and is added all the values of the list. Array is then returned.
	  * 
	  */
	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		int cSize = size();
		Object[] arrayList = new Object[cSize];
		
		if(values[start] == null && start == nextEmpty) {
			return null;
		}
		else {
			int cIndex = start;
			
			for(int i = 0; i < cSize; ++i) {
				arrayList[i] = ((Entry)values[cIndex]).value;
				cIndex = (cIndex + 1) % values.length;
			}
			return arrayList;
		}
	}
	
	/** String representation of the list is returned. 
	  * 
	  */
	@SuppressWarnings("unchecked")
	public String toString() {
		String str = "";
		int cSize = size();
		int cIndex = start;
		
		for(int i = 0; i < cSize; i++) {
			str = str + "(" + ((Entry)values[cIndex]).key + "," + ((Entry)values[cIndex]).value + ") -- ";
			cIndex++;
		}
		return str;
	}
	
	/** Defines an entry.
	  * 
	  */
	private class Entry {
		
		protected K key;
		protected V value;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

	}
}

