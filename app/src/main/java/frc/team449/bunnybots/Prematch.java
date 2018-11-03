package frc.team449.bunnybots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Prematch extends Activity implements AdapterView.OnItemSelectedListener {

    // Team and match #
    private static String matchNumberValue;
    private static String teamNumberValue;

    // Input fields
    private EditText scoutNameText;
    private CheckBox noShowBox;
    private Spinner teamNumber;
    private ArrayAdapter<CharSequence> teamAdapter;
    private Spinner matchNumber;
    private ArrayAdapter<CharSequence> matchAdapter;
    private RadioGroup preloadBunny;

    // Displays prematch page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prematch_page);
        // Creates and sets data trackers to PowerUpDatabase values
        scoutNameText = findViewById(R.id.scoutName);
        scoutNameText.setText(MainActivity.db.scoutName);
        noShowBox = findViewById(R.id.noShow);
        noShowBox.setChecked(MainActivity.db.noShow);
        teamNumber = findViewById(R.id.teamNumber);
        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.dropdown);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamNumber.setAdapter(teamAdapter);
        teamNumber.setOnItemSelectedListener(this);
        teamNumber.setSelection(MainActivity.db.teamNumber);
        matchNumber = findViewById(R.id.matchNumber);
        matchAdapter = ArrayAdapter.createFromResource(this, R.array.matches, R.layout.dropdown);
        matchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        matchNumber.setAdapter(matchAdapter);
        matchNumber.setOnItemSelectedListener(this);
        matchNumber.setSelection(MainActivity.db.matchNumber);
        teamNumberValue = teamNumber.getItemAtPosition(0).toString();
        matchNumberValue = matchNumber.getItemAtPosition(0).toString();
        preloadBunny = findViewById(R.id.bunny);
        switch (MainActivity.db.preloadBunny) {
            case 0:
                preloadBunny.clearCheck();
                break;
            case 1:
                preloadBunny.check(R.id.bunnyNone);
                break;
            case 2:
                preloadBunny.check(R.id.bunnyRobot);
                break;
            case 3:
                preloadBunny.check(R.id.bunnyField);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Checks which dropdown was selected and changes the PowerUpDatabase value accordingly
        if (parent.getId() == R.id.matchNumber) {
            MainActivity.db.matchNumber = pos;
            matchNumberValue = parent.getItemAtPosition(pos).toString();
        }
        else if (parent.getId() == R.id.teamNumber) {
            MainActivity.db.teamNumber = pos;
            teamNumberValue = parent.getItemAtPosition(pos).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Checks which dropdown was selected and changes the PowerUpDatabase value accordingly
        if (parent.getId() == R.id.matchNumber) {
            MainActivity.db.matchNumber = 0;
            matchNumberValue = parent.getItemAtPosition(0).toString();
        }
        else if (parent.getId() == R.id.teamNumber) {
            MainActivity.db.teamNumber = 0;
            teamNumberValue = parent.getItemAtPosition(0).toString();
        }
    }

    public static String getTeamNum() {
        return teamNumberValue;
    }

    public static String getMatchNum() {
        return matchNumberValue;
    }

    // Calls activity to go to auto page
    public void toAuto(View v) {
        // Saves values to PowerUpDatabase
        MainActivity.db.scoutName = scoutNameText.getText().toString();
        MainActivity.db.noShow = noShowBox.isChecked();
        switch (preloadBunny.getCheckedRadioButtonId()) {
            case R.id.bunnyNone:
                MainActivity.db.preloadBunny = 1;
                break;
            case R.id.bunnyRobot:
                MainActivity.db.preloadBunny = 2;
                break;
            case R.id.bunnyField:
                MainActivity.db.preloadBunny = 3;
                break;
            default:
                MainActivity.db.preloadBunny = 0;
                break;
        }
        // Switches pages
        Intent toAuto = new Intent(this, Auto.class);
        startActivity(toAuto);
    }
}