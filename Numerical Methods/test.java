import java.io.*;
import java.util.*;

public class test {
	public static void main (String args[]){
		String m = "Good morning, how may I help you? I289 56";
		Scanner sc = new Scanner(m);
		
		sc.skip("\\s*mo");
		String s = sc.next();
		System.out.print(s);
	}
}
