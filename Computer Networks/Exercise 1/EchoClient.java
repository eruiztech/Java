/*Edgar Ruiz
  CS 380
  January 11, 2016
*/

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class EchoClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("localhost", 22222)) {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            InputStream us = System.in;
            InputStreamReader use = new InputStreamReader(us);
            BufferedReader userIn = new BufferedReader(use);
            String user;

            System.out.print("Client> ");
            
            while((user = userIn.readLine()) != null){
                if(user.equals("exit")){
                    System.exit(0);
                }
                out.println(user);
                System.out.println("Server> " + br.readLine());
                System.out.print("Client> ");
            }
            out.close();
            br.close();
            userIn.close();
            socket.close();
        }
    }
}
