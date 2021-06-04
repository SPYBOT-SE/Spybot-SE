package com.level;

import com.activities.MainActivity;
import com.example.spybot.R;
import com.model.AdjacencyList;
import com.model.LevelState;
import com.model.shortcuts.ActionIdConstants;
import com.pawns.Pawn;
import com.utilities.BoardUtil;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Board {

    private int idCount = 0;

    public ArrayList<Pawn> pawnsOnBoard = new ArrayList<>();
    public ArrayList<Pawn> pawnsInTeam1 = new ArrayList<>();
    public ArrayList<Pawn> pawnsInTeam2 = new ArrayList<>();

    public byte currentPlayer = 0;
    public LevelState currentState = LevelState.Preparation;

    public final int sizeY; //vertical axis
    public final int sizeX; //horizontal axis

    private Field[][] board;
    private AdjacencyList<Field> graph;


    public Board(int[][] level) {
        sizeY = level.length;
        sizeX = level[0].length;

        initBoard(level);
        initGraph();
    }

    public AdjacencyList<Field> getGraph() {
        return graph;
    }

    private void initBoard(int[][] fieldDef) {


        board = new Field[sizeX][sizeY];

        for (short y = 0; y < sizeY; y++) {
            for (short x = 0; x < sizeX; x++) {
                board[x][y] = getField(fieldDef[y][x],x,y);
            }
        }
    }


    /**
     * Initialisation of a field.
     * @param value Field definition as specified in Level Data
     * @param x coordinate on board
     * @param y coordinate on board
     * @return New field with specified data like background, highlighting,
     */
    private Field getField(int value, short x, short y) {
        Field outField;

        boolean enabled = (value & 0x000F) % 2 == 1;
        int background = (value & 0x000F) / 2;

        switch (background) {
            case 0:
                background = R.drawable.field_clean;
                break;
            case 1:
                background = R.drawable.field_classroom;
                break;
            case 2:
                background = R.drawable.field_tiled;
                break;
            case 3:
                background = R.drawable.grass_small;
                break;
            case 4:
                background = R.drawable.dirt_small;
                break;
            case 5:
                background = R.drawable.water_small;
                break;
            case 6:
                background = R.drawable.street_small;
                break;
            default:
                throw new NoSuchElementException("Error, background " + background + " not implemented yet!");
        }
        outField = new Field(idCount, enabled, x, y, background, this);

        int player = (value & 0x00F0)>>4;

        switch (player) {
            case 1:
                outField.setHighlighting(Highlighting.SpawnableP1);
                break;
            case 2:
                outField.setHighlighting(Highlighting.SpawnableP2);
                break;
            default:

        }

        idCount++;
        return outField;
    }


    private void initGraph() {
        graph = new AdjacencyList<>();

        int top, bottom, left, right;
        Field middle;

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                middle = board[x][y];
                if (middle.getStatus()) {
                    graph.addVertex(middle);

                    top = y - 1;
                    bottom = y + 1;
                    left = x - 1;
                    right = x + 1;

                    addVerticalEdge(x, top, middle);
                    addVerticalEdge(x, bottom, middle);
                    addHorizontalEdge(left, y, middle);
                    addHorizontalEdge(right, y, middle);
                }
            }
        }
    }

    public Field getField(short x, short y) {
        if(x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
            return board[x][y];
        } else{
            return null;
        }
    }

    public Field getFieldById(int id) {
        return board[id % sizeX][id / sizeX];
    }

    public LevelState getState() {
        return currentState;
    }

    public void setState(LevelState state) {
        this.currentState = state;
    }

    private void addVerticalEdge(int horizontal, int vertical, Field middle) {
        Field neighbour;
        if (vertical >= 0 && vertical < sizeY) {
            neighbour = board[horizontal][vertical];
            if (neighbour.getStatus()) {
                graph.addEdge(middle, neighbour);
            }
        }
    }

    private void addHorizontalEdge(int horizontal, int vertical, Field middle) {
        Field neighbour;
        if (horizontal >= 0 && horizontal < sizeX) {
            neighbour = board[horizontal][vertical];
            if (neighbour.getStatus()) {
                graph.addEdge(middle, neighbour);
            }
        }
    }

    public void clearBoard() {
        if(currentState == LevelState.Preparation) {
            return;
        }

        for (short y = 0; y < sizeY; y++) {
            for (short x = 0; x < sizeX; x++) {
                Field currentF = getField(x,y);
                currentF.setHighlighting(Highlighting.Empty);
            }
        }
    }

    public void sortPawnsInTeams() {
        for (Pawn pawn: pawnsOnBoard) {
            if (pawn.getTeam() == 0){
                pawnsInTeam1.add(pawn);
            } else if (pawn.getTeam() == 1){
                pawnsInTeam2.add(pawn);
            }
        }
    }

    public void setHighlightingMove(Field field) {
        clearBoard();

        Pawn pawn = field.getSegment().getPawn();

        if (pawn.getLeftSteps() > 0) {
            for (Field neighborField : BoardUtil.getFieldsInRange(this, field.getId(), pawn.getLeftSteps(), ActionIdConstants.MOVE)) {
                if(neighborField.getSegment() != null) {
                    continue;
                }
                neighborField.setHighlighting(Highlighting.Reachable);
            }

            if (getField((short)(field.x + 1), field.y) != null && getField((short)(field.x + 1), field.y).getSegment() == null) {
                getField((short)(field.x + 1), field.y).setHighlighting(Highlighting.MovableRight);
            }

            if (getField((short)(field.x - 1), field.y) != null && getField((short)(field.x - 1), field.y).getSegment() == null) {
                getField((short)(field.x - 1), field.y).setHighlighting(Highlighting.MovableLeft);
            }
            if (getField(field.x, (short)(field.y+1)) != null && getField(field.x, (short)(field.y+1)).getSegment() == null) {
                getField(field.x, (short)(field.y+1)).setHighlighting(Highlighting.MovableDown);
            }
            if (getField(field.x, (short)(field.y-1)) != null && getField(field.x, (short)(field.y-1)).getSegment() == null) {
                getField(field.x, (short)(field.y-1)).setHighlighting(Highlighting.MovableUp);
            }
        }
    }

    public void setHighlightingAttack(Field field, byte attackNum, byte range, MainActivity mainActivity) {
        clearBoard();
        for (Field neighborField : BoardUtil.getFieldsInRange(this, field.getId(), range, ActionIdConstants.ATTACK_1)) {
            if (neighborField.getSegment() != null && neighborField.getSegment().getPawn().getTeam() != currentPlayer) {
                if (attackNum == 1) {
                    neighborField.setHighlighting(Highlighting.Attackable1);
                } else if (attackNum == 2) {
                    neighborField.setHighlighting(Highlighting.Attackable2);
                }
            } else {
                neighborField.setHighlighting(Highlighting.Attackable);
            }

        }
    }
}
