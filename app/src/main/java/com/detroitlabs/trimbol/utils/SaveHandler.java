package com.detroitlabs.trimbol.utils;

import android.app.Activity;
import android.content.Context;

import android.util.Log;
import com.detroitlabs.trimbol.objects.GameBoard;

import java.io.FileOutputStream;
import java.io.IOException;

public class SaveHandler extends Activity {

    public void saveGame(final GameBoard gameBoard) {
        final String SAVEFILE = "trimbol_save";
        final String data = gameBoard.getGrid().toString();

        try {
            final FileOutputStream fos = openFileOutput(SAVEFILE, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
    }

}
