package com.pawns;

import com.example.spybot.R;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;

public class PawnJet extends Pawn {

    public PawnJet() {
        super(false);
        this.name = "Jetplane";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 8;

        this.icon = R.drawable.icon_military_jet;

        this.pictureHead = R.drawable.layer1_military_jet;
        this.pictureTail = R.drawable.hantel_body;
        this.pictureTailDown = R.drawable.hantel_body_down;
        this.pictureTailUp = R.drawable.hantel_body_up;
        this.pictureTailLeft = R.drawable.hantel_body_left;
        this.pictureTailRight = R.drawable.hantel_body_right;


        this.spawnSound = R.raw.jet_flyby;


        this.attack1 = new AttackSize("Lifting", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) 1);
        this.attack2 = new AttackSpeed("Leaping", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) 1);

    }
}
