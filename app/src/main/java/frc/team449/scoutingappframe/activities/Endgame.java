package frc.team449.scoutingappframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.activities.base_activites.BaseActivity;
import frc.team449.scoutingappframe.activities.base_activites.InmatchBaseActivity;
import frc.team449.scoutingappframe.helpers.SubmitHelper;
import frc.team449.scoutingappframe.model.Match;

public class Endgame extends InmatchBaseActivity {

    private EditText comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame_page);

        comments = findViewById(R.id.comments);
        comments.setText(Match.getInstance().getComments());
    }

    @Override
    protected void setupNavButtons() {
        prevButton.setText("Teleop");
        nextButton.setText("Submit");
        prevActivity = Teleop.class;
    }

    @Override
    protected void saveData(){
        Match.getInstance().setComments(comments.getText().toString());
    }

    @Override
    public void toNext(View v) {
        saveData();

        // Submit
        SubmitHelper.submit(this);
    }

}
