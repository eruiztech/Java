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
/*
 * This class is used to hold the priority and name of the customer for table assigning.
 */
public class Customer {
	
	public int priority;
	public String name;
	
	public Customer(int a, String b){
		priority = a;
		name = b;
	}
	
	public int get(){
		return priority;	
	}
	public String getName(){
		return name;
	}
	
	public String toString(){
		return Integer.toString(priority) + " " + name;
	}
}