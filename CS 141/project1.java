// Ruiz, Edgar {edgarruiz}
// CS 141 03
// Project #1 : Draw It 3D
// 

import java.io.*;
import java.util.*;
public class project1 {

	public static void main(String[] args) throws IOException {
		int x, y, z;
		int a, b;
		char[][] graph = new char[70][100];
		Scanner k = new Scanner(System.in); 
		for(int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph[0].length; j++) {
				graph[i][j] = ' ';
			}
		}

		while(k.hasNextInt()) {
			x = k.nextInt();
			y = k.nextInt();
			z = k.nextInt();
				if(x >= 0) {
					a = (int) (x+.5*z);
					b = (int) (y+.5*z);
					graph[a][b] = '.';
				}
		}
		
		for(int m = 0; m < graph.length; m++) {
			for(int n = 0; n < graph[0].length; n++) {
				System.out.print(graph[m][n]);
			}
		System.out.println();
		}
	}
}

		
	
