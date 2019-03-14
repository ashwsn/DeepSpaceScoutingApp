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
    private CheckBox movedForward;
    private CheckBox placedPiece;
    private RadioGroup doubleAuto;

    // Displays auto page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sandstorm_page);

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
    }

    @Override
    protected void setupNavButtons(){
        prevButton.setText("< Prematch");
        nextButton.setText("Teleop >");
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

}
