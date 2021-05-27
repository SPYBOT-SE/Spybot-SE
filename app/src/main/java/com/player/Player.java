package com.player;

import java.util.ArrayList;

public class Player {

    ArrayList<PawnType> catalogue;
    String playerName;
    int money;
    public boolean b;

    public Player(boolean b) {
        this.b = b;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void ChangeMoney(int amount){
        money += amount;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
