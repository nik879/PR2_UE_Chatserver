package Chatserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient implements Runnable {
    private BufferedReader reader;
    private PrintWriter printWriter;
    private ArrayList<ChatClient> clients;
    private Socket client;
    private String name="";
    private Object LockObject = new Object();




    public ChatClient(ArrayList<ChatClient> clients, Socket client) throws IOException {
        this.clients = clients;
        this.client = client;

            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));




    }

    public void sendMessage(String message, Socket clientrs) throws IOException {

        printWriter = new PrintWriter(new OutputStreamWriter(clientrs.getOutputStream()));
        printWriter.println(message);
        printWriter.flush();



    }

    @Override
    public void run() {
        String line;
        synchronized (LockObject) {
            try {
                while (((line = reader.readLine()) != null)){
                    String[] parts=line.split(":");
                    printWriter.flush();
                    switch (parts[0]) {


                        case "name":
                            name = parts[1];
                            break;
                        case "msg":
                            if (name.equals("")) {
                                printWriter.println("before you can send a msg you have to register");
                                printWriter.flush();
                                break;
                            }
                            for (ChatClient chatClient : clients) {
//                                this.client = chatClient.client;
                                sendMessage(parts[1],chatClient.client);
                            }
                            break;
                        case "msgto":
                            if (name.equals(null)) {
                                printWriter.println("before you can send a msg you have to register");
                                printWriter.flush();
                                break;
                            }
                            /*if (parts.length < 3) {
                                printWriter.write("Fehler bei der eingabe");
                                printWriter.flush();
                                break;
                            }*/
                            for (ChatClient chatClient : clients) {
                                if (chatClient.name.equals(parts[1])) {
//                                    this.client = chatClient.client;
                                    sendMessage(parts[2], chatClient.client);
                                } /*else {
                                    printWriter.write("the user is not in the register");
                                    printWriter.flush();
                                }*/
                            }
                            break;
                        default:
                            printWriter.println("Falsche Eingabe");
                            printWriter.flush();
                            break;
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


//            close();

        }
        }



    public void close() {
        try {
            reader.close();
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
