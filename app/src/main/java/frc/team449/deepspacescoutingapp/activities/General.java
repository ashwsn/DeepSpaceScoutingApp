package frc.team449.deepspacescoutingapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.base_activites.InmatchBaseActivity;
import frc.team449.deepspacescoutingapp.helpers.SubmitHelper;
import frc.team449.deepspacescoutingapp.model.Match;

public class General extends InmatchBaseActivity {

//    private CheckBox achievedNothing;
    private RadioGroup dead;
    private CheckBox defense;
    private EditText comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_page);

//        achievedNothing = findViewById(R.id.achievedNothing);
//        achievedNothing.setChecked(Match.getInstance().isAchievedNothing());
        dead = findViewById(R.id.dead);
        defense = findViewById(R.id.defense);

        switch (Match.getInstance().getDead()) {
            case -1:
                dead.clearCheck();
                break;
            case 0:
                dead.check(R.id.alive);
                break;
            case 1:
                dead.check(R.id.broken);
                break;
            case 2:
                dead.check(R.id.halfDead);
                break;
            case 3:
                dead.check(R.id.deadAll);
                break;
        }

        comments = findViewById(R.id.comments);
        comments.setText(Match.getInstance().getComments());
    }

    @Override
    protected void setupNavButtons() {
        prevButton.setText("Endgame");
        nextButton.setText("Submit");
        prevActivity = Endgame.class;
    }

    @Override
    protected void saveData(){
//        Match.getInstance().setAchievedNothing(achievedNothing.isChecked());
        Match.getInstance().setDefense(defense.isChecked());
        switch (dead.getCheckedRadioButtonId()) {
            case R.id.alive:
                Match.getInstance().setDead(0);
                break;
            case R.id.broken:
                Match.getInstance().setDead(1);
                break;
            case R.id.halfDead:
                Match.getInstance().setDead(2);
                break;
            case R.id.deadAll:
                Match.getInstance().setDead(3);
            default:
                Match.getInstance().setDead(4);
                break;
        }
        Match.getInstance().setComments(comments.getText().toString());
    }

    @Override
    public void toNext(View v) {
        saveData();

        // Submit
        SubmitHelper.submit(this);
    }

}
