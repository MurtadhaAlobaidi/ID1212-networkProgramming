import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    public static void main(String[] args) {
        //More clients
        Set<ClientHandler> clients = new HashSet<>();

        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Server ongoing ... " + server);

            while (true) {
                //socket
                Socket userSocket = server.accept();
                //Call the ClientHandler and use Thread
                ClientHandler concurrentClient = new ClientHandler(userSocket, clients);
                clients.add(concurrentClient);
                Thread conClientThread = new Thread(concurrentClient);
                conClientThread.start();
                System.out.println("Client Conected..");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

/****************************************************************************************************/
/****************************************************************************************************/
/****************************************************************************************************/

class ClientHandler implements Runnable {
    private Socket socket;
    public int Exit;
    private Set<ClientHandler> clients = new HashSet<ClientHandler>();
    private BufferedReader br;
    private BufferedWriter bw;

    public ClientHandler(Socket socket, Set<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public void run() {
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String stringMsg = br.readLine();
                if (stringMsg == null) {
                    broadcastMsg("Client has left the server.");
                    clients.remove(this);
                    break;
                } else {
                    broadcastMsg(stringMsg);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler client : clients) {
            if (this == client) {
                continue;
            }
            try {
                client.bw.write(client.socket.getLocalAddress() + " : " + msg);
                client.bw.newLine();
                client.bw.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}