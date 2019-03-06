package frc.team449.deepspacescoutingapp.activities.base_activites;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.model.Match;

public abstract class InmatchBaseActivity extends BaseActivity {

    protected Class prevActivity;
    protected Class nextActivity;

    protected Button nextButton;
    protected Button prevButton;

    @Override
    public void setContentView(int layoutResID){
        CoordinatorLayout coordinatorLayout = setupLayout(layoutResID,R.layout.inmatch_base_activity);
        super.setContentView(coordinatorLayout);
    }

    @Override
    protected void setup(View view) {
        super.setup(view);

        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);

        if (Match.getInstance().getMatchNumber() != 0)
            matchTitle.setText(String.format("Match %1$s", getResources().getStringArray(R.array.matches)[Match.getInstance().getMatchNumber()]));
        if (Match.getInstance().getTeamNumber() != 0)
            teamTitle.setText(String.format("Team %1$s", getResources().getStringArray(R.array.teams)[Match.getInstance().getTeamNumber()]));

        nextButton = findViewById(R.id.toNext);
        prevButton = findViewById(R.id.toPrev);
        setupNavButtons();
    }

    protected abstract void setupNavButtons();

    public void toPrev(View v){
        gotoPage(prevActivity);
    }

    public void toNext(View v) {
        gotoPage(nextActivity);
    }

    private void gotoPage(Class activityToGoto){
        saveData();
        startActivity(new Intent(this, activityToGoto));
    }

}
