package frc.team449.scoutingappframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.model.Match;

public class Teleop extends BaseActivity {

    // Input fields
    private CheckBox achievedNothing;
    private RadioGroup dead;


    // Displays teleop page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teleop_page);

        // Create and set data trackers to their Database values
        achievedNothing = findViewById(R.id.achievedNothing);
        achievedNothing.setChecked(Match.getInstance().isAchievedNothing());
        dead = findViewById(R.id.dead);
        switch (Match.getInstance().getDead()) {
            case 0:
                dead.clearCheck();
                break;
            case 1:
                dead.check(R.id.deadNone);
                break;
            case 2:
                dead.check(R.id.deadPart);
                break;
            case 3:
                dead.check(R.id.deadAll);
                break;
        }
    }

    private void saveData(){
        Match.getInstance().setAchievedNothing(achievedNothing.isChecked());
        switch (dead.getCheckedRadioButtonId()) {
            case R.id.deadNone:
                Match.getInstance().setDead(1);
                break;
            case R.id.deadPart:
                Match.getInstance().setDead(2);
                break;
            case R.id.deadAll:
                Match.getInstance().setDead(3);
                break;
            default:
                Match.getInstance().setDead(0);
                break;
        }
    }

    // Calls activity to go to auto page
    public void toAuto(View v) {
        saveData();
        // Switches pages
        Intent toAuto = new Intent(this, Auto.class);
        startActivity(toAuto);
    }

    // Calls activity to go to endgame page
    public void toEndgame(View v) {
        saveData();
        // Switches pages
        Intent toEndgame = new Intent(this, Endgame.class);
        startActivity(toEndgame);
    }
}
