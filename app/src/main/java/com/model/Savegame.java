package com.model;

import android.util.Log;
import com.model.shortcuts.Json;
import com.player.Player;
import com.utilities.GeneralUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//TODO implement
public class Savegame {

    private HashMap<String, Player> players = new HashMap<>();

    public Savegame() {
        players.put("player1", new Player("player1"));
        players.put("player2", new Player("player2"));
    }

    public Savegame(JSONObject json) {
        try {
            JSONArray playersJson = json.getJSONArray(Json.PLAYERS);
            for (int i = 0; i < playersJson.length(); i++) {
                json = playersJson.getJSONObject(i);
                players.put(json.getString(Json.NAME), new Player(json));
            }
        } catch (JSONException e) {
            Log.e("Spybot/Exception", "JSON parse exception in Savegame constructor");
            e.printStackTrace();
            players.put("player1", new Player("player1"));
            players.put("player2", new Player("player2"));
        }
    }

    public String toJSON(int indent) {
        String ind = GeneralUtil.getIndent(indent);

        StringBuilder builder = new StringBuilder();
        builder.append(ind);
        builder.append("{\n");
        builder.append(ind);
        builder.append("  \"players\":\n");
        builder.append(ind);
        builder.append("  [\n");

        for (Player p: players.values()) {
            builder.append(p.toJSON(indent + 4));
            builder.append(",\n");
        }

        return GeneralUtil.finalizeJSON(ind, builder);
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }
}
