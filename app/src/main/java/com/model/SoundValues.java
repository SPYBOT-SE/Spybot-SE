package com.model;

import com.utilities.GeneralUtil;

public class SoundValues {

    public static final int defaultAmplifier = 50;

    private int masterAmplifier = defaultAmplifier;
    private int musicAmplifier = defaultAmplifier;
    private int sfxAmplifier = defaultAmplifier;

    public int getMasterAmplifier() {
        return masterAmplifier;
    }

    public void setMasterAmplifier(int masterAmplifier) {
        this.masterAmplifier = masterAmplifier;
    }

    public int getMusicAmplifier() {
        return musicAmplifier;
    }

    public void setMusicAmplifier(int musicAmplifier) {
        this.musicAmplifier = musicAmplifier;
    }

    public int getSfxAmplifier() {
        return sfxAmplifier;
    }

    public void setSfxAmplifier(int sfxAmplifier) {
        this.sfxAmplifier = sfxAmplifier;
    }

    public String toJSON(int indent) {
        String ind = GeneralUtil.getIndent(indent);

        return ind + "\"soundsettings\":\n" +
                ind + "{\n" +
                ind + "  \"master\":" + masterAmplifier + ",\n" +
                ind + "  \"music\":" + musicAmplifier + ",\n" +
                ind + "  \"sfx\":" + sfxAmplifier + "\n" +
                ind + "}\n";
    }
}
