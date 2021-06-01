package com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;
import com.player.PawnTypes;
import com.player.Player;
import com.utilities.SavegameUtil;

import static com.activities.MainMenu.music;

public class PawnShop extends AppCompatActivity {

    private Player selectedPlayer;
    private boolean selectedPlayerBool = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pawn_shop);
        AppSettingsHelper.hideSystemUI(this);

        selectedPlayer = MainActivity.player1;



        TextView money = findViewById(R.id.labelPlayerMoneyShop);
        money.setText(String.valueOf(selectedPlayer.getCurrency()));

        TextView playerName = findViewById(R.id.labelPlayerNameShop);
        playerName.setText(selectedPlayer.getPlayerName());

        findViewById(R.id.btnBack).setOnClickListener((v) -> {
            Intent i = new Intent(this, SessionMainMenu.class);
            startActivity(i);
        });

        findViewById(R.id.playerToggle).setOnClickListener((v) -> {
            togglePlayer();
        });

        findViewById(R.id.buyBtnIcon1).setOnClickListener((v) -> {
            buyFigure(PawnTypes.dumbbell,1000, 1);
        });
        findViewById(R.id.buyBtnIcon2).setOnClickListener((v) -> {
            buyFigure(PawnTypes.dumbbell,2000, 2);
        });
        findViewById(R.id.buyBtnIcon3).setOnClickListener((v) -> {
            buyFigure(PawnTypes.t3inf2002,5000, 3);
        });
        findViewById(R.id.buyBtnIcon4).setOnClickListener((v) -> {
            buyFigure(PawnTypes.bug, 10000,4);
        });
        findViewById(R.id.buyBtnIcon5).setOnClickListener((v) -> {
            buyFigure(PawnTypes.bug, 15000, 5);
        });
    }

    private void togglePlayer() {
        selectedPlayerBool = !selectedPlayerBool;

        if (selectedPlayerBool){
            selectedPlayer = MainActivity.player1;
        } else {
            selectedPlayer = MainActivity.player2;
        }
    }

    private void buyFigure(PawnTypes pawnType, int pawnCost, int precondition) {
        if(pawnCost < selectedPlayer.getCurrency()
                && !selectedPlayer.getCatalogue().contains(pawnType)
                && precondition == selectedPlayer.getCatalogue().size()){
            selectedPlayer.addMoney(-pawnCost);
            selectedPlayer.getCatalogue().add(pawnType);
        }

        TextView money = findViewById(R.id.labelPlayerMoneyShop);
        money.setText(String.valueOf(selectedPlayer.getCurrency()));

        SavegameUtil.writeSavegame(this);
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
