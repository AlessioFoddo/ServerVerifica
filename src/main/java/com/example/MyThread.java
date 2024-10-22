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
            String scelta;
            boolean fine = false;
            do {
                try {
                    tentativi++;
                    numeroRicevuto = Integer.parseInt(in.readLine());
                    if(numeroRicevuto >= 100 || numeroRicevuto < 0){
                        risposta = "!";
                    }else if( numeroRicevuto < num ){
                        risposta = "<";
                    }else if( numeroRicevuto > num ){
                        risposta = ">";
                    }else{
                        risposta = "=";
                        fine = true;
                    }
                    System.out.println(num);
                    System.out.println("Numero ricevuto: " + numeroRicevuto);
                } catch (Exception e) {
                    System.out.println("Valore ricevuto inaccettabile");
                    risposta = "!";
                }
                out.writeBytes(risposta + "\n");
                if(fine){
                    out.writeBytes(tentativi + "\n");
                    boolean controllo = false;
                    do{
                        controllo = false;
                        scelta = in.readLine();
                        switch (scelta) {
                            case "1":
                                System.out.println(scelta);
                                out.writeBytes("1\n");
                                num = random.nextInt(100);
                                fine = false;
                                break;
                            case "0":
                            System.out.println(scelta);
                                out.writeBytes("0\n");
                                break;
                            default:
                            System.out.println(scelta);
                                out.writeBytes("!\n");
                                controllo = true;
                                break;
                        }
                    }while(controllo);
                }
            } while (!fine);
            
            s.close();
        } catch (IOException e) {
            risposta = "!";
        }
    }
    
}
