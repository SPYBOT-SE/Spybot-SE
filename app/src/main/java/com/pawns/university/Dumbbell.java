package com.pawns.university;

import com.example.spybot.R;
import com.pawns.Pawn;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;

public class Dumbbell extends Pawn {

    public Dumbbell() {
        super(false);
        this.name = "Dumbbell";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 8;
        this.pictureHead = R.drawable.hantel_head;
        this.pictureTail = R.drawable.hantel_body;
        this.pictureTailDown = R.drawable.hantel_body_down;
        this.pictureTailUp = R.drawable.hantel_body_up;
        this.pictureTailLeft = R.drawable.hantel_body_left;
        this.pictureTailRight = R.drawable.hantel_body_right;

        this.attack1 = new AttackSize("Lifting", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) 1);
        this.attack2 = new AttackSpeed("Leaping", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) 1);

    }

}
