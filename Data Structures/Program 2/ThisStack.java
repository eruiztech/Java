package edu.csupomona.cs.cs240.prog_assgmnt_2;

/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * Contains 4 methods: push (add), pop (remove), max capacity 
 * (which of the character variables in the stack
 * is greater or less than 3), and an empty method (which checks if vacant).
 *
 * Edgar Ruiz 009634885
 */

import java.util.Stack;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ThisStack implements Serializable {

	Stack<Character> stack = new Stack<Character>();
	private int counter = 0;

	/**
	 * Adds to the top of the list.
	 * 
	 * @param c
	 */
	public void push(char c) {
		stack.push(c);
		++counter;
	}

	/**
	 * If the number of letters in a stack is greater than 3, it will 
	 * return true indicating that the stack is full. Else, returns false if
	 * less than 3.
	 * 
	 * @return
	 */
	public boolean maxCapacity() {
		if (counter >= 3) {
			return true;
		}
		return false;
	}

	/**
	 * If counter equals zero, returns true indicating that the
	 * stack is empty. Else, returns false if counter is greater than 0.
	 * 
	 * @return
	 */
	public boolean empty() {
		if (counter == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Pop removes an element from the top of the stack and subtracts 
	 * from counter that indicates that an element has been removed. 
	 * 
	 * @return
	 */
	public char pop() {
		--counter;
		return stack.pop();
	}

}
