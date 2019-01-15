package frc.team449.scoutingappframe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import frc.team449.scoutingappframe.Prematch;
import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.Submit;

public class Endgame extends BaseActivity {

    private EditText comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame_page);
        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);
        if (MainActivity.db.matchNumber != 0) {
            matchTitle.setText("Match " + Prematch.getMatchNum());
        }
        if (MainActivity.db.teamNumber != 0) {
            teamTitle.setText("Team " + Prematch.getTeamNum());
        }
        comments = findViewById(R.id.comments);
        comments.setText(MainActivity.db.comments);
    }

    // Calls activity to go to auto page
    public void toTeleop(View v) {
        // Save values to Database
        MainActivity.db.comments = comments.getText().toString();
        // Switches pages
        Intent toTeleop = new Intent(this, Teleop.class);
        startActivity(toTeleop);
    }

    // Calls activity to go to endgame page
    public void submit(View v) {
        // Save values to Database
        MainActivity.db.comments = comments.getText().toString();
        // Switches pages
        Intent submit = new Intent(this, Submit.class);
        startActivity(submit);
    }

}
