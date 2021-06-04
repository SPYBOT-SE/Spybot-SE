package com.utilities;

public class SoundUtil {

    public static final int defaultAmplifier = 50;
    private static int masterAmplifier = defaultAmplifier;
    private static int musicAmplifier = defaultAmplifier;
    private static int sfxAmplifier = defaultAmplifier;

    public static int getMasterAmplifier() {
        return masterAmplifier;
    }

    public static void setMasterAmplifier(int masterAmplifier) {
        SoundUtil.masterAmplifier = masterAmplifier;
    }

    public static int getMusicAmplifier() {
        return musicAmplifier;
    }

    public static void setMusicAmplifier(int musicAmplifier) {
        SoundUtil.musicAmplifier = musicAmplifier;
    }

    public static int getSfxAmplifier() {
        return sfxAmplifier;
    }

    public static void setSfxAmplifier(int sfxAmplifier) {
        SoundUtil.sfxAmplifier = sfxAmplifier;
    }

    public static float getMasterVolume() {
        return 0.01f * masterAmplifier;
    }

    public static float getMusicVolume() {
        return (0.01f * musicAmplifier) * (0.01f * masterAmplifier);
    }

    public static float getSfxVolume() {
        return (0.01f * sfxAmplifier) * (0.01f * masterAmplifier);
    }


    public static void resetSound() {
        masterAmplifier = defaultAmplifier;
        musicAmplifier = defaultAmplifier;
        sfxAmplifier = defaultAmplifier;
    }

    public static void muteSound() {
        masterAmplifier = 0;
        musicAmplifier = 0;
        sfxAmplifier = 0;
    }
}
