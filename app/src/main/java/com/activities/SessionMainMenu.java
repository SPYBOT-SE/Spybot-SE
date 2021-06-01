package com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;

import static com.activities.MainMenu.music;

public class SessionMainMenu extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_main_menu);
        AppSettingsHelper.hideSystemUI(this);

        Button selectBtn = findViewById(R.id.btnArena);
        selectBtn.setOnClickListener((v) -> loadLevels());

        Button shopButton = findViewById(R.id.btnShop);
        shopButton.setOnClickListener((v) -> loadShop());

        Button settingsButton = findViewById(R.id.btnPlayerSettings);
        settingsButton.setOnClickListener((v) -> playerSettings());

        Button endSessionButton = findViewById(R.id.btnEndSession);
        endSessionButton.setOnClickListener((v) -> endSession());
    }

    private void loadLevels(){

        Intent i = new Intent(this, LevelSelection.class);
        startActivity(i);
    }

    private void endSession(){
        //End Session by setting players 1 and 2 null
        //Save
        Intent i = new Intent(this, MainMenu.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void loadShop(){
        Intent i = new Intent(this, PawnShop.class);
        startActivity(i);
    }

    private void playerSettings(){
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
