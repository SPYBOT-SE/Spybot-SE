package com.pawns.military;

import com.example.spybot.R;
import com.pawns.Pawn;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;
import com.pawns.attacks.AttackHeal;

public class PawnTank extends Pawn {

    public PawnTank() {
        super(false);
        this.name = "Tank";
        this.speed = 3;
        this.leftSteps = this.speed;
        this.maxSize = 10;

        this.icon = R.drawable.icon_military_tank;

        this.pictureHead = R.drawable.layer1_military_tank;

        this.spawnSound = R.raw.tank_moving;


        this.attack1 = new AttackHeal("Canon", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 3, (byte) -5);
        this.attack2 = new AttackHeal("Break", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) -6);

    }
}
