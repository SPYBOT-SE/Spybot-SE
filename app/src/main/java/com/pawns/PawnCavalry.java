package com.pawns;

import com.example.spybot.R;
import com.pawns.attacks.AttackHeal;
import com.pawns.attacks.AttackSpeed;

public class PawnCavalry extends Pawn {

    public PawnCavalry() {
        super(false);
        this.name = "Cavalry";
        this.speed = 3;
        this.leftSteps = this.speed;
        this.maxSize = 4;

        this.icon = R.drawable.icon_military_cavalry;

        this.pictureHead = R.drawable.layer1_military_cavallery;

        this.spawnSound = R.raw.horse;

        this.attack1 = new AttackHeal("Kick", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) -3);
        this.attack2 = new AttackSpeed("Paralyse", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) -1);

    }
}
