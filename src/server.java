import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class server {

    public static void main(String[] args) throws IOException {
        String by, temp;
       ServerSocket s1 = new ServerSocket(5555);
       Socket ss = s1.accept();
       Scanner sc = new Scanner(ss.getInputStream());
       by = sc.nextLine();

       temp = by;
       PrintStream p = new PrintStream(ss.getOutputStream());
       p.println(temp);

        String search = "https://www.google.com/search?q=" + temp + "time";
        SimpleDateFormat date_format=new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        java.util.Date date = new Date();
        String worldTime = date_format.format(date);

       Document doc = Jsoup.connect(search).get();
       System.out.println(doc.title());
       //Elements newsHeadlines = doc.select("div.gsrt vk_bk dDoNo");
       Elements currentTime = doc.select("div.gsrt vk_bk dDoNo");
       for(Element time : currentTime) {
           System.out.println("%s\n\t%s" + time.attr("title") + time.absUrl("href"));
       }


       /*
       URL url = new URL("https://www.worldtimeserver.com");
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("GET");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("by", by);

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();


        con.setRequestProperty("User-Agent", "World Time Server");

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
*/
    }

}
