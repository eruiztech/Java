/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #1
 *
 * Array Lists and Linked Lists
 *
 * Edgar Ruiz 009634885
 */
package edu.csupomona.cs.cs240.prog_assgmnt_1;
import java.util.*;

public class TestDrive {

	private static Scanner kb;

	public static void main(String[] args) {   
		Scanner kb = new Scanner(System.in);
		int size = 100000;
		SimpleBoundedList<Integer, String> newList = new SimpleBoundedList<Integer, String>(size);
		
		int id = 0;
		String name = null;
		System.out.print("Enter name: ");
		name = kb.nextLine();
		System.out.print("Enter ID: ");
		id = kb.nextInt();

		newList.add(id,name);
		
		System.out.println(newList);
		String input = instructions();
		

	    do {
	    	/** Adds name to the list
	    	  * 
	    	  */
			if(input.equals("1")) {
				System.out.print("Enter name: ");
				name = kb.nextLine();
				name = kb.nextLine();
			
				System.out.print("Enter ID: ");
				id = kb.nextInt();
				
				newList.add(id,name);
				System.out.println(newList);
				input = instructions();
			}
			/** Alphabetizes the list
			  * 
			  */
			if(input.equals("2")) {
				Object[] array = newList.toArray();
				String[] newArray = new String[array.length];
				int i = 0;
				while (i != array.length) {
					newArray[i] = array[i].toString();
					++i;
				}
				Arrays.sort(newArray);

				i = 0;
				while (i != newArray.length) {
					System.out.println(newArray[i]);
					++i;
				}
				System.out.println(newList);
				input = instructions();
			}
			/** Finds name within list using ID
			  * 	
			  */
			if(input.equals("3")) {
				System.out.print("Enter the ID: ");
				id = kb.nextInt();
				try {
					String result = newList.lookup(id);
					System.out.println("Name found: " + result);
					
				}
				catch(NullPointerException e) {
					System.out.print("Name not found");
				}
				System.out.println(newList);
				input = instructions();
					
			}
			/** Exits program
			  * 
			  */
			if(input.equals("4")) {
				System.out.println("Finished");
				kb.close();
			}
		}
		while(input.equals("1") || input.equals("2") || input.equals("3"));			
		
	}
	/** Part of program meant to show options to user
	  * 
	  * 
	  */
	public static String instructions () {
		System.out.println("");
		System.out.println("Enter an option: \n" + 
				   "1) Add more names and IDs \n" + 
				   "2) Alphabatical order \n" +
				   "3) Enter ID to find name \n" +
	        	   "4) Exit");
		System.out.println("");
		System.out.print("Enter your option: ");
		kb = new Scanner(System.in);
		String userInput = kb .nextLine();
		return userInput;
	}
}
