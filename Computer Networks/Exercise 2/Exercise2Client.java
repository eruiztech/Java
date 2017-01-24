/*Edgar Ruiz
  CS 380
  January 18, 2016
*/

import java.util.zip.CRC32;
import java.util.zip.Checksum;
import java.nio.ByteBuffer;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.net.Socket;

public final class Exercise2Client{
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("cs380.codebank.xyz", 38101);
		System.out.println("Connected to server");
		InputStream stream = socket.getInputStream();

		int bytes[] = new int[100];
		byte bBytes[] = new byte[100];

		for(int i = 0; i < bytes.length; i++){
			int firstByte = stream.read();
			int secondByte = stream.read();
			int finalByte = (firstByte << 4) ^ secondByte;
			bytes[i] = finalByte;
		}

		System.out.println("Received bytes: ");
		//Just for printing Hex Values of Table
		for(int j = 0; j < bytes.length; j++){
			System.out.print(Integer.toHexString(bytes[j]).toUpperCase());
			bBytes[j] = (byte) bytes[j];
		}
		System.out.println();

		Checksum check = new CRC32();
		check.update(bBytes, 0 , bBytes.length);
		long checkValue = check.getValue();
		String hexCRC = Long.toHexString(checkValue).toUpperCase();

		System.out.println("Generated CRC32: " + hexCRC);

		byte[] crcBytes = new byte[8];

		OutputStream out = socket.getOutputStream();
		ByteBuffer bBuff = ByteBuffer.wrap(crcBytes);
		bBuff.putLong(checkValue);

		byte[] sendBytes = Arrays.copyOfRange(crcBytes, 4, 8);

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

		socket.close();
		System.out.println("Disconnected from server");
	}
}