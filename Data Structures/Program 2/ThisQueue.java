package edu.csupomona.cs.cs240.prog_assgmnt_2;

/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * The queue class contains an add, remove, 
 * and an empty method which checks if it's vacant.
 *
 * Edgar Ruiz 009634885
 * 
 */

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ThisQueue implements Serializable {

	ArrayList<ThisStack> queue = new ArrayList<ThisStack>(6);

	/**
	 * Adds characters to the end of list. Checks if
	 * the list is empty first, in order to add an element to the list.
	 * 
	 * @param c
	 */
	public void add(char letter) {
		int iter = 0;
		ThisStack curStack = new ThisStack();
		if (queue.size() == 0) {
			queue.add(curStack);
			queue.get(0).push(letter);
			return;
		}

		while (iter < queue.size()) {
			if (!queue.get(iter).maxCapacity()) {
				queue.get(iter).push(letter);
				return;
			}
			++iter;
		}

		queue.add(curStack);
		queue.get(queue.size()-1).push(letter);
	}

	/**
	 * Checks first if the list is empty. If empty, 
	 * nothing gets removed. Method removes element 
	 * from beginning of the list due to FIFO.
	 * 
	 * @return
	 */
	public char remove() {
		char letter = queue.get(0).pop();
		if (queue.size() == 0) {
			return ' ';
		}
		
		if (queue.get(0).empty()) {
			queue.remove(0);
		}
		return letter;
	}

	/**
	 * Checks queue is empty. If size is 0, then it
	 * returns true indicating that the first element 
	 * has been removed.
	 * 
	 * @return
	 */
	public boolean empty() {
		if (queue.size() == 0) {
			return true;
		}
		return false;
	}
}
