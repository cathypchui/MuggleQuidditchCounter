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

public class firstOvertime extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 300000;
    final private String teamAKey = "teamAscore";
    final private String teamBKey = "teamBscore";
    protected Button mButtonStartPause;
    private int scoreTeamA;
    private int scoreTeamB;
    private TextView textViewA;
    private TextView textViewB;
    private Button quaffleA;
    private Button quaffleB;
    private Button snitchA;
    private Button snitchB;
    private TextView scoreViewA;
    private TextView scoreViewB;
    private Button matchSummaryButton;
    private Button obliviateButton;
    private TextView mTextViewCountdown;
    private TextView mCountdownTitle;

    private CountDownTimer mCountDownTimer;

    private Boolean mTimerRunning = false;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_overtime);
        textViewA = findViewById(R.id.team_A_name);
        textViewB = findViewById(R.id.team_B_name);
        quaffleA = findViewById(R.id.tenPointsA);
        quaffleB = findViewById(R.id.tenPointsB);
        snitchA = findViewById(R.id.thirtyPointsA);
        snitchB = findViewById(R.id.thirtyPointsB);
        scoreViewA = findViewById(R.id.team_a_score);
        scoreViewB = findViewById(R.id.team_b_score);
        matchSummaryButton = findViewById(R.id.match_summary);
        matchSummaryButton.setVisibility(View.GONE);
        obliviateButton = findViewById(R.id.obliviate_button);
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
                    /*
                     * Hides quaffle and snitch buttons so that no goals or snitch catches are counted
                     * when the stopwatch is not running
                     */
                    quaffleA.setVisibility(View.GONE);
                    quaffleB.setVisibility(View.GONE);
                    snitchA.setVisibility(View.GONE);
                    snitchB.setVisibility(View.GONE);
                    pauseTimer();
                } else {
                    startTimer();
                    /*
                     * Reveals quaffle and snitch buttons so that goals or snitch catches are counted
                     * when the stopwatch is running
                     */
                    quaffleA.setVisibility(View.VISIBLE);
                    quaffleB.setVisibility(View.VISIBLE);
                    snitchA.setVisibility(View.VISIBLE);
                    snitchB.setVisibility(View.VISIBLE);
                }
            }
        });
        startTimer();

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
            mButtonStartPause.setVisibility(View.GONE);
            mCountdownTitle.setVisibility(View.GONE);
            obliviateButton.setVisibility(View.GONE);
            Toast.makeText(firstOvertime.this, textViewA.getText() + " got the Snitch! " + textViewA.getText() + " won the match! Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        } else if (scoreTeamA == scoreTeamB) {
            pauseTimer();
            resetTimer();
            Toast.makeText(firstOvertime.this, "It's a tie! Proceed to overtime!",
                    Toast.LENGTH_LONG).show();
            /*
             * Proceeds to secondOvertime activity with team names saved but not scores
             */
            resetScoresMethod();
            proceedToSecondOvertime();
        } else {
            pauseTimer();
            resetTimer();
            mButtonStartPause.setVisibility(View.GONE);
            obliviateButton.setVisibility(View.GONE);
            Toast.makeText(firstOvertime.this, textViewA.getText() + " got the Snitch! " + textViewB.getText() + " won the match! Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
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
            mButtonStartPause.setVisibility(View.GONE);
            obliviateButton.setVisibility(View.GONE);
            Toast.makeText(firstOvertime.this, textViewB.getText() + " got the Snitch! " + textViewA.getText() + " won the match! Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        } else if (scoreTeamA == scoreTeamB) {
            Toast.makeText(firstOvertime.this, "It's a tie! Proceed to overtime!",
                    Toast.LENGTH_LONG).show();
            /*
             * Proceeds to secondOvertime activity with team names saved, but not scores
             */
            proceedToSecondOvertime();
        } else {
            pauseTimer();
            resetTimer();
            mButtonStartPause.setVisibility(View.GONE);
            obliviateButton.setVisibility(View.GONE);
            Toast.makeText(firstOvertime.this, textViewB.getText() + " got the Snitch! " + textViewB.getText() + " won the match! Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        }
    }

    /*
     * Upon click of MATCH SUMMARY button,  resets scores and team names,
     * makes quaffle and snitch buttons visible again,
     * allows user to proceed to matchSummary activity
     * makes match summary button invisible,
     */
    public void matchSummaryButtonOnClick(View view) {
        resetScoresMethod();
        proceedToMatchSummary();
    }

    /*
     * Resets scores and team names, makes quaffle and snitch buttons visible again.
     */
    private void resetScoresMethod() {
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
        matchSummaryButton.setVisibility(View.GONE);
        textViewA.setText(null);
        textViewB.setText(null);
    }

    /*
     * Resets names of teams, resets scores to zero for both teams, returns to startPage, upon tap of OBLIVIATE
     */
    public void resetAndReturnToStart(View view) {
        resetTimer();
        scoreTeamB = 0;
        scoreTeamA = 0;
        displayForTeamB(scoreTeamB);
        displayForTeamA(scoreTeamA);
        textViewA.setText(null);
        textViewB.setText(null);
        Intent intent = new Intent(firstOvertime.this, startPage.class);
        startActivity(intent);
    }

    /*
     * Proceeds to match summary page without saving team names
     */
    private void proceedToMatchSummary() {
        String teamAName = textViewA.getText().toString();
        String teamBName = textViewB.getText().toString();

        if (teamAName.isEmpty() || teamAName.trim().length() == 0) {
            teamAName = "Team A";
        }
        if (teamBName.isEmpty() || teamBName.trim().length() == 0) {
            teamBName = "Team B";
        }
        Intent intent = new Intent(firstOvertime.this, matchSummary.class);
        intent.putExtra("teamA", teamAName);
        intent.putExtra("teamB", teamBName);
        startActivity(intent);
        finish();
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

        if (mTimerRunning) {
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
        scoreTeamA = savedInstanceState.getInt(teamAKey);
        scoreTeamB = savedInstanceState.getInt(teamBKey);

        scoreViewA.setText(String.valueOf(scoreTeamA));
        scoreViewB.setText(String.valueOf(scoreTeamB));
    }

    private void proceedToSecondOvertime() {
        String teamAName = textViewA.getText().toString();
        String teamBName = textViewB.getText().toString();

        if (teamAName.isEmpty() || teamAName.trim().length() == 0) {
            teamAName = "Team A";
        }
        if (teamBName.isEmpty() || teamBName.trim().length() == 0) {
            teamBName = "Team B";
        }
        Intent intent = new Intent(firstOvertime.this, secondOvertime.class);
        intent.putExtra("teamA", teamAName);
        intent.putExtra("teamB", teamBName);
        startActivity(intent);
        finish();
    }

    /**
     * Starts timer.
     */
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1) {
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
        int MilliSeconds = (int) (mTimeLeftInMillis % 1000);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%03d", minutes, seconds, MilliSeconds);

        mTextViewCountdown.setText(timeLeftFormatted);
        if (seconds == 0) {
            snitchNotCaught();
        }
    }

    /**
     * Sets the visibility and text of timer buttons.
     */
    private void updateButtons() {
        if (mTimerRunning) {
            mButtonStartPause.setText(R.string.pauseString);
        } else {
            mButtonStartPause.setText(R.string.startString);

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
        }

    }

    /*
     * What happens if no one caught the snitch in the five minutes of first overtime
     */
    private void snitchNotCaught() {
        if (scoreTeamA > scoreTeamB) {
            mButtonStartPause.setVisibility(View.GONE);
            obliviateButton.setVisibility(View.GONE);
            Toast.makeText(firstOvertime.this, textViewB.getText() + " got the Snitch! " + textViewA.getText() + " won the match! Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        } else if (scoreTeamA == scoreTeamB) {
            pauseTimer();
            resetTimer();
            Toast.makeText(firstOvertime.this, "It's a tie! Proceed to overtime!",
                    Toast.LENGTH_LONG).show();
            /*
             * Proceeds to secondOvertime activity with team names saved, but not scores
             */
            resetScoresMethod();
            proceedToSecondOvertime();
        } else {
            pauseTimer();
            resetTimer();
            mButtonStartPause.setVisibility(View.GONE);
            mCountdownTitle.setVisibility(View.GONE);
            Toast.makeText(firstOvertime.this, textViewB.getText() + " got the Snitch! " + textViewB.getText() + " won the match! Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        }
    }
}

