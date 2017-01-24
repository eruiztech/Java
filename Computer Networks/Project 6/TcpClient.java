/* Edgar Ruiz
   CS 380
   February 24, 2016
*/

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.nio.ByteBuffer;

public final class TcpClient{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("cs380.codebank.xyz", 38006);
		System.out.println("Connection Successful!");

		Random rand = new Random();

		OutputStream os = socket.getOutputStream();

		InputStream is = socket.getInputStream();

		byte[] ipv4pHeader = new byte[18];

		//start ipv4pHeader
		ipv4pHeader[0] = (byte) 0x45;
		ipv4pHeader[1] = 0;

		short hLength = 40;

		ipv4pHeader[2] = (byte) (hLength >> 8);
		ipv4pHeader[3] = (byte) hLength;
		ipv4pHeader[4] = 0;
		ipv4pHeader[5] = 0;
		ipv4pHeader[6] = (byte) 0x40;
		ipv4pHeader[7] = 0;
		ipv4pHeader[8] = (byte) 0x32;
		ipv4pHeader[9] = (byte) 0x06;
		ipv4pHeader[10] = (byte) 0x86;
		ipv4pHeader[11] = (byte) 0x47;
		ipv4pHeader[12] = (byte) 0xCE;
		ipv4pHeader[13] = (byte) 0x5A;
		ipv4pHeader[14] = (byte) 0x34;
		ipv4pHeader[15] = (byte) 0x21;
		ipv4pHeader[16] = (byte) 0x83;
		ipv4pHeader[17] = (byte) 0x10;
		//end ipv4pHeader

		short checksum = checksumValue(ipv4pHeader);

		byte[] header = new byte[20];

		//start header
		header[0] = (byte) 0x45;
		header[1] = 0;
		header[2] = (byte) (hLength >> 8);
		header[3] = (byte) hLength;
		header[4] = 0;
		header[5] = 0;
		header[6] = (byte) 0x40;
		header[7] = 0;
		header[8] = (byte) 0x32;
		header[9] = (byte) 0x06;
		header[10] = (byte) (checksum >> 8);
		header[11] = (byte) checksum;
		header[12] = (byte) 0x86;
		header[13] = (byte) 0x47;
		header[14] = (byte) 0xCE;
		header[15] = (byte) 0x5A;
		header[16] = (byte) 0x34;
		header[17] = (byte) 0x21;
		header[18] = (byte) 0x83;
		header[19] = (byte) 0x10;
		//end header

		ByteBuffer bBuff = ByteBuffer.wrap(header);

		int seq = rand.nextInt();

		byte[] tcppHeader = new byte[32];

		//start tcppHeader
		tcppHeader[0] = (byte) 0x86;//source address
		tcppHeader[1] = (byte) 0x47;
		tcppHeader[2] = (byte) 0xCE;
		tcppHeader[3] = (byte) 0x5A;
		tcppHeader[4] = (byte) 0x34;//destination address
		tcppHeader[5] = (byte) 0x21;
		tcppHeader[6] = (byte) 0x83;
		tcppHeader[7] = (byte) 0x10;
		tcppHeader[8] = 0;
		tcppHeader[9] = (byte) 0x06;
		tcppHeader[10] = (byte) (((short) 20 ) >> 8);// tcp Length
		tcppHeader[11] = (byte) ((short) 20);
		tcppHeader[12] = (byte) (((short) 2550 ) >> 8);// source Port
		tcppHeader[13] = (byte) ((short) 2550);
		tcppHeader[14] = (byte) (((short) 0) >> 8);// destination Port
		tcppHeader[15] = (byte) ((short) 0);

		short seqOne = (short) (seq >> 16);
		short seqTwo = (short) seq;

		tcppHeader[16] = (byte) (seqOne >> 8);// sequence One
		tcppHeader[17] = (byte) seqOne;
		tcppHeader[18] = (byte) (seqTwo >> 8);//sequence Two
		tcppHeader[19] = (byte) seqTwo;
		tcppHeader[20] = (byte) (((short) 0) >> 8);// acknowledge One
		tcppHeader[21] = (byte) ((short) 0);
		tcppHeader[22] = (byte) (((short) 0) >> 8);// acknowledge Two
		tcppHeader[23] = (byte) ((short) 0);
		tcppHeader[24] = (byte) (((short) 0x5002) >> 8);//data Flag
		tcppHeader[25] = (byte) ((short) 0x5002);
		tcppHeader[26] = (byte) (((short) 0) >> 8);//window Size
		tcppHeader[27] = (byte) ((short) 0);
		tcppHeader[28] = (byte) 0;
		tcppHeader[29] = (byte) 0;
		tcppHeader[30] = (byte) (((short) 0) >> 8);//urgent Pointer
		tcppHeader[31] = (byte) ((short) 0);
		//end tcppHeader

