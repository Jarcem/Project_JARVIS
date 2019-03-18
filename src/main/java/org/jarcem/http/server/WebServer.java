package org.jarcem.http.server;
/*
  Author: Jarcem
  Date: 2019/3/7
  Purpose: 
*/

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
    }
    static class Server{
        static ServerSocket serverSocket = null;
        static Socket socket = null;
        static OutputStream os = null;
//        static InputStream is = null;
        static void start() throws IOException {
            serverSocket = new ServerSocket(808);
            while (true) {
                socket = serverSocket.accept();
                System.out.println("connected");
                updataMsg();
            }
        }
        static void updataMsg() throws IOException {
            if (socket != null){
                os = socket.getOutputStream();
                os.write("HTTP/1.1 200 OK\r\n".getBytes());
                os.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
                os.write("Content-Length:38\r\n".getBytes());
                os.write("Server:gybs\r\n".getBytes());
                os.write("\r\n".getBytes());
                os.write("<h1>hello!</h1>".getBytes());
                os.write("<h3>HTTP服务器!</h3>".getBytes("utf-8"));
                os.close();
            }
        }
    }
}