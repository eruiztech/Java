/* Edgar Ruiz
   CS 380
   March 4, 2016
*/

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class TicTacToeClient{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("cs380.codebank.xyz", 38007);
		System.out.println("Connection Successful!");

		byte[][] tttBoard = new byte[3][3];

		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		Scanner kb = new Scanner(System.in);

		int userIn = 0;

		System.out.print("Enter name: ");
		String name = kb.nextLine();
		System.out.println();
		oos.writeObject(new ConnectMessage(name));
		oos.writeObject(new CommandMessage(CommandMessage.Command.NEW_GAME));

		ErrorMessage em;
		BoardMessage bm;
		Object obj;

		while(true){
			obj = ois.readObject();
			if(obj.getClass().equals(BoardMessage.class)){
				bm = (BoardMessage) obj;
				printBoard(bm.getBoard());

				if(winner(bm.getBoard()) == 1){
					System.out.println("\n" + name + " won!");
					oos.writeObject(new CommandMessage(CommandMessage.Command.EXIT));
					System.exit(0);
				}
				else if(winner(bm.getBoard()) == 2){
					System.out.println("\nComputer won!");
					oos.writeObject(new CommandMessage(CommandMessage.Command.EXIT));
					System.exit(0);
				}
				else if(winner(bm.getBoard()) == 3){
					System.out.println("\nTie Game!");
					oos.writeObject(new CommandMessage(CommandMessage.Command.EXIT));
					System.exit(0);
				}
			}
			else if(obj.getClass().equals(ErrorMessage.class)){
				em = (ErrorMessage) obj;
				System.out.println("error: " + em.getError());
			}

			System.out.println("\nEnter a number to place mark there. Enter '0' to exit game. Enter '10' to surrender.");
			System.out.print("Where should the 'X' go, " + name + "?: ");
			while(true){
				userIn = kb.nextInt();
				System.out.println();
				if(userIn == 0){
					oos.writeObject(new CommandMessage(CommandMessage.Command.EXIT));
					System.out.println("Connection closed.\nLeft Game.");
					System.exit(0);
					break;
				}
				else if(userIn == 1){
					oos.writeObject(new MoveMessage((byte) 0, (byte) 0));
					break;
				}
				else if(userIn == 2){
					oos.writeObject(new MoveMessage((byte) 0, (byte) 1));
					break;
				}
				else if(userIn == 3){
					oos.writeObject(new MoveMessage((byte) 0, (byte) 2));
					break;
				}
				else if(userIn == 4){
					oos.writeObject(new MoveMessage((byte) 1, (byte) 0));
					break;
				}
				else if(userIn == 5){
					oos.writeObject(new MoveMessage((byte) 1, (byte) 1));
					break;
				}
				else if(userIn == 6){
					oos.writeObject(new MoveMessage((byte) 1, (byte) 2));
					break;
				}
				else if(userIn == 7){
					oos.writeObject(new MoveMessage((byte) 2, (byte) 0));
					break;
				}
				else if(userIn == 8){
					oos.writeObject(new MoveMessage((byte) 2, (byte) 1));
					break;
				}
				else if(userIn == 9){
					oos.writeObject(new MoveMessage((byte) 2, (byte) 2));
					break;
				}
				else if(userIn == 10){
					oos.writeObject(new CommandMessage(CommandMessage.Command.SURRENDER));
					System.out.println("You have surrendered.\nGAME OVER");
					System.exit(0);
					break;
				}
				else{
					System.out.println("Please select a number 0-10.");
				}
			}
		}
	}
	
	public static void printBoard(byte[][] tttBoard){
		for(int i = 0; i < tttBoard.length; i++){
			for(int j = 0; j < tttBoard[i].length; j++){
				if(tttBoard[i][j] == 0){
					System.out.print(" " + (3 * i + j + 1) + " ");
				}
				else if(tttBoard[i][j] == 1){
					System.out.print(" X ");
				}
				else if(tttBoard[i][j] == 2){
					System.out.print(" O ");
				}

				if(j < tttBoard[i].length - 1){
					System.out.print("|");
				}
			}
			if(i < tttBoard.length - 1){
				System.out.print("\n - | - | - \n"); 
			}
			else{
				System.out.println();
			}
		}
	}

	public static int winner(byte[][] tttBoard){
		byte player;
		byte currLoc;
		byte sum = 0; 

		//0 for an empty square, 1 for X, and 2 for O
		for(int i = 0; i < tttBoard.length; i++){
			for(int j = 0; j < tttBoard.length; j++){
				sum += tttBoard[i][j];
			}
		}
		//tie game
		if(sum == 13){
			return 3;
		}
		//row win
		for(int k = 0; k < tttBoard.length; k++){
			player = tttBoard[k][0];
			for(int l = 0; l < tttBoard.length; l++){
				currLoc = tttBoard[k][l];
				if(player != currLoc){
					break;
				}
				if((player == currLoc) && l == 2){
					return player;
				}
			}
		}
		//column win
		for(int m = 0; m < tttBoard.length; m++){
			player = tttBoard[0][m];
			for(int n = 0; n < tttBoard.length; n++){
				currLoc = tttBoard[n][m];
				if(player != currLoc){
					break;
				}
				if((player == currLoc) && n == 2){
					return player;
				}
			}
		}
		//diagonal win top left -> bottomright
		player = tttBoard[0][0];
		for(int o = 1; o < tttBoard.length; o++){
			currLoc = tttBoard[o][o];
			if(player != currLoc){
				break;
			}
			if((player == currLoc) && o == 2){
				return player;
			}
		}
		
		//diagonal win top right -> bottom left
		player = tttBoard[0][2];
		for(int p = 1; p < tttBoard.length; p++){
			currLoc = tttBoard[p][tttBoard.length - 1 - p];
			if(player != currLoc){
				break;
			}
			if((player == currLoc) && p == 2){
				return player;
			}
		}
		return 0;
	}
}
