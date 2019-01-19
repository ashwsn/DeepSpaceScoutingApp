package frc.team449.scoutingappframe.activities;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import frc.team449.scoutingappframe.fragments.BluetoothSetupFragment;
import frc.team449.scoutingappframe.helpers.BluetoothHelper;
import frc.team449.scoutingappframe.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID){
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getLayoutInflater().inflate(R.layout.base_activity, null);
        FrameLayout activityContainer = coordinatorLayout.findViewById(R.id.layout_container);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(coordinatorLayout);
        addToolbar();

        setupUI(coordinatorLayout);
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


    //Hide keyboard when user clicks outside edit text
    //https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
    protected void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}