package com.pawns;

import com.example.spybot.R;
import com.pawns.attacks.AttackSize;
import com.pawns.attacks.AttackSpeed;

public class PawnMachineGun extends Pawn {

    public PawnMachineGun() {
        super(false);
        this.name = "Gunner";
        this.speed = 5;
        this.leftSteps = this.speed;
        this.maxSize = 8;

        this.icon = R.drawable.icon_military_troop;

        this.pictureHead = R.drawable.layer1_military_troop;
        this.pictureTail = R.drawable.hantel_body;
        this.pictureTailDown = R.drawable.hantel_body_down;
        this.pictureTailUp = R.drawable.hantel_body_up;
        this.pictureTailLeft = R.drawable.hantel_body_left;
        this.pictureTailRight = R.drawable.hantel_body_right;

        this.spawnSound = R.raw.machine_gun_reloading;

        this.attack1 = new AttackSize("Shooting", R.drawable.highlighting_attack,R.raw.attack_sound, (byte) 1, (byte) 1);
        this.attack2 = new AttackSpeed("Grenade", R.drawable.highlighting_attack,R.raw.attack_sound,(byte) 1, (byte) 1);

    }
}
