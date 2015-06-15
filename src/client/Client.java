package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) throws IOException {
        InetAddress adr = null;
        Socket clientSocket = null;
        adr = java.net.InetAddress.getByName("127.0.0.1");

        clientSocket = new Socket(adr, 80);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Envoi de la string au serveur");
        outToServer.writeBytes("GET /Koala.jpg HTTP/1.1\n"
                + "Host: 127.0.0.1\n"
                + "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0\n"
                + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n"
                + "Accept-Language: fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3\n"
                + "Accept-Encoding: gzip, deflate\n"
                + "Connection: keep-alive");
        outToServer.flush();
        clientSocket.close();
    }
}
