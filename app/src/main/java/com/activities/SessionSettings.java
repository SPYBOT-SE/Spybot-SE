package com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;
import com.model.Savegame;
import com.model.shortcuts.JsonConstants;
import com.player.Player;
import com.utilities.FileUtil;
import com.utilities.SavegameUtil;

import static com.activities.MainMenu.music;

public class SessionSettings extends AppCompatActivity {

    private Player selectedPlayer;
    private boolean selectedPlayerBool = true;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);
        AppSettingsHelper.hideSystemUI(this);

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

    private void swapPlayers(){
        Player temp = MainActivity.player1;
        MainActivity.player1 = MainActivity.player2;
        MainActivity.player2 = temp;

    }

    private void changePlayer(boolean side){

    }


    private void togglePlayer(){
        selectedPlayerBool = !selectedPlayerBool;

        if (selectedPlayerBool){
            selectedPlayer = MainActivity.player1;
        } else {
            selectedPlayer = MainActivity.player2;
        }
    }

    private void renamePlayer(){
        Savegame savegame = SavegameUtil.getSavegame();
        EditText input = findViewById(R.id.inputNewPlayerName);


        if(!savegame.getPlayers().containsKey(input.toString().toUpperCase())){
            selectedPlayer.setPlayerName(input.getText().toString().toUpperCase());
            SavegameUtil.setSavegame(savegame);
            FileUtil.writeToFile(JsonConstants.SAVEGAMEFILE,savegame.toJSON(0), this);
        }


    }

    private void deletePlayer(){
        Savegame savegame = SavegameUtil.getSavegame();

        if(savegame.getPlayers().containsKey(selectedPlayer.getPlayerName().toUpperCase())){

            savegame.getPlayers().remove(selectedPlayer.getPlayerName().toUpperCase());

            SavegameUtil.setSavegame(savegame);
            FileUtil.writeToFile(JsonConstants.SAVEGAMEFILE,savegame.toJSON(0), this);

            Intent i = new Intent(this, MainMenu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }


    }

    private void resetPlayer(){

    }

    private void resetSaveGame(){

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
