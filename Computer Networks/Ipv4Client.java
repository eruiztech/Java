/* Edgar Ruiz
   CS 380
   February 3, 2016
*/

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public final class Ipv4Client{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("cs380.codebank.xyz", 38003);
		System.out.println("Connection Successful!");

		OutputStream os = socket.getOutputStream();

		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		int packetSize;
		short packetSizeShort;
		short sum;

		for(int i = 0; i < 12; i++){
			packetSize = (int) Math.pow(2, i + 1) + 20;
			byte[] packet = new byte[packetSize];
			packetSizeShort = (short) packetSize;
			packet[0] = (byte) 69;
			packet[1] = 0;
			packet[2] = (byte) (packetSizeShort >> 8);
			packet[3] = (byte) packetSizeShort;
			packet[4] = 0;
			packet[5] = 0;
			packet[6] = (byte) 64;
			packet[7] = 0;
			packet[8] = (byte) 50;
			packet[9] = (byte) 6;
			packet[12] = (byte) 192;
			packet[13] = (byte) 168;
			packet[14] = (byte) 10;
			packet[15] = (byte) 1;
			packet[16] = (byte) 52;
			packet[17] = (byte) 33;
			packet[18] = (byte) 131;
			packet[19] = (byte) 16;
			sum = (short) checksumValue(packet);
			packet[10] = (byte) (sum >> 8);
			packet[11] = (byte) sum;

			for(int j = packetSize - 20; j > 0; j--){
				packet[j + 19] = 0;
			}
			os.write(packet);
			System.out.println(br.readLine());
		}
		socket.close();
		br.close();
		os.close();
		System.out.println("Server Disconnected");
		System.exit(0);
	}

	public static long checksumValue(byte[] packet){
		int packetSize = packet.length;
		long sum = 0;
		int count = 0;
		int bitMaskOne = 0xFF00;
		int bitMaskTwo = 0xFF;
		int bitMaskThree = 0xFFFF;
		int bitMaskFour = 0xFFFF0000;

		while(packetSize > 1){
			sum += (((packet[count] << 8) & bitMaskOne) | ((packet[count + 1]) & bitMaskTwo));

			if((sum & bitMaskFour) > 0){
				sum = sum & bitMaskThree;
				sum++;
			}
			count += 2;
			packetSize -= 2;
		}
		if(packetSize > 0){
			sum += (packet[count] << 8 & bitMaskOne);

			if((sum & bitMaskFour) > 0){
				sum = sum & bitMaskThree;
				sum++;
			}
		}
		return ~(sum & bitMaskThree);
	}
}