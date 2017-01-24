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
 * Class: Node
 * 
 * Node with reference to its parent, left, and right child.
 *
 * @param <V> Desired data type.
 */
public class Node<V>{ 

	public V elem;
	public Node<V> left;
	public Node<V> right;
	public Node<V> parent;
	public boolean isValid;
	
	/* NODE Constructor
	 * 
	 */
	public Node(V value, Node<V> leftRef, Node<V> rightRef, Node<V> parentRef){
		elem = value;
		left = leftRef;
		right = rightRef;
		parent = parentRef;
		isValid = true;
	}
}