package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class MyThread extends Thread {
      
    private Socket s;
    private Random random;

    public MyThread(Socket s){
        this.s = s;
        random = new Random();
    }

    public void run() {
        String risposta;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            int tentativi = 0;
            int num = random.nextInt(100);
            int numeroRicevuto;
            do {
                try {
                    numeroRicevuto = Integer.parseInt(in.readLine());
                    if(numeroRicevuto >= 100 || numeroRicevuto < 0){
                        risposta = "!";
                    }else if( numeroRicevuto < num ){
                        risposta = "<";
                    }else if( numeroRicevuto > num ){
                        risposta = ">";
                    }else{
                        risposta = "=";
                    }
                    System.out.println("Numero ricevuto: " + numeroRicevuto);
                } catch (Exception e) {
                    System.out.println("Valore ricevuto inaccettabile");
                    risposta = "!";
                }
                tentativi++;
                out.writeBytes(risposta + "\n");
            } while (!(risposta.equals("=")));
            out.writeBytes(tentativi + "\n");
            s.close();
        } catch (IOException e) {
            risposta = "!";
        }
    }
    
}