		short tcpChecksum = checksumValue(tcppHeader);

		byte tcOne = (byte) (tcpChecksum >> 8);
		byte tcTwo = (byte) tcpChecksum;

		byte[] tcpHeader = new byte[20];

		//start tcpHeader
		tcpHeader[0] = (byte) (((short) 2550) >> 8);//source Port
		tcpHeader[1] = (byte) ((short) 2550);
		tcpHeader[2] = (byte) (((short) 0) >> 8);//destination Port
		tcpHeader[3] = (byte) ((short) 0);
		tcpHeader[4] = (byte) (seqOne >> 8);
		tcpHeader[5] = (byte) seqOne;
		tcpHeader[6] = (byte) (seqTwo >> 8);
		tcpHeader[7] = (byte) seqTwo;
		tcpHeader[8] = (byte) (((short) 0) >> 8);//acknowledge One
		tcpHeader[9] = (byte) ((short) 0);
		tcpHeader[10] = (byte) (((short) 0) >> 8);// acknowledge Two
		tcpHeader[11] = (byte) ((short) 0);
		tcpHeader[12] = (byte) (((short) 0x5002) >> 8);//data Flag
		tcpHeader[13] = (byte) ((short) 0x5002);
		tcpHeader[14] = (byte) (((short) 0) >> 8);//window Size
		tcpHeader[15] = (byte) ((short) 0);
		tcpHeader[16] = tcOne;
		tcpHeader[17] = tcTwo;
		tcpHeader[18] = (byte) (((short) 0) >> 8);//urgent Pointer
		tcpHeader[19] = (byte) ((short) 0);
		//end tcpHeader

		os.write(header);
		os.write(tcpHeader);

		byte[] serverResponse = new byte[4];
		is.read(serverResponse);
        System.out.println("0x" + bytesToHex(serverResponse).toUpperCase());

        byte[] tcpReadIn = new byte[20];
        is.read(tcpReadIn);

        seq++;
        seqOne = (short) (seq >> 16);
        seqTwo = (short) seq;

        ByteBuffer bBuffTwo = ByteBuffer.wrap(tcpReadIn);
        int ackNumber = bBuffTwo.getInt(4);
        ackNumber++;

        short ackNumOne = (short) (ackNumber >> 16);
        short ackNumTwo = (short) ackNumber;

        byte[] tcppHeaderTwo = new byte[32];

        //start tcppHeaderTwo
        tcppHeaderTwo[0] = (byte) 0x86;//source address
		tcppHeaderTwo[1] = (byte) 0x47;
		tcppHeaderTwo[2] = (byte) 0xCE;
		tcppHeaderTwo[3] = (byte) 0x5A;
		tcppHeaderTwo[4] = (byte) 0x34;//destination address
		tcppHeaderTwo[5] = (byte) 0x21;
		tcppHeaderTwo[6] = (byte) 0x83;
		tcppHeaderTwo[7] = (byte) 0x10;
		tcppHeaderTwo[8] = 0;
		tcppHeaderTwo[9] = (byte) 0x06;
		tcppHeaderTwo[10] = (byte) (((short) 20 ) >> 8);// tcp Length
		tcppHeaderTwo[11] = (byte) ((short) 20);
		tcppHeaderTwo[12] = (byte) (((short) 2550 ) >> 8);// source Port
		tcppHeaderTwo[13] = (byte) ((short) 2550);
		tcppHeaderTwo[14] = (byte) (((short) 0) >> 8);// destination Port
		tcppHeaderTwo[15] = (byte) ((short) 0);
		tcppHeaderTwo[16] = (byte) (seqOne >> 8);// sequence One
		tcppHeaderTwo[17] = (byte) seqOne;
		tcppHeaderTwo[18] = (byte) (seqTwo >> 8);//sequence Two
		tcppHeaderTwo[19] = (byte) seqTwo;
		tcppHeaderTwo[20] = (byte) (((short) ackNumOne) >> 8);// acknowledge One
		tcppHeaderTwo[21] = (byte) ((short) ackNumOne);
		tcppHeaderTwo[22] = (byte) (((short) ackNumTwo) >> 8);// acknowledge Two
		tcppHeaderTwo[23] = (byte) ((short) ackNumTwo);
		tcppHeaderTwo[24] = (byte) (((short) 0x5010) >> 8);//data Flag
		tcppHeaderTwo[25] = (byte) ((short) 0x5010);
		tcppHeaderTwo[26] = (byte) (((short) 0) >> 8);//window Size
		tcppHeaderTwo[27] = (byte) ((short) 0);
		tcppHeaderTwo[28] = (byte) 0;
		tcppHeaderTwo[29] = (byte) 0;
		tcppHeaderTwo[30] = (byte) (((short) 0) >> 8);//urgent Pointer
		tcppHeaderTwo[31] = (byte) ((short) 0);
        //end tcppHeaderTwo

