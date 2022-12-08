/*
 * Lab2
 *@author: code taken from Sten Andersson.
 * Nätverksprogrammering: KTH ID1212-HT22-2. Tillägg Murtadha Alobaidi And Abdullah Trabulsiah
 * lecture F2 .
 * Grupp 6
 * */

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class HttpServer {
    static Game game;
    static int guessValue = 0;
    static Socket s;
    static int Value = 0;
    static File f;
    static BufferedReader request;

    public static void main(String[] args) throws IOException {
        System.out.println("Access to server at http://localhost:" + 8080);
        ServerSocket ss = new ServerSocket(8080);

        while (true) {
            System.out.println("Waiting for client...");
            s = ss.accept();
            System.out.println("Client connected");
            System.out.println("A new instance of the guessing game has been created...");
            getRequest(request, s);
            System.out.println("Request processed.");

            s.shutdownInput();
            s.shutdownOutput();
            s.close();
        }
    }

    public static void getGuessValue(StringTokenizer tokens) {
        while (tokens.hasMoreTokens()) {
            String word = tokens.nextToken();
            if (word.contains("guess")) {
                String[] guess = word.split("=");
                guessValue = Integer.parseInt(guess[1].trim());
            }
        }
    }

    public static void getRequest(BufferedReader request, Socket s) throws IOException {
        request = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = request.readLine();
        System.out.println(str);
        StringTokenizer tokens = new StringTokenizer(str, " ?");
        tokens.nextToken(); // The word GET
        String requestedDocument = tokens.nextToken();
        while ((str = request.readLine()) != null && str.length() > 0) {
            System.out.println(str);
        }
        getGuessValue(tokens);
        serverResponse(requestedDocument, s);
    }

    public static void serverResponse(String requestedDocument, Socket s) throws IOException {
        PrintStream response = new PrintStream(s.getOutputStream());
        response.println("HTTP/1.1 200 OK");
        response.println("Server: Trash 0.1 Beta");
        if (requestedDocument.indexOf(".html") != -1)
            response.println("Content-Type: text/html");
        if (requestedDocument.indexOf(".gif") != -1)
            response.println("Content-Type: image/gif");
        response.println("Set-Cookie: clientId=1; expires=Wednesday,31-Dec-22 21:00:00 GMT");

        switch (Value) {
            case 0:
                game = new Game();
                guessHandler(requestedDocument, response);
                break;
            case 1:
                // higher
                guessHandler(requestedDocument, response);
                break;
        }
    }

    public static void guessHandler(String requestedDocument, PrintStream response) throws IOException {
        if (!"\favicon.ico".equals(requestedDocument)) { // Ignore any additional request to retrieve the bookmark-icon.

            if (guessValue == 0) {
                f = new File(
                        "/Users/murtadha/Library/Mobile Documents/com~apple~CloudDocs/Murtadha/SKOLAN/KTH2020 Ingenjör /KTH Högskoleingenjörsdatateknik, Kista/År 3/2-ID1212 Nätverksprogrammering/2022/LABS/Lab2/labVisualStudio/index.html"
                                + requestedDocument);
                Value++;
            } else if (guessValue < game.randomNumber) {
                f = new File(
                        "/Users/murtadha/Library/Mobile Documents/com~apple~CloudDocs/Murtadha/SKOLAN/KTH2020 Ingenjör /KTH Högskoleingenjörsdatateknik, Kista/År 3/2-ID1212 Nätverksprogrammering/2022/LABS/Lab2/labVisualStudio/higher.html"
                                + requestedDocument);
            } else if (guessValue > game.randomNumber) {
                f = new File(
                        "/Users/murtadha/Library/Mobile Documents/com~apple~CloudDocs/Murtadha/SKOLAN/KTH2020 Ingenjör /KTH Högskoleingenjörsdatateknik, Kista/År 3/2-ID1212 Nätverksprogrammering/2022/LABS/Lab2/labVisualStudio/lower.html"
                                + requestedDocument);
            } else if (guessValue == game.randomNumber) {
                f = new File(
                        "/Users/murtadha/Library/Mobile Documents/com~apple~CloudDocs/Murtadha/SKOLAN/KTH2020 Ingenjör /KTH Högskoleingenjörsdatateknik, Kista/År 3/2-ID1212 Nätverksprogrammering/2022/LABS/Lab2/labVisualStudio/win.html"
                                + requestedDocument);
                // s.close();
            }

            FileInputStream infil = new FileInputStream(f);
            byte[] b = new byte[1024];
            while (infil.available() > 0) {
                response.write(b, 0, infil.read(b));
            }

        }
    }
}