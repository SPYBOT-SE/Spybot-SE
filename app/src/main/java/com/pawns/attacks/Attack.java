package com.pawns.attacks;

import com.activities.MainActivity;
import com.level.Field;

public abstract class Attack {

    protected String nameOfAttack;
    protected int resource = 0;
    protected int audio;
    protected byte range;
    protected byte magnitude;

    public Attack(String attackName, int icon, int audio, byte range, byte magnitude) {
        this.nameOfAttack = attackName;
        this.resource = icon;
        this.range = range;
        this.magnitude = magnitude;
        this.audio = audio;
    }

    public abstract void performAttack(MainActivity m, Field target);

    public byte getRange() {
        return range;
    }

    public void setRange(byte range) {
        this.range = range;
    }

    public byte getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(byte magnitude) {
        this.magnitude = magnitude;
    }

    public String getNameOfAttack() {
        return nameOfAttack;
    }

    public int getResource() {
        return resource;
    }

    public int getAudio() {
        return audio;
    }
}
