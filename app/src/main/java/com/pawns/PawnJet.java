package com.pawns;

import com.example.spybot.R;
import com.pawns.attacks.AttackHeal;

public class PawnJet extends Pawn {

    public PawnJet() {
        super(false);
        this.name = "Jet";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 4;

        this.icon = R.drawable.icon_military_jet;

        this.pictureHead = R.drawable.layer1_military_jet;


        this.spawnSound = R.raw.jet_flyby;


        this.attack1 = new AttackHeal("Bomb", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) -6);
        this.attack2 = new AttackHeal("Gun", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 3, (byte) -3);

    }
}
