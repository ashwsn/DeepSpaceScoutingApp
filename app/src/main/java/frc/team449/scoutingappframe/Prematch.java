package frc.team449.scoutingappframe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import frc.team449.scoutingappframe.Activities.Auto;
import frc.team449.scoutingappframe.Activities.BaseActivity;
import frc.team449.scoutingappframe.Activities.MainActivity;


public class Prematch extends BaseActivity implements AdapterView.OnItemSelectedListener {

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

    // Displays prematch page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prematch_page);
        // Creates and sets data trackers to PowerUpDatabase values
        scoutNameText = findViewById(R.id.scoutName);
        scoutNameText.setText(MainActivity.match.scoutName);
        noShowBox = findViewById(R.id.noShow);
        noShowBox.setChecked(MainActivity.match.noShow);
        teamNumber = findViewById(R.id.teamNumber);
        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.dropdown);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamNumber.setAdapter(teamAdapter);
        teamNumber.setOnItemSelectedListener(this);
        teamNumber.setSelection(MainActivity.match.teamNumber);
        matchNumber = findViewById(R.id.matchNumber);
        matchAdapter = ArrayAdapter.createFromResource(this, R.array.matches, R.layout.dropdown);
        matchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        matchNumber.setAdapter(matchAdapter);
        matchNumber.setOnItemSelectedListener(this);
        matchNumber.setSelection(MainActivity.match.matchNumber);
        teamNumberValue = teamNumber.getItemAtPosition(0).toString();
        matchNumberValue = matchNumber.getItemAtPosition(0).toString();
//
//        if (!BluetoothHelper.getInstance().isConnected()) {
//            //show bluetooth dialog
//            FragmentManager fm = getSupportFragmentManager();
//            DialogFragment fragment = new BluetoothSetupFragment();
//            fragment.show(fm, "dialog");
//        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Checks which dropdown was selected and changes the PowerUpDatabase value accordingly
        if (parent.getId() == R.id.matchNumber) {
            MainActivity.match.matchNumber = pos;
            matchNumberValue = parent.getItemAtPosition(pos).toString();
        }
        else if (parent.getId() == R.id.teamNumber) {
            MainActivity.match.teamNumber = pos;
            teamNumberValue = parent.getItemAtPosition(pos).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Checks which dropdown was selected and changes the PowerUpDatabase value accordingly
        if (parent.getId() == R.id.matchNumber) {
            MainActivity.match.matchNumber = 0;
            matchNumberValue = parent.getItemAtPosition(0).toString();
        }
        else if (parent.getId() == R.id.teamNumber) {
            MainActivity.match.teamNumber = 0;
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
        MainActivity.match.scoutName = scoutNameText.getText().toString();
        MainActivity.match.noShow = noShowBox.isChecked();
        // Switches pages
        Intent toAuto = new Intent(this, Auto.class);
        startActivity(toAuto);
    }
}