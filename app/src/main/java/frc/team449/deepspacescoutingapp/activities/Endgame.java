package frc.team449.deepspacescoutingapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.base_activites.InmatchBaseActivity;
import frc.team449.deepspacescoutingapp.model.Match;

public class Endgame extends InmatchBaseActivity {

    private RadioGroup attemptLevel;
    private RadioGroup levelReached;
    private NumberPicker climbTime;
    private Button toggleTimeButton;
    private Thread timer;
    private volatile boolean timerOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endgame_page);

        attemptLevel = findViewById(R.id.attemptLevel);
        switch (Match.getInstance().getAttemptLevel()) {
            case 0:
                attemptLevel.check(R.id.attemptLevelNone);
                break;
            case 1:
                attemptLevel.check(R.id.attemptLevelOne);
                break;
            case 2:
                attemptLevel.check(R.id.attemptLevelTwo);
                break;
            case 3:
                attemptLevel.check(R.id.attemptLevelThree);
                break;
            case -1:
                attemptLevel.clearCheck();
                break;
        }
        levelReached = findViewById(R.id.levelReached);
        switch (Match.getInstance().getLevelReached()) {
            case 0:
                levelReached.check(R.id.levelReachedNone);
                break;
            case 1:
                levelReached.check(R.id.levelReachedOne);
                break;
            case 2:
                levelReached.check(R.id.levelReachedTwo);
                break;
            case 3:
                levelReached.check(R.id.levelReachedThree);
                break;
            case -1:
                levelReached.clearCheck();
                break;
        }

        climbTime = findViewById(R.id.timeTaken);
        climbTime.setMinValue(0);
        climbTime.setMaxValue(99);
        climbTime.setValue(Match.getInstance().getClimbTime());
        timer = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        if (timerOn) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    climbTime.setValue((climbTime.getValue() + 1) % 100);
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        toggleTimeButton = findViewById(R.id.toggleTimer);
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.interrupt();
        timerOn = false;
    }

    public void toggleTimer(View view) {
        timerOn = !timerOn;
        if (timerOn) {
            toggleTimeButton.setText("Stop Timer");
        } else {
            toggleTimeButton.setText("Start Timer");
        }
    }

    @Override
    protected void setupNavButtons() {
        prevButton.setText("< Teleop");
        nextButton.setText("General >");
        prevActivity = Teleop.class;
        nextActivity = General.class;
    }

    @Override
    protected void saveData(){
        switch (attemptLevel.getCheckedRadioButtonId()) {
            case R.id.attemptLevelNone:
                Match.getInstance().setAttemptLevel(0);
                break;
            case R.id.attemptLevelOne:
                Match.getInstance().setAttemptLevel(1);
                break;
            case R.id.attemptLevelTwo:
                Match.getInstance().setAttemptLevel(2);
                break;
            case R.id.attemptLevelThree:
                Match.getInstance().setAttemptLevel(3);
                break;
            default:
                Match.getInstance().setAttemptLevel(-1);
                break;
        }
        switch (levelReached.getCheckedRadioButtonId()) {
            case R.id.levelReachedNone:
                Match.getInstance().setLevelReached(0);
                break;
            case R.id.levelReachedOne:
                Match.getInstance().setLevelReached(1);
                break;
            case R.id.levelReachedTwo:
                Match.getInstance().setLevelReached(2);
                break;
            case R.id.levelReachedThree:
                Match.getInstance().setLevelReached(3);
                break;
            default:
                Match.getInstance().setLevelReached(-1);
                break;
        }
        Match.getInstance().setClimbTime(climbTime.getValue());
    }

}
