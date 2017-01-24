// Ruiz, Edgar {edgarruiz}
// CS 141 03
// Project #5 : Fractions
//

public class Fraction {

	protected int numerator;
	protected int denominator;
	
	public Fraction(){
	}
	
	public Fraction(int numerator, int denominator) throws Exception{
		if(denominator == 0) {
			throw new Exception( "Illegal 0 Denominator" );
		}
		this.numerator = ( denominator < 0 ? -numerator : numerator );
		this.denominator = ( denominator < 0 ? -denominator : denominator );
		int gcd = gcd( numerator, denominator);
		this.numerator /= gcd;
		this.denominator /= gcd;
	}
	
	public Fraction( int I ) throws Exception {
		new Fraction( I , 1 );
	}
	
	public Fraction inverse() throws Exception{
		return new Fraction( denominator, numerator );
	}
	
	public Fraction negate() throws Exception{
		return new Fraction( -1 * numerator, denominator );
	}
	
	public Fraction add(Fraction F) throws Exception{
		return new Fraction( numerator * F.denominator + denominator * F.numerator , denominator * F.denominator );
	}
	
	public Fraction subtract(Fraction F) throws Exception{
		return new Fraction( numerator * F.denominator - denominator * F.numerator , denominator * F.denominator );
	}
	
	public Fraction multiply(Fraction F) throws Exception{
		return new Fraction( numerator * F.numerator , denominator * F.denominator);
	}
	
	public Fraction divide(Fraction F) throws Exception{
		return new Fraction( numerator * F.denominator , denominator * F.numerator);
	}
	
	public String toString(){
		return numerator + "/" + denominator;
	}
	
	public boolean equals(Fraction F){
		int gcdF = gcd( F.numerator , F.denominator);
		if(( numerator == (F.numerator /= gcdF)) && ( denominator == (F.denominator /= gcdF))){
			return true;
		}
		return false;
	}
	
	private int gcd( int a , int b ){
		if( a == 0 )
			return b;
		if( b == 0)
			return a;
		if (a > b)
			return gcd( b, a % b );
		return gcd( a , b % a );
	}
	
}
