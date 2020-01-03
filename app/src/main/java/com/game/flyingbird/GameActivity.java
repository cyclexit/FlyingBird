package com.game.flyingbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    private Handler handler = new Handler();
    private final static long TIMER_PERIOD = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (gameView.getLifeCounter() > 0) {
                            gameView.invalidate();
                        } else {
                            setContentView(R.layout.activity_game);
                            TextView scoreTextView = findViewById(R.id.scoreTextView);
                            scoreTextView.setText("Your final score is " + gameView.getScore());
                            cancel();
                        }
                    }
                });
            }
        }, 0, TIMER_PERIOD);
    }

    // onClick method for restartButton
    public void restart(View view) {
        gameView = new GameView(this);
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (gameView.getLifeCounter() > 0) {
                            gameView.invalidate();
                        } else {
                            setContentView(R.layout.activity_game);
                            TextView scoreTextView = findViewById(R.id.scoreTextView);
                            scoreTextView.setText("Your final score is " + gameView.getScore());
                            cancel();
                        }
                    }
                });
            }
        }, 0, TIMER_PERIOD);
    }

    // onClick method for returnButton
    public void returnMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
