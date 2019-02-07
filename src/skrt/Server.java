package skrt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args) throws IOException {

        // server port 5555
        ServerSocket ss = new ServerSocket(5555);

        // running infinite loop for getting
        // client request
        while (true) {
            Socket s = null;

            try {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client has connected : " + s);

                //input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                //new thread object
                Thread t = new ClientHandler(s, dis, dos);
                t.start();
            } catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                // Ask user what city he is located in
                dos.writeUTF("Write the city you are currently located in.\n"+
                        "Type 'Exit' to terminate connection.");

                // receive the answer from the client
                received = dis.readUTF();

                if(received.equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                } else {
                    // Jsoup html parser
                    String search = "https://www.google.com/search?q=" + received + "+" + "time";

                    Document doc = null;
                    try {
                        doc = Jsoup.connect(search).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Element contentDiv = doc.select("div[class=gsrt vk_bk dDoNo]").first();
                    Element contentDivK = doc.select("div[class=vk_gy vk_sh]").first();

                    // Could not extract data from the div/city not found
                    if(contentDiv == null) {
                        String feilMelding = "Could not find local time in " + received;
                        System.out.println(feilMelding);
                        dos.writeUTF(feilMelding);
                    }

                    String text = contentDivK.getElementsByTag("div").text();
                    text += " " + contentDiv.getElementsByTag("div").text();
                    text += " - " + received;
                    System.out.println(text);
                    dos.writeUTF(text);

                }
                    }catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing DataInputStream and DataOutputStream
            this.dis.close();
            this.dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

