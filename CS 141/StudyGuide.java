import java.util.*;
public class StudyGuide {
	
	int question;
	static int start;
	
	public StudyGuide(int question)throws Exception {
	if(question < 1) {
		throw new Exception ("Starting question is 1");
	}
	if(question > 16 && question != 141) {
		throw new Exception ("Questions are up to 16! Put 141 for the study guide!");
	}
	this.question = question;
	}
	
	public static void main(String[] args)throws Exception {
		Scanner k = new Scanner(System.in);
		String enter;

		System.out.println("Disclaimer: This will not guarantee you whatever grade you desire!");
		System.out.println("I am also probably not covering all possible topics.");
		System.out.print("Press enter to continue:");
		enter = k.nextLine();
		
		if(enter.equals("")) {
			System.out.print("\n");
			System.out.println("Questions 1-5 briefly cover the concepts behind the projects");
			System.out.println("Questions 6-10 are T/F on 2D Arrays");
			System.out.println("Questions 11-13 are T/F on recursion");
			System.out.println("Questions 14-16 are T/F on exceptions");
			System.out.println("Type 141 if you want to see the study guide section!");
			System.out.print("\n");
			System.out.print("What question would you like to start at: ");
			start = k.nextInt();
			
			StudyGuide Test141 = new StudyGuide(start);
			Test141.projects();
			Test141.arrays();
			Test141.recursion();
			Test141.exceptions141();
			Test141.studyguide();
		}
	}
	
	public void projects() {
		if(question < 6) {
		System.out.println("One word answers behind the concepts! Except for #1");
		System.out.println("Press enter for a hint!");
		Scanner k = new Scanner(System.in);
		String hint = "";
		boolean correct = false;
		
		while(question < 6) {
			System.out.print("\n");
			System.out.print("The concept behind project " + question + " was: ");
			String answer = k.nextLine();
			correct = false;
			
			if(answer.toLowerCase().contains("2d arrays") && question == 1) 
				correct = true;
			
			
			if(answer.toLowerCase().contains("animation") && question == 2) 
				correct = true;
			
		
			if(answer.toLowerCase().contains("recursion") && question == 3) 
				correct = true;
			
			
			if(answer.toLowerCase().contains("aggregation") && question == 4) 
				correct = true;
			
			
			if(answer.toLowerCase().contains("inheritance") && question == 5) 
				correct = true;
			
			if(correct) {
				System.out.println("Correct!");
				question++;
			}
			
			if(!correct) {
				if(question == 1)
					hint = "Viewed as rows and columns";
				if(question == 2)
					hint = "How did we make the ball move?";
				if(question == 3)
					hint = "A method that calls upon itself";
				if(question == 4)
					hint = "A term that means to use other classes, starts with an A";
				if(question == 5)
					hint = "All classes are derived from the Object class through...?";
				System.out.println("Hint: " + hint); 
			}
		}
		}
	}
	
