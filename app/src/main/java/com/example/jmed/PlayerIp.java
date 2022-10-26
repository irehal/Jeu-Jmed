package com.example.jmed;

public class PlayerIp {

    private String ip;
    private int port1,port2;
    private boolean connected1;
    private boolean connected2;

    public PlayerIp() {}

    public PlayerIp(String ip, int port1, int port2, boolean connected1, boolean connected2) {
        this.ip = ip;
        this.port1 = port1;
        this.port2 = port2;
        this.connected1 = connected1;
        this.connected1 = connected2;
    }
    public PlayerIp(String ip, int port1, int port2) {
        this.ip = ip;
        this.port1 = port1;
        this.port2 = port2;
        connected1 = false;
        connected2 = false;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort1() {
        return port1;
    }

    public void setPort1(int port1) {
        this.port1 = port1;
    }

    public int getPort2() {
        return port2;
    }

    public void setPort2(int port2) {
        this.port2 = port2;
    }

    public boolean getConnected1() {
        return connected1;
    }

    public void setConnected1(boolean connected1) {
        this.connected1 = connected1;
    }

    public boolean getConnected2() {
        return connected2;
    }

    public void setConnected2(boolean connected2) {
        this.connected2 = connected2;
    }
}
