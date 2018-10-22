package frc.team449.scoutingapptemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FixErrors extends Activity {

    private TextView errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_page);
        errors = (TextView) findViewById(R.id.errors);
        errors.setText(MainActivity.db.checkData());
    }

    // Switches pages
    public void goToPrematch(View v) {
        Intent toPrematch = new Intent(this, Prematch.class);
        startActivity(toPrematch);
    }
}
