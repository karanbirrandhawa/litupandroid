package com.ivywire.resources;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Font Manager class to handle font management
 */
public class FontManager {

    // Constructor is not necessary since all class methods are static
    private FontManager() {}

    // Methods to apply fonts to various types of views
    public static void applyFont(Context ctx, TextView view, String font) {
        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), font);
        view.setTypeface(typeface);
    }
    public static void applyFont(Context ctx, EditText view, String font) {
        Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), font);
        view.setTypeface(typeface);
    }

}
