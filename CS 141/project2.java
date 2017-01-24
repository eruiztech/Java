import java.util.Scanner;
public class project2 {
    public static void main( String[] args ) {
	Scanner k = new Scanner( System.in );
	char[][] box;                     // [ Ycoord ][ Xcoord ]
	int[] dims = new int[2];          // Width, Height
	int[] ball = { 1, 1, 1, 1, 500 }; // X, Y; Xinc, Yinc, Delay
	
	// cheat, use ball[4] as the time to pause since 'ball' is
	// already being passed to other methods
	ball[4] = Integer.parseInt( args[0] );
	
	dims = get_dimensions( k );
	box = load_box( k, dims );
	place_ball( box, ball, 1, 1 );
	animate( box, ball );
    }

    public static int[] get_dimensions( Scanner k ) {
	int[] tmp = new int[2];
	
	tmp[0] = k.nextInt();
	tmp[1] = k.nextInt();
	k.nextLine(); // Skip rest of line with dimensions.
	return tmp;
    }

    public static char[][] load_box( Scanner k, int[] dims ) {
	String line = "";
	char[][] mybox = new char[ dims[1] ][ dims[0] ];

	// Read in dims[0] rows
	for ( int y = 0; y < dims[1]; y++ ) { 
	    // For each row get the line
	    line = k.nextLine();
	    load_row_into_box( y, line, mybox );
	}
	return mybox;
    }

    public static void load_row_into_box( int y, String line, char[][] B ) {
	// Only load in B[0].length worth of 'line'.  They *should*
	//  be equal
	if ( line.length() != B[0].length ) {
	    // Warn the user there is an issue
	    System.out.print( "Error: Row " + y + " has " +
			      line.length() + " chars in data file but " +
			      "the box is " + B[0].length + "\n" );
	    // Since this is suppose to animate, let's just quit
	    System.exit(0);
	}
	// Else move on and load the line
	for ( int x = 0; x < line.length(); x++ ) {
	    insert2Box( B, line.charAt( x ), x, y ); 
	}

	// Done. Nothing to return.
    }

    public static void animate( char[][] box, int[] ball ) {
	// LOOP: clear screen and put cursor at 0,0
	// print box
	// pause
	// update box
	// goto LOOP

	while ( true ) {
	    clear_screen();
	    print_box( box );
	    pause( ball[4] );
	    update_box( box, ball );
	}
    }

    public static void place_ball( char[][] B, int[] ball, int x, int y ) {
	// First clear where the ball was
	insert2Box( B, ' ', ball[ 0 ], ball[ 1 ] );
	insert2Box( B, 'O', x, y );
	ball[0] = x;
	ball[1] = y;
    }

    public static void update_box( char[][] box, int[] ball ) {
	// Need to see if the next position will be a wall and if so,
	// raise the exception 
	char next_pos_char = getFromBox( box, ball[ 0 ] + ball[ 2 ],
					 ball[ 1 ] + ball[ 3 ] );

	if ( next_pos_char == '-' ) {
	    reverse_y( ball );
	} else if ( next_pos_char == '|' ) {
	    reverse_x( ball );
	} else if ( next_pos_char == '+' ) {
	    reverse_y( ball );
	    reverse_x( ball );
	} 
	place_ball( box, ball, ball[0] + ball[2], ball[1] + ball[3] );
    }

    public static void reverse_y( int[] ball ) {
	ball[3] *= -1;
    }

    public static void reverse_x( int[] ball ) {
	ball[2] *= -1;
    }

    public static void print_box( char[][] box ) {
	for ( int y = 0; y < box.length; y++ ) {
	    for ( int x = 0; x < box[0].length; x++ ) {
		System.out.print( getFromBox( box, x, y ) );
	    }
	    System.out.print( "\n" );
	}
    }

    public static void clear_screen() {
	System.out.print( "\033[2J\033[0;0H" );
    }

    public static void pause( int time ) {
	try {
	    Thread.sleep( time );
	} catch ( Exception e ) {
	}
    }

    public static void insert2Box( char[][] box, char C, int X, int Y ) {
	box[ Y ][ X ] = C;
    }

    public static char getFromBox( char[][] box, int X, int Y ) {
	return box[ Y ][ X ];
    }

}