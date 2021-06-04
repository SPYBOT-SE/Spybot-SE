package com.pawns.military;

import com.example.spybot.R;
import com.pawns.Pawn;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;

public class PawnHealer extends Pawn {

    public PawnHealer() {
        super(false);
        this.name = "Doctor";
        this.speed = 2;
        this.leftSteps = this.speed;
        this.maxSize = 3;

        this.icon = R.drawable.icon_military_healer;

        this.pictureHead = R.drawable.layer1_military_healer;

        this.attack1 = new AttackSize("Cure", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) 2);
        this.attack2 = new AttackSpeed("Steroids", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) 2);

    }
}
