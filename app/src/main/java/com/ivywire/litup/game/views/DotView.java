package com.ivywire.litup.game.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ivywire.litup.R;

/**
 * Created by KaranDesktop on 10/10/2014.
 */
public class DotView extends View {
    private Context context;
    private boolean litUp;
    private Paint bgPaint;
    private Paint borderPaint;
    private Paint litUpBgPaint;
    private Paint litUpBorderPaint;
    private int bgColor;
    private int borderColor;
    private int litUpBgColor;
    private int litUpBorderColor;
    private float borderWidth;
    private float positionX;
    private float positionY;
    private float radius;

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DotView,
                0,
                0
        );

        try {
            this.context = context;
            // Get values from typedarray
        } finally {
            a.recycle();
            init();
        }
    }

    private void init() {
        // Set light up to false on default
        litUp = false;
        // Inner fill
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bgColor = context.getResources().getColor(R.color.green1);
        bgPaint.setColor(bgColor);
        // Border of circle
        borderPaint = new Paint();
        borderWidth = 10;
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        borderColor = context.getResources().getColor(R.color.lightgreen1);
        borderPaint.setColor(borderColor);
        // Lit up inner fill
        litUpBgPaint = new Paint();
        litUpBgPaint.setStyle(Paint.Style.FILL);
        litUpBgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        litUpBgColor = context.getResources().getColor(R.color.brownred1);
        litUpBgPaint.setColor(litUpBgColor);
        // Lit up border
        litUpBorderPaint = new Paint();
        litUpBorderPaint.setStyle(Paint.Style.STROKE);
        litUpBorderPaint.setStrokeWidth(borderWidth);
        litUpBorderPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        litUpBorderColor = context.getResources().getColor(R.color.lightbrownred1);
        litUpBorderPaint.setColor(litUpBorderColor);

        radius = 35.0f;
        positionX = radius + borderWidth;
        positionY = radius + borderWidth;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw circle
        if (!litUp) {
            canvas.drawCircle(positionX, positionY, radius, borderPaint);
            canvas.drawCircle(positionX, positionY, radius, bgPaint);
        } else {
            canvas.drawCircle(positionX, positionY, radius, litUpBorderPaint);
            canvas.drawCircle(positionX, positionY, radius, litUpBgPaint);
        }
    }

    public void toggleLight() {
        litUp = !litUp;
        super.invalidate();
    }
}
