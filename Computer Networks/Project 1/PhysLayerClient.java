/*Edgar Ruiz
  CS 380
  January 20, 2016
*/

import java.util.Calendar;
import java.util.Random;
import java.io.OutputStream;
import java.util.Arrays;
import java.io.InputStream;
import java.net.Socket;

public final class PhysLayerClient{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("cs380.codebank.xyz", 38001);
		System.out.println("Connected to server");
		InputStream stream = socket.getInputStream();

		double[] preamble = new double[64];
		double baseLine = 0;

		for(int i = 0; i < preamble.length; i++){
			preamble[i] = (double) stream.read();
			baseLine += preamble[i];
		}
		System.out.println("BaseLine: " + baseLine);
		baseLine = (baseLine / preamble.length);
		System.out.println("Baseline established from preamble: " + baseLine);

		int[] fiveBit = new int[320];
		int upcomingNum;
		int firstNum = stream.read();
		boolean previousHighSignal;
		System.out.println("FirstNum: " + firstNum);

		if(firstNum > (int) baseLine){
			fiveBit[0] = 1;
			previousHighSignal = true;
		}
		else{
			fiveBit[0] = 0;
			previousHighSignal = false;
		}

		boolean highSignal;
		for(int j = 1; j < fiveBit.length; j++){
			upcomingNum = stream.read();
			System.out.println(j + "Upcoming num:" + upcomingNum);
			if(upcomingNum > (int) baseLine){
				highSignal = true;
			}
			else{
				highSignal = false;
			}

			if((highSignal == true) && (previousHighSignal == true)){
				fiveBit[j] = 0;
				previousHighSignal = true;
			}
			else if((highSignal == true) && (previousHighSignal == false)){
				fiveBit[j] = 1;
				previousHighSignal = true;
			}
			else if((highSignal == false) && (previousHighSignal == true)){
				fiveBit[j] = 1;
				previousHighSignal = false;
			}
			else{
				fiveBit[j] = 0;
				previousHighSignal = false;
			}
		}

		System.out.println("FiveBit: " + Arrays.toString(fiveBit));

		int[] fourBit = new int[256];
		int[] fiveBitCode = new int[5];
		int[] fourBitCode = new int[4];
		int count = 0;

		for(int k = 0; k < fiveBit.length; k+=5){
			for(int l = 0; l < 5; l++){
				fiveBitCode[l] = fiveBit[k + l];
			}
			fourBitCode = fiveBitToFourBitConversion(fiveBitCode);
			System.arraycopy(fourBitCode, 0, fourBit, count, fourBitCode.length);
			count+=4;
		}
		System.out.println("FourBit: " + Arrays.toString(fourBit));

		int[] values = convertZNOToI(fourBit);
		byte[] sendBytes = new byte[32];

		OutputStream out = socket.getOutputStream();
		
		for(int i = 0; i < sendBytes.length; i++){
			sendBytes[i] = (byte) values[i];
		}

		System.out.println("Received 32 bytes: " + bytesToHex(sendBytes).toUpperCase());
		out.write(sendBytes);

		int response = stream.read();

		if(response == 1){
			System.out.println("Response good");
		}
		else if(response == 0){
			System.out.println("Response bad");
		}
		else{
			System.out.println("Error");
		}

