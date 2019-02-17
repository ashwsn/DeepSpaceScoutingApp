package frc.team449.scoutingappframe.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.activities.base_activites.InmatchBaseActivity;
import frc.team449.scoutingappframe.model.Match;

public class Teleop extends InmatchBaseActivity {

    // Input fields
    private CheckBox achievedNothing;
    private RadioGroup dead;
    private Drawable cargo;
    private Drawable hatch;
    private HashMap<ImageButton, Integer> locations = new HashMap<>();
    private ArrayList<ImageButton> buttons = new ArrayList<>();
    private ArrayList<ImageButton> highRockets = new ArrayList<>();
    private ArrayList<ImageButton> midRockets = new ArrayList<>();
    private ArrayList<ImageButton> lowRockets = new ArrayList<>();
    private ArrayList<ImageButton> cargoShips = new ArrayList<>();
    // [l11, l12, l13, l21, l22, l23, c11, c12, c13, c14, c21, c22, c23, c24, r11, r12, r13, r21, r22, r23]
    private int[] piecePositions = new int[20];

    // Displays teleop page on activity call
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teleop_page);

        // Create and set data trackers to their Database values
        achievedNothing = findViewById(R.id.achievedNothing);
        achievedNothing.setChecked(Match.getInstance().isAchievedNothing());
        dead = findViewById(R.id.dead);

        // all ids are based on field_drawing.png
        buttons.add((ImageButton) findViewById(R.id.l11));
        buttons.add((ImageButton) findViewById(R.id.l12));
        buttons.add((ImageButton) findViewById(R.id.l13));
        buttons.add((ImageButton) findViewById(R.id.l21));
        buttons.add((ImageButton) findViewById(R.id.l22));
        buttons.add((ImageButton) findViewById(R.id.l23));
        buttons.add((ImageButton) findViewById(R.id.c11));
        buttons.add((ImageButton) findViewById(R.id.c12));
        buttons.add((ImageButton) findViewById(R.id.c13));
        buttons.add((ImageButton) findViewById(R.id.c14));
        buttons.add((ImageButton) findViewById(R.id.c21));
        buttons.add((ImageButton) findViewById(R.id.c22));
        buttons.add((ImageButton) findViewById(R.id.c23));
        buttons.add((ImageButton) findViewById(R.id.c24));
        buttons.add((ImageButton) findViewById(R.id.r11));
        buttons.add((ImageButton) findViewById(R.id.r12));
        buttons.add((ImageButton) findViewById(R.id.r13));
        buttons.add((ImageButton) findViewById(R.id.r21));
        buttons.add((ImageButton) findViewById(R.id.r22));
        buttons.add((ImageButton) findViewById(R.id.r23));

        highRockets.add((ImageButton) findViewById(R.id.l13));
        highRockets.add((ImageButton) findViewById(R.id.l23));
        highRockets.add((ImageButton) findViewById(R.id.r13));
        highRockets.add((ImageButton) findViewById(R.id.r23));

        midRockets.add((ImageButton) findViewById(R.id.l12));
        midRockets.add((ImageButton) findViewById(R.id.l22));
        midRockets.add((ImageButton) findViewById(R.id.r12));
        midRockets.add((ImageButton) findViewById(R.id.r12));

        lowRockets.add((ImageButton) findViewById(R.id.l11));
        lowRockets.add((ImageButton) findViewById(R.id.l21));
        lowRockets.add((ImageButton) findViewById(R.id.r11));
        lowRockets.add((ImageButton) findViewById(R.id.r21));

        cargoShips.add((ImageButton) findViewById(R.id.c11));
        cargoShips.add((ImageButton) findViewById(R.id.c12));
        cargoShips.add((ImageButton) findViewById(R.id.c13));
        cargoShips.add((ImageButton) findViewById(R.id.c14));
        cargoShips.add((ImageButton) findViewById(R.id.c21));
        cargoShips.add((ImageButton) findViewById(R.id.c22));
        cargoShips.add((ImageButton) findViewById(R.id.c23));
        cargoShips.add((ImageButton) findViewById(R.id.c24));

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
        cargo = getResources().getDrawable(R.drawable.cargo);
        hatch = getResources().getDrawable(R.drawable.hatch_panel);
        for (ImageButton i : buttons) {
            locations.put(i, 0);
        }
    }

    @Override
    protected void setupNavButtons() {
        prevButton.setText("Sandstorm");
        nextButton.setText("Endgame");
        prevActivity = Auto.class;
        nextActivity = Endgame.class;
    }

    @Override
    protected void saveData(){
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
        Match.getInstance().setNumHatchL3(0);
        Match.getInstance().setNumHatchL2(0);
        Match.getInstance().setNumHatchL1(0);
        Match.getInstance().setNumHatchShip(0);
        Match.getInstance().setNumCargoL1(0);
        Match.getInstance().setNumCargoL2(0);
        Match.getInstance().setNumCargoL3(0);
        Match.getInstance().setNumCargoShip(0);
        for (ImageButton i : locations.keySet()) {
            switch (locations.get(i)) {
                case 1:
                    if (highRockets.contains(i)) {
                        Match.getInstance().setNumHatchL3(Match.getInstance().getNumHatchL3()+1);
                    } else if (midRockets.contains(i)) {
                        Match.getInstance().setNumHatchL2(Match.getInstance().getNumHatchL2()+1);
                    } else if (lowRockets.contains(i)) {
                        Match.getInstance().setNumHatchL1(Match.getInstance().getNumHatchL1()+1);
                    } else if (cargoShips.contains(i)) {
                        Match.getInstance().setNumHatchShip(Match.getInstance().getNumHatchShip()+1);
                    }
                    break;
                case 2:
                    if (highRockets.contains(i)) {
                        Match.getInstance().setNumCargoL3(Match.getInstance().getNumCargoL3()+1);
                    } else if (midRockets.contains(i)) {
                        Match.getInstance().setNumCargoL2(Match.getInstance().getNumCargoL2()+1);
                    } else if (lowRockets.contains(i)) {
                        Match.getInstance().setNumCargoL1(Match.getInstance().getNumCargoL1()+1);
                    } else if (cargoShips.contains(i)) {
                        Match.getInstance().setNumCargoShip(Match.getInstance().getNumCargoShip()+1);
                    }
                    break;
            }
        }
    }

    private void changeImage(ImageButton i, int state) {
        switch (state % 3) {
            case 0:
                i.setImageDrawable(null);
                break;
            case 1:
                i.setImageDrawable(hatch);
                break;
            case 2:
                i.setImageDrawable(cargo);
                break;
        }
    }

    public void changeState(View v) {
        for (ImageButton i : locations.keySet()) {
            if (i.equals(v)) {
                locations.put(i, locations.get(i)+1);
                changeImage(i, locations.get(i));
            }
        }
    }

}
