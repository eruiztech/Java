// Ruiz, Edgar {edgarruiz}
// CS 141 03
// Project #2 : Bouncy Ball
//

import java.util.*;
public class project2 {
	public static void main(String[] args) {
		
		Scanner k = new Scanner(System.in);
		char line;
		int w = k.nextInt();
		int h = k.nextInt();
		k.nextLine();
		char[][] box = new char[h][w];
		int count = 0;
		int N = Integer.parseInt(args[0]);
		char ball = 'O';
		int x = 1;
		int y = 1;
		boolean con = true;
		
		
		for(int i = 0; i < box.length; i++){
			for(int j = 0; j < box[0].length; j++){
				box[i][j] = ' ';
			}
		}
		
		while (k.hasNext()){
			String str = k.nextLine();
			for(int a = 0; a < str.length(); a++){
				line = str.charAt(a);
				box[count][a] = line;
			} 
			count++;
		}
		
		while (con = true){
			for(int a = 0; a < box.length; a++){
				try{
					Thread.sleep( N );
				} catch ( Exception e ) {
					if(box[x+1][y+1] == '+'){
						x--;
						y--;
					}
					else if(box[x+1][y+1] == '-'){
						x--;
					}
					else if(box[x+1][y+1] == '|')
						y--;
				}
				
				for(int b = 0; b < box[a].length; b++)
				{
					box[x][y] = ball;
					System.out.print(box[a][b]);
				}	
			System.out.println();
			box[x][y] = ' ';
			}

			x++;
			y++;
			System.out.print("\033[2J\033[0;0H");

		}

	}
}