		System.out.println("Answer: " + bytesToHex(makeBytes()).toUpperCase());
		System.out.println("Disconnected from server");
		socket.close();
	}

	public static int[] fiveBitToFourBitConversion(int[] fiveBitCode){
		int[] compare = new int[5];
		int[] returnDecoded = new int[4];

		for(int i = 0; i < 16; i++){
			compare = fillFiveUp(i);
			if(Arrays.equals(fiveBitCode, compare)){
				returnDecoded = fillFourUp(i);
				break;
			}
		}
		return returnDecoded;
	}

	public static int[] fillFiveUp(int value){
		int[] a = new int[5];
		switch (value) {
			case 0: int[] b = {1, 1, 1, 1, 0};
					a = Arrays.copyOf(b, 5);
					break;
			case 1: int[] c = {0, 1, 0, 0, 1};
					a = Arrays.copyOf(c, 5);
					break;
			case 2: int[] d = {1, 0, 1, 0, 0};
					a = Arrays.copyOf(d, 5);
					break;
			case 3: int[] e = {1, 0, 1, 0, 1};
					a = Arrays.copyOf(e, 5);
					break;
			case 4: int[] f = {0, 1, 0, 1, 0};
					a = Arrays.copyOf(f, 5);
					break;
			case 5: int[] g = {0, 1, 0, 1, 1};
					a = Arrays.copyOf(g, 5);
					break;
			case 6: int[] h = {0, 1, 1, 1, 0};
					a = Arrays.copyOf(h, 5);
					break;
			case 7: int[] i = {0, 1, 1, 1, 1};
					a = Arrays.copyOf(i, 5);
					break;
			case 8: int[] j = {1, 0, 0, 1, 0};
					a = Arrays.copyOf(j, 5);
					break;
			case 9: int[] k = {1, 0, 0, 1, 1};
					a = Arrays.copyOf(k, 5);
					break;
			case 10: int[] l = {1, 0, 1, 1, 0};
					 a = Arrays.copyOf(l, 5);
					 break;
			case 11: int[] m = {1, 0, 1, 1, 1};
					 a = Arrays.copyOf(m, 5);
					 break;
			case 12: int[] n = {1, 1, 0, 1, 0};
					 a = Arrays.copyOf(n, 5);
					 break;
			case 13: int[] o = {1, 1, 0, 1, 1};
					 a = Arrays.copyOf(o, 5);
					 break;
			case 14: int[] p = {1, 1, 1, 0, 0};
					 a = Arrays.copyOf(p, 5);
					 break;
			case 15: int[] q = {1, 1, 1, 0, 1};
					 a = Arrays.copyOf(q, 5);
					 break;
		}
		return a;
	}

	public static int[] fillFourUp(int value){
		int[] r = new int[4];
		switch (value) {
			case 0: int[] s = {0, 0, 0, 0};
					r = Arrays.copyOf(s, 4);
					break;
			case 1: int[] t = {0, 0, 0, 1};
					r = Arrays.copyOf(t, 4);
					break;
			case 2: int[] u = {0, 0, 1, 0};
					r = Arrays.copyOf(u, 4);
					break;
			case 3: int[] v = {0, 0, 1, 1};
					r = Arrays.copyOf(v, 4);
					break;
			case 4: int[] w = {0, 1, 0, 0};
					r = Arrays.copyOf(w, 4);
					break;
			case 5: int[] x = {0, 1, 0, 1};
					r = Arrays.copyOf(x, 4);
					break;
			case 6: int[] y = {0, 1, 1, 0};
					r = Arrays.copyOf(y, 4);
					break;
			case 7: int[] z = {0, 1, 1, 1};
					r = Arrays.copyOf(z, 4);
					break;
			case 8: int[] aa = {1, 0, 0, 0};
					r = Arrays.copyOf(aa, 4);
					break;
			case 9: int[] bb = {1, 0, 0, 1};
					r = Arrays.copyOf(bb, 4);
					break;
			case 10: int[] cc = {1, 0, 1, 0};
					 r = Arrays.copyOf(cc, 4);
					 break;
			case 11: int[] dd = {1, 0, 1, 1};
					 r = Arrays.copyOf(dd, 4);
					 break;
			case 12: int[] ee = {1, 1, 0, 0};
					 r = Arrays.copyOf(ee, 4);
					 break;
			case 13: int[] ff = {1, 1, 0, 1};
					 r = Arrays.copyOf(ff, 4);
					 break;
			case 14: int[] gg = {1, 1, 1, 0};
					 r = Arrays.copyOf(gg, 4);
					 break;
			case 15: int[] hh = {1, 1, 1, 1};
					 r = Arrays.copyOf(hh, 4);
					 break;
		}
		return r;
	}

	public static int[] convertZNOToI(int[] fourBit){
		int total = 0;
		int count = 0;
		int[] a = new int[64];
		int[] b = new int[32];
		for(int j = 0; j < fourBit.length; j+=4){
			for(int i = 0; i < 4; i++){
				total += fourBit[j + i] * Math.pow(2, 3 - i);
			}
			a[count] = total;
			count++;
			total = 0;
		}
		int pos = 0;
		for(int k = 0; k < a.length; k+=2){
			int firstByte = a[k];
			int secondByte = a[k+1];
			int finalByte = (firstByte << 4) ^ secondByte;
			b[pos] = finalByte;
			pos += 1;
		}
		return b;
	}

	private static byte[] makeBytes() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis();
        Random rand = new Random(time);
        byte[] bytes = new byte[32];
        rand.nextBytes(bytes);
        return bytes;
    }

    public static String bytesToHex(byte[] bytesToDisplay) {
    	StringBuilder str = new StringBuilder();
    	for(byte b : bytesToDisplay) {
        	str.append(String.format("%02x", b));
    	}
    	return str.toString();
	}

}