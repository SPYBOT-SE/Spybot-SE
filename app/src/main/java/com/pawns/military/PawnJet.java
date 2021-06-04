package com.pawns.military;

import com.example.spybot.R;
import com.pawns.Pawn;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;

public class PawnJet extends Pawn {

    public PawnJet() {
        super(false);
        this.name = "Jet";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 8;

        this.icon = R.drawable.icon_military_jet;

        this.pictureHead = R.drawable.layer1_military_jet;


        this.spawnSound = R.raw.jet_flyby;


        this.attack1 = new AttackSize("Lifting", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) 1);
        this.attack2 = new AttackSpeed("Leaping", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) 1);

    }
}
