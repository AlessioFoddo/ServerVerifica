package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3000);
        System.out.println("Server avviato!");
        do{
            Socket s = server.accept();
            System.out.println("Un client si è collegato!");
    
            MyThread t = new MyThread(s);
            t.start();
        }while(true);
    }
}
