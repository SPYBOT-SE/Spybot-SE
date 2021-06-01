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

        selectedPlayer = MainActivity.player1;



        TextView money = findViewById(R.id.labelPlayerMoneyShop);
        money.setText(String.valueOf(selectedPlayer.getCurrency()));

        TextView playerName = findViewById(R.id.labelPlayerName);
        playerName.setText(selectedPlayer.getPlayerName());

        findViewById(R.id.btnBack).setOnClickListener((v) -> {
            Intent i = new Intent(this, SessionMainMenu.class);
            startActivity(i);
        });

        findViewById(R.id.playerToggle).setOnClickListener((v) -> {
            TogglePlayer();
        });

        findViewById(R.id.buyBtnIcon1).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.bug,1000, 1);
        });
        findViewById(R.id.buyBtnIcon2).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.dumbbell,2000, 2);
        });
        findViewById(R.id.buyBtnIcon3).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.t3inf2002,5000, 3);
        });
        findViewById(R.id.buyBtnIcon4).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.bug, 10000,4);
        });
        findViewById(R.id.buyBtnIcon5).setOnClickListener((v) -> {
            BuyFigure(PawnTypes.bug, 15000, 5);
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

    void BuyFigure(PawnTypes pawnType, int pawnCost, int precondition){
        if(pawnCost <= selectedPlayer.getCurrency()
                && !selectedPlayer.getCatalogue().contains(pawnType)
                && precondition == selectedPlayer.getCatalogue().size()){
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
