package com.detroitlabs.trimbol.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.detroitlabs.trimbol.R;

/**
 * Created by andrewjb on 12/5/14.
 */
public class ThemeGen {

    public static Paint menuText = new Paint();
    public static String background = new String();
    public static boolean isDark = false;

    public static Paint themeRocCircle = new Paint();
    public static Paint themePapCircle = new Paint();
    public static Paint themeSciCircle = new Paint();
    public static Paint themeRocSelected = new Paint();
    public static Paint themePapSelected = new Paint();
    public static Paint themeSciSelected = new Paint();
    public static Bitmap themeRocIcon;
    public static Bitmap themePapIcon;
    public static Bitmap themeSciIcon;

    public static void makePaints(Context context) {
        menuText.setARGB(160, 255, 255, 255);
        menuText.setAntiAlias(true);
        menuText.setStyle(Paint.Style.FILL);

        if (Settings.gameTheme == Settings.GameTheme.TRIMBOL) {
            background = "#ebebeb";

            themeRocCircle.setARGB(255, 108, 155, 69);
            themePapCircle.setARGB(255, 3, 145, 207);
            themeSciCircle.setARGB(255, 248, 152, 71);

            themeRocSelected.setARGB(255, 66, 95, 42);
            themePapSelected.setARGB(255, 2, 89, 127);
            themeSciSelected.setARGB(255, 152, 95, 46);

            isDark = false;
            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci);
        }
        if (Settings.gameTheme == Settings.GameTheme.CLASSIC) {
            background = "#d5e0dc";

            themeRocCircle.setARGB(255, 132, 155, 69);
            themePapCircle.setARGB(255, 3, 202, 207);
            themeSciCircle.setARGB(255, 248, 93, 63);

            themeRocSelected.setARGB(255, 81, 95, 42);
            themePapSelected.setARGB(255, 2, 124, 127);
            themeSciSelected.setARGB(255, 152, 65, 46);

            isDark = false;
            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc_classic);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap_classic);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci_classic);
        }
        if (Settings.gameTheme == Settings.GameTheme.LITERALLY) {
            background = "#393939";

            themeRocCircle.setARGB(255, 125, 126, 97);
            themePapCircle.setARGB(255, 163, 163, 150);
            themeSciCircle.setARGB(255, 141, 160, 186);

            themeRocSelected.setARGB(255, 76, 77, 59);
            themePapSelected.setARGB(255, 127, 128, 121);
            themeSciSelected.setARGB(255, 86, 98, 114);

            isDark = true;
            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc_literally);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap_literally);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci_literally);
        }
        themeRocCircle.setAntiAlias(true);
        themeRocCircle.setStyle(Paint.Style.FILL);

        themePapCircle.setAntiAlias(true);
        themePapCircle.setStyle(Paint.Style.FILL);

        themeSciCircle.setAntiAlias(true);
        themeSciCircle.setStyle(Paint.Style.FILL);

        themeRocSelected.setStrokeWidth(6);
        themeRocSelected.setAntiAlias(true);
        themeRocSelected.setStyle(Paint.Style.STROKE);

        themePapSelected.setStrokeWidth(6);
        themePapSelected.setAntiAlias(true);
        themePapSelected.setStyle(Paint.Style.STROKE);

        themeSciSelected.setStrokeWidth(6);
        themeSciSelected.setAntiAlias(true);
        themeSciSelected.setStyle(Paint.Style.STROKE);
    }

    public static void scaleBitmaps(int size) {
        int iconSize = (int) (size * .38); //.38 of canvas size
        menuText.setTextSize((float) (size * .32));
        themeRocIcon = Bitmap.createScaledBitmap(themeRocIcon, iconSize, iconSize, true);
        themePapIcon = Bitmap.createScaledBitmap(themePapIcon, iconSize, iconSize, true);
        themeSciIcon = Bitmap.createScaledBitmap(themeSciIcon, iconSize, iconSize, true);
    }

    public static void refreshView(ViewGroup viewGroup) {
        for (int index = 0; index < viewGroup.getChildCount(); index++) {
            View childView = viewGroup.getChildAt(index);

            if (childView instanceof ViewGroup) {
                childView.invalidate();
                refreshView((ViewGroup) childView);
            } else {
                childView.invalidate();
            }
        }
    }

    public static void themePick() {
        boolean changedTheme = false;
        if (Settings.gameTheme == Settings.GameTheme.CLASSIC && changedTheme == false) {
            Settings.gameTheme = Settings.GameTheme.TRIMBOL;
            changedTheme = true;
        }
        if (Settings.gameTheme == Settings.GameTheme.LITERALLY && changedTheme == false) {
            Settings.gameTheme = Settings.GameTheme.CLASSIC;
            changedTheme = true;
        }
        if (Settings.gameTheme == Settings.GameTheme.TRIMBOL && changedTheme == false) {
            Settings.gameTheme = Settings.GameTheme.LITERALLY;
            changedTheme = true;
        }
    }

    public static void setTitle(ImageView title) {
        if (isDark)
            title.setImageResource(R.drawable.title_dk);
        else
            title.setImageResource(R.drawable.title);
    }
}
