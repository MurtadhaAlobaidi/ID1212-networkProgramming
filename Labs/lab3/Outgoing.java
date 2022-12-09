/**
 * Lab3
 *@author: Murtadha Alobaidi And Abdullah Trabulsiah
 * Nätverksprogrammering: KTH ID1212-HT22-2.
 * Grupp 6
 **/
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Outgoing {
    public static String host = "smtp.kth.se";
    public static int port = 587;
    public static BufferedReader br;
    public static BufferedWriter bw;

    public static void main(String[] args) {
        try {
            /**
             * Establish a new connection from SSLSocket and starts a handshake
             * to set up the security attributes.
             **/
            Socket socket = new Socket(host, port);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String response;

            // Reads in the username and password of a KTH-user account
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username (without @kth.se): ");
            String username = scanner.nextLine();
            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            // Writing commands to the server, "Login", "List" and "Fetch"
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("EHLO " + host + "\r\n");
            bw.flush();

            bw.write("STARTTLS" + "\r\n");
            bw.flush();

            // Read the first response from the server
            while ((response = br.readLine()) != null) {
                System.out.println("S: " + response);
                if (response.contains(" Ready to start TLS")) {
                    break;
                }
            }

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            /**
             * Inspiration from
             * "https://docs.oracle.com/javase/7/docs/api/javax/net/ssl/SSLSocketFactory.html#createSocket
             * (java.net.Socket,%20java.lang.String,%20int,%20boolean)"
             **/
            socket = factory.createSocket(socket, String.valueOf(socket.getLocalPort()), socket.getPort(), true);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /**
             * Writing commands to the server,
             * the commands we use from website
             * "https://www.samlogic.net/articles/smtp-commands-reference.htm"
             * recommended by the teacher "Sten Andersson"
             **/
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bw.write("EHLO " + host + "\r\n");
            bw.flush();

            Thread.sleep(3000);

            bw.write("AUTH LOGIN" + "\r\n");
            bw.flush();

            bw.write(Base64.getEncoder().encodeToString(username.getBytes()) + "\r\n");
            bw.flush();
            bw.write(Base64.getEncoder().encodeToString(password.getBytes()) + "\r\n");
            bw.flush();

            Thread.sleep(1000);
            bw.write("MAIL FROM:<mhaao@kth.se>" + "\r\n");
            bw.flush();
            Thread.sleep(1000);

            bw.write("RCPT TO:<abdullahtr249@gmail.com>" + "\r\n");
            bw.flush();
            Thread.sleep(1000);

            bw.write("data " + "\r\n");
            bw.flush();
            Thread.sleep(1000);
            while ((response = br.readLine()) != null) {
                System.out.println("S: " + response);
                if (response.contains("End data with")) {
                    break;
                }
            }

            bw.write("Subject:Test" + "\r\n");
            bw.flush();
            Thread.sleep(1000);

            bw.write("اشلونك عيني تيست تيس : " + "\r\n");
            bw.flush();
            Thread.sleep(1000);

            bw.write("." + "\r\n");
            bw.flush();
            Thread.sleep(1000);

            while ((response = br.readLine()) != null) {
                System.out.println("S: " + response);
                if (response.contains("queued")) {
                    break;
                }
            }

            bw.write("QUIT" + "\r\n");
            bw.flush();
            while ((response = br.readLine()) != null) {
                System.out.println("S: " + response);
                if (response.contains("QUIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}