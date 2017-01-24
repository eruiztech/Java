/* Edgar Ruiz
   CS 380
   February 17, 2016
*/

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.nio.ByteBuffer;

public final class UdpClient{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("cs380.codebank.xyz", 38005);
		System.out.println("Connection Successful!");

		OutputStream os = socket.getOutputStream();

		InputStream is = socket.getInputStream();

        byte[] packet = ipv4Packet(24);
        byte[] serverResponse = new byte[4];

        os.write(packet);
        is.read(serverResponse);
        System.out.println("0x" + bytesToHex(serverResponse).toUpperCase());

        byte[] portNum = new byte[2];
        is.read(portNum);

        long initTime;
        long finalTime;
        long totalTime;
        long total = 0;
        int dataSize = 1;
        
        for(int i = 0; i < 12; i++){
            dataSize = dataSize * 2;
            packet = udpPacket(dataSize, portNum);
            initTime = System.currentTimeMillis();
            os.write(packet);
            is.read(serverResponse);
            finalTime = System.currentTimeMillis();
            totalTime = finalTime - initTime;
            System.out.println("0x" + bytesToHex(serverResponse).toUpperCase());
            System.out.println("RTT in ms: " + totalTime);
            total += totalTime;
        }
        System.out.println("Mean RTT is: " + (total / 12));
        socket.close();
        os.close();
        is.close();
        System.out.println("Server Disconnected");
        System.exit(0);
	}

	public static byte[] ipv4Packet(int pSize){
        int packetSize = pSize;
        short packetSizeShort = (short) packetSize;
        short sum;
        byte[] packet = new byte[packetSize];

        packet[0] = (byte) 69;
        packet[1] = 0;
        packet[2] = (byte) (packetSizeShort >> 8); 
        packet[3] = (byte) packetSizeShort;      
        packet[4] = 0;
        packet[5] = 0;
        packet[6] = (byte) 64;
        packet[7] = 0;
        packet[8] = (byte) 50;
        packet[9] = (byte) 17;
        packet[12] = (byte) 134;
        packet[13] = (byte) 71;
        packet[14] = (byte) 115;
        packet[15] = (byte) 91;
        packet[16] = (byte) 52;
        packet[17] = (byte) 33;
        packet[18] = (byte) 131;
        packet[19] = (byte) 16;
        sum = (short) checksumValue(packet);
        packet[10] = (byte) (sum >> 8);
        packet[11] = (byte) sum;
        packet[20] = (byte) 0xDE;
        packet[21] = (byte) 0xAD;
        packet[22] = (byte) 0xBE;
        packet[23] = (byte) 0xEF;
        return packet;
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

    public static String bytesToHex(byte[] bytesToDisplay) {
        StringBuilder str = new StringBuilder();
        for(byte b : bytesToDisplay) {
            str.append(String.format("%02x", b));
        }
        return str.toString();
    }

    public static byte[] udpPacket(int pSize, byte[] portNum){
        Random rand = new Random();
        int size = 8 + pSize;
        byte[] data = new byte[pSize];
        short totalDataSize = (short) ((((byte) 5) * 4) + (8 + data.length));
        short checksum = 0;

        for(int i = 0; i < data.length; i++){
            data[i] = (byte) rand.nextInt();
        }

        ByteBuffer pHeader = ByteBuffer.allocate(size);
        pHeader.putShort((short) 51248);
        pHeader.put(portNum);
        pHeader.putShort((short) size);
        pHeader.putShort((short) 0);
        pHeader.put(data);

        byte[] udp = new byte[8 + data.length];
        ByteBuffer udpBuff = ByteBuffer.wrap(udp);
        udpBuff.putShort((short) 51248);
        udpBuff.put(portNum);
        udpBuff.putShort((short) udp.length);
        udpBuff.putShort(udpChecksum(pHeader, udp.length));
        udpBuff.put(data);

        byte[] udpIpv4 = new byte[totalDataSize];
        ByteBuffer udpIpv4Buff = ByteBuffer.wrap(udpIpv4);
        int bitMask = 0xF;
        udpIpv4Buff.put((byte) (((((byte) 4) & bitMask) << 4) | (((byte) 5) & bitMask)));
        udpIpv4Buff.put((byte) 0);
        udpIpv4Buff.putShort(totalDataSize);
        udpIpv4Buff.putShort((short) 0);
        int bitMaskOne = 0x7;
        int bitMaskTwo = 0x1FFFF;
        udpIpv4Buff.putShort((short) (((((short) 0) & bitMaskOne) << 13) | (((short) 1024) & bitMaskTwo) << 4));
        udpIpv4Buff.put((byte) 50);
        udpIpv4Buff.put((byte) 0x11);
        udpIpv4Buff.putShort((short) 0);
        int sourceAdd = 0x8647735B;
        int destAdd = 0x34218310;
        udpIpv4Buff.putInt(sourceAdd);
        udpIpv4Buff.putInt(destAdd);
        checksumValueTwo(udpIpv4Buff, 5); 
        udpIpv4Buff.put(udp);
        return udpIpv4;
    }

    public static short udpChecksum(ByteBuffer pHeader, int udpLength){
        int sum = 0;
        pHeader.rewind();
        int sourceAdd = 0x8647735B;
        int destAdd = 0x34218310;
        int bitMask = 0xFFFF;
        int bitMaskTwo = 0xFF;
        sum += ((sourceAdd >> 16) & bitMask) + (sourceAdd & bitMask);
        sum += ((destAdd >> 16) & bitMask) + (destAdd & bitMask);
        sum += (byte) 17 & bitMask;
        sum += udpLength & bitMask;
        for(int i = 0; i < (udpLength / 2); i++){
            sum += pHeader.getShort() & bitMask;
        }
        if(udpLength % 2 > 0){
            sum +=  (pHeader.get() & bitMaskTwo) << 8;
        }
        sum = ((sum >> 16) & bitMask) + (sum & bitMask);
        return (short) (~sum & bitMask);
    } 

    public static void checksumValueTwo(ByteBuffer bBuff, int a){
        short checksum;
        bBuff.rewind();
        int sum = 0;
        int bitMask = 0xFFFF;
        for(int i = 0; i < (a * 2); i++){
            sum += bBuff.getShort() & bitMask;
        }
        sum = ((sum >> 16) & bitMask) + (sum & bitMask);
        checksum = (short) (~sum & bitMask);
        bBuff.putShort(10, checksum);
    }

}