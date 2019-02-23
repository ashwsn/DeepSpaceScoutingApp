package frc.team449.deepspacescoutingapp.activities.base_activites;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
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

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.helpers.PopupHelper;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID){
        CoordinatorLayout cl = setupLayout(layoutResID,R.layout.base_activity);
        super.setContentView(cl);
        setup(cl);
    }

    @Override
    public void setContentView(View view) {
        CoordinatorLayout cl = (CoordinatorLayout) getLayoutInflater().inflate(R.layout.base_activity, null);
        super.setContentView(cl);
        FrameLayout activityContainer = cl.findViewById(R.id.layout_container);
        activityContainer.addView(view);
        setup(cl);
    }

    protected CoordinatorLayout setupLayout(int layoutResID, int baseLayout) {
        CoordinatorLayout cl = (CoordinatorLayout) getLayoutInflater().inflate(baseLayout, null);
        FrameLayout activityContainer = cl.findViewById(R.id.layout_container);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        return cl;
    }

    protected void setup(View view){
        setupKeyboard(view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    //onClick for help icons
    public void help(View view) {
        switch (view.getId()){
            case R.id.noShowHelp:
                helpPopup(R.string.help_noshow);
                break;
            default:
                helpPopup(R.string.help_default);
                break;
        }
    }

    private void helpPopup(int textID){
        PopupHelper.info(getString(R.string.help_title),getString(textID),this);
    }

    //Hide keyboard when user clicks outside edit text
    //https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
    protected void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void setupKeyboard(View view) {
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
                setupKeyboard(innerView);
            }
        }
    }
}