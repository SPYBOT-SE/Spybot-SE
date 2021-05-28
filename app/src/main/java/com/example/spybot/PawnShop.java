package com.example.spybot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.player.PawnTypes;
import com.player.Player;
import com.spybot.app.AppSetting;

import static com.example.spybot.MainMenu.music;

public class PawnShop extends AppCompatActivity {

    private Player selectedPlayer;
    private boolean selectedPlayerBool = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pawn_shop);
        AppSetting.hideSystemUI(this);

        TextView money = findViewById(R.id.labelPlayerMoneyShop);
        // money.setText(selectedPlayer.getMoney());

        TextView playerName = findViewById(R.id.labelPlayerName);
        // playerName.setText(selectedPlayer.getPlayerName());

        findViewById(R.id.btnBack).setOnClickListener((v) -> {
            Intent i = new Intent(this, SessionMainMenu.class);
            startActivity(i);
        });

        findViewById(R.id.playerToggle).setOnClickListener((v) -> {
            TogglePlayer();
        });

        findViewById(R.id.buyBtnIcon1).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.bug,1000);
        });
        findViewById(R.id.buyBtnIcon2).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.dumbbell,2000);
        });
        findViewById(R.id.buyBtnIcon3).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.T3INF2002,5000);
        });
        findViewById(R.id.buyBtnIcon4).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.bug, 10000);
        });
        findViewById(R.id.buyBtnIcon5).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.bug, 15000);
        });
    }

    void TogglePlayer(){
        selectedPlayerBool = !selectedPlayerBool;

        if (selectedPlayerBool){
            selectedPlayer = MainActivity.player1;
        } else {
            selectedPlayer = MainActivity.player2;
        }
    }

    void BuyFigure(PawnTypes pawnType, int pawnCost){
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
