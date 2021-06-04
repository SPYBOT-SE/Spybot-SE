package com.pawns;

import com.example.spybot.R;
import com.pawns.attacks.AttackHeal;

public class PawnMachineGun extends Pawn {

    public PawnMachineGun() {
        super(false);
        this.name = "Troop";
        this.speed = 2;
        this.leftSteps = this.speed;
        this.maxSize = 3;

        this.icon = R.drawable.icon_military_troop;

        this.pictureHead = R.drawable.layer1_military_troop;

        this.spawnSound = R.raw.machine_gun_reloading;

        this.attack1 = new AttackHeal("Shooting", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 3, (byte) -2);
        this.attack2 = new AttackHeal("Grenade", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) -4);

    }
}
