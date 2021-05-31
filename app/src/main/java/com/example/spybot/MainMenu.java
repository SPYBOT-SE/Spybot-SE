package com.example.spybot;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.model.shortcuts.JsonConstants;
import com.utilities.FileUtil;
import com.utilities.SavegameUtil;

public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    public static MediaPlayer music;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startmenu);

        AppSettingsHelper.hideSystemUI(this);

        //create Buttons
        Button start = findViewById(R.id.btnStart);
        start.setOnClickListener(this);
        Button quit = findViewById(R.id.btnQuit);
        quit.setOnClickListener(this);
        Button settings = findViewById(R.id.btnSettings);
        settings.setOnClickListener(this);

        music = MediaPlayer.create(this, R.raw.sound_background);
        music.setLooping(true);
        music.start();

        MediaPlayer winxp = MediaPlayer.create(this, R.raw.winxp);
        winxp.start();


    }


    @Override
    public void onPause() {
        super.onPause();
        music.pause();
        // stop the clock
    }

    @Override
    public void onResume() {
        super.onResume();
        music.start();

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnStart:
                Intent i = new Intent(MainMenu.this, PlayerSelection.class);
                startActivity(i);
                break;
            case R.id.btnQuit:
                System.exit(0);
                break;
            case R.id.btnSettings:
                FileUtil.writeToFile(JsonConstants.SAVEGAMEFILE, "defaultSavegame", this);
                SavegameUtil.loadSavegame(this);
                break;
            default:
                System.exit(1);
                break;
        }
    }

}



