package com.model.constants;

public class JsonConstants {

    public static final String SAVEGAMEFILE = "savegame.json";

    public static final String PLAYERS = "players";
    public static final String NAME = "name";
    public static final String CUR = "currency";
    public static final String INV = "inventory";

    public static final String SOUNDSTG = "soundsettings";
    public static final String MASTERAMP = "master";
    public static final String MUSICAMP = "music";
    public static final String SFXAMP = "sfx";

    public static final String DEFAULTSAVEGAME = "{\n" +
            "  \"players\":\n" +
            "  {\n" +
            "    \"0\":\n" +
            "    {\n" +
            "      \"name\":\"P1\",\n" +
            "      \"currency\":0,\n" +
            "      \"inventory\":[\n" +
            "        \"troop\"\n" +
            "      ]\n" +
            "    },\n" +
            "    \"1\":\n" +
            "    {\n" +
            "      \"name\":\"P1\",\n" +
            "      \"currency\":0,\n" +
            "      \"inventory\":[\n" +
            "        \"troop\"\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
