// Ruiz, Edgar {edgarruiz}
// CS 141 03
// Project #3 : Recursive Binary Sequence
//

import java.util.*;
public class project3 {
	
	public static void main(String[] args) {
	Scanner k = new Scanner(System.in);
	int N = Integer.parseInt(args[0]);
	int count = 0;
	populate(N, count);
	if(N%5 != 0) {
		System.out.println();
	}
	}
	
	public static void populate(int N, int count) {
			double bin = Math.pow(2, count);
			System.out.printf("%15.0f", bin);
			count++;
			if(count%5 == 0) {
				System.out.println();
			}
			if(count < N) {
				populate(N, count);
	}
	}
}
