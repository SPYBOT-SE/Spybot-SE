package com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;
import com.model.Savegame;
import com.player.Player;
import com.utilities.SavegameUtil;

import java.util.HashMap;

import static com.activities.MainMenu.music;


public class PlayerSelection extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_selection_view);
        AppSettingsHelper.hideSystemUI(this);

        Button selectBtn = findViewById(R.id.selectPlayers);
        selectBtn.setOnClickListener((v) -> {
            moveToNextLevel();
        });
    }

    private void moveToNextLevel(){
        SavegameUtil.loadSavegame(this);
        Savegame savegame = SavegameUtil.getSavegame();

        EditText input = findViewById(R.id.player1Input);
        String player1Name = input.getText().toString().toUpperCase();

        input = findViewById(R.id.player2Input);
        String player2Name = input.getText().toString().toUpperCase();

        MainActivity.player1 = getPlayerFromSavegame(player1Name, savegame.getPlayers());
        MainActivity.player2 = getPlayerFromSavegame(player2Name, savegame.getPlayers());

        SavegameUtil.setSavegame(savegame);
        SavegameUtil.writeSavegame(this);

        Intent i = new Intent(this, SessionMainMenu.class);
        startActivity(i);
    }

    private Player getPlayerFromSavegame(String playerName, HashMap<String, Player> players){
        if (players.containsKey(playerName)){
            return players.get(playerName);
        } else {
            return createNewPlayer(playerName, players);
        }

    }

    private Player createNewPlayer(String playerName, HashMap<String, Player> players){
        Player newPlayer = new Player(playerName);
        players.put(playerName,newPlayer);
        return newPlayer;
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
