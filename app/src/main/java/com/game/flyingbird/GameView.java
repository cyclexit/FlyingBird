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
    private int birdX;
    private int birdY;
    private int minBirdY;
    private int maxBirdY;
    private int birdSpeed;
    // worm
    private Bitmap worm;
    private int wormX;
    private int wormY;
    private int wormSpeed;
    private final static int WORM_VALUE = 10;
    // shit
    private Bitmap shit;
    private int shitX;
    private int shitY;
    private int shitSpeed;
    // life
    private int lifeCounter = 3;
    private Bitmap[] life = new Bitmap[2];
    // score
    private int score;
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

        worm = BitmapFactory.decodeResource(getResources(), R.drawable.worm);

        shit = BitmapFactory.decodeResource(getResources(), R.drawable.shit);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_full);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_empty);

        // set up scorePaint
        score = 0;
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(32);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        // set up levelPaint
        levelPaint.setColor(Color.BLACK);
        levelPaint.setTextSize(32);
        levelPaint.setTypeface(Typeface.DEFAULT_BOLD);
        levelPaint.setAntiAlias(true);

        // initial state of the bird
        birdX = 10;
        birdY = 0;
        birdSpeed = 0;

        // initial state of the worm
        wormX = canvasWidth + worm.getWidth();
        wormY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY) + minBirdY);
        wormSpeed = 20;

        // initial state of the shit
        shitX = canvasWidth + shit.getWidth();
        shitY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY) + minBirdY);
        shitSpeed = 20;
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
        maxBirdY = canvasHeight - tree.getHeight() - bird[0].getHeight();
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

        // draw worm
        wormX -= wormSpeed;
        if (hitCheck(wormX, wormY)) {
            score += WORM_VALUE;
            wormX = canvasWidth + worm.getWidth();
            wormY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY) + minBirdY);
        } else if (wormX < 0) {
            wormX = canvasWidth + worm.getWidth();
            wormY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY) + minBirdY);
        }
        canvas.drawBitmap(worm, wormX, wormY, null);

        // draw shit
        shitX -= shitSpeed;
        if (hitCheck(shitX, shitY)) {
            --lifeCounter;
            shitX = canvasWidth + shit.getWidth();
            shitY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY) + minBirdY);
        } else if (shitX < 0) {
            shitX = canvasWidth + shit.getWidth();
            shitY = (int) Math.floor(Math.random() * (maxBirdY - minBirdY) + minBirdY);
        }
        canvas.drawBitmap(shit, shitX, shitY, null);


        // draw life
        int lifeX = canvasWidth - life[0].getWidth() - 20, lifeY = 20, lifeGap = life[0].getWidth() + 20;
        for (int i = 0; i < 3; ++i) {
            if (i < lifeCounter) {
                canvas.drawBitmap(life[0], lifeX - i * lifeGap, lifeY, null);
            } else {
                canvas.drawBitmap(life[1], lifeX - i * lifeGap, lifeY, null);
            }
        }
        if (lifeCounter == 0) {
            // dead, go into another window
        }
        // canvas.drawBitmap(life[0], lifeX, lifeY, null);
        // canvas.drawBitmap(life[0], lifeX - lifeGap, lifeY, null);
        // canvas.drawBitmap(life[0], lifeX - 2 * lifeGap, lifeY, null);

        // paint score
        canvas.drawText("Score: " + score, 40, life[0].getHeight() / 2, scorePaint);

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

    public boolean hitCheck(int x, int y) {
        return birdX < x && x < (birdX + bird[0].getWidth()) &&
                birdY < y && y < (birdY + bird[0].getHeight());
    }

    // getters
    public int getLifeCounter() {
        return lifeCounter;
    }

    public int getScore() {
        return score;
    }
}
