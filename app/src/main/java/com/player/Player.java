package com.player;

import android.util.Log;
import com.model.constants.JsonConstants;
import com.utilities.GeneralUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Player {

    private ArrayList<PawnTypes> catalogue = new ArrayList<>();
    private String playerName;
    private int currency = 0;

    public Player(String name) {
        playerName = name;
        catalogue = new ArrayList<>();
        catalogue.add(PawnTypes.troop);
    }

    public Player(JSONObject json) {
        try {
            playerName = json.getString(JsonConstants.NAME);
            currency = json.getInt(JsonConstants.CUR);

            JSONArray inventory = json.getJSONArray(JsonConstants.INV);
            for (int i = 0; i < inventory.length(); i++) {
                String pawn = inventory.getString(i);
                switch (pawn){
                    case "troop":
                        catalogue.add(PawnTypes.troop);
                        break;
                    case "healer":
                        catalogue.add(PawnTypes.healer);
                        break;
                    case "plane":
                        catalogue.add(PawnTypes.plane);
                        break;
                    case "tank":
                        catalogue.add(PawnTypes.tank);
                        break;
                    case "sniper":
                        catalogue.add(PawnTypes.sniper);
                        break;
                    case "horse":
                        catalogue.add(PawnTypes.horse);
                        break;
                    default:
                        Log.i("Spybot/Info", "Could not read pawntype from savegame for " + pawn);
                }
            }
        } catch (JSONException e) {
            Log.e("Spybot/Exception", "JSON parse exception in Player constructor");
            //e.printStackTrace();
            playerName = "player1";
            catalogue = new ArrayList<>();
            catalogue.add(PawnTypes.troop);
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void addMoney(int amount){
        currency += amount;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public ArrayList<PawnTypes> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(ArrayList<PawnTypes> catalogue) {
        this.catalogue = catalogue;
    }

    public String toJSON(int indent) {
        String ind = GeneralUtil.getIndent(indent);

        StringBuilder builder = new StringBuilder();
        builder.append(ind);
        builder.append("{\n");
        builder.append(ind);
        builder.append("  \"name\":\"");
        builder.append(playerName);
        builder.append("\",\n");
        builder.append(ind);
        builder.append("  \"currency\":");
        builder.append(currency);
        builder.append(",\n");
        builder.append(ind);
        builder.append("  \"inventory\":[\n");

        for (PawnTypes pawnType : catalogue) {
            builder.append(ind);
            builder.append("    \"");
            builder.append(pawnType);
            builder.append("\"");
            builder.append(",\n");
        }

        return GeneralUtil.finalizeJSON(ind, builder);
    }

}
