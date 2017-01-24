package edu.csupomona.cs.cs240.prog_assgmnt_2;

/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * 
 * Use its own methods to determine the loading factor, maximum number
 * of chains, density factor, and the chain factor.>
 *
 * Edgar Ruiz 009634885
 */

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class XorShiftHashTable<K extends Comparable<K>, V>  implements Serializable {

	
	private SimpleLinkedList<K, V> table[];
	ThisQueue queue = new ThisQueue();
	
	@SuppressWarnings("unchecked")
	public XorShiftHashTable(int bound) {
		table = new SimpleLinkedList[bound];
		int i = 0;
		while (i < table.length) {
			table[i] = new SimpleLinkedList<K, V>();
			++i;
		}
	}

	/**
	 * Adds elements into the list.
	 */
	public void add(K key, V value, String grade) {
		int index = xorShiftHashing(key, table.length);

		if (table[index] == null) {
			table[index] = new SimpleLinkedList<K, V>();
		}

		table[index].add(key, value, grade);
	}

	/**
	 * Removes an element from the list based on the given key.
	 */
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		int index = xorShiftHashing(key, table.length);
		V value = (V) table[index];
		table[index].remove(key);

		System.out.println(key + " removed.");
		return value;
	}

	/**
	 * Uses given key to look up the name from the list.
	 */
	public V lookup(K key) {
		int index = xorShiftHashing(key, table.length);
		if (table[index] == null) {
			return null;
		}
		V value = (V) table[index].lookup(key);
		return value;
	}

	/**
	 *  Sorts list in alphabetical order based on the ID.
	 */
	@SuppressWarnings("unchecked")
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
	 * Prints out the loading factor, the maximum number
	 * of collisions (maxChain), the density factor, and the chain factor of the
	 * table.
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

	private int xorShiftHashing(K key, int tableSize) {
		int hash = 0;
		char[] k = (scramble((String) key)).toCharArray();
		for (char c : k) {
			hash ^= (hash << 4) ^ (hash >> 28) ^ c;
		}
		
		hash = Math.abs(hash);

		return hash % tableSize;
	}

	/**
	 * Returns result of the number of used
	 * buckets over the total number of buckets.
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
	
	/**
	 * Scrambles the names.
	 * @param oldName
	 * @return
	 */
	public String scramble(String name) {
		   ThisQueue queue = new ThisQueue();
		   String secondName = "";
		   int i = 0;
		   while (i < name.length()) {
			   queue.add(name.charAt(i++));
		   }
		   secondName += queue.remove(); 
		   return secondName;
	}
}
