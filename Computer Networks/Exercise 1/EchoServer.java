/*Edgar Ruiz
  CS 380
  January 11, 2016
*/

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer implements Runnable {

    static ServerSocket serverSocket;
    static Socket socket;

    public static void main(String[] args) throws Exception {
            serverSocket = new ServerSocket(22222);
            while (true) {
                socket = serverSocket.accept();
                EchoServer echo = new EchoServer(socket);
                new Thread(echo).start();
            }
    }

    public EchoServer(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            String address = socket.getInetAddress().getHostAddress();
            System.out.printf("Client connected: %s%n", address);
            OutputStream os = socket.getOutputStream();
            PrintWriter out = new PrintWriter(os, true);
            out.printf("Hi %s, thanks for connecting!%n", address);
                
            InputStream inpSt = socket.getInputStream();
            InputStreamReader inp = new InputStreamReader(inpSt);
            BufferedReader br = new BufferedReader(inp);
            String str;

            while((str = br.readLine()) != null){
                out.println(str);
                if(str.equals("exit")){
                    break;
                }  
            }    

            System.out.printf("Client disconnected: %s%n", address);
            out.close();
            br.close(); 
            socket.close(); 
        }

        catch(Exception e){
            System.out.println("Error");
        }
    }

}
