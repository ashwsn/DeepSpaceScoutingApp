package frc.team449.scoutingappframe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import frc.team449.scoutingappframe.R;

public class Submitted extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitted_page);
    }

    public void nextMatch(View v) {
        Intent nextMatch = new Intent(this, MainActivity.class);
        startActivity(nextMatch);
    }
}
