package com.pawns.attacks;

import com.activities.MainActivity;
import com.level.Field;


public class AttackBuild extends Attack{

    public AttackBuild(String attackName, int icon, int audio, byte range, byte magnitude) {
        super(attackName,icon, audio, range, magnitude);
    }

    @Override
    public void performAttack(MainActivity m, Field target) {
        if (magnitude > 0 && !target.getStatus()){
            //Build
            target.setStatus(true);
        } else if (magnitude < 0){
            //Destroy
            target.setStatus(false);
        }
    }
}
