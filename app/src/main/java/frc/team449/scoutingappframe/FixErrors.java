package frc.team449.scoutingappframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import frc.team449.scoutingappframe.Activities.MainActivity;

public class FixErrors extends Activity {

    private TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_page);
        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);
        if (MainActivity.db.matchNumber != 0) {
            matchTitle.setText("Match " + Prematch.getMatchNum());
        }
        if (MainActivity.db.teamNumber != 0) {
            teamTitle.setText("Team " + Prematch.getTeamNum());
        }
        errors = findViewById(R.id.errors);
        errors.setText(MainActivity.db.checkData());
    }

    // Switches pages
    public void goToPrematch(View v) {
        Intent toPrematch = new Intent(this, Prematch.class);
        startActivity(toPrematch);
    }
}
