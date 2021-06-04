package com.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.application.AppSettingsHelper;
import com.example.spybot.R;
import com.level.Board;
import com.level.Field;
import com.level.Highlighting;
import com.level.LevelSingle;
import com.model.Direction;
import com.model.LevelState;
import com.model.constants.ActionIdConstants;
import com.pawns.BodyType;
import com.pawns.Pawn;
import com.pawns.PawnSegment;
import com.pawns.military.*;
import com.player.PawnTypes;
import com.player.Player;
import com.utilities.SavegameUtil;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static com.activities.MainMenu.music;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static int[][] selectedLevel = LevelSingle.Error;

    private Board board = null;

    private int height = 0;
    private int width = 0;

    private Field lastSelected = null;

    public static Player player1;
    public static Player player2;
    private Player currentPlayer;

    private final int[] playerOneColor = new int[]{0xFF008fe2, 0xFF00a6c7, 0xFF00bca9, 0xFF00d28e, 0xFF00e872, 0xFF00ff55};
    private final int[] playerTwoColor = new int[]{0xFFff390e, 0xFFff5c1c, 0xFFff7f2b, 0xFFffa239, 0xFFffc546, 0xFFffe955};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        board = new Board(selectedLevel);

        height = board.sizeY;
        width = board.sizeX;

        currentPlayer = player1;

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


        for (short y = 0; y < height; y++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            for (short x = 0; x < width; x++) {
                int id = y * width + x;

                if (board.getField(x,y).getStatus()) {
                    createButton(row, id, View.VISIBLE, 20);
                } else {
                    createButton(row, id, View.INVISIBLE, 20);
                }


            }
            fields.addView(row);
        }



        setContentView(parentLayout);
        //resetButtons();
        refreshBoard();
        loadDefaultView();
    }

    private void createButton(LinearLayout layout, int id, int viewVisibility, int ratio) {
        Button btnTag = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btnTag.setLayoutParams(new LinearLayout.LayoutParams(width / ratio, width / ratio));
        btnTag.setId(id);

        btnTag.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btnTag.setVisibility(viewVisibility);
        layout.addView(btnTag);
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


    void OnClick(int id) {

        //Field field = board.getFieldById(id);//should be in following if block
        if (id < 1000) { // if button is on board
            Field field = board.getFieldById(id);

            switch (board.getState()) {
                case Preparation:
                    doHighlightingActions(field);
                    break;
                case Running:
                    doHighlightingActions(field);

                    if(field.getSegment() != null
                            && field.getSegment().getBodyType() == BodyType.Head
                            && field.getSegment().getPawn().getTeam() == board.currentPlayer) {
                        lastSelected = field;
                        loadInfoWithPawn();
                        board.setHighlightingMove(field);
                    }
                    break;
                default:
            }
        } else { // ID > 1000 are not on board
            board.clearBoard();

            if(lastSelected == null) {
                return;
            }

            switch (id) {
                case ActionIdConstants.MOVE:
                    if (lastSelected.getSegment().getPawn().getLeftSteps() == 0) break;
                    board.setHighlightingMove(lastSelected);
                    break;
                case ActionIdConstants.ATTACK_1:
                    if (!lastSelected.getSegment().getPawn().attackAvailable) break;
                    board.setHighlightingAttack(lastSelected, (byte) 1, lastSelected.getSegment().getPawn().getAttack1().getRange(), this);
                    break;
                case ActionIdConstants.ATTACK_2:
                    if (!lastSelected.getSegment().getPawn().attackAvailable) break;
                    board.setHighlightingAttack(lastSelected, (byte) 2, lastSelected.getSegment().getPawn().getAttack2().getRange(), this);
                    break;
                default:
            }
        }

        refreshBoard();

    }

    @SuppressLint("WrongConstant")
    private void refreshInteractableView(Field field){
        if (field != null){
            Button button = findViewById(field.getId());
            int visible = field.getStatus() ? 0 : 4;
            button.setVisibility(visible);

        } else{
            for (short y = 0; y < height; y++) {
                for (short x = 0; x < width; x++) {
                    int id = y * width + x;
                    if (board.getField(x,y).getStatus()) {
                        Button button = findViewById(id);
                        button.setVisibility(View.VISIBLE);
                    } else {
                        Button button = findViewById(field.getId());
                        button.setVisibility(View.INVISIBLE);
                    }
                }

            }
        }

    }


    @SuppressLint("ResourceType")
    private void setUpInfoPanel(LinearLayout panel) {
        Button btn = new Button(this);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        btn.setLayoutParams(new LinearLayout.LayoutParams(width / 6, width / 6));
        btn.setId(1100);

        btn.setVisibility(View.VISIBLE);
        btn.setClickable(false);
        panel.addView(btn);


        createTextViews(panel, "Name:", Color.WHITE, ActionIdConstants.NAME);
        createTextViews(panel, "HP:", Color.WHITE, ActionIdConstants.HP);
        createTextViews(panel, "Steps:", Color.WHITE, ActionIdConstants.STEPS);
        createTextViews(panel, "NAME", Color.WHITE, ActionIdConstants.CLASS);

        LinearLayout btnLayout = new LinearLayout(this);
        btnLayout.setOrientation(LinearLayout.VERTICAL);
        btnLayout.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));




        btn = createButton(btnLayout, ActionIdConstants.MOVE, 20);
        btn.setText("Move");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionIdConstants.ATTACK_1, 20);
        btn.setText("Attack 1");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionIdConstants.ATTACK_2, 20);
        btn.setText("Attack 2");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            OnClick(v.getId());
        });

        btn = createButton(btnLayout, ActionIdConstants.NEXT_TURN, 20);
        btn.setText("Next Turn");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            turnButtonOnClick();
        });

        btn = createButton(btnLayout, ActionIdConstants.BACK, 20);
        btn.setText("Back");
        btnLayout.addView(btn);
        btn.setOnClickListener((v) -> {
            loadMainMenu();
        });

        panel.addView(btnLayout);
    }

    private void loadMainMenu(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Leave game");
        builder1.setIcon(android.R.drawable.ic_dialog_alert);
        builder1.setMessage("Do you really want to leave the game? Progress will not be saved");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        exitGame();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void exitGame(){
        Intent i = new Intent(this, LevelSelection.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void turnButtonOnClick(){
        if (board.currentState.equals(LevelState.Preparation) && board.currentPlayer == 0){
            board.currentPlayer = 1;
            currentPlayer = player2;
        } else if(board.currentState.equals(LevelState.Preparation) && board.currentPlayer == 1){
            board.currentPlayer = 0;
            board.sortPawnsInTeams();
            board.currentState = LevelState.Running;
            currentPlayer = player1;
        } else if(board.currentState.equals(LevelState.Running) && board.currentPlayer == 0){
            board.currentPlayer = 1;
            currentPlayer = player2;


        } else if(board.currentState.equals(LevelState.Running) && board.currentPlayer == 1){
            board.currentPlayer = 0;
            currentPlayer = player1;

        }
        int currentPlayerIndex = board.currentPlayer;

        TextView playerName = findViewById(ActionIdConstants.CLASS);
        playerName.setText(currentPlayer.getPlayerName());

        resetAttributes();
        board.clearBoard();
        refreshBoard();
        Toast.makeText(MainActivity.this, Integer.toString(currentPlayerIndex), Toast.LENGTH_SHORT).show();
    }


    private void resetAttributes(){
        for (Pawn pawn: board.pawnsOnBoard) {
            pawn.setLeftSteps(pawn.getSpeed());
            pawn.attackAvailable = true;
        }
    }


    private void createTextViews(LinearLayout panel, String description, int color, int id) {
        TextView text = new TextView(this);
        text.setText(description);
        text.setTextColor(color);
        text.setId(id);


        panel.addView(text);
    }


    /**
     * Function iterates over field and refreshes every button representation
     */
    public void refreshBoard() {
        for (short y = 0; y < height; y++) {
            for (short x = 0; x < width; x++) {

                mapFieldToView(board.getField(x,y));
            }
        }
    }

    private void rewardWinner(Player winner){
        winner.addMoney(10000);

        SavegameUtil.writeSavegame(this);
    }


    /**
     * Function maps the status of a field to the correct picture representation
     *
     * @param field current field to refresh picture
     */
    private void mapFieldToView(Field field) {

        Button currBut = findViewById(field.getId());


        Drawable[] layerView = new Drawable[3];

        layerView[0] = this.getDrawable(field.background);
        layerView[1] = this.getDrawable(R.drawable.field_transparent);
        layerView[2] = this.getDrawable(R.drawable.field_transparent);

        if (field.getStatus()) {

            currBut.setVisibility(View.VISIBLE);

            switch (field.getHighlighting()) {
                case Empty:
                    layerView[2] = this.getDrawable(R.drawable.field_transparent);
                    break;

                case Reachable:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_reachable);
                    break;
                case MovableUp:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_up);
                    break;
                case MovableDown:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_down);
                    break;
                case MovableLeft:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_left);
                    break;
                case MovableRight:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable_right);
                    break;
                case Movable:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_movable);
                    break;
                case Healable:
                    break;
                case Attackable:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_attack_reachable);
                    break;
                case Attackable1:
                    layerView[2] = this.getDrawable(lastSelected.getSegment().getPawn().getAttack1().getResource());
                    break;
                case Attackable2:
                    layerView[2] = this.getDrawable(lastSelected.getSegment().getPawn().getAttack2().getResource());
                    break;
                case Buildable:
                    break;
                case SpawnableP1:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_spawnable_p0);
                    break;
                case SpawnableP2:
                    layerView[2] = this.getDrawable(R.drawable.highlighting_spawnable_p1);
                    break;
                default:
            }
            PawnSegment segment = field.getSegment();
            if (segment != null) {

                switch (segment.getBodyType()) {
                    case Head:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureHead);
                        break;
                    case Tail:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTail);
                        break;
                    case TailUp:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailUp);
                        break;
                    case TailDown:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailDown);
                        break;
                    case TailLeft:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailLeft);
                        break;
                    case TailRight:
                        layerView[1] = this.getDrawable(segment.getPawn().pictureTailRight);
                        break;
                    default:
                }
                layerView[1].mutate().setColorFilter(colorPawn(segment.getPawn()), PorterDuff.Mode.MULTIPLY);

            }
        } else {
            currBut.setVisibility(View.INVISIBLE);
        }

        LayerDrawable layerDrawable = new LayerDrawable(layerView);
        currBut.setBackground(layerDrawable);

    }

    private int colorPawn(Pawn pawn){
        if(pawn.getTeam() == 0){
            return getColorOfPawn(playerOneColor, pawn.getName());
        } else if(pawn.getTeam() == 1){
            return getColorOfPawn(playerTwoColor, pawn.getName());
        } else return -1;
    }

    private int getColorOfPawn(int[] colorPalette, String pawnName){
        switch(pawnName){
            case "Gunner":
                return colorPalette[0];
            case "Cavalry":
                return colorPalette[1];
            case "Doctor":
                return colorPalette[2];
            case "Sniper":
                return colorPalette[3];
            case "Tank":
                return colorPalette[4];
            case "Jet":
                return colorPalette[5];
            default:
                return -1;

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        music.pause();
        // stop the clock
    }

    @Override
    public void onResume() {
        super.onResume();
        music.start();

    }
    /**
     * By clicking on a highlighted field the associated action will be performed here
     *
     * @param field clicked field
     */
    private void doHighlightingActions(Field field) {
        if (field.getHighlighting() != Highlighting.Empty) {

            Pawn actor;
            // Pawn target;

            MediaPlayer audio;
            audio = MediaPlayer.create(this, R.raw.move_sound);
            audio.setVolume(GameSettings.sfxAmplifier,GameSettings.sfxAmplifier);

            // Actions when clicking a highlighted field
            switch (field.getHighlighting()) {
                case Empty:
                    board.clearBoard();
                    break;
                case Reachable:
                    break;
                case MovableUp:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.UP);
                    audio.start();
                    break;
                case MovableDown:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.DOWN);
                    audio.start();
                    break;
                case MovableLeft:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.LEFT);
                    audio.start();
                    break;
                case MovableRight:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.RIGHT);
                    audio.start();
                    break;
                case Movable:
                    actor = lastSelected.getSegment().getPawn();
                    actor.move(lastSelected, field, Direction.NONE);
                    audio.start();
                    break;
                case Healable:
                    //TODO
                    break;
                case Attackable1:
                    actor = lastSelected.getSegment().getPawn();
                    if(field.getSegment() != null && actor.attackAvailable) {
                        actor.attack1(this, field);
                        audio = MediaPlayer.create(this, actor.getAttack1().getAudio());
                        audio.start();
                        audio.setVolume(GameSettings.sfxAmplifier, GameSettings.sfxAmplifier);
                        actor.attackAvailable = false;

                    }
                    break;
                case Attackable2:
                    actor = lastSelected.getSegment().getPawn();
                    if(field.getSegment() != null && actor.attackAvailable) {
                        actor.attack2(this, field);
                        audio = MediaPlayer.create(this, actor.getAttack2().getAudio());
                        audio.start();
                        audio.setVolume(GameSettings.sfxAmplifier, GameSettings.sfxAmplifier);
                        actor.attackAvailable = false;
                    }
                    break;
                case Buildable:
                    //TODO
                    break;
                case SpawnableP1:
                    if(board.currentPlayer != 0) break;
                    showSpawnableList(findViewById(field.getId()));
                    break;
                case SpawnableP2:
                    if(board.currentPlayer != 1) break;
                    showSpawnableList(findViewById(field.getId()));
                    break;
                default:

            }
            refreshInteractableView(field);

            checkEndCondition();

            board.clearBoard();
            refreshBoard();

        }
    }

    private void checkEndCondition() {
        if(board.currentState == LevelState.Running && (board.pawnsInTeam1.size() == 0 || board.pawnsInTeam2.size() == 0)){
            //game has ended

            if(board.pawnsInTeam1.size() == 0 ) {
                Toast.makeText(MainActivity.this, player2.getPlayerName() + " hat gewonnen", Toast.LENGTH_SHORT).show();
                rewardWinner(player2);
            } else if (board.pawnsInTeam2.size() == 0) {
                Toast.makeText(MainActivity.this, player1.getPlayerName() + " hat gewonnen", Toast.LENGTH_SHORT).show();
                rewardWinner(player1);
            }

            board.setState(LevelState.Finished);

        }
    }


    private void loadDefaultView() {
        board.clearBoard();
        clearInfoPanel();
    }

    private void clearInfoPanel() {
        TextView showName =  findViewById(ActionIdConstants.NAME);
        TextView showHealth = findViewById(ActionIdConstants.HP);
        TextView showSteps = findViewById(ActionIdConstants.STEPS);
        TextView showClass = findViewById(ActionIdConstants.CLASS);

        showName.setVisibility(View.INVISIBLE);
        showHealth.setVisibility(View.INVISIBLE);
        showSteps.setVisibility(View.INVISIBLE);
        showClass.setVisibility(View.INVISIBLE);

        Button btn = findViewById(ActionIdConstants.MOVE);
        btn.setVisibility(View.INVISIBLE);
        btn = findViewById(ActionIdConstants.ATTACK_1);
        btn.setVisibility(View.INVISIBLE);
        btn = findViewById(ActionIdConstants.ATTACK_2);
        btn.setVisibility(View.INVISIBLE);
    }


    @SuppressLint("ResourceType")
    private void loadInfoWithPawn() {
        if (lastSelected.getSegment().getPawn().getTeam() == board.currentPlayer) {
            TextView showName = findViewById(ActionIdConstants.NAME);
            TextView showHealth = findViewById(ActionIdConstants.HP);
            TextView showSteps = findViewById(ActionIdConstants.STEPS);
            TextView showClass = findViewById(ActionIdConstants.CLASS);

            showName.setVisibility(View.VISIBLE);
            showHealth.setVisibility(View.VISIBLE);
            showSteps.setVisibility(View.VISIBLE);
            showClass.setVisibility(View.VISIBLE);

            showName.setText("Name: " + lastSelected.getSegment().getPawn().getName());
            showHealth.setText("HP: " + lastSelected.getSegment().getPawn().getCurrentSize() + " / " + lastSelected.getSegment().getPawn().getMaxSize());
            showSteps.setText("Steps: " + lastSelected.getSegment().getPawn().getLeftSteps());

            Button btn = findViewById(ActionIdConstants.MOVE);
            btn.setVisibility(View.VISIBLE);
            btn = findViewById(ActionIdConstants.ATTACK_1);
            btn.setVisibility(View.VISIBLE);
            btn = findViewById(ActionIdConstants.ATTACK_2);
            btn.setVisibility(View.VISIBLE);

            btn = findViewById(ActionIdConstants.ATTACK_1);
            btn.setText(lastSelected.getSegment().getPawn().getAttack1().getNameOfAttack());
            btn = findViewById(ActionIdConstants.ATTACK_2);
            btn.setText(lastSelected.getSegment().getPawn().getAttack2().getNameOfAttack());

            btn = findViewById(1100);
            btn.setBackgroundResource(lastSelected.getSegment().getPawn().icon);
            
        }
    }

    /**
     * Show menu for spawnable pawns
     * @param v spawnable button field that has been pressed
     */
    public void showSpawnableList(View v) {
        PopupMenu selectionList = new PopupMenu(this, v);
        selectionList.setOnMenuItemClickListener(this);

        for(int i=0;i<currentPlayer.getCatalogue().size();i++){
            ArrayList<PawnTypes> catalogue = currentPlayer.getCatalogue();
            switch(catalogue.get(i)){
                case troop:
                    selectionList.getMenu().add(v.getId(), 0 ,0 , "Troop");
                    break;
                case plane:
                    selectionList.getMenu().add(v.getId(), 0 ,0 , "Plane");
                    break;
                case sniper:
                    selectionList.getMenu().add(v.getId(), 0 ,0 , "Sniper");
                    break;
                case tank:
                    selectionList.getMenu().add(v.getId(), 0 ,0 , "Tank");
                    break;
                case healer:
                    selectionList.getMenu().add(v.getId(), 0 ,0 , "Healer");
                    break;
                case horse:
                    selectionList.getMenu().add(v.getId(), 0 ,0 , "Horse");
                    break;
                default:
                    break;
            }
        }

        selectionList.show();
    }

    /**
     * Actual spawning of pawn on the selected button field
     * @param item selected pawn to spawn on field
     * @return if spawning was successful
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        Field field = board.getFieldById(item.getGroupId());
        Pawn p;
        if(item.getTitle().equals("Troop")) {
            p = new PawnMachineGun();
        } else if(item.getTitle().equals("Plane")) {
            p = new PawnJet();
        } else if(item.getTitle().equals("Sniper")){
            p = new PawnSniper();
        } else if(item.getTitle().equals("Horse")){
            p = new PawnCavalry();
        } else if(item.getTitle().equals("Healer")){
            p = new PawnHealer();
        } else if(item.getTitle().equals("Tank")){
            p = new PawnTank();
        } else {
            throw new NoSuchElementException("Error, selected pawn not implemented");
        }

        MediaPlayer sound_spawn;
        sound_spawn = MediaPlayer.create(this, p.getSpawnSound());
        sound_spawn.setVolume(GameSettings.sfxAmplifier,GameSettings.sfxAmplifier);
        sound_spawn.start();

        field.setHighlighting(Highlighting.Empty);

        board.pawnsOnBoard.add(p);
        p.createSegment(field, BodyType.Head);
        p.setTeam(board.currentPlayer);
        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

        mapFieldToView(field);

        return true;
    }


}
