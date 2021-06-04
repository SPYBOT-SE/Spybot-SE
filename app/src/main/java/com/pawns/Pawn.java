package com.pawns;

import android.content.Context;
import android.media.MediaPlayer;
import com.activities.GameSettings;
import com.activities.MainActivity;
import com.example.spybot.R;
import com.level.Field;
import com.model.Direction;
import com.pawns.attacks.Attack;

import java.util.LinkedList;

public abstract class Pawn {

    public final boolean buildAbility;

    protected String name = "Template";
    protected byte team = 0;

    protected byte speed = 0;
    protected byte leftSteps = 0;

    protected byte maxSize = 0;

    protected Attack attack1;
    protected Attack attack2;

    public int icon = R.drawable.bug;
    public int pictureHead = R.drawable.blank_head;
    public int pictureTail = R.drawable.blank_body;
    public int pictureTailUp = R.drawable.blank_up;
    public int pictureTailDown = R.drawable.blank_down;
    public int pictureTailRight = R.drawable.blank_right;
    public int pictureTailLeft = R.drawable.blank_left;

    protected int spawnSound = R.raw.spawn;
    private final LinkedList<PawnSegment> segments = new LinkedList<>();

    public Pawn (boolean buildAbility) {
        this.buildAbility = buildAbility;
    }

    public void createSegment(Field field, BodyType type) {
        PawnSegment newSeg = new PawnSegment(this, type, field);
        segments.add(newSeg);
        field.setSegment(newSeg);
    }

    public void die(Context c) {

        Field field = this.getSegments().get(0).getField();
        field.setSegment(null);
        this.segments.remove(0);

        field.board.pawnsOnBoard.remove(this);
        field.board.pawnsInTeam2.remove(this);
        field.board.pawnsInTeam1.remove(this);

        MediaPlayer deathSound = MediaPlayer.create(c, R.raw.death_sound);
        deathSound.setVolume(GameSettings.sfxAmplifier, GameSettings.sfxAmplifier);
        deathSound.start();

    }

    public void move(Field from, Field to, Direction direction) {
        if(from.getSegment() == null) {
            return;
        }
        //Pawn pawn = from.getSegment().getPawn();

        if(this.getLeftSteps() <= 0) {
            return;
        }

        this.setLeftSteps((byte) (this.getLeftSteps()-1));
        to.setSegment(from.getSegment());

        this.segments.get(0).setField(to);

        switch (direction) {
            case UP:
                createSegment(from, BodyType.TailUp);
                break;
            case DOWN:
                createSegment(from, BodyType.TailDown);
                break;
            case LEFT:
                createSegment(from, BodyType.TailLeft);
                break;
            case RIGHT:
                createSegment(from, BodyType.TailRight);
                break;
            default:
                createSegment(from, BodyType.Tail);
                break;
        }



        while(segments.size() > maxSize) {
            Field segField = segments.get(1).getField();
            segField.setSegment(null);
            segments.remove(1);
        }
    }

    public void attack1(MainActivity m, Field target) {
        this.attack1.performAttack(m, target);
    }

    public void attack2(MainActivity m, Field target) {
        this.attack2.performAttack(m, target);
    }

// ----- Getter & Setter -----

    public byte getSpeed() {
        return speed;
    }

    public LinkedList<PawnSegment> getSegments() {
        return segments;
    }

    public String getName() {
        return name;
    }

    public byte getLeftSteps() {
        return leftSteps;
    }

    public void setSpeed(byte speed) {
        this.speed = speed;
    }

    public byte getCurrentSize() {
        return (byte) segments.size();
    }

    public byte getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(byte maxSize) {
        this.maxSize = maxSize;
    }

    public void setLeftSteps(byte leftSteps) {
        this.leftSteps = leftSteps;
    }

    public byte getTeam(){
        return team;
    }

    public void setTeam(byte team) {
        this.team = team;
    }

    public Attack getAttack1() {
        return attack1;
    }

    public Attack getAttack2() {
        return attack2;
    }

    public int getSpawnSound() {
        return spawnSound;
    }
}
