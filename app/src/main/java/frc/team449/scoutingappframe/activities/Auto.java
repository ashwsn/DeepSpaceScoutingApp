package frc.team449.scoutingappframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.model.Match;

public class Auto extends BaseActivity {

    // Text fields that display current value
    // Input fields
    private CheckBox noAuto;
    private CheckBox movedForward;

    // Displays auto page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autonomous_page);

        noAuto = findViewById(R.id.noAuto);
        noAuto.setChecked(Match.getInstance().isNoAuto());
        movedForward = findViewById(R.id.movedForward);
        movedForward.setChecked(Match.getInstance().isMovedForward());
    }

    public void saveData(){
        Match.getInstance().setNoAuto(noAuto.isChecked());
        Match.getInstance().setMovedForward(movedForward.isChecked());
    }

    // Calls activity to go to teleop page
    public void toTeleop(View v) {
        saveData();
        // Switch pages
        Intent toTeleop = new Intent(this, Teleop.class);
        startActivity(toTeleop);
    }

    // Calls activity to go to prematch page
    public void toPrematch(View v) {
        saveData();
        // Switches pages
        Intent toPrematch = new Intent(this, Prematch.class);
        startActivity(toPrematch);
    }
}
