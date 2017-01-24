// Ruiz, Edgar {edgarruiz}
// CS 141 03
// Project #5 : Fractions
//

public class MixedFraction extends Fraction {
	
	protected int whole;
	
	public MixedFraction( int whole, int numerator, int denominator) throws Exception {	
		super( numerator , denominator );
		if( numerator > denominator ) {
			this.whole = whole + ( numerator / denominator );
			numerator = numerator % denominator;
		}
		this.whole = ( numerator < 0 ? -whole : whole);
	}
	
	public MixedFraction( int whole, Fraction F) throws Exception{
		super( F.numerator , F.denominator );
		this.whole = whole;
	}
	
	public MixedFraction( int numerator, int denominator) throws Exception {
		super( numerator % denominator , denominator );
		whole = numerator / denominator;
	}
	
	public MixedFraction( Fraction F ) throws Exception {
		super( F.numerator % F.denominator , F.denominator );
		whole = F.numerator / F.denominator;
	}
	
	public MixedFraction( int I ) throws Exception {
		super( 0 , 1 );
		whole = I;
	}
	
	public Fraction toImproper() throws Exception {
		return new Fraction ( (whole * denominator) + numerator , denominator );
	}
	
	public MixedFraction negate() throws Exception {
		return new MixedFraction ( -1 * whole , numerator, denominator);
	}
	
	public MixedFraction add( MixedFraction MF ) throws Exception {
		return new MixedFraction (  ((numerator * MF.denominator + denominator * MF.numerator) / (denominator * MF.denominator)) + whole + MF.whole ,  (numerator * MF.denominator + denominator * MF.numerator) % (denominator * MF.denominator) , denominator * MF.denominator );
	}
	
	public MixedFraction subtract( MixedFraction MF ) throws Exception {
		return new MixedFraction( MF.whole - whole - ((numerator * MF.denominator - denominator * MF.numerator) / (denominator * MF.denominator)) ,  (numerator * MF.denominator - denominator * MF.numerator) % (denominator * MF.denominator) , denominator * MF.denominator );
	}
	
	public MixedFraction multiply( MixedFraction MF ) throws Exception {
		return new MixedFraction( (((MF.whole * MF.denominator) + MF.numerator) * ((whole * denominator) + numerator))  / (MF.denominator * denominator) , (((MF.whole * MF.denominator) + MF.numerator ) * ((whole * denominator) + numerator))  % (MF.denominator * denominator)   , denominator * MF.denominator);
	}
	
	public MixedFraction divide( MixedFraction MF ) throws Exception {
		return new MixedFraction( toImproper().multiply(MF.toImproper().inverse()));
	}
	
	public String toString() {
		return whole + " " + numerator + "/" + denominator;
	}
	
	public boolean equals( MixedFraction MF ) {
		return super.equals(MF);
	}
	
	public boolean equals( Fraction F ) {
		return super.equals(F);
	}
	
}