        tcpChecksum = checksumValue(tcppHeaderTwo);

        tcOne = (byte) (tcpChecksum >> 8);
        tcTwo = (byte) tcpChecksum;

        byte[] tcpHeaderTwo = new byte[20];

        //start tcpHeaderTwo
        tcpHeaderTwo[0] = (byte) (((short) 2550) >> 8);//source Port
		tcpHeaderTwo[1] = (byte) ((short) 2550);
		tcpHeaderTwo[2] = (byte) (((short) 0) >> 8);//destination Port
		tcpHeaderTwo[3] = (byte) ((short) 0);
		tcpHeaderTwo[4] = (byte) (seqOne >> 8);
		tcpHeaderTwo[5] = (byte) seqOne;
		tcpHeaderTwo[6] = (byte) (seqTwo >> 8);
		tcpHeaderTwo[7] = (byte) seqTwo;
		tcpHeaderTwo[8] = (byte) (((short) ackNumOne) >> 8);//acknowledge One
		tcpHeaderTwo[9] = (byte) ((short) ackNumOne);
		tcpHeaderTwo[10] = (byte) (((short) ackNumTwo) >> 8);// acknowledge Two
		tcpHeaderTwo[11] = (byte) ((short) ackNumTwo);
		tcpHeaderTwo[12] = (byte) (((short) 0x5010) >> 8);//data Flag
		tcpHeaderTwo[13] = (byte) ((short) 0x5010);
		tcpHeaderTwo[14] = (byte) (((short) 0) >> 8);//window Size
		tcpHeaderTwo[15] = (byte) ((short) 0);
		tcpHeaderTwo[16] = tcOne;
		tcpHeaderTwo[17] = tcTwo;
		tcpHeaderTwo[18] = (byte) (((short) 0) >> 8);//urgent Pointer
		tcpHeaderTwo[19] = (byte) ((short) 0);
        //end tcpHeaderTwo

        os.write(header);
        os.write(tcpHeaderTwo);

        is.read(serverResponse);
        System.out.println("0x" + bytesToHex(serverResponse).toUpperCase());

