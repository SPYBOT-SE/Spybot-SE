package com.example.spybot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.player.Player;
import com.spybot.app.AppSetting;

import static com.example.spybot.MainMenu.music;


public class PlayerSelection extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_selection_view);
        AppSetting.hideSystemUI(this);

        Button selectBtn = findViewById(R.id.selectPlayers);
        selectBtn.setOnClickListener((v) -> {
            MoveToNextLevel();
        });
    }

    void MoveToNextLevel(){
        String player1Name = findViewById(R.id.player1Input).toString().toUpperCase();
        String player2Name = findViewById(R.id.player2Input).toString().toUpperCase();

//        MainActivity.player1 = GetPlayerFromSavegame(player1Name);
//        MainActivity.player2 = GetPlayerFromSavegame(player2Name);
        Intent i = new Intent(this, SessionMainMenu.class);
        startActivity(i);
    }

    Player GetPlayerFromSavegame(String playerName){
        //Find player in save game
        //If not found: create new Player
        //              add new player to savegame -> CreateNewPlayer()

        throw new UnsupportedOperationException("Not implemented");
    }

    Player CreateNewPlayer(String playerName){
        Player newPlayer = new Player(playerName);
        //create new player
        //add player to savegame
        //return
        throw new UnsupportedOperationException();
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
