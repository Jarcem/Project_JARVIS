package org.jarcem.http.server;
/*
  User: Jarcem
  Date: 2019/3/7
  Purpose: 
*/

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(80), 0);
        httpServer.createContext("/test", new WebResponseServer());
        httpServer.start();
    }

    static class WebResponseServer implements HttpHandler {

        public void handle(HttpExchange exchange) throws IOException {
            String responseStr = "Hello World";
            exchange.sendResponseHeaders(200, 0);
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(responseStr.getBytes());
            outputStream.close();
        }
    }
}
