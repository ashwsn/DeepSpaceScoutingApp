package frc.team449.scoutingappframe.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.helpers.PopupHelper;
import frc.team449.scoutingappframe.model.Match;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID){
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getLayoutInflater().inflate(R.layout.base_activity, null);
        FrameLayout activityContainer = coordinatorLayout.findViewById(R.id.layout_container);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(coordinatorLayout);

        setup();
    }

    private void setup(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        TextView matchTitle = findViewById(R.id.matchTitle);
        TextView teamTitle = findViewById(R.id.teamTitle);
        if (Match.getInstance().getMatchNumber() != 0) {
            matchTitle.setText(String.format("Match %1$s", Prematch.getMatchNum()));
        }
        if (Match.getInstance().getTeamNumber() != 0) {
            teamTitle.setText(String.format("Team %1$s", Prematch.getTeamNum()));
        }
        matchTitle.setVisibility(View.VISIBLE);
        teamTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bluetooth_icon:
                PopupHelper.bluetoothPopup(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}