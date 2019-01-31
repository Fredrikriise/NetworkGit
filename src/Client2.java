import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;



public class Client2 {
    public static void main(String[] args) throws IOException {

        String by, temp;
        Date worldTime;

        Scanner sc = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1", 5555);
        Scanner sc1 = new Scanner(s.getInputStream());
        System.out.println("Skriv inn hvilke by du holder til: ");
        by = sc.nextLine();
        PrintStream p = new PrintStream(s.getOutputStream());
        p.println(by);
        temp=sc1.nextLine();
        System.out.println(temp);
        String search = "https://www.google.com/search?q=" + temp + "+" + "time";
        Document doc = null;
        try {
            doc = Jsoup.connect(search).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element contentDiv = doc.select("div[class=gsrt vk_bk dDoNo]").first();
        Element contentDivK = doc.select("div[class=vk_gy vk_sh]").first();
        String text = contentDivK.getElementsByTag("div").text();
        text += " " + contentDiv.getElementsByTag("div").text();
        System.out.println(text);
        //System.out.println(text.);

    }


}
