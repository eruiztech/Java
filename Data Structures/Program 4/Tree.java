/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * Implementation of Red-Black Tree.
 * The method toPrettyString() will return a string with 
 * the values in the tree, in a pyramid fashion, each value 
 * appearing along with its color, so as to make it easy to 
 * visualize the structure of the tree.
 *
 * @author Edgar Ruiz 009634885
 *   
 */
package edu.csupomona.cs.cs241.prog_assgmnt_2;

public interface Tree<K extends Comparable<K>, V> {
	  /*
	   * Adds a node with a key and value based using any of the 5 cases
	   */
	  public void add(K key, V value);
	  /*
	   *  Finds a node with the given key and removes it from tree
	   *  Tree is fixed to satisfy the 5 Red Black Tree invariants
	   */
	  public V remove(K key);
	  /*
	   * Looks up value associated with key
	   */
	  public V lookup(K key);
	  /*
	   * Prints Red Black Tree to visualize the structure of the tree
	   */
	  public String toPrettyString();

	}