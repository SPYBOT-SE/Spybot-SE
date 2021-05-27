package com.example.spybot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.player.Player;
import com.spybot.app.AppSetting;

import static com.example.spybot.MainMenu.music;

public class SessionSettings extends AppCompatActivity {

    private Player selectedPlayer;
    private boolean selectedPlayerBool = true;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
        AppSetting.hideSystemUI(this);

        selectedPlayer = MainActivity.player1;

        findViewById(R.id.btnBackToMenu).setOnClickListener((v) -> {
            Intent i = new Intent(this, SessionMainMenu.class);
            startActivity(i);
        });
        findViewById(R.id.btnSwapPlayers).setOnClickListener((v) -> {
            SwapPlayers();
        });
        findViewById(R.id.btnChangePlayer1).setOnClickListener((v) -> {
            ChangePlayer(true);
        });
        findViewById(R.id.btnChangePlayer2).setOnClickListener((v) -> {
            ChangePlayer(false);
        });
        findViewById(R.id.btnTogglePlayer).setOnClickListener((v) -> {
            TogglePlayer();
        });
        findViewById(R.id.btnRenamePlayer).setOnClickListener((v) -> {
            RenamePlayer();
        });
        findViewById(R.id.btnResetPlayer).setOnClickListener((v) -> {
            ResetPlayer();
        });
        findViewById(R.id.btnDeletePlayer).setOnClickListener((v) -> {
            DeletePlayer();
        });
        findViewById(R.id.btnResetSavegame).setOnClickListener((v) -> {
            ResetSaveGame();
        });


    }

    void SwapPlayers(){
        Player temp = MainActivity.player1;
        MainActivity.player1 = MainActivity.player2;
        MainActivity.player2 = temp;

    }

    void ChangePlayer(boolean side){

    }


    void TogglePlayer(){
        selectedPlayerBool = !selectedPlayerBool;

        if (selectedPlayerBool){
            selectedPlayer = MainActivity.player1;
        } else {
            selectedPlayer = MainActivity.player2;
        }
    }

    void RenamePlayer(){
        selectedPlayer.setPlayerName(findViewById(R.id.inputNewPlayerName).toString());
    }

    void DeletePlayer(){

    }

    void ResetPlayer(){

    }

    void ResetSaveGame(){

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
