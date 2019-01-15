package frc.team449.scoutingappframe.Activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import frc.team449.scoutingappframe.Fragments.BluetoothSetupFragment;
import frc.team449.scoutingappframe.Helpers.BluetoothHelper;
import frc.team449.scoutingappframe.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID){
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getLayoutInflater().inflate(R.layout.base_activity, null);
        FrameLayout activityContainer = coordinatorLayout.findViewById(R.id.layout_container);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(coordinatorLayout);
        addToolbar();
    }

    protected void addToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //getSupportActionBar().setIcon(R.drawable.icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bluetooth_icon:
                if (!BluetoothHelper.getInstance().isConnected()) {
                    //show bluetooth dialog
                    FragmentManager fm = getSupportFragmentManager();
                    DialogFragment fragment = new BluetoothSetupFragment();
                    fragment.show(fm, "dialog");
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}