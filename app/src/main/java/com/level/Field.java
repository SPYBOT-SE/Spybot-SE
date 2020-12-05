package com.level;


import com.pawns.PawnSegment;

/**
 *  Class representing one field of the game board.
 */
public class Field {

    private Highlighting highlighting = Highlighting.Empty;


    private boolean enabled = true; //active or inactive
    private int id; //id of button
    private PawnSegment segment = null;

    public PawnSegment getSegment() {
        return segment;
    }

    public void setSegment(PawnSegment segment) {
        this.segment = segment;
    }



    public Field(int id) {

        this.id = id;

    }

    public Field(int id, boolean enabled) {

        this.id = id;
        this.enabled = enabled;

    }

    public boolean getStatus() {
        return enabled;
    }

    public void setStatus(boolean newStatus) {
        this.enabled = newStatus;
    }

    public int getId() {return id; }

    public Highlighting getHighlighting() {
        return highlighting;
    }

    public void setHighlighting(Highlighting highlighting) {
        this.highlighting = highlighting;
    }
}
