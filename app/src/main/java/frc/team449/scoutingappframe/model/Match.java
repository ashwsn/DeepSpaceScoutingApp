package frc.team449.scoutingappframe.model;

/*
 * Database is a class that stores all data being collected.
 *
 * Used for preventing data loss between page flips and for submission.
 *
 * Created by Nate on 10/10/2017.
 */


import android.content.Context;

import frc.team449.scoutingappframe.R;

public class Match {

    private static final Match match = new Match();

    // All data being collected
    private String scoutName;
    private int matchNumber;
    private int teamNumber;
    private boolean noShow;
    private boolean noAuto;
    private boolean movedForward;
    private boolean achievedNothing;
    private int dead;
    private String comments;
    // the rest of the values for the game go here

    public static Match getInstance(){return match;}

    // Default entries
    private Match() {
        scoutName = "";
        matchNumber = 0;
        teamNumber = 0;
        noShow = false;
        noAuto = false;
        movedForward = false;
        achievedNothing = false;
        dead = 0;
        comments = "";
        // the rest of the values for the game go here
    }

    public void reset(){
        matchNumber++;
        teamNumber = 0;
        noShow = false;
        noAuto = false;
        movedForward = false;
        achievedNothing = false;
        dead = 0;
        comments = "";
    }

    public String toString(Context ctxt) {
        // each instance variable separated by a comma
        return scoutName+","+  ctxt.getResources().getStringArray(R.array.matches)[matchNumber]+","+
                ctxt.getResources().getStringArray(R.array.teams)[teamNumber]+","+(noShow ? 1 : 0)
                +","+(noAuto ? 1 : 0)+","+(movedForward ? 1 : 0)+","+(achievedNothing ? 1 : 0)+","+
                dead+","+(comments==null ? "" : comments);
    }

    public String checkData() {
        String errors = "";
        // check for errors
        if (scoutName.equals("")) {
            errors += "Scout name cannot be empty\n";
        }
        if (matchNumber == 0) {
            errors += "Please select a match number\n";
        }
        if (teamNumber == 0) {
            errors += "Please select a team number\n";
        }
        if (dead == 0) {
            errors += "Please select an option for deadness\n";
        }
        return errors.trim();
    }

    public String getScoutName() {
        return scoutName;
    }

    public void setScoutName(String scoutName) {
        this.scoutName = scoutName;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public boolean isNoShow() {
        return noShow;
    }

    public void setNoShow(boolean noShow) {
        this.noShow = noShow;
    }

    public boolean isNoAuto() {
        return noAuto;
    }

    public void setNoAuto(boolean noAuto) {
        this.noAuto = noAuto;
    }

    public boolean isMovedForward() {
        return movedForward;
    }

    public void setMovedForward(boolean movedForward) {
        this.movedForward = movedForward;
    }

    public boolean isAchievedNothing() {
        return achievedNothing;
    }

    public void setAchievedNothing(boolean achievedNothing) {
        this.achievedNothing = achievedNothing;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
