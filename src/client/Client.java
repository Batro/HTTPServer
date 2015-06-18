package client;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        InetAddress adr = null;
        Socket clientSocket = null;
        adr = java.net.InetAddress.getByName("127.0.0.1");
        System.out.println(adr);
        clientSocket = new Socket(adr, 80);
        Scanner sc = new Scanner(System.in);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        System.out.println("Envoi de la requete au serveur");
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Ecrivez votre requête.");
        String page = sc.next();
        outToServer.writeBytes("GET " + page + " HTTP/1.1\nConnection: keep-alive\n");
        outToServer.flush();

        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String temp = "";
        String getResp = "";
        String getHTML = "";
        String getFile = "";
        for (int i = 0; i < 5 && (temp = inFromServer.readLine()) != null; i++) {
            getResp += temp + "\n";
        }
        System.out.print("Receive : " + getResp);

        while ((temp = inFromServer.readLine()) != null) {
            if (temp.contains("<html>")) {
                getHTML += temp;
            } else {
                getFile += temp;
            }
        }
        
        System.out.print("GetHTML : " + getHTML + "End\n");
        
        System.out.print("getFile : " + getFile + "End\n");
        
        if (getHTML.contains("<html>")) {
            PrintWriter out = new PrintWriter("page.html");
            out.println(getHTML);
            out.close();
            File htmlFile = new File("page.html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        }
        if (!getFile.equals("")) {
            PrintWriter out = new PrintWriter(page);
            out.println(getFile);
            out.close();
            File htmlFile = new File(page);
            Desktop.getDesktop().browse(htmlFile.toURI());
        }
        clientSocket.close();
    }
}
