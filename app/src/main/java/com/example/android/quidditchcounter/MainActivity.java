package com.example.android.quidditchcounter;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int scoreTeamA;
    private int scoreTeamB;
    private TextView textViewA;
    private TextView textViewB;
    Button quaffleA;
    Button quaffleB;
    Button snitchA;
    Button snitchB;
    private TextView scoreViewA;
    private TextView scoreViewB;
    final private String teamAKey = "teamAscore";
    final private String teamBKey = "teamBscore";
    private static final long START_TIME_IN_MILLIS = 300000;

    private TextView mTextViewCountdown;
    private Button mButtonStartPause;
    private TextView mCountdownTitle;

    private CountDownTimer mCountDownTimer;

    private Boolean mTimerRunning = false;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewA = findViewById(R.id.team_A_name);
        textViewB = findViewById(R.id.team_B_name);
        quaffleA = findViewById(R.id.tenPointsA);
        quaffleB = findViewById(R.id.tenPointsB);
        snitchA = findViewById(R.id.thirtyPointsA);
        snitchB = findViewById(R.id.thirtyPointsB);
        scoreViewA = findViewById(R.id.team_a_score);
        scoreViewB = findViewById(R.id.team_b_score);
        Intent intent = getIntent();
        final String textViewALabel = intent.getStringExtra("teamA");
        final String textViewBLabel = intent.getStringExtra("teamB");
        textViewA.setText(textViewALabel);
        textViewB.setText(textViewBLabel);
        displayForTeamA(0);
        displayForTeamB(0);
        mTextViewCountdown = findViewById(R.id.countdown);
        mButtonStartPause = findViewById(R.id.countdown_start_pause);
        mCountdownTitle = findViewById(R.id.countdownTitle);
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        mTextViewCountdown.setVisibility(View.GONE);
        mButtonStartPause.setVisibility(View.GONE);
        mCountdownTitle.setVisibility(View.GONE);

    }

    /**
     * Displays the given score for Team A and B.
     */

    public void displayForTeamA(int score) {
        scoreViewA.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        scoreViewB.setText(String.valueOf(score));
    }

    public void displayTenPointsA(View view) {
        scoreTeamA = scoreTeamA + 10;
        displayForTeamA(scoreTeamA);
    }

    public void displayThirtyPointsA(View view) {
        scoreTeamA = scoreTeamA + 30;
        displayForTeamA(scoreTeamA);
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        if (scoreTeamA > scoreTeamB) {
            pauseTimer();
            mTextViewCountdown.setVisibility(View.GONE);
            mButtonStartPause.setVisibility(View.GONE);
            mCountdownTitle.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, textViewA.getText() + " got the Snitch! " + textViewA.getText() + " won the match! Press RESET",
                    Toast.LENGTH_LONG).show();
        } else if (scoreTeamA == scoreTeamB) {
            mTextViewCountdown.setVisibility(View.VISIBLE);
            mButtonStartPause.setVisibility(View.VISIBLE);
            mCountdownTitle.setVisibility(View.VISIBLE);
            resetTimer();
            startTimer();
            Toast.makeText(MainActivity.this, "It's a tie! Proceed to overtime!",
                    Toast.LENGTH_LONG).show();
            quaffleA.setVisibility(View.VISIBLE);
            quaffleB.setVisibility(View.VISIBLE);
            snitchA.setVisibility(View.VISIBLE);
            snitchB.setVisibility(View.VISIBLE);
        } else {
            pauseTimer();
            mTextViewCountdown.setVisibility(View.GONE);
            mButtonStartPause.setVisibility(View.GONE);
            mCountdownTitle.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, textViewA.getText() + " got the Snitch! " + textViewB.getText() + " won the match! Press RESET",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void displayTenPointsB(View view) {
        scoreTeamB = scoreTeamB + 10;
        displayForTeamB(scoreTeamB);
    }

    public void displayThirtyPointsB(View view) {
        scoreTeamB = scoreTeamB + 30;
        displayForTeamB(scoreTeamB);
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        if (scoreTeamA > scoreTeamB) {
            pauseTimer();
            mTextViewCountdown.setVisibility(View.GONE);
            mButtonStartPause.setVisibility(View.GONE);
            mCountdownTitle.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, textViewB.getText() + " got the Snitch! " + textViewA.getText() + " won the match! Press RESET",
                    Toast.LENGTH_LONG).show();
        } else if (scoreTeamA == scoreTeamB) {
            mTextViewCountdown.setVisibility(View.VISIBLE);
            mButtonStartPause.setVisibility(View.VISIBLE);
            mCountdownTitle.setVisibility(View.VISIBLE);
            resetTimer();
            startTimer();
            Toast.makeText(MainActivity.this, "It's a tie! Proceed to overtime!",
                    Toast.LENGTH_LONG).show();
            quaffleA.setVisibility(View.VISIBLE);
            quaffleB.setVisibility(View.VISIBLE);
            snitchA.setVisibility(View.VISIBLE);
            snitchB.setVisibility(View.VISIBLE);
        } else {
            pauseTimer();
            mTextViewCountdown.setVisibility(View.GONE);
            mButtonStartPause.setVisibility(View.GONE);
            mCountdownTitle.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, textViewB.getText() + " got the Snitch! " + textViewB.getText() + " won the match! Press RESET",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void resetScores(View view) {
        pauseTimer();
        resetTimer();
        scoreTeamB = 0;
        scoreTeamA = 0;
        displayForTeamB(scoreTeamB);
        displayForTeamA(scoreTeamA);
        quaffleA.setVisibility(View.VISIBLE);
        quaffleB.setVisibility(View.VISIBLE);
        snitchA.setVisibility(View.VISIBLE);
        snitchB.setVisibility(View.VISIBLE);
        mTextViewCountdown.setVisibility(View.GONE);
        mButtonStartPause.setVisibility(View.GONE);
        mCountdownTitle.setVisibility(View.GONE);
    }

    /**
     * This stores the teams' scores in case of orientation change.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("millisLeft", mTimeLeftInMillis);
        savedInstanceState.putBoolean("timerRunning", mTimerRunning);
        savedInstanceState.putLong("endTime", mEndTime);
        savedInstanceState.putInt(teamAKey, scoreTeamA);
        savedInstanceState.putInt(teamBKey, scoreTeamB);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * This restores the teams' scores in case of orientation change.
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        updateButtons();

        if(mTimerRunning){
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
        scoreTeamA = savedInstanceState.getInt(teamAKey);
        scoreTeamB = savedInstanceState.getInt(teamBKey);

        scoreViewA.setText(String.valueOf(scoreTeamA));
        scoreViewB.setText(String.valueOf(scoreTeamB));
    }

    /**
     * Starts timer.
     */
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();

    }

    /**
     * Pauses timer.
     */
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }

    /**
     * Resets timer to starting point.
     */
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    /**
     * Updates countdown textview for each tick of timer.
     */
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountdown.setText(timeLeftFormatted);
    }

    /**
     * Sets the visibility and text of timer buttons.
     */
    private void updateButtons() {
        if (mTimerRunning) {
            mButtonStartPause.setText("Pause");
        } else {
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
        }

    }
}