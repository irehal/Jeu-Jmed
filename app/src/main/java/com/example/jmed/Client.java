package com.example.jmed;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private Socket S;
    public int n = 0;
    private Online activity;

    public Client(Online activity, Socket S) {
        this.activity = activity;
        this.S = S;
    }

    public void run() {
        try {
            PrintWriter PW = new PrintWriter(S.getOutputStream());
            while(!interrupted()) {
                switch(n) {
                    case 1 :
                        PW.println("1");
                        PW.flush();
                        n = 0;
                        break;
                    case 2 :
                        PW.println("2");
                        PW.flush();
                        n = 0;
                        break;
                    case 3 :
                        PW.println("3");
                        PW.flush();
                        n = 0;
                        break;
                    case 4 :
                        PW.println("4");
                        PW.flush();
                        n = 0;
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
