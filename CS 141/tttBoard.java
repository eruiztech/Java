// Ruiz, Edgar {edgarruiz}
// CS 141 03
// Project #4 : Tic-Tac-Toe
//  

public class tttBoard{
	
	char[][] board = new char[3][3];
	int[][] board2 = new int[3][3];
	char winnerMark = ' ';
	public static final char BLANK = ' ';

    public tttBoard() {
    	int count = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
            	board[i][j] = ' ';
                board2[i][j] += count;
            }
        }
    }
	
    public String toString() {
    	return " " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + "\n"
    		   + "-----------\n" +	
    		   " " + board[1][0] + " | " + board[1][1] + " | " + board[1][2] + "\n"
    		   + "-----------\n" +
    		   " " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] ;
    }
    
    public void set(int position, char mark) {
        board[(position - 1)/3][(position-1)%3] = mark;
    }
    
    public char get(int position) {
    	return board[(position - 1)/3][(position-1)%3];
    }
    
    public void clear() {
    	for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
            	board[i][j] = ' ';
            }
    	}    
    }
    
    public boolean full() {
    	for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
            	if(board[i][j] == ' ') {
            		return false;
            	}
            }
    	}
    	return true;
    }
    
    public boolean open(int position) {
    	if(board[(position - 1)/3][(position-1)%3] != ' ')
    		return false;
    	return true;
    }
    
    public boolean winner() {
       		for (int i = 0; i < board.length; i++) {
            	if ( board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i] ) {
            		winnerMark = board[0][i];
            		return true;
            	}
            	else if (( board[0][0] != ' ' && board[0][0] == board [1][1] && board[1][1] ==  board[2][2] ) || ( board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0] )) {
            		winnerMark = board[1][1];
            		return true;
            	}
       			else if ( board[i][0] != ' ' && board[i][0] == board[i][1] && board [i][1] == board[i][2] ) {
               		winnerMark = board[i][0];
       				return true;
               	}
       		}
       return false;	
    }   	
    
    public char getWinnerMark() {
    	return winnerMark;
    }
    
}