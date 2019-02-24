package frc.team449.deepspacescoutingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.base_activites.InmatchBaseActivity;
import frc.team449.deepspacescoutingapp.model.Match;

public class Sandstorm extends InmatchBaseActivity {

    // Input fields
//    private CheckBox noAuto;
    private CheckBox movedForward;
    private CheckBox placedPiece;
    private RadioGroup doubleAuto;
//    private Spinner placedLocation;
//    private ArrayAdapter<CharSequence> locAdapter;

    // Displays auto page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sandstorm_page);

//        noAuto = findViewById(R.id.noAuto);
//        noAuto.setChecked(Match.getInstance().isNoAuto());
        movedForward = findViewById(R.id.movedForward);
        movedForward.setChecked(Match.getInstance().isMovedForward());
        placedPiece = findViewById(R.id.placed);
        placedPiece.setChecked(Match.getInstance().isPlacedPiece());
        doubleAuto = findViewById(R.id.placed2);
        switch (Match.getInstance().getDoubleAuto()) {
            case -1:
                doubleAuto.clearCheck();
                break;
            case 0:
                doubleAuto.check(R.id.placedNone);
                break;
            case 1:
                doubleAuto.check(R.id.placedHatch);
                break;
            case 2:
                doubleAuto.check(R.id.placedCargo);
                break;
        }
//        placedLocation = findViewById(R.id.placedLocation);
//        locAdapter = ArrayAdapter.createFromResource(this, R.array.field_locations, R.layout.dropdown);
//        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        placedLocation.setAdapter(locAdapter);
//        placedLocation.setOnItemSelectedListener(onItemSelectedListener);
//        placedLocation.setSelection(Match.getInstance().getPlacedLocation());
    }

    @Override
    protected void setupNavButtons(){
        prevButton.setText("Prematch");
        nextButton.setText("Teleop");
        prevActivity = Prematch.class;
        nextActivity = Teleop.class;
    }

    @Override
    public void saveData(){
        Match.getInstance().setMovedForward(movedForward.isChecked());
        Match.getInstance().setPlacedPiece(placedPiece.isChecked());
        switch (doubleAuto.getCheckedRadioButtonId()) {
            case R.id.placedNone:
                Match.getInstance().setDoubleAuto(0);
                break;
            case R.id.placedHatch:
                Match.getInstance().setDoubleAuto(1);
                break;
            case R.id.placedCargo:
                Match.getInstance().setDoubleAuto(2);
                break;
            default:
                Match.getInstance().setDoubleAuto(-1);
                break;
        }
    }

//    private static final AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener(){
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            Match.getInstance().setPlacedLocation(position);
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//            Match.getInstance().setPlacedLocation(0);
//        }
//    };

}
