package com.game.flyingbird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    // canvas
    private int canvasWidth;
    private int canvasHeight;
    // tree
    private Bitmap tree;
    // bird
    private Bitmap[] bird = new Bitmap[2];
    private int birdX = 10;
    private int birdY = 0;
    private int minBirdY;
    private int maxBirdY;
    private int birdSpeed = 0;
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        // draw tree
        int treeX = 10, gapX = 50, treeY = canvasHeight - tree.getHeight();
        canvas.drawBitmap(tree, treeX, treeY, null);
        treeX = treeX + tree.getWidth() + gapX;
        canvas.drawBitmap(tree, treeX, treeY, null);
        treeX = treeX + tree.getWidth() + gapX;
        canvas.drawBitmap(tree, treeX, treeY, null);

        // draw bird
        minBirdY = bird[0].getHeight();
        maxBirdY = canvasHeight - bird[0].getHeight();
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
        int life_x = canvasWidth - life[0].getWidth() - 20, life_y = 20, life_gap = life[0].getWidth() + 20;
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
