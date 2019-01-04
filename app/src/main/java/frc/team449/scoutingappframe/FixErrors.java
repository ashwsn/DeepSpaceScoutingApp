package frc.team449.scoutingappframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FixErrors extends Activity {

    private TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_page);
        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);
        if (MainActivity.match.matchNumber != 0) {
            matchTitle.setText("Match " + Prematch.getMatchNum());
        }
        if (MainActivity.match.teamNumber != 0) {
            teamTitle.setText("Team " + Prematch.getTeamNum());
        }
        errors = findViewById(R.id.errors);
        errors.setText(MainActivity.match.checkData());
    }

    // Switches pages
    public void goToPrematch(View v) {
        Intent toPrematch = new Intent(this, Prematch.class);
        startActivity(toPrematch);
    }
}
