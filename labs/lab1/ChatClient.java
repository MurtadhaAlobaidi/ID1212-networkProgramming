import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {

        try {
            Socket socket = new Socket("localhost", 8080);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Scanner scanner = new Scanner(System.in);
            String string = null;

            Listener clientSock = new Listener(socket);
            Thread conClientThread = new Thread(clientSock);
            conClientThread.start();

            System.out.println("Connected to server.");

            while (true) {
                // reads user input
                string = scanner.nextLine();

                // writes to the server
                bw.write(string);
                bw.newLine();
                bw.flush();
            }
        } catch (UnknownHostException e) {
            System.out.print(e.getMessage());
        }
    }
}
/****************************************************************************************************/
/****************************************************************************************************/
/****************************************************************************************************/
class Listener implements Runnable {
    private final Socket clientSocket;
    // Constructor
    public Listener(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        BufferedReader br;
        try {
            // get the input stream of client
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String string;

            while ((string = br.readLine()) != null) {
                System.out.printf(string + "\n");
            }

            if ((string = br.readLine()) == null){
                System.out.println("Server is Closed :(");
                br.close();
                clientSocket.close();

            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

