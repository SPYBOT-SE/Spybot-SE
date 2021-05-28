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
            swapPlayers();
        });
        findViewById(R.id.btnChangePlayer1).setOnClickListener((v) -> {
            changePlayer(true);
        });
        findViewById(R.id.btnChangePlayer2).setOnClickListener((v) -> {
            changePlayer(false);
        });
        findViewById(R.id.btnTogglePlayer).setOnClickListener((v) -> {
            togglePlayer();
        });
        findViewById(R.id.btnRenamePlayer).setOnClickListener((v) -> {
            renamePlayer();
        });
        findViewById(R.id.btnResetPlayer).setOnClickListener((v) -> {
            resetPlayer();
        });
        findViewById(R.id.btnDeletePlayer).setOnClickListener((v) -> {
            deletePlayer();
        });
        findViewById(R.id.btnResetSavegame).setOnClickListener((v) -> {
            resetSaveGame();
        });


    }

    public void swapPlayers(){
        Player temp = MainActivity.player1;
        MainActivity.player1 = MainActivity.player2;
        MainActivity.player2 = temp;

    }

    public void changePlayer(boolean side){

    }


    public void togglePlayer(){
        selectedPlayerBool = !selectedPlayerBool;

        if (selectedPlayerBool){
            selectedPlayer = MainActivity.player1;
        } else {
            selectedPlayer = MainActivity.player2;
        }
    }

    public void renamePlayer(){
        selectedPlayer.setPlayerName(findViewById(R.id.inputNewPlayerName).toString());
    }

    public void deletePlayer(){

    }

    public void resetPlayer(){

    }

    public void resetSaveGame(){

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
