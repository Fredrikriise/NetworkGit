import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class client {

    public static void main(String[] args) throws IOException {

        String by, temp;

        Scanner sc = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1", 5555);
        Scanner sc1 = new Scanner(s.getInputStream());
        System.out.println("Skriv inn hvilke by du holder til");
        by = sc.nextLine();
        PrintStream p = new PrintStream(s.getOutputStream());
        p.println(by);
        temp=sc1.nextLine();
        System.out.println(temp);


    }

}
