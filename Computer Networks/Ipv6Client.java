/* Edgar Ruiz
   CS 380
   February 10, 2016
*/

import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;

public final class Ipv6Client{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("cs380.codebank.xyz", 38004);

		OutputStream os = socket.getOutputStream();

		InputStream is = socket.getInputStream();

		int packetSize;
		short packetSizeShort;
		short sum;
		byte[] serverResponse = new byte[4];

		for(int i = 0; i < 12; i++){
			packetSize = (int) Math.pow(2, i + 1) + 40;
			byte[] packet = new byte[packetSize];
			packetSizeShort = (short) (packetSize - 40);
			packet[0] = (byte) 96;
			packet[1] = 0;
			packet[2] = 0;
			packet[3] = 0;
			packet[4] = (byte) (packetSizeShort >> 8);
			packet[5] = (byte) packetSizeShort;
			packet[6] = (byte) 17;
			packet[7] = (byte) 20;
			packet[8] = 0;
			packet[9] = 0;
			packet[10] = 0;
			packet[11] = 0;
			packet[12] = 0;
			packet[13] = 0;
			packet[14] = 0;
			packet[15] = 0;
			packet[16] = 0;
			packet[17] = 0;
			packet[18] = (byte) 255;
			packet[19] = (byte) 255;
			packet[20] = (byte) 134;
			packet[21] = (byte) 71;
			packet[22] = (byte) 206;
			packet[23] = (byte) 97;
			packet[24] = 0;
			packet[25] = 0;
			packet[26] = 0;
			packet[27] = 0;
			packet[28] = 0;
			packet[29] = 0;
			packet[30] = 0;
			packet[31] = 0;
			packet[32] = 0;
			packet[33] = 0;
			packet[34] = (byte) 255;
			packet[35] = (byte) 255;
			packet[36] = (byte) 52;
			packet[37] = (byte) 33;
			packet[38] = (byte) 131;
			packet[39] = (byte) 16;
			for(int j = packetSize - 40; j > 0; j--){
				packet[j + 39] = 0;
			}
			os.write(packet);
			is.read(serverResponse);
			System.out.println("0x" + bytesToHex(serverResponse).toUpperCase());
		}
		socket.close();
		os.close();
		is.close();
		System.out.println("Server Disconnected");
		System.exit(0);
	}

	public static String bytesToHex(byte[] bytesToDisplay) {
    	StringBuilder str = new StringBuilder();
    	for(byte b : bytesToDisplay) {
        	str.append(String.format("%02x", b));
    	}
    	return str.toString();
	}
}