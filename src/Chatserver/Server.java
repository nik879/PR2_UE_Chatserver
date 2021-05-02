package Chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static int counter=0;
    public static void main(String[] args) throws IOException {

        try (ServerSocket ss = new ServerSocket(1111);) {


            ArrayList<ChatClient> chatClients = new ArrayList<>();
            while (true) {
                Socket s = ss.accept();

                System.out.println("Client " + counter + " is connected");
                ChatClient ch = new ChatClient(chatClients, s);
                chatClients.add(ch);
                counter++;
                Thread t1 = new Thread(ch);
                t1.start();

            }







            /*try (
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(s.getInputStream()));
                    BufferedWriter bw = new BufferedWriter(
                            new OutputStreamWriter(s.getOutputStream()));
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(" --> received: " + line);
                    bw.write("test");
                    bw.newLine();
                    bw.flush();
                }
                bw.newLine();
                bw.flush();
            }*/
        }
    }
}


