package com.player;

import java.util.ArrayList;

public class Player {

    ArrayList<PawnTypes> catalogue;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<PawnTypes> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(ArrayList<PawnTypes> catalogue) {
        this.catalogue = catalogue;
    }
}
