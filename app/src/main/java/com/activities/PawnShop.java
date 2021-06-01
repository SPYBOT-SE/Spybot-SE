package com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;
import com.player.PawnTypes;
import com.player.Player;

import static com.activities.MainMenu.music;

public class PawnShop extends AppCompatActivity {

    private Player selectedPlayer;
    private boolean selectedPlayerBool = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pawn_shop);
        AppSettingsHelper.hideSystemUI(this);

        TextView money = findViewById(R.id.labelPlayerMoneyShop);
        // money.setText(selectedPlayer.getMoney());

        TextView playerName = findViewById(R.id.labelPlayerName);
        // playerName.setText(selectedPlayer.getPlayerName());

        findViewById(R.id.btnBack).setOnClickListener((v) -> {
            Intent i = new Intent(this, SessionMainMenu.class);
            startActivity(i);
        });

        findViewById(R.id.playerToggle).setOnClickListener((v) -> {
            togglePlayer();
        });

        findViewById(R.id.buyBtnIcon1).setOnClickListener((v) -> {
            buyFigure(PawnTypes.bug,1000);
        });
        findViewById(R.id.buyBtnIcon2).setOnClickListener((v) -> {
            buyFigure(PawnTypes.dumbbell,2000);
        });
        findViewById(R.id.buyBtnIcon3).setOnClickListener((v) -> {
            buyFigure(PawnTypes.t3inf2002,5000);
        });
        findViewById(R.id.buyBtnIcon4).setOnClickListener((v) -> {
            buyFigure(PawnTypes.bug, 10000);
        });
        findViewById(R.id.buyBtnIcon5).setOnClickListener((v) -> {
            buyFigure(PawnTypes.bug, 15000);
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

    private void buyFigure(PawnTypes pawnType, int pawnCost) {
        if(pawnCost < selectedPlayer.getCurrency() && !selectedPlayer.getCatalogue().contains(pawnType)){
            selectedPlayer.setCurrency(pawnCost);
            selectedPlayer.getCatalogue().add(pawnType);
        }

        TextView money = findViewById(R.id.labelPlayerMoneyShop);
        money.setText(selectedPlayer.getCurrency());
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
