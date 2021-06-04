package com.pawns.military;

import com.example.spybot.R;
import com.pawns.Pawn;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;

public class PawnSniper extends Pawn {

    public PawnSniper() {
        super(false);
        this.name = "Sniper";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 8;

        this.icon = R.drawable.icon_military_sniper;

        this.pictureHead = R.drawable.layer1_military_sniper;

        this.spawnSound = R.raw.sniper;

        this.attack1 = new AttackSize("Sniper Shot", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) 1);
        this.attack2 = new AttackSpeed("Side pistol", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) 1);

    }
}
