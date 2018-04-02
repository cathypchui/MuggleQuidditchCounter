package com.example.android.quidditchcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class startPage extends AppCompatActivity {
    EditText teamA;
    EditText teamB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        teamA = findViewById(R.id.team_a_pick_name);
        teamB = findViewById(R.id.team_b_pick_name);

    }
    public void startMatch(View view){
        String teamAName = teamA.getText().toString();
        String teamBName = teamB.getText().toString();

        if(teamAName.isEmpty() || teamAName.trim().length() == 0){
            teamAName = "Team A";
        }
        if(teamBName.isEmpty() || teamBName.trim().length() == 0){
            teamBName = "Team B";
        }

        Intent intent = new Intent(startPage.this, MainActivity.class);
        intent.putExtra("teamA", teamAName);
        intent.putExtra("teamB", teamBName);
        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String nameOfTeamA = teamA.getText().toString();
        savedInstanceState.putString("teamANameKey", nameOfTeamA);
        String nameOfTeamB = teamB.getText().toString();
        savedInstanceState.putString("teamBNameKey", nameOfTeamB);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * This restores the teams' names in case of orientation change.
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        String teamAString = savedInstanceState.getString("teamANameKey");
        teamA.setText(teamAString);
        String teamBString = savedInstanceState.getString("teamBNameKey");
        teamB.setText(teamBString);

    }

}
