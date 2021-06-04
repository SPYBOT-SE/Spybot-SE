package com.pawns.military;

import com.example.spybot.R;
import com.pawns.Pawn;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;
import com.pawns.attacks.AttackHeal;

public class PawnSniper extends Pawn {

    public PawnSniper() {
        super(false);
        this.name = "Sniper";
        this.speed = 1;
        this.leftSteps = this.speed;
        this.maxSize = 2;

        this.icon = R.drawable.icon_military_sniper;

        this.pictureHead = R.drawable.layer1_military_sniper;

        this.spawnSound = R.raw.sniper;

        this.attack1 = new AttackHeal("Snipe", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 6, (byte) -3);
        this.attack2 = new AttackHeal("Knife", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) -4);

    }
}
