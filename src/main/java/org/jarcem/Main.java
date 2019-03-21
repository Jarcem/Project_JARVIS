package org.jarcem;
/*
  Author: Jarcem
  Date: 2019/3/7
  Purpose: 
*/

import org.apache.catalina.LifecycleException;
import org.jarcem.http.server.TomcatServer;
import org.jarcem.speecher.Speecher;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
//        TomcatServer tomcatServer = new TomcatServer();
//        try {
//            tomcatServer.init();
//        } catch (LifecycleException e) {
//            e.printStackTrace();
//        }
        String[] strs = {"welcome home sir.", "watch out.", "be careful sir.", "always at your service sir."};
        for (int i = 0; i < strs.length; i++){
            Speecher.say(strs[i]);
        }

//        System.out.println(System.getProperty("user.dir"));
//        Server.start();
//        Recognizer.initSystem();
//        SpeechResult speechResult = null;
//        while ((speechResult = Recognizer.getLiveSpeechRecognizer().getResult()) != null) {
//            System.out.println("Recognizer result: "+speechResult.getHypothesis());
//            WebResponseServer.updataMsg(speechResult.getHypothesis().getBytes());
//            Server.updateMsg(speechResult.getHypothesis().getBytes());
//        }
    }
//    static class Server{
//        static ServerSocket serverSocket = null;
//        static void start() throws IOException {
//            serverSocket = new ServerSocket(8080);
//            while (true) {
//                Socket socket = serverSocket.accept();
//                System.out.println(socket.getInetAddress().toString());
////                updataMsg(socket);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            //获取请求包，并处理数据得到需要的内容
//                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                            //获得请求头(GET /picture.html HTTP/1.1)
//                            String line = br.readLine();
//                            System.out.println(line);
//                            //获得访问的资源(/picture.html)
////                            String path = line.split(" ")[1].substring(1);
////                            FileInputStream fis=null;
////                            try {
////                                //创建文件输入流
////                                fis= new FileInputStream(path);
////                            } catch (Exception e) {
////                                //如果访问的网页资源不存在，则返回默认的网页
////                                fis=new FileInputStream("");
////                            }
//
//                            //获得文件输出流并生成一个http响应包
//                            OutputStream os = socket.getOutputStream();
//                            os.write("HTTP/1.1 200 OK\r\n".getBytes());
//                            os.write("Content-Type:text/html\r\n".getBytes());
//                            os.write("<h3>HTTP服务器!</h3>".getBytes("utf-8"));
//                            os.write("\r\n".getBytes());
////                            byte[] buffer=new byte[1024];
////                            int len=-1;
////                            while ((len = fis.read(buffer)) != -1) {
////                                os.write(buffer,0,len);
////                            }
////                            fis.close();
//                            os.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//        }
//        static void updataMsg(Socket socket) throws IOException {
//            if (socket != null){
//                OutputStream os = socket.getOutputStream();
//                os.write("HTTP/1.1 200 OK\r\n".getBytes());
//                os.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
//                os.write("Content-Length:38\r\n".getBytes());
//                os.write("Server:gybs\r\n".getBytes());
//                os.write("\r\n".getBytes());
//                os.write("<h1>hello!</h1>".getBytes());
//                os.write("<h3>HTTP服务器!</h3>".getBytes("utf-8"));
//                os.close();
//            }
//        }
//    }
}