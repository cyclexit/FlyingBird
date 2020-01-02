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
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    // screen
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private int screenWidth;
    private int screenHeight;
    // tree
    private Bitmap tree;
    // bird
    private Bitmap[] bird = new Bitmap[2];
    private int birdX;
    private int birdY;
    private int minBirdY;
    private int maxBirdY;
    private int birdSpeed;
    // life
    private Bitmap[] life = new Bitmap[2];
    // score
    private Paint scorePaint = new Paint();
    // level
    private Paint levelPaint = new Paint();
    // activity check
    private boolean touchFlag = false;

    public GameView(Context context) {
        super(context);
        // get resources
        tree = BitmapFactory.decodeResource(getResources(), R.drawable.tree);

        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird0);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird1);

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

        // get screen size
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // initial state of the bird
        birdX = 0;
        birdY = screenHeight / 2 - bird[0].getHeight();
        minBirdY = bird[0].getHeight();
        maxBirdY = screenHeight - bird[0].getHeight();
        birdSpeed = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw tree
        int treeX = 10, treeY = screenHeight - tree.getHeight();
        canvas.drawBitmap(tree, treeX, treeY, null);

        // draw bird
        birdY += birdSpeed;
        if (birdY < minBirdY) {
            birdY = minBirdY;
        }
        if (birdY > maxBirdY) {
            birdY = maxBirdY;
        }
        if (birdY < maxBirdY) {
            birdSpeed += 2;
        }
        if (touchFlag) {
            canvas.drawBitmap(bird[1], birdX, birdY, null);
            touchFlag = false;
        } else {
            canvas.drawBitmap(bird[0], birdX, birdY, null);
        }

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchFlag = true;
            if (birdY > minBirdY) {
                birdSpeed -= 20;
            }
        }
        return true;
    }
}
