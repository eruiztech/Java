/*
 * Edgar Ruiz
 * CS 301
 * Programming Assignment #1
 * November 20, 2015
 */
public class Root {
	
	public static void main(String[] args) {
		double a = 0;
		double b = 100;
		bisectionMethod(a, b);  //Bisection Method on the interval [a,b] = [0,100]
	}
	
	public static void bisectionMethod(double a, double b) {
		System.out.println("Bisection Method on interval [0,100]");
		System.out.println();
		while(b - a > 1){  //stop Bisection Method when length of interval is <= 1
			double c = (a + b) / 2;  //value of c is midpoint of a and b
			System.out.println("a: " + a);
			System.out.println("b: " + b);
			if(f(c) == 0 || ((b-a) / 2) < 1) { 
				System.out.println("c: " + c); 
				System.out.println("---------------");
				double x0 = c; //value of c is assigned to x0; midpoint of interval from Bisection Method
				newtonsMethod(x0);  //Perform Newton's Method on x0
			}
			
			
			if(f(c) * f(a) > 0) { //if f(c) and f(a) are both positive or both negative
				a = c;
			}
			else { //Where f(c) and f(a) have opposite signs (positive or negative
				b = c;
			}
			System.out.println("---------------");
		}
	}
	
	public static void newtonsMethod(double x0) {
		boolean found = false; //boolean turns to true if root is found
		double x1 = 0.0; //x1 is initially 0
		int count = 0; //count is used to count iterations and used to print the different x values (x0,x1,x2, etc.)
		System.out.println("Newton's Method");
		System.out.println();
		System.out.println("x0: " + x0);
		while(found == false) { //while root is not found
			double fx0 = f(x0); //double value is returned when x0 is passed to f(x)
			double fpx0 = fprime(x0); //double value is returned when x0 is passed to f'(x)
			x1 = x0 - (fx0/fpx0);  //Value of x1 is computed by subtracting (fx0 divided by fpx0) from x0
			
			if(fpx0 - Math.pow(10.0, -5) == 0) { //Check to see if f'(x) is within 10^-5 of zero
				System.out.println("Warning: f'(x) is 10^-5 of zero.");	//print warning if above statement is true	
				System.exit(0); //Quit 
			}
			
			
			if(Math.abs(x0 - x1) <= Math.pow(10.0, -8)) { //Absolute value of subtracting x1 from x0 and making sure it is less than or equal to 10^-8 to stop iterating Newton's Method
				found = true; //Set found true when above statement is true
			}
			else {
				count++; //increase count by 1 and do another iteration
				System.out.println("x" + count + ": " + x1); //print x(count) along with value
			}
			x0 = x1; //x0 = x1 to do another iteration of Newton's Method with the use of previous value
		}
		
		if(found == true) { //if root is found
			System.out.println("Root (approx.): " + x1); //print root approximation
		}
		else {
			System.out.println("Root could not be found."); //print if root not found
		}
	}
	
	static double f(double x) { 
		return Math.pow((x-30),2) * Math.pow((x-60),2) * (Math.pow(x,2) - 2) - 10; //return value of f(x) with certain value of x
	}
	
	static double fprime(double x) { //f'(x)
		return 2 * (x-30) * (x-60) * ((3 * Math.pow(x,3)) - (180 * Math.pow(x,2)) + (1796 * x) + 180); //return value of f'(x) with certain value of x
	}
}
