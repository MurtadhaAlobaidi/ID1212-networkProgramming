/** 
 * Lab3
 *@author: Murtadha Alobaidi And Abdullah Trabulsiah
 * Nätverksprogrammering: KTH ID1212-HT22-2.
 * Grupp 6
 **/

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.util.Scanner;

public class Incoming {
    public static String host = "webmail.kth.se";
    public static int port = 993;
    public static BufferedReader br;
    public static BufferedWriter bw;

    public static void main(String[] args) {
        try {
            /**
             * Establish a new connection from SSLSocket and starts a handshake
             * to set up the security attributes.
             * Inspiration from
             * "https://docs.oracle.com/javase/10/security/sample-code-illustrating-secure
             * -socket-connection-client-and-server.htm#JSSEC-GUID-AA1C27A1-2CA8-4309-B281-
             * D6199F60E666"
             **/
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socket.startHandshake();
            String response;

            // Reads in the username and password of a KTH-user account
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username (without @kth.se): ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            /**
             * Writing commands to the server,
             * the commands we use from website
             * "https://www.samlogic.net/articles/smtp-commands-reference.htm"
             * recommended by the teacher "Sten Andersson"
             **/
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("a001" + " " + "LOGIN " + username + " " + password + "\r\n");
            bw.flush();

            bw.write("a002" + " " + "list \"inbox\" \"*\"" + "\r\n");
            bw.flush();

            bw.write("a003" + " " + "select inbox" + "\r\n");
            bw.flush();

            bw.write("a004" + " " + "fetch " + 230 + " body[1]" + "\r\n");
            bw.flush();

            // Read the first response from the server
            while ((response = br.readLine()) != null) {
                System.out.println("S: " + response);

                if (response.contains(" FETCH completed")) {
                    break;
                }
            }
            br.close();
            bw.close();
            socket.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}