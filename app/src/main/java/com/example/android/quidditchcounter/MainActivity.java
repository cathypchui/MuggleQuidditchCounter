package com.example.android.quidditchcounter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    int finalScoreMatchTeamA;
    private int scoreTeamB;
    private TextView textViewA;
    private TextView textViewB;
    int finalScoreMatchTeamB;
    String matchDuration;
    /*
     * Declaring variables
     */
    private int scoreTeamA;
    private Button quaffleA;
    private TextView scoreViewA;
    private TextView scoreViewB;
    private Button quaffleB;
    private Button snitchA;
    private Button snitchB;
    private Button matchSummaryButton;
    private Button obliviateButton;
    private String timeWhenStopped;
    private String snitchCatchAndResultsString;
    final private String teamAKey = "teamAscore";
    final private String teamBKey = "teamBscore";
    /*
     * Textview with the stopwatch starting at 00:00
     */
    private Chronometer stopwatch;
    private long pauseOffset;
    private boolean running;

    private Button startPause;

    private long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    private Handler handler;

    private int Seconds, Minutes, MilliSeconds;
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%03d", Minutes, Seconds, MilliSeconds);

            stopwatch.setText(timeLeftFormatted);

            handler.postDelayed(this, 0);
        }

    };
    private Boolean stopwatchRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Initializing variables
         */
        textViewA = findViewById(R.id.team_A_name);
        textViewB = findViewById(R.id.team_B_name);
        quaffleA = findViewById(R.id.tenPointsA);
        quaffleB = findViewById(R.id.tenPointsB);
        snitchA = findViewById(R.id.thirtyPointsA);
        snitchB = findViewById(R.id.thirtyPointsB);
        scoreViewA = findViewById(R.id.team_a_score);
        scoreViewB = findViewById(R.id.team_b_score);
        obliviateButton = findViewById(R.id.obliviate_button);
        matchSummaryButton = findViewById(R.id.match_summary);
        matchSummaryButton.setVisibility(View.GONE);
        Intent intent = getIntent();
        final String textViewALabel = intent.getStringExtra("teamA");
        final String textViewBLabel = intent.getStringExtra("teamB");
        textViewA.setText(textViewALabel);
        textViewB.setText(textViewBLabel);
        displayForTeamA(0);
        displayForTeamB(0);
        finalScoreMatchTeamA = 0;
        finalScoreMatchTeamB = 0;
        matchDuration = "00:00:00";
        stopwatch = findViewById(R.id.stopwatch);
        startPause = findViewById(R.id.start_pause_stopwatch);
        handler = new Handler();
        timeWhenStopped = "00:00:00";
        startStopwatch();
        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stopwatchRunning) {
                    /*
                     * Pauses stopwatch if running, updates button to say START
                     */
                    stopwatchRunning = false;
                    timeWhenStopped = stopwatch.getText().toString();
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    quaffleA.setVisibility(View.GONE);
                    quaffleB.setVisibility(View.GONE);
                    snitchA.setVisibility(View.GONE);
                    snitchB.setVisibility(View.GONE);
                    updateButtons();
                } else {
                    /*
                     * Starts stopwatch if stopped, updates button to say PAUSE
                     */
                    startStopwatch();
                    quaffleA.setVisibility(View.VISIBLE);
                    quaffleB.setVisibility(View.VISIBLE);
                    snitchA.setVisibility(View.VISIBLE);
                    snitchB.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    /*
     * Pauses or starts stopwatch depending on whether stopwatch is running
     * Changes text of startPause button to say START
     *
        }

        /**
         * Starts stopwatch (default upon onCreate of match to start counting match time
         * Changes text of startPause button to say PAUSE
         */
    private void startStopwatch() {
        stopwatchRunning = true;
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        updateButtons();
    }

    /**
     * Resets stopwatch (default upon OBLIVIATE tap)
     */
    private void resetStopwatch() {
        MillisecondTime = 0L;
        StartTime = 0L;
        TimeBuff = 0L;
        UpdateTime = 0L;
        Seconds = 0;
        Minutes = 0;
        MilliSeconds = 0;

        stopwatch.setText("00:00:00");
        timeWhenStopped = stopwatch.getText().toString();

    }

    /**
     * Pauses stopwatch, does not update startPause button (to be called when snitch is caught, ending game, or if there is a tie upon snitch catch)
     * startPause button disappears
     */
    private void pauseStopwatch() {
        stopwatchRunning = false;
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);
    }

    /**
     * Sets the visibility and text of stopwatch buttons.
     */
    private void updateButtons() {
        if (stopwatchRunning) {
            startPause.setText(R.string.pauseString);
        } else {
            startPause.setText(R.string.startString);
        }

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

    /*
     * Increases and displays score when team A scores a quaffle goal
     */
    public void displayTenPointsA(View view) {
        scoreTeamA = scoreTeamA + 10;
        displayForTeamA(scoreTeamA);
    }

    /*
     * Increases and displays score when team A gets the snitch
     */
    public void displayThirtyPointsA(View view) {
        /*
         * Pauses the stopwatch
         * Hides startPause button so that the user can't keep the clock running after the snitch is caught
         */
        pauseStopwatch();
        startPause.setVisibility(View.GONE);
        scoreTeamA = scoreTeamA + 30;
        displayForTeamA(scoreTeamA);
        /*
         * Hides buttons for quaffle and snitch so that the scorekeeper can't change the score after the snitch is caught.
         * Disables OBLIVIATE, as the match is over. User is instructed to proceed to view match summary, where they can reset.
         */
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        obliviateButton.setVisibility(View.GONE);
        saveScoresAndTimes();
        /*
         * if there is no tie, the game is over. The team with more points, wins.
         */
        if (scoreTeamA > scoreTeamB) {
            finalScoreMatchTeamA = scoreTeamA;
            finalScoreMatchTeamB = scoreTeamB;
            matchDuration = stopwatch.getText().toString();
            snitchCatchAndResultsString = textViewA.getText() + " got the Snitch! " + textViewA.getText() + " won the match!";
            Toast.makeText(MainActivity.this, snitchCatchAndResultsString + " Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        } else if (scoreTeamA == scoreTeamB) {
            finalScoreMatchTeamA = scoreTeamA;
            finalScoreMatchTeamB = scoreTeamB;
            matchDuration = stopwatch.getText().toString();
            Toast.makeText(MainActivity.this, "It's a tie! Proceed to overtime!",
                    Toast.LENGTH_LONG).show();
            /*
             * Resets scores and team names, makes quaffle and snitch buttons visible again.
             * This method is called automatically when a snitch is caught to cause a tie)
             */
            resetScoresMethod();
            /*
             * intent to go to first overtime activity
             */
            proceedToFirstOvertime();
        } else {
            finalScoreMatchTeamA = scoreTeamA;
            finalScoreMatchTeamB = scoreTeamB;
            matchDuration = stopwatch.getText().toString();
            snitchCatchAndResultsString = textViewB.getText() + " got the Snitch! " + textViewB.getText() + " won the match!";
            Toast.makeText(MainActivity.this, snitchCatchAndResultsString + " Proceed to MATCH SUMMARY",
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
        pauseStopwatch();
        scoreTeamB = scoreTeamB + 30;
        displayForTeamB(scoreTeamB);
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        obliviateButton.setVisibility(View.GONE);
        if (scoreTeamA > scoreTeamB) {
            finalScoreMatchTeamA = scoreTeamA;
            finalScoreMatchTeamB = scoreTeamB;
            matchDuration = stopwatch.getText().toString();
            snitchCatchAndResultsString = textViewB.getText() + " got the Snitch! " + textViewA.getText() + " won the match!";
            Toast.makeText(MainActivity.this, snitchCatchAndResultsString + " Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        } else if (scoreTeamA == scoreTeamB) {
            finalScoreMatchTeamA = scoreTeamA;
            finalScoreMatchTeamB = scoreTeamB;
            matchDuration = stopwatch.getText().toString();
            Toast.makeText(MainActivity.this, "It's a tie! Proceed to overtime!",
                    Toast.LENGTH_LONG).show();
            /*
             * Resets scores and team names, makes quaffle and snitch buttons visible again.
             * This method is called automatically when a snitch is caught to cause a tie)
             */
            resetScoresMethod();
            /*
             * intent to go to first overtime activity
             */
            proceedToFirstOvertime();
        } else {
            finalScoreMatchTeamA = scoreTeamA;
            finalScoreMatchTeamB = scoreTeamB;
            matchDuration = stopwatch.getText().toString();
            snitchCatchAndResultsString = textViewB.getText() + " got the Snitch! " + textViewB.getText() + " won the match!";
            Toast.makeText(MainActivity.this, snitchCatchAndResultsString + " Proceed to MATCH SUMMARY",
                    Toast.LENGTH_LONG).show();
            /*
             * Makes matchSummaryButton appear
             */
            matchSummaryButton.setVisibility(View.VISIBLE);
        }
    }

    /*
     * Saves scores of both teams and match times before they are reset and the activity is killed
     */
    private void saveScoresAndTimes() {
        finalScoreMatchTeamA = scoreTeamA;
        finalScoreMatchTeamB = scoreTeamB;
        matchDuration = stopwatch.getText().toString();

    }

    /*
     * Upon click of MATCH SUMMARY button,  resets scores and team names,
     * makes startPause, quaffle, and snitch buttons visible again,
     * allows user to proceed to matchSummary activity
     * makes match summary button invisible once it is clicked (part of the reset process).
     */
    public void matchSummaryButtonOnClick(View view) {
        resetScoresMethod();
        matchSummaryButton.setVisibility(View.GONE);
        proceedToMatchSummary();
    }

    /*
     * Resets scores and team names, makes quaffle and snitch buttons visible again.
     * Makes match summary button invisible
     * Resets stopwatch and makes startPause button visible again
     */
    private void resetScoresMethod() {
        startPause.setVisibility(View.VISIBLE);
        scoreTeamB = 0;
        scoreTeamA = 0;
        displayForTeamB(scoreTeamB);
        displayForTeamA(scoreTeamA);
        quaffleA.setVisibility(View.VISIBLE);
        quaffleB.setVisibility(View.VISIBLE);
        snitchA.setVisibility(View.VISIBLE);
        snitchB.setVisibility(View.VISIBLE);
        textViewA.setText(null);
        textViewB.setText(null);
        resetStopwatch();
    }

    /*
     * Proceeds to firstOvertime activity with team names saved
     */
    private void proceedToFirstOvertime() {
        String teamAName = textViewA.getText().toString();
        String teamBName = textViewB.getText().toString();

        if (teamAName.isEmpty() || teamAName.trim().length() == 0) {
            teamAName = "Team A";
        }
        if (teamBName.isEmpty() || teamBName.trim().length() == 0) {
            teamBName = "Team B";
        }
        Intent intent = new Intent(MainActivity.this, firstOvertime.class);
        /*
         * Carries over result statement, scores of both teams, and match duration to next activity
         */
        intent.putExtra("teamA", teamAName);
        intent.putExtra("teamB", teamBName);
        intent.putExtra("finalScoreMatchTeamALabel", finalScoreMatchTeamA);
        intent.putExtra("finalScoreMatchTeamBLabel", finalScoreMatchTeamB);
        intent.putExtra("matchDurationLabel", matchDuration);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(MainActivity.this, matchSummary.class);
        /*
         * Carries over result statement, scores of both teams, and match duration to next activity
         */
        intent.putExtra("teamA", teamAName);
        intent.putExtra("teamB", teamBName);
        intent.putExtra("finalScoreMatchTeamALabel", finalScoreMatchTeamA);
        intent.putExtra("finalScoreMatchTeamBLabel", finalScoreMatchTeamB);
        intent.putExtra("matchDurationLabel", matchDuration);
        startActivity(intent);
        finish();
    }

    /*
     * Resets names of teams, resets scores to zero for both teams, returns to startPage, upon tap of OBLIVIATE
     */
    public void resetAndReturnToStart(View view) {
        scoreTeamB = 0;
        scoreTeamA = 0;
        displayForTeamB(scoreTeamB);
        displayForTeamA(scoreTeamA);
        textViewA.setText(null);
        textViewB.setText(null);
        resetStopwatch();
        Intent intent = new Intent(MainActivity.this, startPage.class);
        startActivity(intent);
        finish();
    }

    /**
     * This stores the teams' scores in case of orientation change.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(teamAKey, scoreTeamA);
        savedInstanceState.putInt(teamBKey, scoreTeamB);
        savedInstanceState.putString("stopwatchKey", stopwatch.getText().toString());
        savedInstanceState.putBoolean("stopwatchRunningKey", stopwatchRunning);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * This restores the teams' scores in case of orientation change.
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        scoreTeamA = savedInstanceState.getInt(teamAKey);
        scoreTeamB = savedInstanceState.getInt(teamBKey);
        scoreViewA.setText(String.valueOf(scoreTeamA));
        scoreViewB.setText(String.valueOf(scoreTeamB));
        stopwatch.setText(savedInstanceState.getString("stopwatchKey"));
        Boolean stopwatchRunningState = savedInstanceState.getBoolean("stopwatchRunningKey");
        stopwatchRunning = !stopwatchRunningState;
        updateButtons();
    }
}
