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
/*
 * This class constructs a node with a given key and value
 */

public class Node<K extends Comparable<K>,V> {
    /**
     * Right child of node
     */
    private Node<K, V> rightChild;
    /**
     * Left child of node
     */
    private Node<K, V> leftChild;
    /**
     * Parent of node
     */
    private Node<K, V> parent;
    /**
     * Color of node 
     * if true, red 
     * if false, black
     */
    private boolean red;
    /**
     * Value held in node
     */
    private V value;
    /**
     * Key associated with the value in node
     */
    private K key;
    /**
     * Creates node with given color, leftChild, rightChild, parent, value, and key.
     * 
     */
    public Node(boolean color, Node<K, V> leftChild, Node<K, V> rightChild, Node<K, V> parent, V value, K key) {
        red = color;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
        this.value = value;
        this.key = key;
    }

    /**
     * Returns right child of node
     * 
     */
    public Node<K, V> getRightChild() {
        return rightChild;
    }

    /**
     * Sets right child of node
     * 
     */
    public void setRightChild(Node<K, V> rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Returns left child of node
     * 
     */
    public Node<K, V> getLeftChild() {
        return leftChild;
    }

    /**
     * Sets left child of node
     * 
     */
    public void setLeftChild(Node<K, V> leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Returns parent of node
     *
     */
    public Node<K, V> getParent() {
        return parent;
    }

    /**
     * Sets parent node of node
     * 
     */
    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    /**
     * Checks color of node 
     * True if red 
     * False if black
     */
    public boolean isRed() {
        return red;
    }

    /**
     * Sets color of node 
     * True for red 
     * False for black
     * 
     */
    public void setColor(boolean red) {
        this.red = red;
    }

    /**
     * Returns value held by node
     * 
     */
    public V getValue() {
        return value;
    }

    /**
     * Returns key associated with value of node
     *
     */
    public K getKey() {
        return key;
    }

    /**
     * Creates new mapping from a key to a value in node
     * 
     */
    public void setMapping(K key, V value) {
        this.key = key;
    }
}