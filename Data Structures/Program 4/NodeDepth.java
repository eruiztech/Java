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

/**
 * This class holds a node and it's current depth
 * 
 */
public class NodeDepth<K extends Comparable<K>, V> {

    /**
     * the depth of the held node
     */
    private int depth;
    
    /**
     * Node 
     */
    private Node<K, V> node;
    
    /**
     * Creates a node and depth of node
     */
    public NodeDepth(int deep, Node<K, V> nn) {
        depth = deep;
        node = nn;
    }
    
    /**
     * Returns depth of node
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Returns node
     */
    public Node<K, V> getNode() {
        return node;
    }

    /**
     * Sets depth of node
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }
}
