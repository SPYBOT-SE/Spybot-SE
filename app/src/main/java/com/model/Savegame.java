package com.model;

import android.util.Log;
import com.model.shortcuts.Json;
import com.player.Player;
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
}
