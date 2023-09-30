package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class SocketServer {




    public static void main(String[] args) throws IOException {

        SocketServer socketServer = new SocketServer();

        socketServer.start();

    }

    public void start(){

        BufferedReader in = null;
        PrintWriter out = null;

        boolean state;

        ServerSocket serverSocket = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            serverSocket = new ServerSocket(8000);
            System.out.println("[Server실행] Client연결대기중...");
            socket = serverSocket.accept();			// 연결대기
            System.out.println("Client 연결됨.");

            while(true){

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());



                SocketServer socketServer = new SocketServer();
                state = socketServer.pushData(in, out);

                if(!state){
                    scanner.close();		// Scanner 닫기
                    socket.close();			// Socket 닫기
                    serverSocket.close();		// ServerSocket 닫기

                    serverSocket = new ServerSocket(8000);
                    System.out.println("[Server실행] Client연결대기중...");
                    socket = serverSocket.accept();			// 연결대기
                    System.out.println("Client 연결됨.");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean pushData(BufferedReader in, PrintWriter out) throws IOException {

        String inputMessage = in.readLine();	// 수신데이터 한줄씩 읽기

        if(Objects.isNull(inputMessage)){

            SocketServer socketServer = new SocketServer();
            socketServer.start();
            return false;
        }

        if(inputMessage.equals("test")){
            String value = String.format("%.1f", 20 * (Math.random()*0.2 + 1));
            String value2 = String.format("%.1f",20 * (Math.random()*0.2f + 1));
            String outputMessage = "<temp1_"+value+">,<temp2_"+value2+">";
            out.println(outputMessage);
            out.flush();
            return true;
        }
        else if(inputMessage.equals("hello")){
            String value = "me too";
            out.println(value);
            out.flush();
            return true;
        }else{
            String value = "error";
            out.println(value);
            out.flush();
            return true;
        }

    }

}
