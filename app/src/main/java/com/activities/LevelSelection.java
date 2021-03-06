package com.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;
import com.level.LevelSingle;
import com.model.constants.ActionIdConstants;

import static com.activities.MainMenu.music;

public class LevelSelection extends AppCompatActivity implements View.OnClickListener {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.level_selection);
            AppSettingsHelper.hideSystemUI(this);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            int name = 0;
            for (int i = 0; i < 4; i++) {
                LinearLayout row = new LinearLayout(this);
                row.setLayoutParams(new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 4; j++) {
                    if(name < 11) {
                        String buttonName = String.format("%8s", Integer.toBinaryString(name & 0xFF)).replace(' ', '0');
                        Log.e("Debug", name + " is level " + LevelSingle.getLevelName(name));
                        createButton(buttonName, row, name);
                        name++;
                    }
                }
                layout.addView(row);
            }
            //create back button
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            createBackButton(row);
            layout.addView(row);
            setContentView(layout);
            layout.setBackgroundResource(R.drawable.main_background);
        }

        private void createBackButton(LinearLayout row){
            Button backButton = new Button(this);

            DisplayMetrics dm = new DisplayMetrics();
            this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;

            backButton.setLayoutParams(new LinearLayout.LayoutParams(width / 4, width / 10));
            backButton.setText("Back");


            backButton.setId(ActionIdConstants.BACK);
            backButton.setOnClickListener((v) -> {
                    Intent i = new Intent(this, SessionMainMenu.class);
                    startActivity(i);
           });
            row.addView(backButton);
        }


        private void createButton(String name, LinearLayout layout, int i) {
            Button btnTag = new Button(this);

            DisplayMetrics dm = new DisplayMetrics();
            this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;


            btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / 4, width / 10));
            btnTag.setText(LevelSingle.getLevelName(i));


            btnTag.setId(i);

            btnTag.setOnClickListener(this);
            layout.addView(btnTag);

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

        @Override
        public void onClick(View v) {
            MainActivity.selectedLevel = LevelSingle.getLevel(v.getId());

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }


