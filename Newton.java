import java.lang.*;
public class Newton {
    public static void main(String argv[]) {
        double del = 1e-6,xx = 1 ;
        double dx =0, x=2;
        int k = 0;
        while (Math.abs(xx-x) > del && k<5 && f(x)!=0) {
            dx = f(x)/d(x);
            System.out.println("dx = " + dx);
            xx=x;
            System.out.println("xx = " + xx);
            x =x - dx;
            System.out.println("x = " + x);
            k++;
            
            System.out.println("Iteration number: " + k);
            System.out.println("Root obtained: " + x);
            System.out.println("Estimated error: " + Math.abs(xx-x));
        }
    }
    
    // Method to provide function f(x)
    
    public static double f(double x) {
        return Math.cos(x)-(Math.cos(3*x));
    }
    
    // Method to provide the derivative f'(x).
    
    public static double d(double x) {
        return 3*(Math.sin(3*x))-Math.sin(x);
    }
}

