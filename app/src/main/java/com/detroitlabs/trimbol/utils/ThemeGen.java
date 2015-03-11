package com.detroitlabs.trimbol.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.detroitlabs.trimbol.R;

/**
 * Created by andrewjb on 12/5/14.
 */
public class ThemeGen {

    public static Paint buttonText = new Paint();
    public static String background;
    public static boolean isDark = false;

    public static int rocCircle;
    public static int papCircle;
    public static int sciCircle;

    public static String rocName;
    public static String papName;
    public static String sciName;

    public static Paint themeRocCircle = new Paint();
    public static Paint themePapCircle = new Paint();
    public static Paint themeSciCircle = new Paint();
    public static Paint themeRocSelected = new Paint();
    public static Paint themePapSelected = new Paint();
    public static Paint themeSciSelected = new Paint();
    public static Paint themeRocDepth = new Paint();
    public static Paint themePapDepth = new Paint();
    public static Paint themeSciDepth = new Paint();
    public static Bitmap themeRocIcon;
    public static Bitmap themePapIcon;
    public static Bitmap themeSciIcon;

    public static void makePaints(final Context context) {
        buttonText.setARGB(160, 255, 255, 255);
        buttonText.setAntiAlias(true);
        buttonText.setStyle(Paint.Style.FILL);

        if (Settings.gameTheme == Settings.GameTheme.TRIMBOL) {
            background = "#ebebeb";
            isDark = false;
            rocCircle = Color.parseColor("#6c9b45");
            papCircle = Color.parseColor("#0091cf");
            sciCircle = Color.parseColor("#f8923e");

            rocName = "ROCK";
            papName = "PAPER";
            sciName = "SCISSORS";

            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci);
        }
        if (Settings.gameTheme == Settings.GameTheme.DARK) {
            background = "#393939";
            isDark = true;
            rocCircle = Color.parseColor("#6c9b45");
            papCircle = Color.parseColor("#0091cf");
            sciCircle = Color.parseColor("#f8923e");

            rocName = "ROCK";
            papName = "PAPER";
            sciName = "SCISSORS";

            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci);
        }
        if (Settings.gameTheme == Settings.GameTheme.CLASSIC) {
            background = "#ebebeb";
            isDark = false;
            rocCircle = Color.parseColor("#849b45");
            papCircle = Color.parseColor("#03cacf");
            sciCircle = Color.parseColor("#f85d3f");

            rocName = "ROCK";
            papName = "PAPER";
            sciName = "SCISSORS";

            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc_classic);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap_classic);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci_classic);
        }
        if (Settings.gameTheme == Settings.GameTheme.LITERALLY) {
            background = "#393939";
            isDark = true;
            rocCircle = Color.parseColor("#7d7e61");
            papCircle = Color.parseColor("#a3a396");
            sciCircle = Color.parseColor("#649eb7");

            rocName = "STONE";
            papName = "PARCHMENT";
            sciName = "SHEARS";

            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc_literally);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap_literally);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci_literally);
        }
        if (Settings.gameTheme == Settings.GameTheme.SMORES) {
            background = "#36302d";
            isDark = true;
            rocCircle = Color.parseColor("#419a9e");
            papCircle = Color.parseColor("#586688");
            sciCircle = Color.parseColor("#a03325");

            rocName = "MALLOW";
            papName = "GRAHAM";
            sciName = "CHOCOLATE";

            themeRocIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.roc_smore);
            themePapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.pap_smore);
            themeSciIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.sci_smore);
        }

        themeRocCircle.setARGB(255, Color.red(rocCircle), Color.green(rocCircle), Color.blue(rocCircle));
        themePapCircle.setARGB(255, Color.red(papCircle), Color.green(papCircle), Color.blue(papCircle));
        themeSciCircle.setARGB(255, Color.red(sciCircle), Color.green(sciCircle), Color.blue(sciCircle));
        themeRocCircle.setAntiAlias(true);
        themeRocCircle.setStyle(Paint.Style.FILL);
        themePapCircle.setAntiAlias(true);
        themePapCircle.setStyle(Paint.Style.FILL);
        themeSciCircle.setAntiAlias(true);
        themeSciCircle.setStyle(Paint.Style.FILL);

        themeRocDepth.setARGB(255, Math.abs(Color.red(rocCircle)-40), Math.abs(Color.green(rocCircle)-40), Math.abs(Color.blue(rocCircle)-40));
        themePapDepth.setARGB(255, Math.abs(Color.red(papCircle)-40), Math.abs(Color.green(papCircle)-40), Math.abs(Color.blue(papCircle)-40));
        themeSciDepth.setARGB(255, Math.abs(Color.red(sciCircle)-40), Math.abs(Color.green(sciCircle)-40), Math.abs(Color.blue(sciCircle)-40));
        themeRocDepth.setAntiAlias(true);
        themeRocDepth.setStyle(Paint.Style.FILL);
        themePapDepth.setAntiAlias(true);
        themePapDepth.setStyle(Paint.Style.FILL);
        themeSciDepth.setAntiAlias(true);
        themeSciDepth.setStyle(Paint.Style.FILL);

        themeRocSelected.setARGB(255, Math.abs(Color.red(rocCircle)-40), Math.abs(Color.green(rocCircle)-40), Math.abs(Color.blue(rocCircle)-40));
        themePapSelected.setARGB(255, Math.abs(Color.red(papCircle)-40), Math.abs(Color.green(papCircle)-40), Math.abs(Color.blue(papCircle)-40));
        themeSciSelected.setARGB(255, Math.abs(Color.red(sciCircle)-40), Math.abs(Color.green(sciCircle)-40), Math.abs(Color.blue(sciCircle)-40));
        themeRocSelected.setStrokeWidth(5);
        themeRocSelected.setAntiAlias(true);
        themeRocSelected.setStyle(Paint.Style.STROKE);
        themePapSelected.setStrokeWidth(5);
        themePapSelected.setAntiAlias(true);
        themePapSelected.setStyle(Paint.Style.STROKE);
        themeSciSelected.setStrokeWidth(5);
        themeSciSelected.setAntiAlias(true);
        themeSciSelected.setStyle(Paint.Style.STROKE);
    }

    public static void scaleBitmaps(final int size) {
        final int iconSize = (int) (size * .38); //.38 of canvas size
        buttonText.setTextSize((float) (size * .32));
        themeRocIcon = Bitmap.createScaledBitmap(themeRocIcon, iconSize, iconSize, true);
        themePapIcon = Bitmap.createScaledBitmap(themePapIcon, iconSize, iconSize, true);
        themeSciIcon = Bitmap.createScaledBitmap(themeSciIcon, iconSize, iconSize, true);
    }

    public static void refreshView(final ViewGroup viewGroup) {
        for (int index = 0; index < viewGroup.getChildCount(); index++) {
            final View childView = viewGroup.getChildAt(index);

            if (childView instanceof ViewGroup) {
                childView.invalidate();
                refreshView((ViewGroup) childView);
            } else {
                childView.invalidate();
            }
        }
    }

    public static void setTitle(final ImageView title) {
        if (isDark) {
            title.setImageResource(R.drawable.title_dk);
        } else {
            title.setImageResource(R.drawable.title);
        }
    }
}
