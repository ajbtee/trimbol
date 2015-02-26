package com.detroitlabs.trimbol.utils;

import android.app.Activity;
import android.content.Context;

import com.detroitlabs.trimbol.objects.GameBoard;

import java.io.FileOutputStream;
import java.io.IOException;

public class SaveHandler extends Activity {

    public void saveGame(GameBoard gameBoard) {
        String SAVEFILE = "trimbol_save";
        String data = gameBoard.getGrid().toString();

        try {
            FileOutputStream fos = openFileOutput(SAVEFILE, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

}
