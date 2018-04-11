package com.example.android.quidditchcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class matchSummary extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);
        TextView resultsSummary = findViewById(R.id.match_summary_view);
        TextView durationOfMatch = findViewById(R.id.match_duration_view);
        TextView finalScoreTeamAView = findViewById(R.id.score_team_A_view);
        TextView finalScoreTeamBView = findViewById(R.id.score_team_B_view);
        TextView teamANameView = findViewById(R.id.score_team_A_name_view);
        TextView teamBNameView = findViewById(R.id.score_team_B_name_view);
        Intent intent = getIntent();
        final String teamALabel = intent.getStringExtra("teamA");
        final String teamBLabel = intent.getStringExtra("teamB");
    }

    public void resetAndReturn(View view) {
        Intent intent = new Intent(matchSummary.this, startPage.class);
        startActivity(intent);
        finish();
    }
}
