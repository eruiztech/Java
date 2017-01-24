package edu.csupomona.cs.cs240.prog_assgmnt_2;

/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * AdditiveHashTable will give all the methods
 * from the Hash Table. It will have its own methods: 
 * loading factor, maximum number of chains, 
 * density factor, and chain factor of the table. 
 * Adds the sum of the functions and puts
 * them into the same hash.
 *
 * Edgar Ruiz 009634885
 * 
 */

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class AdditiveHashTable<K extends Comparable<K>, V> implements Serializable {

	private SimpleLinkedList<K, V> table[];

	@SuppressWarnings("unchecked")
	public AdditiveHashTable(int bound) {
		table = new SimpleLinkedList[bound];
		int num = 0;
		while (num < table.length) {
			table[num] = new SimpleLinkedList<K, V>();
			++num;
		}
	}

	/**
	 * Additive hashing function is used and adds the elements
	 * to the list.
	 */
	public void add(K key, V value, String grade) {
		int index = additiveHashing(key, table.length);

		if (table[index] == null) {
			table[index] = new SimpleLinkedList<K, V>();
		}

		table[index].add(key, value, grade);
	}

	@SuppressWarnings("unchecked")
	
	/**
	 * Additive hashing is used and removes
	 * an element from the list on the key entered.
	 */
	public V remove(K key) {
		int index = additiveHashing(key, table.length);
		V value = (V) table[index];
		table[index].remove(key);

		System.out.println(key + " removed.");
		return value;
	}

	/**
	 * Uses given key to look up value from the list.
	 */
	public V lookup(K key) {
		int index = additiveHashing(key, table.length);
		if (table[index] == null) {
			return null;
		}
		V value = (V) table[index].lookup(key);
		return value;
	}

	@SuppressWarnings("unchecked")
	
	/**
	 * Sorts the the list in alphabetical
	 * order. Counts the total number the size
	 * of the list. Use copyOf to copy in
	 * the elements into array. Will get names of 
	 * students from each index and sort them 
	 */
	public V[] getSortedList(V[] list) {

		int size = 0;
		int i = 0;
		while (i < table.length) {
			size += table[i].size(); 
			++i;
		}

		V[] elements = (V[]) Arrays.copyOf(list, size, list.getClass());

		int index = 0;
		for (int j = 0; j < table.length; ++j) {
			for (int k = 0; k < table[j].size(); ++k) {
				elements[index] = table[j].get(k);
				++index;
			}
		}

		Arrays.sort(elements);

		return elements;
	}

	/**
	 * Prints out loading factor, maximum number
	 * of collisions, the density factor, and the chain factor of table
	 */
	public void printReport() {
		System.out.println("The loading factor of the table: " + loadfactor());
		System.out.println("Maximum number of collisions : " + maxChain());
		System.out.println("The density factor of the table: " + densityfactor());
		System.out.println("The chain factor of the table: " + chainfactor());
	}

	/**
	 * Prints out the key and the name from a position in the table.
	 */
	public void printTheList() {
		int i = 0;
		while (i < table.length) {
			if (table[i].size() > 0) {
				System.out.println(table[i]);
			}
			++i;
		}
	}

	/**
	 * Adds the sum of the function into the same hash. 
	 * Returns hash modulus size of the hash table.
	 * 
	 * @param key
	 * @param tableSize
	 * @return
	 */
	private int additiveHashing(K key, int tableSize) {
		int hash = 0;
		char[] k = ((String) key).toCharArray();
		for (char c : k) {
			hash += c;
		}

		return hash % tableSize;
	}

	/**
	 * Returns result of the number of used
	 * buckets over the total number of buckets.
	 * 
	 * @return
	 */
	public float loadfactor() {
		float bucketsHit = 0;
		int i = 0;
		while (i < table.length) {
			if (table[i].size() != 0) {
				++bucketsHit;
			}
			++i;
		}
		return bucketsHit / table.length;
	}

	/**
	 * Gives the maximum number of collisions in the table.
	 * 
	 * @return
	 */
	public int maxChain() {
		int maxChain = 0;
		int i = 0;
		while (i < table.length) {
			if (table[i].size() > maxChain) {
				maxChain = table[i].size();
			}
			++i;
		}

		return maxChain;
	}

	/**
	 * Returns result from the number of elements
	 * in the table over the total number of buckets.
	 * 
	 * @return
	 */
	public float densityfactor() {
		float maxElements = 0;
		int i = 0;
		while (i < table.length) {
			maxElements += table[i].size();
			++i;
		}

		float bucketsHit = 0;
		int j = 0;
		while (j < table.length) {
			if (table[j].size() != 0) {
				++bucketsHit;
			}
		}

		return maxElements / bucketsHit;
	}

	/**
	 * Returns the average length of of any chain in the
	 * table
	 * 
	 * @return
	 */
	public double chainfactor() {
		double numOfElements = 0;
		int i = 0;
		while (i < table.length) {
			numOfElements += table[i].size();
		}
		return numOfElements / table.length;
	}
}
