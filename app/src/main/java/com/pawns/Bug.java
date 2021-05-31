package com.pawns;

import com.example.spybot.R;
import com.pawns.attacks.AttackHeal;
import com.pawns.attacks.AttackSpeed;

public class Bug extends Pawn {

    public Bug() {
        super(false);
        this.team = 0;
        this.name = "Bug";
        this.speed = 6;
        this.leftSteps = this.speed;
        this.maxSize = 3;

        this.pictureHead = R.drawable.button_icon_bug;
        this.pictureTail = R.drawable.button_icon_bug;

        this.attack1 = new AttackHeal("Byte", R.drawable.highlighting_attack, R.raw.attack_sound,(byte) 3, (byte) -3);
        this.attack2 = new AttackSpeed("Bite",R.drawable.highlighting_attack, R.raw.attack_sound,(byte) 1, (byte) -1);

    }

}
