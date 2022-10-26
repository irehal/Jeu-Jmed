package com.example.jmed;

public class Joueur {
    private Position position;
    private boolean status;

    public Joueur(Position p) {
        position = p;
    }

    public Position getPosition() { return position; }
    public boolean getStatus() { return status; }
    public void setStatus(boolean s) { status=s; }

    public void avancer(float x) {

        position.setY(position.getY()-x);
    }
}