        int size = 2;
        short totalLength = hLength;
        short tcpLength = 20;
        for(int i = 0; i < 12; i++){
        	totalLength = (short) (hLength + size);

        	byte[] checksumData = new byte[18];

        	//start checksumData
 			checksumData[0] = (byte) 0x45;
			checksumData[1] = 0;
			checksumData[2] = (byte) (totalLength >> 8);
			checksumData[3] = (byte) totalLength;
			checksumData[4] = 0;
			checksumData[5] = 0;
			checksumData[6] = (byte) 0x40;
			checksumData[7] = 0;
			checksumData[8] = (byte) 0x32;
			checksumData[9] = (byte) 0x06;
			checksumData[10] = (byte) 0x86;//source Address
			checksumData[11] = (byte) 0x47;
			checksumData[12] = (byte) 0xCE;
			checksumData[13] = (byte) 0x5A;
			checksumData[14] = (byte) 0x34;//destination Address
			checksumData[15] = (byte) 0x21;
			checksumData[16] = (byte) 0x83;
			checksumData[17] = (byte) 0x10;
        	//end checkSumData

        	short checksumDataValue = checksumValue(checksumData);

        	byte[] headerTwo = new byte[20];

        	//start headerTwo
			headerTwo[0] = (byte) 0x45;
			headerTwo[1] = 0;
			headerTwo[2] = (byte) (totalLength >> 8);
			headerTwo[3] = (byte) totalLength;
			headerTwo[4] = 0;
			headerTwo[5] = 0;
			headerTwo[6] = (byte) 0x40;
			headerTwo[7] = 0;
			headerTwo[8] = (byte) 0x32;
			headerTwo[9] = (byte) 0x06;
			headerTwo[10] = (byte) (checksumDataValue >> 8);
			headerTwo[11] = (byte) checksumDataValue;
			headerTwo[12] = (byte) 0x86;
			headerTwo[13] = (byte) 0x47;
			headerTwo[14] = (byte) 0xCE;
			headerTwo[15] = (byte) 0x5A;
			headerTwo[16] = (byte) 0x34;
			headerTwo[17] = (byte) 0x21;
			headerTwo[18] = (byte) 0x83;
			headerTwo[19] = (byte) 0x10;
        	//end headerTwo

        	seq += (size / 2);

        	seqOne = (short) (seq >> 16);
        	seqTwo = (short) seq;

        	tcpLength = (short) (20 + size);

        	byte[] pHeader = new byte[32];

        	//start pHeader
        	pHeader[0] = (byte) 0x86;//source address
			pHeader[1] = (byte) 0x47;
			pHeader[2] = (byte) 0xCE;
			pHeader[3] = (byte) 0x5A;
			pHeader[4] = (byte) 0x34;//destination address
			pHeader[5] = (byte) 0x21;
			pHeader[6] = (byte) 0x83;
			pHeader[7] = (byte) 0x10;
			pHeader[8] = 0;
			pHeader[9] = (byte) 0x06;
			pHeader[10] = (byte) (((short) tcpLength) >> 8);// tcp Length
			pHeader[11] = (byte) ((short) tcpLength);
			pHeader[12] = (byte) (((short) 2550 ) >> 8);// source Port
			pHeader[13] = (byte) ((short) 2550);
			pHeader[14] = (byte) (((short) 0) >> 8);// destination Port
			pHeader[15] = (byte) ((short) 0);
			pHeader[16] = (byte) (seqOne >> 8);// sequence One
			pHeader[17] = (byte) seqOne;
			pHeader[18] = (byte) (seqTwo >> 8);//sequence Two
			pHeader[19] = (byte) seqTwo;
			pHeader[20] = (byte) (((short) ackNumOne) >> 8);// acknowledge One
			pHeader[21] = (byte) ((short) ackNumOne);
			pHeader[22] = (byte) (((short) ackNumTwo) >> 8);// acknowledge Two
			pHeader[23] = (byte) ((short) ackNumTwo);
			pHeader[24] = (byte) (((short) 0x5010) >> 8);//data Flag
			pHeader[25] = (byte) ((short) 0x5010);
			pHeader[26] = (byte) (((short) 0) >> 8);//window Size
			pHeader[27] = (byte) ((short) 0);
			pHeader[28] = (byte) 0;
			pHeader[29] = (byte) 0;
			pHeader[30] = (byte) (((short) 0) >> 8);//urgent Pointer
			pHeader[31] = (byte) ((short) 0);
        	//end pHeader

			byte[] rData = new byte[size];
			rand.nextBytes(rData);

			ByteBuffer bBuffThree = ByteBuffer.allocate(pHeader.length + rData.length);
			bBuffThree.put(pHeader);
			bBuffThree.put(rData);

			tcpChecksum = checksumValue(bBuffThree.array());
			tcOne = (byte) (tcpChecksum >> 8);
			tcTwo = (byte) tcpChecksum; 

			byte[] tcpHeaderThree = new byte[20];

			//start tcpHeaderThree
			tcpHeaderThree[0] = (byte) (((short) 2550) >> 8);//source Port
			tcpHeaderThree[1] = (byte) ((short) 2550);
			tcpHeaderThree[2] = (byte) (((short) 0) >> 8);//destination Port
			tcpHeaderThree[3] = (byte) ((short) 0);
			tcpHeaderThree[4] = (byte) (seqOne >> 8);
			tcpHeaderThree[5] = (byte) seqOne;
			tcpHeaderThree[6] = (byte) (seqTwo >> 8);
			tcpHeaderThree[7] = (byte) seqTwo;
			tcpHeaderThree[8] = (byte) (((short) ackNumOne) >> 8);//acknowledge One
			tcpHeaderThree[9] = (byte) ((short) ackNumOne);
			tcpHeaderThree[10] = (byte) (((short) ackNumTwo) >> 8);// acknowledge Two
			tcpHeaderThree[11] = (byte) ((short) ackNumTwo);
			tcpHeaderThree[12] = (byte) (((short) 0x5010) >> 8);//data Flag
			tcpHeaderThree[13] = (byte) ((short) 0x5010);
			tcpHeaderThree[14] = (byte) (((short) 0) >> 8);//window Size
			tcpHeaderThree[15] = (byte) ((short) 0);
			tcpHeaderThree[16] = tcOne;
			tcpHeaderThree[17] = tcTwo;
			tcpHeaderThree[18] = (byte) (((short) 0) >> 8);//urgent Pointer
			tcpHeaderThree[19] = (byte) ((short) 0);
			//end tcpHeaderThree

			ByteBuffer bBuffFour = ByteBuffer.allocate(tcpHeaderThree.length + rData.length);
			bBuffFour.put(tcpHeaderThree);
			bBuffFour.put(rData);

			os.write(headerTwo);
			os.write(bBuffFour.array());

			is.read(serverResponse);
        	System.out.println("0x" + bytesToHex(serverResponse).toUpperCase());
        	size *= 2;
        }

