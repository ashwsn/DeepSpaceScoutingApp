package frc.team449.deepspacescoutingapp.model;

import android.util.Log;

import java.util.HashMap;

import frc.team449.deepspacescoutingapp.activities.Endgame;
import frc.team449.deepspacescoutingapp.activities.General;
import frc.team449.deepspacescoutingapp.activities.Prematch;
import frc.team449.deepspacescoutingapp.activities.Sandstorm;
import frc.team449.deepspacescoutingapp.activities.Teleop;

public class ErrorInfo {

    private String errorString = "";
    private Class pageToGoTo = General.class;

    private HashMap<Class, Integer> order = new HashMap<>();

    public ErrorInfo() {
        order.put(Prematch.class, 0);
        order.put(Sandstorm.class, 1);
        order.put(Teleop.class, 2);
        order.put(Endgame.class, 3);
        order.put(General.class, 4);
    }

    public String getErrorString() {
        return errorString.trim();
    }

    public void addToErrorString(String errorString) {
        this.errorString += errorString + "\n";
    }

    public Class getPageToGoTo() {
        return pageToGoTo;
    }

    public void addPageToGoTo(Class pageToGoTo) {
        Log.i("============", String.valueOf(order.get(this.pageToGoTo)));
        Log.i("++++++++++++", String.valueOf(order.get(pageToGoTo)));
        if (this.pageToGoTo == null || order.get(this.pageToGoTo) > order.get(pageToGoTo))
            this.pageToGoTo = pageToGoTo;

        Log.i("!!!!!!!!!!!!", String.valueOf(order.get(this.pageToGoTo)));
        Log.i("------------", String.valueOf(order.get(pageToGoTo)));
    }
}
