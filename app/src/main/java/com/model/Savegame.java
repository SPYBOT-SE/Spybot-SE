package com.model;

import android.util.Log;
import com.model.constants.JsonConstants;
import com.player.Player;
import com.utilities.GeneralUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//TODO implement
public class Savegame {

    private HashMap<String, Player> players = new HashMap<>();
    private SoundValues sounds = new SoundValues();

    public Savegame() {
        players.put("player1", new Player("player1"));
        players.put("player2", new Player("player2"));
    }

    public Savegame(JSONObject json) {
        try {
            JSONArray playersJson = json.getJSONArray(JsonConstants.PLAYERS);
            JSONObject player;
            for (int i = 0; i < playersJson.length(); i++) {
                player = playersJson.getJSONObject(i);
                players.put(player.getString(JsonConstants.NAME), new Player(player));
            }

            JSONObject soundsJson = json.getJSONObject(JsonConstants.SOUNDSTG);
            sounds.setMasterAmplifier(soundsJson.getInt(JsonConstants.MASTERAMP));
            sounds.setMusicAmplifier(soundsJson.getInt(JsonConstants.MUSICAMP));
            sounds.setSfxAmplifier(soundsJson.getInt(JsonConstants.SFXAMP));
        } catch (JSONException e) {
            Log.e("Spybot/Exception", "JSON parse exception in Savegame constructor");
            e.printStackTrace();
            players.put("player1", new Player("player1"));
            players.put("player2", new Player("player2"));
        }
    }


    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public SoundValues getSounds() {
        return sounds;
    }

    public void setSounds(SoundValues sounds) {
        this.sounds = sounds;
    }

    public void resetSounds() {
        sounds.setMasterAmplifier(SoundValues.defaultAmplifier);
        sounds.setMusicAmplifier(SoundValues.defaultAmplifier);
        sounds.setSfxAmplifier(SoundValues.defaultAmplifier);
    }

    public void muteSounds() {
        sounds.setMasterAmplifier(0);
        sounds.setMusicAmplifier(0);
        sounds.setSfxAmplifier(0);
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

        String tmp = builder.substring(0, builder.length() - 2);
        builder = new StringBuilder(tmp);
        builder.append("\n");
        builder.append(ind);
        builder.append("  ],\n");

        builder.append(sounds.toJSON(indent + 2));
        builder.append(ind);
        builder.append("}");

        return builder.toString();
    }
}
