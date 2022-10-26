package com.example.jmed;

import java.io.IOException;
import java.net.Socket;

public class ClientIp extends Thread {

    private String ip;
    private int port;
    public Socket S;

    public ClientIp(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        while(true) {
            try {
                S = new Socket(ip,port);
                break;
            }
            catch (IOException e) {
                //System.out.println(e.getMessage());
            }
        }
        System.out.println("connected successfully with " + S.getInetAddress().getHostName() + ":" + S.getPort());
    }
}
