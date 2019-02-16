package frc.team449.scoutingappframe.activities;

import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
    private RadioGroup startingLevel;
    private RadioGroup preload;
    private Drawable fieldDrawable;
    private ImageView fieldImage;
    private static final float[] NEGATIVE = {
            -1.0f,     0,     0,    0, 255, // red
            0, -1.0f,     0,    0, 255, // green
            0,     0, -1.0f,    0, 255, // blue
            0,     0,     0, 1.0f,   0  // alpha
    };

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
        startingLevel = findViewById(R.id.startingLevel);
        switch (Match.getInstance().getStartingLevel()) {
            case 0:
                startingLevel.clearCheck();
                break;
            case 1:
                startingLevel.check(R.id.startingLevelOne);
                break;
            case 2:
                startingLevel.check(R.id.startingLevelTwo);
                break;
        }
        preload = findViewById(R.id.preload);
        switch (Match.getInstance().getPreload()) {
            case 0:
                preload.clearCheck();
                break;
            case 1:
                preload.check(R.id.preloadNone);
                break;
            case 2:
                preload.check(R.id.preloadCargo);
                break;
            case 3:
                preload.check(R.id.preloadHatch);
                break;
        }

        fieldDrawable = getResources().getDrawable(R.drawable.field_drawing);
        fieldDrawable.setColorFilter(new ColorMatrixColorFilter(NEGATIVE));
        fieldImage = findViewById(R.id.fieldDrawing);
//        fieldImage.setImageDrawable(fieldDrawable);

        if (justLaunched) {
            justLaunched = false;
            PopupHelper.bluetoothPopup(this);
        }
    }

    private void saveData() {
        switch (startingLevel.getCheckedRadioButtonId()) {
            case R.id.startingLevelOne:
                Match.getInstance().setStartingLevel(1);
                break;
            case R.id.startingLevelTwo:
                Match.getInstance().setStartingLevel(2);
                break;
            default:
                Match.getInstance().setStartingLevel(0);
                break;
        }
        switch (preload.getCheckedRadioButtonId()) {
            case R.id.preloadNone:
                Match.getInstance().setPreload(1);
                break;
            case R.id.preloadCargo:
                Match.getInstance().setPreload(2);
                break;
            case R.id.preloadHatch:
                Match.getInstance().setPreload(3);
                break;
            default:
                Match.getInstance().setPreload(0);
                break;
        }
        Match.getInstance().setScoutName(scoutNameText.getText().toString());
        Match.getInstance().setNoShow(noShowBox.isChecked());
    }

    public void toAuto(View v) {
        saveData();
        startActivity(new Intent(this, Auto.class));
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