	public void arrays() {
		if(question >= 6 && question <= 10) {
		System.out.println("True/False Questions on 2D Arrays, input answers as T/F");
		Scanner k = new Scanner(System.in);
		String answer = "";

		while(question >= 6 && question <= 10) {
			if(question == 6) {
				System.out.println("");
				System.out.print("All data must be homogeneous in 2D Arrays: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("t"))
					System.out.println("That is correct!");
				System.out.println("The answer is true, because like 1D arrays," +
				"they are linear collections of homogeneous data");
				question++;
			}
			
			if(question == 7) {
				System.out.println("");
				System.out.print("To initialize a String[][] array, we can use a nested-for loop: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("t"))
					System.out.println("That is correct!");
				System.out.println("The answer is true, the most common way we have been " +
				"initializing String 2D arrays is using a nested for loop, if we don't initialize it then " +
				"the contents will be returned as null!");
				question++;
			}
				
			if(question == 8) {
				System.out.println("");
				System.out.print("We can initialize a 2D array via: "
				+ "int[][] numbers = [ [ 1, 2, 3 ], [ 4, 5, 6 ], [ 7, 8, 9 ] ]; ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("f"))
					System.out.println("That is correct!");
				System.out.println("The answer is false, watch out for the brackets! "+
				"This would be the correct answer:{ { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } } );");
				question++;
			}
			
			if(question == 9) {
				System.out.println("");
				System.out.print("2D Arrays must have the same rows/cols: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("f"))
					System.out.println("That is correct!");
				System.out.println("The answer is false, there exist arrays with different "+
				"number of cols from rows, they are known as ragged arrays.");
				question++;
			}
			
			if(question == 10) {
				System.out.println("");
				System.out.print("To java an array of rows and an array of cols are the same: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("t"))
					System.out.println("That is correct!");
				System.out.println("The answer is true. IT JUST IS. Check out his lecture " +
				"on Mon Oct 13 2014, Page 3 for more info!");
				question++;
			}
		}
		}
	}
	
	public void recursion() {
		if(question >= 11 && question <= 13) {
		System.out.println("True/False Questions on Recursion, input answers as T/F");
		Scanner k = new Scanner(System.in);
		String answer = "";

		while(question >= 11 && question <= 13) {
			if(question == 11) {
				System.out.println("");
				System.out.print("Recursion is when a method calls itself: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("t"))
					System.out.println("That is correct!");
				System.out.println("The answer is true, it is what it is.");
				question++;
			}
			
			if(question == 12) {
				System.out.println("");
				System.out.print("Indirect recursion is when a method calling chain of 2 or more methods occurs: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("t"))
					System.out.println("That is correct!");
				System.out.println("The answer is true, he doesn't really explain this concept, and I am to lazy to do it for you. :D");
				question++;
			}
				
			if(question == 13) {
				System.out.println("");
				System.out.print("Recursion without a base-case can result in an infinite loop: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("f"))
					System.out.println("That is correct!");
				System.out.println("The answer is false, generally a base case is needed to terminate it, but there are exceptions!");
				question++;
				
				System.out.println("");		
			}
		}
		}
	}
	
	public void exceptions141() {
		if(question >= 14 && question <= 16) {
		System.out.println("True/False Questions on Exceptions, input answers as T/F");
		Scanner k = new Scanner(System.in);
		String answer = "";

		while(question >= 14 && question <= 16) {
			if(question == 14) {
				System.out.println("");
				System.out.print("Exceptions have a hierarchy: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("t"))
					System.out.println("That is correct!");
				System.out.println("The answer is true, exceptions are all classes and through inheritance they have a hierarchy.");
				question++;
			}
			
			if(question == 15) {
				System.out.println("");
				System.out.print("In a try and catch block, you can have multiple 'catch' blocks: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("t"))
					System.out.println("That is correct!");
				System.out.println("The answer is true, remember you can only catch an exception once! So go from specific to general.");
				question++;
			}
				
			if(question == 16) {
				System.out.println("");
				System.out.print("We can't create our own custom exceptions: ");
				answer = k.nextLine();
				if(answer.toLowerCase().contains("f"))
					System.out.println("That is correct!");
				System.out.println("The answer is false, because of inheritance it is actually really easy to create one!");
				question = 141;
				
				System.out.print("\n");
				System.out.println("Sorry, I got too lazy to continue writing questions");
				System.out.println("It requires too many if statements and what not.");
			}
		}
		}
	}
	
	public void studyguide() {
		System.out.print("\n");
		System.out.println("Here is the study guide section");
		System.out.println("-Test Cases, Wed Oct 1, 2014");
		System.out.println("-2D Arrays, Mon Oct 13, 2014");
		System.out.println("-Exceptions, Wed Oct 15, 2014 and Mon Oct 20, 2014");
		System.out.println("-Recursion, Wed Oct 22, 2014 and Mon Oct 27, 2014");
		System.out.println("-Classes and Objects, Mon Oct 27, 2014 and Wed Oct 29, 2014");
		System.out.println("-Inheritance, Mon Nov 24, 2014");
		System.out.println("-Polymorphism, Mon Dec 1, 2014");
		System.out.print("\n");
		System.out.println("Make sure you review your projects. Previous quizzes and midterms.");
		System.out.println("Study the Selection Sort and Tower of Hanoi! Review his in-class examples!");
		System.out.println("Also, there is a midterm/final from 2014 Winter. Use those to study definitions and T/F.");
		System.out.println("Good luck, and I hope to see you all in CS 240!");
		System.out.println("Try your best! Don't settle for a low score!");
		System.out.print("\n");
		System.out.println("P.S. the best way to review all of these is to code them!");
		
		System.out.print("\n");
		System.out.println("Sincerely,");
		System.out.print("\n");
		System.out.println("	a random asian dude");
	}
}

//Choi