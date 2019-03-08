package org.jarcem.http.server;
/*
  Author: Jarcem
  Date: 2019/3/7
  Purpose: 
*/

import org.jarcem.recognizer.Recognizer;
import org.jarcem.recognizer.api.SpeechResult;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WebServer {
    public static void main(String[] args) throws IOException {
        Server.start();
//        Recognizer.initSystem();
//        SpeechResult speechResult = null;
//        while ((speechResult = Recognizer.getLiveSpeechRecognizer().getResult()) != null) {
//            System.out.println("Recognizer result: "+speechResult.getHypothesis());
//            WebResponseServer.updataMsg(speechResult.getHypothesis().getBytes());
//            Server.updateMsg(speechResult.getHypothesis().getBytes());
//        }
        Scanner sn = new Scanner(System.in);
        while (true){
            Server.updateMsg(sn.next().getBytes());
        }
    }

    static class Server{
        static ServerSocket serverSocket = null;
        static Socket socket = null;
        static OutputStream os = null;
//        static InputStream is = null;
        static void start() throws IOException {
            serverSocket = new ServerSocket(80);
            while (socket ==null){
                socket = serverSocket.accept();
                System.out.println("connected");
            }
        }

        static void updateMsg(byte[] message) throws IOException {
            if (socket != null){
                os = socket.getOutputStream();
                os.write(message);
                os.close();
            }
        }
    }
}
