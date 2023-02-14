import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        final int PORT = 8989;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Старт сервера на порту " + PORT + "...");
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream())
                ) {
                    String word = in.readLine();
                    BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
                    String gText = engine.search(word).toString();
                    out.println(gText);
                }
            }
        } catch (IOException e) {
            System.out.println("Сервер не работает");
            e.printStackTrace();
        }
    }
}