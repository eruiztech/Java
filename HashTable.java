package edu.csupomona.cs.cs240.prog_assgmnt_2;

/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * Hash Table will be used as a blueprint for other hash table classes.
 *
 * Edgar Ruiz 009634885
 * 
 */

import java.io.Serializable;

@SuppressWarnings("unused")
public interface HashTable<K extends Comparable<K>, V> {
	
	/**
	 * Adds a key to the index of the hash table
	 * array and stores in a value of that element.
	 * @param key
	 * @param value
	 */
	public void add(K key, V value);

	/**
	 * Removes an element in the hash table based
	 * on the key given.
	 * @param key
	 * @return
	 */
	public V remove(K key);

	/**
	 * Looks up an element based on the
	 * given key.
	 * @param key
	 * @return
	 */
	public V lookup(K key);

	/**
	 * Sorts list in alphabetical order based on the ID.
	 * @param list
	 * @return
	 */
	public V[] getSortedList(V[] list);

	/**
	 * Prints out a report based on
	 * loading factor, density factor, maximum number
	 * of collisions, and chain factor.
	 */
	public void printReport();

}