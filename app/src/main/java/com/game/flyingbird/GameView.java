package com.game.flyingbird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class GameView extends View {

    // screen
    DisplayMetrics displayMetrics = new DisplayMetrics();
    // bird
    private Bitmap bird;
    // life
    private Bitmap[] life = new Bitmap[2];
    // score
    private Paint scorePaint = new Paint();
    // level
    private Paint levelPaint = new Paint();

    public GameView(Context context) {
        super(context);
        // get resources
        bird = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_full);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_empty);
        // set up scorePaint
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        // set up levelPaint
        levelPaint.setColor(Color.BLACK);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        // draw bird
        canvas.drawBitmap(bird, 0,  screenHeight / 2 - bird.getHeight(), null);
        // draw life
        int life_x = screenWidth - life[0].getWidth() - 20, life_y = 20, life_gap = life[0].getWidth() + 20;
        canvas.drawBitmap(life[0], life_x, life_y, null);
        canvas.drawBitmap(life[0], life_x - life_gap, life_y, null);
        canvas.drawBitmap(life[0], life_x - 2 * life_gap, life_y, null);
        // paint score
        canvas.drawText("Score: 0", 40, life[0].getHeight() / 2, scorePaint);
        // paint level
        canvas.drawText("Level 1", 40, life[0].getHeight(), levelPaint);
    }
}
