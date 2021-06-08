package com.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;

import java.util.NoSuchElementException;

import static com.model.constants.BackgroundConstants.G;

public class LevelEditor extends AppCompatActivity {

    private boolean changeBackground = false;
    private boolean changeSpawning = false;
    private boolean changeState = true;
    private boolean side = true;

    private int bgIndex = 5;

    public int[][] level = {
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G},
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        AppSettingsHelper.hideSystemUI(this);

        LinearLayout parentLayout = new LinearLayout(this); //main layout of the level screen
        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLayout.setBackgroundResource(R.drawable.main_background);

        LinearLayout infoPanel = new LinearLayout(this); //layout containing the information
        infoPanel.setOrientation(LinearLayout.VERTICAL);
        parentLayout.addView(infoPanel); //add info box to parent

        setUpInfoPanel(infoPanel);
        //infoPanel.setBackgroundColor(Color.GRAY);


        LinearLayout gameLayout = new LinearLayout(this); //layout containing the game and a info box
        gameLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.addView(gameLayout);

        LinearLayout fields = new LinearLayout(this); //fields
        fields.setOrientation(LinearLayout.VERTICAL);
        gameLayout.addView(fields);

        LinearLayout infoBox = new LinearLayout(this); //info box
        infoBox.setOrientation(LinearLayout.VERTICAL);
        gameLayout.addView(infoBox);


        for (short y = 0; y < 10; y++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            for (short x = 0; x < 18; x++) {
                int id = y * 18 + x;
                createButton(row, id, View.VISIBLE, 20);

            }
            fields.addView(row);
        }



