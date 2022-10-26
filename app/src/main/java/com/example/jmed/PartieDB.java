package com.example.jmed;


public class PartieDB {

    private PlayerIp player1,player2,player3;

    public PartieDB() {}

    public PartieDB(PlayerIp player1, PlayerIp player2, PlayerIp player3) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
    }

    public PlayerIp getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerIp player1) {
        this.player1 = player1;
    }

    public PlayerIp getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerIp player2) {
        this.player2 = player2;
    }

    public PlayerIp getPlayer3() {
        return player3;
    }

    public void setPlayer3(PlayerIp player3) {
        this.player3 = player3;
    }

}