        tcpLength = (short) 20;

        //start tcppHeader
        tcppHeader[0] = (byte) 0x86;//source address
		tcppHeader[1] = (byte) 0x47;
		tcppHeader[2] = (byte) 0xCE;
		tcppHeader[3] = (byte) 0x5A;
		tcppHeader[4] = (byte) 0x34;//destination address
		tcppHeader[5] = (byte) 0x21;
		tcppHeader[6] = (byte) 0x83;
		tcppHeader[7] = (byte) 0x10;
		tcppHeader[8] = 0;
		tcppHeader[9] = (byte) 0x06;
		tcppHeader[10] = (byte) (((short) 20 ) >> 8);// tcp Length
		tcppHeader[11] = (byte) ((short) 20);
		tcppHeader[12] = (byte) (((short) 2550 ) >> 8);// source Port
		tcppHeader[13] = (byte) ((short) 2550);
		tcppHeader[14] = (byte) (((short) 0) >> 8);// destination Port
		tcppHeader[15] = (byte) ((short) 0);
		tcppHeader[16] = (byte) (seqOne >> 8);// sequence One
		tcppHeader[17] = (byte) seqOne;
		tcppHeader[18] = (byte) (seqTwo >> 8);//sequence Two
		tcppHeader[19] = (byte) seqTwo;
		tcppHeader[20] = (byte) (((short) ackNumOne) >> 8);// acknowledge One
		tcppHeader[21] = (byte) ((short) ackNumOne);
		tcppHeader[22] = (byte) (((short) ackNumTwo) >> 8);// acknowledge Two
		tcppHeader[23] = (byte) ((short) ackNumTwo);
		tcppHeader[24] = (byte) (((short) 0x5001) >> 8);//data Flag
		tcppHeader[25] = (byte) ((short) 0x5001);
		tcppHeader[26] = (byte) (((short) 0) >> 8);//window Size
		tcppHeader[27] = (byte) ((short) 0);
		tcppHeader[28] = (byte) 0;
		tcppHeader[29] = (byte) 0;
		tcppHeader[30] = (byte) (((short) 0) >> 8);//urgent Pointer
		tcppHeader[31] = (byte) ((short) 0);
		//end tcppHeader

		tcpChecksum = checksumValue(tcppHeader);

		tcOne = (byte) (tcpChecksum >> 8);
		tcTwo = (byte) tcpChecksum;

		//start tcpHeader
		tcpHeader[0] = (byte) (((short) 2550) >> 8);//source Port
		tcpHeader[1] = (byte) ((short) 2550);
		tcpHeader[2] = (byte) (((short) 0) >> 8);//destination Port
		tcpHeader[3] = (byte) ((short) 0);
		tcpHeader[4] = (byte) (seqOne >> 8);
		tcpHeader[5] = (byte) seqOne;
		tcpHeader[6] = (byte) (seqTwo >> 8);
		tcpHeader[7] = (byte) seqTwo;
		tcpHeader[8] = (byte) (((short) ackNumOne) >> 8);//acknowledge One
		tcpHeader[9] = (byte) ((short) ackNumOne);
		tcpHeader[10] = (byte) (((short) ackNumTwo) >> 8);// acknowledge Two
		tcpHeader[11] = (byte) ((short) ackNumTwo);
		tcpHeader[12] = (byte) (((short) 0x5001) >> 8);//data Flag
		tcpHeader[13] = (byte) ((short) 0x5001);
		tcpHeader[14] = (byte) (((short) 0) >> 8);//window Size
		tcpHeader[15] = (byte) ((short) 0);
		tcpHeader[16] = tcOne;
		tcpHeader[17] = tcTwo;
		tcpHeader[18] = (byte) (((short) 0) >> 8);//urgent Pointer
		tcpHeader[19] = (byte) ((short) 0);
		//end tcpHeader

		os.write(header);
		os.write(tcpHeader);

		is.read(serverResponse);
        System.out.println("0x" + bytesToHex(serverResponse).toUpperCase());

		socket.close();
		System.out.println("Server Disconnected");
		System.exit(0);
	}

	public static short checksumValue(byte[] packet){
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
        return (short) (~sum);
    }

    public static String bytesToHex(byte[] bytesToDisplay) {
        StringBuilder str = new StringBuilder();
        for(byte b : bytesToDisplay) {
            str.append(String.format("%02x", b));
        }
        return str.toString();
    }
}
