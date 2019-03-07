package org.jarcem.http.server;
/*
  Author: Jarcem
  Date: 2019/3/7
  Purpose: 
*/

//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpServer;
import org.jarcem.recognizer.Recognizer;
import org.jarcem.recognizer.api.SpeechResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class WebServer {
    public static void main(String[] args) throws IOException {
//        HttpServer httpServer = HttpServer.create(new InetSocketAddress(80), 0);
//        httpServer.createContext("/test", new WebResponseServer());
//        httpServer.start();


        Server.start();
        Recognizer.initSystem();
        SpeechResult speechResult = null;
        while ((speechResult = Recognizer.getLiveSpeechRecognizer().getResult()) != null) {
            System.out.println("Recognizer result: "+speechResult.getHypothesis());
//            WebResponseServer.updataMsg(speechResult.getHypothesis().getBytes());
            Server.updateMsg(speechResult.getHypothesis().getBytes());
        }
    }

//    static class WebResponseServer implements HttpHandler {
//        private static OutputStream outputStream = null;
//        private static HttpExchange exchanged = null;
//
//        public void handle(HttpExchange exchange) throws IOException {
//            String responseStr = "Hello World";
//            exchanged = exchange;
//            exchanged.sendResponseHeaders(200, 0);
//            outputStream = exchanged.getResponseBody();
//            outputStream.write(responseStr.getBytes());
//            outputStream.close();
//        }
//
//        public static void updataMsg(byte[] message) throws IOException {
//            outputStream.write(message);
//            outputStream.close();
//        }
//    }

    static class Server{
        static ServerSocket serverSocket = null;
        static Socket socket = null;
        static OutputStream os = null;
        static void start() throws IOException {
            serverSocket = new ServerSocket(80);
            while ((socket = serverSocket.accept())==null){
                socket = serverSocket.accept();
            }
//            while (true){
//                socket = serverSocket.accept();
//                InputStream is = socket.getInputStream();
//                is.read(new byte[2048]);
                //is.close();
//                os = socket.getOutputStream();

//                os.write("HTTP/1.1 200 OK\r\n".getBytes());
//                os.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
//                os.write("Content-Length:38\r\n".getBytes());
//                os.write("Server:gybs\r\n".getBytes());
//                os.write(("Date:"+new Date()+"\r\n").getBytes());
//                os.write("\r\n".getBytes());
//                os.write("<h1>hello!</h1>".getBytes());
//                os.write("<h3>HTTP服务器!</h3>".getBytes("utf-8"));
//                os.close();
//                socket.close();
//            }
        }

        static void updateMsg(byte[] message) throws IOException {
            if (socket != null){
                os = socket.getOutputStream();
                os.write(message);
                os.notifyAll();
            }
        }
    }
}
