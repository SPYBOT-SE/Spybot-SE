package com.example.spybot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.spybot.app.AppSetting;

import static com.example.spybot.MainMenu.music;

public class SessionMainMenu extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_main_menu);
        AppSetting.hideSystemUI(this);

        Button selectBtn = findViewById(R.id.btnArena);
        selectBtn.setOnClickListener((v) -> {
            LoadLevels();
        });

        Button shopButton = findViewById(R.id.btnShop);
        shopButton.setOnClickListener((v) -> {
            LoadShop();
        });

        Button settingsButton = findViewById(R.id.btnPlayerSettings);
        settingsButton.setOnClickListener((v) -> {
            PlayerSettings();
        });

        Button endSessionButton = findViewById(R.id.btnEndSession);
        endSessionButton.setOnClickListener((v) -> {
            EndSession();
        });
    }

    void LoadLevels(){

        Intent i = new Intent(this, LevelSelection.class);
        startActivity(i);
    }

    void EndSession(){
        //End Session by setting players 1 and 2 null
        //Save
        Intent i = new Intent(this, MainMenu.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    void LoadShop(){
        Intent i = new Intent(this, PawnShop.class);
        startActivity(i);
    }

    void PlayerSettings(){
        Intent i = new Intent(this, SessionSettings.class);
        startActivity(i);
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
}