        setContentView(parentLayout);

    }


    private Button createButton(LinearLayout layout, int id, int ratio) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / 6, width / ratio));
        btnTag.setId(id);

        btnTag.setVisibility(View.VISIBLE);

        return btnTag;
    }

    @SuppressLint("ResourceType")
    private void setUpInfoPanel(LinearLayout panel) {
        Button btn = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btn.setLayoutParams(new LinearLayout.LayoutParams(width / 6, width / 6));
        btn.setId(900000);

        btn.setVisibility(View.VISIBLE);
        btn.setClickable(false);
        panel.addView(btn);

        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.VERTICAL);
        btnLayout.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));


        btn = createButton(btnLayout, 900001, 20);
        btn.setText("STATE");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            changeBackground = false;
            changeState = true;
            changeSpawning = false;
        });

        btn = createButton(btnLayout, 900002, 20);
        btn.setText("BACKGROUND");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            bgIndex = bgIndex + 2;

            if(bgIndex > 15){
                bgIndex = 5;
            }

            Button btnImage = findViewById(900000);
            btnImage.setBackground(showNewTerrain());


            changeBackground = true;
            changeState = false;
            changeSpawning = false;
        });

        btn = createButton(btnLayout, 900003, 20);
        btn.setText("SPAWN");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            changeBackground = false;
            changeState = false;
            changeSpawning = true;

        });

        btn = createButton(btnLayout, 900004, 20);
        btn.setText("TOGGLE P");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            side = !side;
        });

        btn = createButton(btnLayout, 900005, 20);
        btn.setText("PLAY");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            MainActivity.selectedLevel = level;
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        btn = createButton(btnLayout, 900006, 20);
        btn.setText("DEBUG");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            debugOutput();
        });

        btn = createButton(btnLayout, 900007, 20);
        btn.setText("BACK");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {

        });

        panel.addView(btnLayout);
    }

    private Drawable showNewTerrain(){
        switch (bgIndex/2) {
            case 0:
                return this.getDrawable(R.drawable.field_clean);
            case 1:
                return this.getDrawable(R.drawable.field_classroom);
            case 2:
                return this.getDrawable(R.drawable.sand_small);
            case 3:
                return this.getDrawable(R.drawable.grass_small);
            case 4:
                return this.getDrawable(R.drawable.dirt_small);
            case 5:
                return this.getDrawable(R.drawable.water_small);
            case 6:
                return this.getDrawable(R.drawable.street_small);
            case 7:
                return this.getDrawable(R.drawable.forest_small);
            default:
                throw new NoSuchElementException("Error, background " + bgIndex/2 + " not implemented yet!");
        }
    }

    private void createButton(LinearLayout layout, int id, int viewVisibility, int ratio) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / ratio, width / ratio));
        btnTag.setId(id);
        btnTag.setBackgroundResource(R.drawable.grass_small);
        btnTag.setVisibility(viewVisibility);
        layout.addView(btnTag);
        btnTag.setOnClickListener((v) -> {
            changeButtonAttribute(v.getId());
        });
    }


    private void debugOutput(){
        String[] output = new String[10];
        for (short i = 0; i < 10; i++){
            output[i] = i + " \t{" + Integer.toHexString(level[i][0]);
            for (short j = 1; j < 18; j++){
                output[i] += "," + Integer.toHexString(level[i][j]);
            }
            output[i] += "},";
        }

        for(String line: output){
            Log.d("Output", line);
        }
    }

    private void refreshBoard(){
        Drawable[] layerView = new Drawable[3];

        for (short y = 0; y < 10; y++) {
            for (short x = 0; x < 18; x++) {
                int id = y * 18 + x;
                Button btn = findViewById(id);

                layerView[0] = drawBackground(x,y);
                layerView[1] = setSpawning(x,y);
                layerView[2] = enableButton(x,y);

                LayerDrawable layerDrawable = new LayerDrawable(layerView);
                btn.setBackground(layerDrawable);

            }
        }
    }

    private Drawable drawBackground(int x, int y){
        int value = level[y][x];
        int background = (value & 0x000F) / 2;

        switch (background) {
            case 0:
                return this.getDrawable(R.drawable.field_clean);
            case 1:
                return this.getDrawable(R.drawable.field_classroom);
            case 2:
                return this.getDrawable(R.drawable.sand_small);
            case 3:
                return this.getDrawable(R.drawable.grass_small);
            case 4:
                return this.getDrawable(R.drawable.dirt_small);
            case 5:
                return this.getDrawable(R.drawable.water_small);
            case 6:
                return this.getDrawable(R.drawable.street_small);
            case 7:
                return this.getDrawable(R.drawable.forest_small);
            default:
                throw new NoSuchElementException("Error, background " + background + " not implemented yet!");
        }
    }

    private Drawable enableButton(int x, int y){
        int value = level[y][x];
        boolean enabled = (value & 0x000F) % 2 == 1;

        if(enabled){
            return this.getDrawable(R.drawable.transparent);
        } else return this.getDrawable(R.drawable.delete_field);
    }

    private Drawable setSpawning(int x, int y){
        int value = level[y][x];
        int spawn = (value & 0x00F0);

        if(spawn == 16){
            return this.getDrawable(R.drawable.highlighting_spawnable_p0);
        } else if(spawn == 32){
            return this.getDrawable(R.drawable.highlighting_spawnable_p1);
        } else return this.getDrawable(R.drawable.transparent);
    }

    private void changeButtonAttribute(int id){
        int y = id/18;
        int x = id%18;
        if(changeBackground){
            changeBackgroundOfField(x,y);
        } else if(changeSpawning){
            changeSpawningOfField(x,y);
        } else if(changeState){
            changeStateOfField(x,y);
        }
        refreshBoard();
    }

    private void changeBackgroundOfField(int x, int y){
        int value = level[y][x];
        int background = (value & 0x000F) / 2;
        int spawn = (value & 0x00F0);

//        background = background * 2 + 3;
//
//        if(background > 15){
//            background = 5;
//        }

        level[y][x] = spawn + bgIndex;
    }

    private void changeSpawningOfField(int x, int y){
        int value = level[y][x];
        int background = value & 0x000F;
        int spawn = (value & 0x00F0);

        if(spawn > 0){
            spawn = 0;
        }
        if(side){
            spawn = 0x010;
        } else{
            spawn = 0x020;
        }

        level[y][x] = spawn + background;

    }

    private void changeStateOfField(int x, int y){
        int value = level[y][x];
        int background = (value & 0x000F);
        int spawn = (value & 0x00F0);

        boolean enabled = (value & 0x000F) % 2 == 1;

        if(enabled){
            background -= 1;
        } else{
            background += 1;
        }
        level[y][x] = spawn + background;
    }

}

