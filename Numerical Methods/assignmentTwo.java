import java.util.Random;

public class assignmentTwo {

	public static void main(String[] args) {
		int count = 0;
		int rounds = 0;
		int[] numbers = new int[35];
		Random rand = new Random();
		
		System.out.println("Do two people out of thrity-five people share the same birthday? \n");
		while(rounds != 1000){
			for(int i = 0; i < numbers.length; i++){
				numbers[i] = rand.nextInt(365) + 1;
			}
		    

			boolean found = false;
			outerloop:
			for(int j = 0; j < numbers.length; j++){
				for(int k = j; k < numbers.length; k++){
					if(j != k){
						if(numbers[j] == numbers[k]){
							count += 1;
							found = true;
							break outerloop;
						}
					}
				}
			}
			if(found){
				System.out.println("Round " + rounds + ": Two people with the same birthday found!");
			}
			else{
				System.out.println("Round " + rounds + ": Two people do not share the same birthday!");
			}
			rounds++;
		}
		System.out.println("\nTotal Number of Occurences Where People Share Birthdays out of 1,000 iterations: " + count);
		
		int pairs = (35 * 34) / 2;
		System.out.println("\nWith " + numbers.length + " people, we have " + pairs +" pairs.");
		double probable = 1.0 - Math.pow((364.0/365.0), pairs);
		System.out.println("Probability: 1 - (364/365)^" + pairs + " = " + probable);
	}
}
