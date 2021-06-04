package com.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;

import static com.activities.MainMenu.music;

public class GameSettings extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, Switch.OnClickListener{
    //TODO fix bugs while starting the settings menu

    public static float masterAmplifier = 0.5f;
    public static float musicAmplifier = 0.5f;
    public static float sfxAmplifier = 0.5f;

    private final float divider = 0.01f;

    private static boolean determineSound = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings_menu);
        AppSettingsHelper.hideSystemUI(this);

        findViewById(R.id.btnReturnToMainMenu).setOnClickListener((v) -> {
            Intent i = new Intent(this, MainMenu.class);
            startActivity(i);
        });

        findViewById(R.id.btnResetSaveGame).setOnClickListener((v) -> {
            Intent i = new Intent(this, SessionMainMenu.class);
            startActivity(i);
        });

        findViewById(R.id.btnMuteGame).setOnClickListener((v) -> {
            andreasMode();
        });

        findViewById(R.id.btnResetVolume).setOnClickListener((v) -> {
            resetSound();
        });

        SeekBar masterSlider = findViewById(R.id.seekbarMaster);
        SeekBar musicSlider = findViewById(R.id.seekbarMusic);
        SeekBar sfxSlider = findViewById(R.id.seekbarSFX);

        masterSlider.setOnSeekBarChangeListener(this);
        musicSlider.setOnSeekBarChangeListener(this);
        sfxSlider.setOnSeekBarChangeListener(this);


        masterSlider.setProgress((int)masterAmplifier * 100);
        musicSlider.setProgress((int) (((musicAmplifier / masterAmplifier))/divider));
        sfxSlider.setProgress((int) (((sfxAmplifier / masterAmplifier))/divider));

        Switch soundtrackSwitch = findViewById(R.id.switchSoundtrack);
        soundtrackSwitch.setOnClickListener(this);
        soundtrackSwitch.setChecked(determineSound);


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        SeekBar masterSlider = findViewById(R.id.seekbarMaster);
        masterAmplifier = masterSlider.getProgress() * divider;

        if (masterAmplifier == 0){
            musicAmplifier = 0;
            sfxAmplifier = 0;

            music.setVolume(0,0);
            return;
        }

        SeekBar musicSlider = findViewById(R.id.seekbarMusic);
        musicAmplifier = musicSlider.getProgress() * masterAmplifier * divider;

        SeekBar sfxSlider = findViewById(R.id.seekbarSFX);
        sfxAmplifier = sfxSlider.getProgress() * masterAmplifier * divider;

        music.setVolume(musicAmplifier, musicAmplifier);


    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onPause() {
        super.onPause();
        music.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        music.start();

    }

    private void andreasMode(){
        masterAmplifier = 0;
        musicAmplifier = 0;

        MainMenu.music.setVolume(0,0);

        sfxAmplifier = 0;

        SeekBar masterSlider = findViewById(R.id.seekbarMaster);
        SeekBar musicSlider = findViewById(R.id.seekbarMusic);
        SeekBar sfxSlider = findViewById(R.id.seekbarSFX);

        masterSlider.setProgress(0);
        musicSlider.setProgress(0);
        sfxSlider.setProgress(0);
    }

    private void resetSound(){
        float volumeCalculations;

        masterAmplifier = .5f;
        musicAmplifier = .5f;
        sfxAmplifier = .5f;

        music.setVolume(musicAmplifier,musicAmplifier);

        SeekBar masterSlider = findViewById(R.id.seekbarMaster);
        SeekBar musicSlider = findViewById(R.id.seekbarMusic);
        SeekBar sfxSlider = findViewById(R.id.seekbarSFX);

        volumeCalculations = masterAmplifier *100;
        masterSlider.setProgress((int)volumeCalculations);

        volumeCalculations = musicAmplifier/(masterAmplifier * 0.01f);
        musicSlider.setProgress((int) volumeCalculations);

        volumeCalculations = sfxAmplifier/(masterAmplifier * 0.01f);
        sfxSlider.setProgress((int) volumeCalculations);
    }

    private void switchSoundtrack(){
        music.stop();
        determineSound = !determineSound;

        if(determineSound){
            music = MediaPlayer.create(this, R.raw.epic_background);
        } else {
            music = MediaPlayer.create(this, R.raw.sound_background);
        }

        music.start();
        music.setLooping(true);
        music.setVolume(musicAmplifier, musicAmplifier);

        Switch soundtrackSwitch = findViewById(R.id.switchSoundtrack);
        soundtrackSwitch.setChecked(determineSound);


    }

    @Override
    public void onClick(View v) {
        switchSoundtrack();
    }
}
