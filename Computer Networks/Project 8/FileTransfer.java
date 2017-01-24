/* Edgar Ruiz
   CS 380
   March 14, 2016
*/

import java.net.*;
import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import java.util.*;
import java.util.zip.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class FileTransfer{
	public static void main(String[] args) throws Exception{
		if (args[0].equals("makekeys")){
			makeKeys();
		}
		else if (args[0].equals("server") && args[1].equals("private.bin") && args[2].equals("38008")){
			server();
		}
		else if (args[0].equals("client") && args[1].equals("public.bin") && args[2].equals("localhost") && args[3].equals("38008")){
			File file = new File(args[1]);
			client(file);
		}
		else{
			System.out.println("Invalid input");
		}
	}

	public static void makeKeys() {
		try {
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
			gen.initialize(2048);
			KeyPair keyPair = gen.genKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("public.bin")))) {
				oos.writeObject(publicKey);
			}
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("private.bin")))) {
				oos.writeObject(privateKey);
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public static void server() throws Exception{
		try{
			ServerSocket serverSocket = new ServerSocket(38008);
			while(true){
				Socket clientSocket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());	
				ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
				System.out.println("Connection Successful!");
				Object obj;
				StartMessage stms;
				StopMessage stopms;
				Chunk chunk;
				SecretKeySpec secretKS = null;
				FileOutputStream fos = null;
				int numChunks = 0;
			

				while(true){
					obj = ois.readObject();

					if(obj.getClass().equals(StartMessage.class)){
						stms = (StartMessage) obj;
						numChunks = (int) stms.getSize() / stms.getChunkSize();

						String filesplit = stms.getFile();
						//System.out.println(filesplit.indexOf('.'));
						int dot = filesplit.indexOf('.');
						String filename = filesplit.substring(0, dot)+ "2" + filesplit.substring(dot);
						fos = new FileOutputStream(filename);

						Cipher cipher = Cipher.getInstance("RSA");
						ObjectInputStream oisOne = new ObjectInputStream(new FileInputStream("private.bin"));
						obj = oisOne.readObject();
						PrivateKey privKey = (PrivateKey) obj;
						cipher.init(Cipher.DECRYPT_MODE, privKey);
						byte[] encryptedKey = cipher.doFinal(stms.getEncryptedKey());
						secretKS = new SecretKeySpec(encryptedKey, "AES");
						oos.writeObject(new AckMessage(0));
					}

					else if(obj.getClass().equals(Chunk.class)){
						AckMessage ack;
						int ackCounter = 0;
						while(true){
							if(ackCounter != 0){
								obj = ois.readObject();
							}

							chunk = (Chunk) obj;

							if(ackCounter == chunk.getSeq()){
								Cipher cipher = Cipher.getInstance("AES");
								cipher.init(Cipher.DECRYPT_MODE, secretKS);
								byte[] data = cipher.doFinal(chunk.getData());
								Checksum checksum = new CRC32();
								checksum.update(data, 0, data.length);
								int checksumValue = (int) checksum.getValue();

								if(checksumValue == chunk.getCrc()) {
									if(ackCounter < numChunks){
										fos.write(data);
										fos.flush();
									}
									else if(ackCounter == numChunks){
										clientSocket.close();
										break;
									}
									ackCounter++;
									oos.writeObject(new AckMessage(ackCounter));
									System.out.println("Chunk received [" + ackCounter + "/" + numChunks + "].");
								}
								//Resend Ack Message in case of Bad CRC value
								else {
									oos.writeObject(new AckMessage(ackCounter));
								} 
							}
							//Resend Ack Message in case of ackCounter not matching the next sequence
							else {
								oos.writeObject(new AckMessage(ackCounter));
							}
						}
						//break out after all chunks have been received
						if(ackCounter == numChunks){
							System.out.println("Transfer Complete.");
							break;
						}
					}

					else if(obj.getClass().equals(DisconnectMessage.class)){
						clientSocket.close();
						break;
					}

					else if(obj.getClass().equals(StopMessage.class)){
						fos.close();
						oos.writeObject(new AckMessage(-1));
					}
				}
			}
		}
		catch (IOException  | ClassNotFoundException e){
			e.printStackTrace(System.err);
		}
	}

	public static void client(File file) {	
		try {
			while(true){
				Socket socket = new Socket("localhost", 38008);
				String address = socket.getInetAddress().getHostAddress();
				System.out.printf("Connected: localhost/%s%n", address);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

				System.out.println("Continue (hit enter) or disconnect (type in 'disconnect')?");
				Scanner kb = new Scanner(System.in);
				String choice = kb.nextLine();
				if(choice.equals("disconnect")){
					oos.writeObject(new DisconnectMessage());
					break;
				}

				KeyGenerator keyGen = KeyGenerator.getInstance("AES");
				keyGen.init(128);
				SecretKey secretKey = keyGen.generateKey();

				byte[] secretK = secretKey.getEncoded();
				ObjectInputStream oisOne = new ObjectInputStream(new FileInputStream("public.bin"));

				Object obj = oisOne.readObject();

				PublicKey pubKey = (PublicKey) obj;

				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, pubKey);

				byte[] encryptedKey = cipher.doFinal(secretK);

				System.out.print("Enter path: ");
				String path = kb.nextLine();
				System.out.print("Enter chunk size [1024]: ");
				int chunkSize = kb.nextInt();
				File fileOne = new File(path);
				long fileSize = fileOne.length();

				StartMessage stms = new StartMessage(path, encryptedKey, chunkSize); 

				oos.writeObject(stms);

				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				obj = ois.readObject();
				AckMessage ack = (AckMessage) obj;

				if(-1 != ack.getSeq()){
					FileInputStream fileTwo = new FileInputStream(path);
					int ackCounter = ((int) stms.getSize()) / chunkSize;
					Cipher aesCipher = Cipher.getInstance("AES");
					aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
					System.out.println("Sending: " + path + ". File size: " + stms.getSize() + ".");
					System.out.println("Sending " + ackCounter + " chunks.");
					int counter = 0;
					byte[] data;
					Checksum checksum;
					while(ack.getSeq() < ackCounter){
						if(counter >= 1){
							obj = ois.readObject();
							ack = (AckMessage) obj;
						}

						if(ackCounter != ack.getSeq()){
							data = new byte[chunkSize];
						}
						else{
							int remainder = ((int) stms.getSize()) - (chunkSize * (ack.getSeq()));
							data = new byte[remainder];
						}
						int num = fileTwo.read(data);
						checksum = new CRC32();
						checksum.update(data, 0, data.length);
						int checksumValue = (int) checksum.getValue();
						byte[] chunkData = aesCipher.doFinal(data);
						Chunk chunkDeliver = new Chunk(ack.getSeq(), chunkData, checksumValue);
						oos.writeObject(chunkDeliver);
						System.out.println("Chunk sent [" + ack.getSeq() + "/" + ackCounter + "].");
						counter++;
					}
				}
			}
		} 
		catch (IOException | ClassNotFoundException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException e) {
			e.printStackTrace(System.err);
		}
	}
}