package com.utilities;

import android.content.Context;
import android.util.Log;
import com.example.spybot.R;
import com.model.Savegame;
import com.model.constants.JsonConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class SavegameUtil {

    private static Savegame savegame;


    public static Savegame getSavegame() {
        return savegame;
    }

    public static void setSavegame(Savegame savegame) {
        SavegameUtil.savegame = savegame;
    }

    public static void loadSavegame(Context ctx) {
        Savegame sg;

        if (!isFileExisting(JsonConstants.SAVEGAMEFILE, ctx)) {
            resetSavegame(ctx);
        }

        sg = getSavegame(ctx, false);
        if (sg == null) {
            //Savegame is corrupted
            sg = getSavegame(ctx, true);
        }

        savegame = sg;
    }

    /**
     * Get an instance of a savegame related to the savegame file.
     * If there is a problem with the savegame and the defaultSavegame param is false, null will be returned,
     * otherwise a default instance will be returned.
     *
     * @param ctx Context
     * @param defaultSavegame, if returned value should be a default savegame, otherwise will be null
     * @return loaded Savegame
     */
    private static Savegame getSavegame(Context ctx, boolean defaultSavegame) {
        Savegame savegame;

        if (defaultSavegame) {
            savegame = withoutError(ctx);
        } else {
            try {
                savegame = withError(ctx);
            } catch (IOException e) {
                Log.e("Spybot/Exception", "Corrupted Savegame");
                savegame = null;
            } catch (JSONException e) {
                Log.e("Spybot/Exception", "JSON parse Exception");
                savegame = null;
            }
        }

        return savegame;
    }

    private static Savegame withError(Context ctx) throws IOException, JSONException {
        String json = FileUtil.readFromFile(JsonConstants.SAVEGAMEFILE, ctx);

        return new Savegame(new JSONObject(json));
    }

    private static Savegame withoutError(Context ctx) {
        Savegame savegame;
        try {
            savegame = withError(ctx);
        } catch (IOException |JSONException e) {
            savegame = new Savegame();
        }

        return  savegame;
    }

    public static void resetSavegame(Context ctx) {
        String defaultSavegame = JsonConstants.DEFAULTSAVEGAME;

        try {
            defaultSavegame = FileUtil.getFileContent(ctx.getResources().openRawResource(R.raw.savegame));
        } catch (Exception e) {
            Log.e("Spybot/Exception", "Could not read default savegame resource, hardcoded String used");
        }

        FileUtil.writeToFile(JsonConstants.SAVEGAMEFILE, defaultSavegame, ctx);
        Log.i("Spybot/Savegame", "Savegame resetted");
    }

    public static boolean isFileExisting(String filename, Context ctx) {
        boolean ret = true;
        try {
            InputStream inputStream = ctx.openFileInput(filename);
            inputStream.close();
        } catch (FileNotFoundException e) {
            ret = false;
        } catch (IOException e) {
            Log.e("Spybot/Exception", "General IOException");
        }
        return ret;
    }

    public static void writeSavegame(Context ctx) {
        FileUtil.writeToFile(JsonConstants.SAVEGAMEFILE, savegame.toJSON(0), ctx);
    }


    public static float getMasterVolume() {
        return 0.01f * savegame.getSounds().getMasterAmplifier();
    }

    public static float getMusicVolume() {
        return (0.01f * savegame.getSounds().getMusicAmplifier()) * (0.01f * savegame.getSounds().getMasterAmplifier());
    }

    public static float getSfxVolume() {
        return (0.01f * savegame.getSounds().getSfxAmplifier()) * (0.01f * savegame.getSounds().getMasterAmplifier());
    }

    public static int getMasterAmplifier() {
        return savegame.getSounds().getMasterAmplifier();
    }

    public static void setMasterAmplifier(int amplifier) {
        savegame.getSounds().setMasterAmplifier(amplifier);
    }

    public static int getMusicAmplifier() {
        return savegame.getSounds().getMusicAmplifier();
    }

    public static void setMusicAmplifier(int amplifier) {
        savegame.getSounds().setMusicAmplifier(amplifier);
    }

    public static int getSfxAmplifier() {
        return savegame.getSounds().getSfxAmplifier();
    }

    public static void setSfxAmplifier(int amplifier) {
        savegame.getSounds().setSfxAmplifier(amplifier);
    }



    public static void resetSounds() {
        savegame.resetSounds();
    }

    public static void muteSounds() {
        savegame.muteSounds();
    }

}
