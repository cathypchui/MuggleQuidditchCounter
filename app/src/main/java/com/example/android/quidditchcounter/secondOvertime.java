package com.example.android.quidditchcounter;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class secondOvertime extends AppCompatActivity {

    final private String teamAKey = "teamAscore";
    final private String teamBKey = "teamBscore";
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
    private TextView stopwatch;
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
        setContentView(R.layout.activity_second_overtime);
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
        stopwatch = findViewById(R.id.stopwatch);
        startPause = findViewById(R.id.start_pause_stopwatch);
        handler = new Handler();
        /*
         * Pauses or starts stopwatch depending on whether stopwatch is running
         * Changes text of startPause button to say START
         */
        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stopwatchRunning) {
                    /*
                     * Pauses stopwatch if running, updates button to say START
                     */
                    stopwatchRunning = false;
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
        startStopwatch();

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
     * Pauses stopwatch, does not update startPause button (to be called when snitch is caught, ending game, or if there is a tie upon snitch catch)
     * startPause button disappears
     */
    private void pauseStopwatch() {
        stopwatchRunning = false;
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);
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

    /*
     * Displays the given score for Team A and B.
     */

    public void displayForTeamA(int score) {
        scoreViewA.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        scoreViewB.setText(String.valueOf(score));
    }

    /*
     * Displays what happens if team A gets a quaffle goal
     */
    public void displayTenPointsA(View view) {
        pauseStopwatch();
        startPause.setVisibility(View.GONE);
        scoreTeamA = scoreTeamA + 10;
        displayForTeamA(scoreTeamA);
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        obliviateButton.setVisibility(View.GONE);
        Toast.makeText(secondOvertime.this, textViewA.getText() + " scored first! " + textViewA.getText() + " won the match! Proceed to MATCH SUMMARY",
                Toast.LENGTH_LONG).show();
        matchSummaryButton.setVisibility(View.VISIBLE);
    }

    /*
     * Displays what happens if team A gets the snitch
     */
    public void displayThirtyPointsA(View view) {
        pauseStopwatch();
        startPause.setVisibility(View.GONE);
        scoreTeamA = scoreTeamA + 30;
        displayForTeamA(scoreTeamA);
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        Toast.makeText(secondOvertime.this, textViewA.getText() + " scored first! " + textViewA.getText() + " won the match! Proceed to MATCH SUMMARY",
                Toast.LENGTH_LONG).show();
        matchSummaryButton.setVisibility(View.VISIBLE);
    }

    /*
     * Displays what happens if team B gets a quaffle goal
     */
    public void displayTenPointsB(View view) {
        pauseStopwatch();
        startPause.setVisibility(View.GONE);
        scoreTeamB = scoreTeamB + 10;
        displayForTeamB(scoreTeamB);
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        obliviateButton.setVisibility(View.GONE);
        Toast.makeText(secondOvertime.this, textViewB.getText() + " scored first! " + textViewB.getText() + " won the match! Proceed to MATCH SUMMARY",
                Toast.LENGTH_LONG).show();
        matchSummaryButton.setVisibility(View.VISIBLE);
    }

    /*
     * Displays what happens if team B gets the snitch
     */
    public void displayThirtyPointsB(View view) {
        pauseStopwatch();
        startPause.setVisibility(View.GONE);
        scoreTeamB = scoreTeamB + 30;
        displayForTeamB(scoreTeamB);
        quaffleA.setVisibility(View.GONE);
        quaffleB.setVisibility(View.GONE);
        snitchA.setVisibility(View.GONE);
        snitchB.setVisibility(View.GONE);
        obliviateButton.setVisibility(View.GONE);
        Toast.makeText(secondOvertime.this, textViewB.getText() + " scored first! " + textViewB.getText() + " won the match! Proceed to MATCH SUMMARY",
                Toast.LENGTH_LONG).show();
        matchSummaryButton.setVisibility(View.VISIBLE);
    }

    /*
     * Upon click of MATCH SUMMARY button,  resets scores and team names,
     * makes quaffle and snitch buttons visible again,
     * allows user to proceed to matchSummary activity
     * makes match summary button invisible,
     */
    public void matchSummaryButtonOnClick(View view) {
        resetScoresMethod();
        matchSummaryButton.setVisibility(View.GONE);
        proceedToMatchSummary();
    }

    /*
     * Resets scores and team names, makes quaffle and snitch buttons visible again.
     * Resets stopwatch, makes startPause visible again
     */
    private void resetScoresMethod() {
        scoreTeamB = 0;
        scoreTeamA = 0;
        displayForTeamB(scoreTeamB);
        displayForTeamA(scoreTeamA);
        startPause.setVisibility(View.VISIBLE);
        quaffleA.setVisibility(View.VISIBLE);
        quaffleB.setVisibility(View.VISIBLE);
        snitchA.setVisibility(View.VISIBLE);
        snitchB.setVisibility(View.VISIBLE);
        textViewA.setText(null);
        textViewB.setText(null);
        resetStopwatch();
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
        Intent intent = new Intent(secondOvertime.this, startPage.class);
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
        Intent intent = new Intent(secondOvertime.this, matchSummary.class);
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
        savedInstanceState.putInt(teamAKey, scoreTeamA);
        savedInstanceState.putInt(teamBKey, scoreTeamB);
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
    }
}
