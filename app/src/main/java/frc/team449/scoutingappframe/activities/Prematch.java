package frc.team449.scoutingappframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.activities.base_activites.BaseActivity;
import frc.team449.scoutingappframe.helpers.PopupHelper;
import frc.team449.scoutingappframe.model.Match;


public class Prematch extends BaseActivity {

    private static boolean justLaunched = true;

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

        // Creates and sets data trackers to Match values
        scoutNameText = findViewById(R.id.scoutName);
        scoutNameText.setText(Match.getInstance().getScoutName());
        noShowBox = findViewById(R.id.noShow);
        noShowBox.setChecked(Match.getInstance().isNoShow());
        teamNumber = findViewById(R.id.teamNumber);
        teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.dropdown);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamNumber.setAdapter(teamAdapter);
        teamNumber.setOnItemSelectedListener(onItemSelectedListener);
        teamNumber.setSelection(Match.getInstance().getTeamNumber());
        matchNumber = findViewById(R.id.matchNumber);
        matchAdapter = ArrayAdapter.createFromResource(this, R.array.matches, R.layout.dropdown);
        matchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        matchNumber.setAdapter(matchAdapter);
        matchNumber.setOnItemSelectedListener(onItemSelectedListener);
        matchNumber.setSelection(Match.getInstance().getMatchNumber());

        if (justLaunched) {
            justLaunched = false;
            PopupHelper.bluetoothPopup(this);
        }
    }

    // Calls activity to go to auto page
    public void toAuto(View v) {
        // Saves values to Match
        Match.getInstance().setScoutName(scoutNameText.getText().toString());
        Match.getInstance().setNoShow(noShowBox.isChecked());
        // Switches pages
        Intent toAuto = new Intent(this, Auto.class);
        startActivity(toAuto);
    }

    private static final AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.matchNumber) {
                Match.getInstance().setMatchNumber(position);
            }
            else if (parent.getId() == R.id.teamNumber) {
                Match.getInstance().setTeamNumber(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            if (parent.getId() == R.id.matchNumber) {
                Match.getInstance().setMatchNumber(0);
            }
            else if (parent.getId() == R.id.teamNumber) {
                Match.getInstance().setTeamNumber(0);
            }
        }
    };
}