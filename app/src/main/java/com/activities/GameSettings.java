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
import com.utilities.SavegameUtil;
import com.utilities.SoundUtil;

import static com.activities.MainMenu.music;

public class GameSettings extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, Switch.OnClickListener{
    //TODO fix bugs while starting the settings menu

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
            SavegameUtil.resetSavegame(this);
            Intent i = new Intent(this, MainMenu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        masterSlider.setProgress(SoundUtil.getMasterAmplifier());
        musicSlider.setProgress(SoundUtil.getMusicAmplifier());
        sfxSlider.setProgress(SoundUtil.getSfxAmplifier());

        Switch soundtrackSwitch = findViewById(R.id.switchSoundtrack);
        soundtrackSwitch.setOnClickListener(this);
        soundtrackSwitch.setChecked(determineSound);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekbarMaster:
                    SoundUtil.setMasterAmplifier(progress);
                    break;
                case R.id.seekbarMusic:
                    SoundUtil.setMusicAmplifier(progress);
                    break;
                case R.id.seekbarSFX:
                    SoundUtil.setSfxAmplifier(progress);
                    break;
                default:
                    break;
            }
            music.setVolume(SoundUtil.getMusicVolume(), SoundUtil.getMusicVolume());
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seekbarSFX) {
            MediaPlayer testSound;
            testSound = MediaPlayer.create(this, R.raw.sniper);
            testSound.setVolume(SoundUtil.getSfxVolume(), SoundUtil.getSfxVolume());
            testSound.start();
        }
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
        SoundUtil.muteSound();
        resetSlider(0);
        MainMenu.music.setVolume(SoundUtil.getMusicVolume(), SoundUtil.getMusicVolume());
    }

    private void resetSound(){
        SoundUtil.resetSound();
        resetSlider(SoundUtil.defaultAmplifier);
        MainMenu.music.setVolume(SoundUtil.getMusicVolume(), SoundUtil.getMusicVolume());
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
        music.setVolume(SoundUtil.getMusicVolume(), SoundUtil.getMusicVolume());

        Switch soundtrackSwitch = findViewById(R.id.switchSoundtrack);
        soundtrackSwitch.setChecked(determineSound);


    }

    @Override
    public void onClick(View v) {
        switchSoundtrack();
    }

    private void resetSlider(int progress) {
        SeekBar masterSlider = findViewById(R.id.seekbarMaster);
        masterSlider.setProgress(progress);

        SeekBar musicSlider = findViewById(R.id.seekbarMusic);
        musicSlider.setProgress(progress);

        SeekBar sfxSlider = findViewById(R.id.seekbarSFX);
        sfxSlider.setProgress(progress);
    }
}
