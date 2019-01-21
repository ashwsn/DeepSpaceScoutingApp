package frc.team449.scoutingappframe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import frc.team449.scoutingappframe.R;
import frc.team449.scoutingappframe.activities.base_activites.BaseActivity;

public class Submitted extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitted_page);
    }

    public void nextMatch(View v) {
        Intent nextMatch = new Intent(this, Prematch.class);
        startActivity(nextMatch);
    }
}
