package frc.team449.scoutingappframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Endgame extends Activity {

    private EditText comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame_page);
        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);
        if (MainActivity.match.matchNumber != 0) {
            matchTitle.setText("Match " + Prematch.getMatchNum());
        }
        if (MainActivity.match.teamNumber != 0) {
            teamTitle.setText("Team " + Prematch.getTeamNum());
        }
        comments = findViewById(R.id.comments);
        comments.setText(MainActivity.match.comments);
    }

    // Calls activity to go to auto page
    public void toTeleop(View v) {
        // Save values to Database
        MainActivity.match.comments = comments.getText().toString();
        // Switches pages
        Intent toTeleop = new Intent(this, Teleop.class);
        startActivity(toTeleop);
    }

    // Calls activity to go to endgame page
    public void submit(View v) {
        // Save values to Database
        MainActivity.match.comments = comments.getText().toString();
        // Switches pages
        Intent submit = new Intent(this, Submit.class);
        startActivity(submit);
    }

}
