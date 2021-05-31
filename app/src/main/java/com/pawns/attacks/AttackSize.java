package com.pawns.attacks;

import com.example.spybot.MainActivity;
import com.level.Field;

public class AttackSize extends Attack {

    public AttackSize(String attackName, int icon, int audio, byte range, byte magnitude) {
        super(attackName,icon, audio, range, magnitude);
    }

    @Override
    public void performAttack(MainActivity m, Field target) {
        target.getSegment().getPawn().setMaxSize((byte) (target.getSegment().getPawn().getMaxSize() + magnitude));
    }
}
