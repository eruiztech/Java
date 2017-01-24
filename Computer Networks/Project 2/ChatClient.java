/*Edgar Ruiz
  CS 380
  January 27, 2016
*/

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public final class ChatClient{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("cs380.codebank.xyz", 38002);
		System.out.println("Connection Successful!");
		System.out.print("Type in your username: ");

		InputStream ins = System.in;
		InputStreamReader insr = new InputStreamReader(ins);
		BufferedReader br = new BufferedReader(insr); 

		OutputStream os = socket.getOutputStream();
		PrintStream out = new PrintStream(os, true);

		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		final BufferedReader userIn = new BufferedReader(isr);

		String clMess;

		Thread thread = new Thread(){
			public void run(){
				String input;
				try{
					while((input = userIn.readLine()) != null){
						System.out.println(input);
					}
				}
				catch(Exception e){
					e.getMessage();
				}
			}
		};
		thread.start();

		boolean user = false;

		while((clMess = br.readLine()) != null){
			if(user == false){
				System.out.println("Connected as: " + clMess + ". You may begin sending messages below!");
				user = true;
			}
			out.println(clMess);
		}
	}
}