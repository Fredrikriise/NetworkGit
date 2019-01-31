package mlab1;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerTCPMultiClient
{


    public static void main(String[] args) throws IOException
    {
        int portNumber = 5555; // Default port to use

        if (args.length > 0)
        {
            if (args.length == 1)
                portNumber = Integer.parseInt(args[0]);
            else
            {
                System.err.println("Usage: java EchoUcaseServerMutiClients [<port number>]");
                System.exit(1);
            }
        }

        System.out.println("Hi, I am the EchoUCase Multi-client TCP server.");

        try (
                // Create server socket with the given port number
                ServerSocket serverSocket =
                        new ServerSocket(portNumber);
        )
        {
            String receivedText;
            // continuously listening for clients
            while (true)
            {
                // create and start a new ClientServer thread for each connected client
                ClientServer clientserver = new ServerTCPMultiClient.ClientServer(serverSocket.accept());
                clientserver.start();
            }
        } catch (IOException e)
        {

            System.out.println("Exception occurred when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }


    /***
     * This class serves a client in a separate thread
     */
    static class ClientServer<contentDiv> extends Thread
    {
        Socket connectSocket;
        InetAddress clientAddr;
        int serverPort, clientPort;

        public ClientServer(Socket connectSocket)
        {
            this.connectSocket = connectSocket;
            clientAddr = connectSocket.getInetAddress();
            clientPort = connectSocket.getPort();
            serverPort = connectSocket.getLocalPort();
        }

        public void run()
        {
            try (
                    // Create server socket with the given port number
                    PrintWriter out =
                            new PrintWriter(connectSocket.getOutputStream(), true);
                    // Stream reader from the connection socket
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connectSocket.getInputStream()));
            )
            {

                String receivedText;
                // read from the connection socket
                while (((receivedText = in.readLine()) != null))
                {
                    System.out.println("Client [" + clientAddr.getHostAddress() +  ":" + clientPort +"] > " + receivedText);

                    // Write the converted uppercase string to the connection socket
                    String outText = ProcessString(receivedText);

                    out.println(outText);
                    System.out.println("I (Server) [" + connectSocket.getLocalAddress().getHostAddress() + ":" + serverPort +"] > " + outText);
                }

                // close the connection socket
                connectSocket.close();

            } catch (IOException e)
            {
                System.out.println("Exception occurred when trying to communicate with the client " + clientAddr.getHostAddress());
                System.out.println(e.getMessage());
            }
        }

        /***
         * Process the input string and returns.
         * @param intext Input text
         * @return processed text
         */
        SimpleDateFormat date_format=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date=new Date();
        String worldTime=date_format.format(date);

        private String ProcessString(String intext)
        {
            String outtext = intext.toUpperCase();
            String city=outtext;
            String wt=worldTime;
            String search = "https://www.google.com/search?q=" +city+ " +   "+wt + " ";
            String result=city+ "  "+wt;

            return result;
        }


    }
}
