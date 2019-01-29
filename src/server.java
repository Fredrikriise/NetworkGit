import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

    String id;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String by, temp;
       ServerSocket s1 = new ServerSocket(5555);
       Socket ss = s1.accept();
       Scanner sc = new Scanner(ss.getInputStream());
       by = sc.nextLine();

       temp = by;
       PrintStream p = new PrintStream(ss.getOutputStream());
       p.println(temp.toUpperCase());

        String search = "https://www.google.com/search?q=" + temp + "+" + "time";
        //SimpleDateFormat date_format=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        //java.util.Date date = new Date();
        //String worldTime = date_format.format(date);

        Document doc = null;
        try {
            doc = Jsoup.connect(search).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(doc.title());

        Element contentDiv = doc.select("div[class=gsrt vk_bk dDoNo]").first();
        Element contentDivK = doc.select("div[class=vk_gy vk_sh]").first();


        if(contentDiv == null) {
            System.out.println("Kunne ikke funne lokaltid til " + temp);
           // return "Kunne ikke funne lokaltid";
        }

        String text = contentDivK.getElementsByTag("div").text();
        text += " " + contentDiv.getElementsByTag("div").text();
        System.out.println(text);

    }

}
