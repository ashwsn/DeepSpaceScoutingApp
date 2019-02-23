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

public class Auto extends InmatchBaseActivity {

    // Input fields
    private CheckBox noAuto;
    private CheckBox movedForward;
    private RadioGroup placedPiece;
    private Spinner placedLocation;
    private ArrayAdapter<CharSequence> locAdapter;

    // Displays auto page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sandstorm_page);

        noAuto = findViewById(R.id.noAuto);
        noAuto.setChecked(Match.getInstance().isNoAuto());
        movedForward = findViewById(R.id.movedForward);
        movedForward.setChecked(Match.getInstance().isMovedForward());
        placedPiece = findViewById(R.id.placed);
        switch (Match.getInstance().getPlacedPiece()) {
            case 0:
                placedPiece.clearCheck();
                break;
            case 1:
                placedPiece.check(R.id.placedNone);
                break;
            case 2:
                placedPiece.check(R.id.placedCargo);
                break;
            case 3:
                placedPiece.check(R.id.placedHatch);
                break;
        }
        placedLocation = findViewById(R.id.placedLocation);
        locAdapter = ArrayAdapter.createFromResource(this, R.array.field_locations, R.layout.dropdown);
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placedLocation.setAdapter(locAdapter);
        placedLocation.setOnItemSelectedListener(onItemSelectedListener);
        placedLocation.setSelection(Match.getInstance().getPlacedLocation());
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
        switch (placedPiece.getCheckedRadioButtonId()) {
            case R.id.placedNone:
                Match.getInstance().setPlacedPiece(1);
                break;
            case R.id.placedCargo:
                Match.getInstance().setPlacedPiece(2);
                break;
            case R.id.placedHatch:
                Match.getInstance().setPlacedPiece(3);
                break;
            default:
                Match.getInstance().setPlacedPiece(0);
                break;
        }
        Match.getInstance().setNoAuto(noAuto.isChecked());
        Match.getInstance().setMovedForward(movedForward.isChecked());
    }

    private static final AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Match.getInstance().setPlacedLocation(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Match.getInstance().setPlacedLocation(0);
        }
    };

}
