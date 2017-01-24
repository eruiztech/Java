package edu.csupomona.cs.cs240.prog_assgmnt_2;

/**
 * CS 240: Data Structures
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * Implement the hash tables to make a registry
 * for student records. There will be an option to create an 
 * entry of the student, an option to look up a student
 * with an ID, an option to remove an entry with the 
 * provided ID, and an option to sort the students
 * by their ID.
 *
 * Edgar Ruiz 009634885
 * 
 */

import java.util.Scanner;
import java.io.*;

@SuppressWarnings("serial")
public class Test implements Serializable {

	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		// AdditiveHashingTable<String, String> table = new AdditiveHashingTable<String, String>(6);
		XorShiftHashTable<String, String> table = new XorShiftHashTable<String, String>(6);
		Scanner sc = new Scanner(System.in);

		try {

			int option = 0;

			while (option != 5) {
				System.out.println("1: ADD");
				System.out.println("2: LOOKUP");
				System.out.println("3: REMOVE");
				System.out.println("4: SORT");
				System.out.println("5: QUIT");

				option = sc.nextInt();

				switch (option) {
				case 1:
					System.out.println("Please type out a name: ");
					String name = sc.next();
					System.out.println("Please type out an ID: ");
					String idInput = sc.next();
					System.out.println("Please type out a grade: ");
					String gradeInput = sc.next();
					table.add(idInput, name, gradeInput);
					table.printTheList();
					break;
				case 2:
					System.out.println("Enter ID to look up: ");
					idInput = sc.next();
					System.out.println(table.lookup(idInput));
					break;
				case 3:
					System.out.println("Enter ID to remove: ");
					idInput = sc.next();
					table.remove(idInput);
					table.printTheList();
					break;
				case 4:
					String[] sortList = table.getSortedList(new String[] {});
					for (int i = 0; i < sortList.length; ++i) {
						System.out.println(sortList[i]);
					}
					break;
				case 5:
					System.out.println("Thank you for playing.");


					FileOutputStream fileOutput = new FileOutputStream(
							"hashingFile.ser");
					ObjectOutputStream objectOutput = new ObjectOutputStream(
							fileOutput);
					objectOutput.writeObject(table);
					objectOutput.close();
					fileOutput.close();

					break;
				default:
					System.out.println("Not sure if serious.");
					break;
				}
			}

		} catch (Exception i) {
			i.printStackTrace();
		}


		// AdditiveHashingTable<String,String> hashTable = null;
		XorShiftHashTable<String, String> hashTable = null;

		try {
			FileInputStream fileInput = new FileInputStream("hashingFile.ser");
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			// table = (AdditiveHashingTable<String, String>)
			// objectInput.readObject();
			table = (XorShiftHashTable<String, String>) objectInput
					.readObject();
			objectInput.close();
			fileInput.close();
			hashTable = table;
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Error. Can't find the class.");
			c.printStackTrace();
			return;
		}
		System.out.println("This is a deserialized message.");
		hashTable.printTheList();

		sc.close();
	}
}
