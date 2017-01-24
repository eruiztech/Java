package edu.csupomona.cs.cs241.prog_assgmnt_1;

/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #1
 *
 *
 * @author Edgar Ruiz
 */


//import java.util.Arrays;
import java.util.Scanner;

/**
 * Class: Driver
 * 
 * This class will be used to implement a table-assigning application for a restaurant. Priority is as follows:
 * 
 * 1. VIPs
 * 2. Advance Call: customers who called in advance.
 * 3. Seniors.
 * 4. Veterans.
 * 5. Large Groups (more than 4).
 * 6. Families with children.
 * 7. Everyone else.
 * 
 * User will be host and will add customers to the heap as they come in. When user assigns table to customer, top of the heap
 * will be removed.
 * 
 * @author Edgar Ruiz
 */
public class Table{

	public static void main(String[] args){
		//NodeHeap<String> myHeap = new NodeHeap<String>(Heap.MODE.MAX); //Also works in MAX Heap mode; adjust int pr below.
		NodeHeap<String> myHeap = new NodeHeap<String>(Heap.MODE.MIN);
		Scanner kb = new Scanner(System.in);
		
		System.out.println("Welcome");
		int opt = 0;
		
		while(opt != 3){
			System.out.println("What would you like to do?(Choose 1,2, or 3)");
			System.out.print("1.Add Customer\n2.Seat Next Customer\n3.Exit\n");
			opt = kb.nextInt();
			switch(opt){
			case 1:
				System.out.println("What is the customer's name?");
				kb.nextLine();
				String cust = kb.nextLine();
				System.out.println("Which priority?\n1. VIPs\n2. Advance Call: customers who called in advance\n3. Seniors");
				System.out.println("4. Veterans\n5. Large Groups (more than 4)\n6. Families with children\n7. Everyone else");
				//int pr = 8 - kb.nextInt(); //Has to be "8 - " so it can remove highest priority from top due to it being a MAX heap
				int pr = kb.nextInt();  //if MIN heap
				Customer customer = new Customer(pr, cust);
				if(pr > 0 && pr < 8){
					myHeap.add(customer.toString());	
					System.out.println(customer.toString().substring(2) + " was successfully added!");
					//System.out.println(Arrays.toString(myHeap.toArray())); //See elements in heap
				}
				else{
					System.out.println("Please choose one of these numbers: 1,2, or 3");
				}
				break;
			case 2:
				if(myHeap.size() == 0){
					System.out.println("Add customer first before removing a customer");
				}
				else{
					System.out.println(myHeap.remove().substring(2) + " was successfully seated!");
					if(myHeap.size() != 0){
						myHeap.heapify();
						//System.out.println(Arrays.toString(myHeap.toArray())); //See elements in heap
					}
				}
				break;
			case 3:
				System.out.println("Have a nice day!");
				kb.close();
				break;
			default:
				System.out.println("Please choose one of these numbers: 1,2, or 3");
				break;
			}
		}
	}
}
