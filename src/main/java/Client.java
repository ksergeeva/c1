import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final int PORT = 8989;
        final String HOST = "localhost";
                try (Socket client = new Socket(HOST, PORT);
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(
                             new BufferedWriter(
                                     new OutputStreamWriter(client.getOutputStream())), true)) {

                    out.println("Бизнес");
                    String s;
                    for (; (s = in.readLine()) != null; ) {
                        System.out.println(s);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
