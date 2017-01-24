public class RegularFalsi
{
    /** function to find root for **/
    public double f(double x)
    {
        /** make your own function here but accordingly change (b, a) **/
        
        return Math.cos(x) - Math.cos(3*x);
    }
    /** function to find root **/
    public double findRoot(double b, double a, double e, int m)
    {
        double c = 0.0,fc;
        int n, side = 0;
        
        /** starting values at endpoints of interval **/
        double fb = f(b);
        double fa = f(a);
        
        for (n = 0; n < m; n++)
        {
            
            c = (fb * a - fa * b) / (fb - fa);
            System.out.println("c = " + c);
            if (Math.abs(a - b) < e * Math.abs(a + b))
                break;
            fc = f(c);
            System.out.println("fc = " + fc);
            
            if (fc * fa > 0)
            {
                /** fc and fa have same sign, copy c to a **/
                a = c;
                System.out.println("a = " + a);
                fa = fc;
                System.out.println("fa = " + fa);
                if (side == -1)
                    fb /= 2;
                    System.out.println("fb = " + fb);
                side = -1;
                System.out.println("side = " + side);
            }
            else if (fb * fc > 0)
            {
                /** fc and fb have same sign, copy c to b **/
                b = c;
                System.out.println("b = " + b);
                fb = fc;
                System.out.println("fb = " + fb);
                if (side == +1)
                    fa /= 2;
                    System.out.println("fa = " + fa);
                side = +1;
                System.out.println("side = " + side);
            }
            else
            {
                /** fc * f_ very small (looks like zero) **/
                break;
            }
        }
        return c;
    }
    /** Main function **/
    public static void main(String[] args)
    {
        System.out.println("Regular Falsi Test ");
        
        RegularFalsi rf = new RegularFalsi();
        /** lower limit **/
        double b = 2;
        /** upper limit **/
        double a = 1;
        /** half of upper bound for relative error **/
        double e = 5E-15;
        /** number of iterations **/
        int iterations = 5;
        
        System.out.println("\nRoot : "+ rf.findRoot(b, a, e, iterations));
    }
}
