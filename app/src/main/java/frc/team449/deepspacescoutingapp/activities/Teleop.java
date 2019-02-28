package frc.team449.deepspacescoutingapp.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.base_activites.InmatchBaseActivity;
import frc.team449.deepspacescoutingapp.model.Match;

public class Teleop extends InmatchBaseActivity {

    // Input fields
    private Drawable cargo;
    private Drawable hatch;
    private Drawable hatchCargo;
    private HashMap<ImageButton, Integer> locations = new HashMap<>();
    private TextView droppedHatch;
    private TextView droppedCargo;

    // all ids are based on field_drawing.png
    private int[] buttonIds = {R.id.l11, R.id.l12, R.id.l13, R.id.l21, R.id.l22, R.id.l23, R.id.c11,
                               R.id.c12, R.id.c13, R.id.c14, R.id.c21, R.id.c22, R.id.c23, R.id.c24,
                               R.id.r11, R.id.r12, R.id.r13, R.id.r21, R.id.r22, R.id.r23};
    private int[] highRocketIds = {R.id.l13, R.id.l23, R.id.r13, R.id.r23};
    private int[] midRocketIds = {R.id.l12, R.id.l22, R.id.r12, R.id.r22};
    private int[] lowRocketIds = {R.id.l11, R.id.l21, R.id.r11, R.id.r21};
    private int[] cargoShipIds = {R.id.c11, R.id.c12, R.id.c13, R.id.c14, R.id.c21, R.id.c22, R.id.c23, R.id.c24};
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

        populateList(buttons, buttonIds);
        populateList(highRockets, highRocketIds);
        populateList(midRockets, midRocketIds);
        populateList(lowRockets, lowRocketIds);
        populateList(cargoShips, cargoShipIds);

        droppedHatch = findViewById(R.id.hatchDroppedCount);
        droppedCargo = findViewById(R.id.cargoDroppedCount);

        droppedHatch.setText(String.valueOf(Match.getInstance().getDroppedHatch()));
        droppedCargo.setText(String.valueOf(Match.getInstance().getDroppedCargo()));

        cargo = getResources().getDrawable(R.drawable.cargo);
        hatch = getResources().getDrawable(R.drawable.hatch_panel);
        hatchCargo = getResources().getDrawable(R.drawable.hatch_and_cargo);
        for (ImageButton i : buttons) {
            if (Match.getInstance().getTeleopPiecePositions() != null) {
                locations.put(i, Match.getInstance().getTeleopPiecePositions()[buttons.indexOf(i)]);
            } else {
                locations.put(i, 0);
            }
            setImage(i, locations.get(i));
        }
    }

    private void populateList(ArrayList<ImageButton> list, int[] ids) {
        for (int id : ids) {
            list.add((ImageButton) findViewById(id));
        }
    }

    @Override
    protected void setupNavButtons() {
        prevButton.setText("Sandstorm");
        nextButton.setText("Endgame");
        prevActivity = Sandstorm.class;
        nextActivity = Endgame.class;
    }

    @Override
    protected void saveData() {
        Match.getInstance().setNumHatchL3(0);
        Match.getInstance().setNumHatchL2(0);
        Match.getInstance().setNumHatchL1(0);
        Match.getInstance().setNumHatchShip(0);
        Match.getInstance().setNumCargoL1(0);
        Match.getInstance().setNumCargoL2(0);
        Match.getInstance().setNumCargoL3(0);
        Match.getInstance().setNumCargoShip(0);
        for (ImageButton i : locations.keySet()) {
            piecePositions[buttons.indexOf(i)] = locations.get(i);
            switch (locations.get(i)) {
                case 1:
                    incrementHatch(i);
                    break;
                case 2:
                    incrementCargo(i);
                    break;
                case 3:
                    incrementHatch(i);
                    incrementCargo(i);
            }
        }
        Match.getInstance().setTeleopPiecePositions(piecePositions);
    }

    private void incrementHatch(ImageButton i){
        if (highRockets.contains(i)) {
            Match.getInstance().setNumHatchL3(Match.getInstance().getNumHatchL3()+1);
        } else if (midRockets.contains(i)) {
            Match.getInstance().setNumHatchL2(Match.getInstance().getNumHatchL2()+1);
        } else if (lowRockets.contains(i)) {
            Match.getInstance().setNumHatchL1(Match.getInstance().getNumHatchL1()+1);
        } else if (cargoShips.contains(i)) {
            Match.getInstance().setNumHatchShip(Match.getInstance().getNumHatchShip()+1);
        }
    }

    private void incrementCargo(ImageButton i){
        if (highRockets.contains(i)) {
            Match.getInstance().setNumCargoL3(Match.getInstance().getNumCargoL3()+1);
        } else if (midRockets.contains(i)) {
            Match.getInstance().setNumCargoL2(Match.getInstance().getNumCargoL2()+1);
        } else if (lowRockets.contains(i)) {
            Match.getInstance().setNumCargoL1(Match.getInstance().getNumCargoL1()+1);
        } else if (cargoShips.contains(i)) {
            Match.getInstance().setNumCargoShip(Match.getInstance().getNumCargoShip()+1);
        }
    }

    private void setImage(ImageButton i, int state) {
        switch (state) {
            case 0:
                i.setImageDrawable(null);
                break;
            case 1:
                i.setImageDrawable(hatch);
                break;
            case 2:
                i.setImageDrawable(cargo);
                break;
            case 3:
                i.setImageDrawable(hatchCargo);
                break;
        }
    }

    public void changeState(View v) {
        for (ImageButton i : locations.keySet()) {
            if (i.equals(v)) {
                locations.put(i, (locations.get(i)+1)%4);
                setImage(i, locations.get(i));
            }
        }
    }


    public void plusHDrop(View v) {
        Match.getInstance().incrementDroppedHatch(1);
        droppedHatch.setText(Integer.toString(Match.getInstance().getDroppedHatch()));
    }

    public void plusCDrop(View v) {
        Match.getInstance().incrementDroppedCargo(1);
        droppedCargo.setText(Integer.toString(Match.getInstance().getDroppedCargo()));
    }

    public void minusHDrop(View v) {
        Match.getInstance().incrementDroppedHatch(-1);
        droppedHatch.setText(Integer.toString(Match.getInstance().getDroppedHatch()));
    }

    public void minusCDrop(View v) {
        Match.getInstance().incrementDroppedCargo(-1);
        droppedCargo.setText(Integer.toString(Match.getInstance().getDroppedCargo()));
    }

}
