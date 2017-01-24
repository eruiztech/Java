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
 * This class is used to show the structure of the Red Black Tree as nodes get added and deleted.
 */
public class Test {
    public static void main(String[] args) {

       RedBlackTree<String, String> RBT =  new RedBlackTree<String, String>();
       System.out.println("Red Black Tree");
       System.out.println("N=null, :R=red node, :B=black node");
       System.out.println("All nodes are added with key displayed and default value 'a'");
       System.out.println("Node 'k' becomes root for Red Black Tree");
       RBT.add("k", "a");
       RBT.remove("k");
       RBT.toPrettyString();
       System.out.println("-----------------------------------");
       System.out.println("Add node with key 'a'");
       RBT.add("a", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------");
       System.out.println("Add node with key 'd'");
       RBT.add("d", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------");
       System.out.println("Add node with key 't'");
       RBT.add("t", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------");
       System.out.println("Add node with key 'x'");
       RBT.add("x", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------");
       System.out.println("Add node with key 'w'");
       RBT.add("w", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
       System.out.println("Add node with key 'f'");
       RBT.add("f", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
       System.out.println("Add node with key 'q'");
       RBT.add("q", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
       System.out.println("Add node with key 'z'");
       RBT.add("z", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
       System.out.println("Add node with key 'e'");
       RBT.add("e", "a");
       RBT.toPrettyString();
       System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
       System.out.println("Remove node with key 'x'");
       RBT.remove("x");
       RBT.toPrettyString();
       System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }  

}