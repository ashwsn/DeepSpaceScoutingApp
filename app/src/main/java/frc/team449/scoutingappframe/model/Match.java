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
    private int startingLevel;
    private int preload;
    private boolean noShow;
    private boolean noAuto;
    private boolean movedForward;
    private int placedPiece;
    private int placedLocation;
    private boolean achievedNothing;
    private int dead;
    private int[] teleopPiecePositions;
    private int numCargoL1;
    private int numCargoL2;
    private int numCargoL3;
    private int numHatchL1;
    private int numHatchL2;
    private int numHatchL3;
    private int numCargoShip;
    private int numHatchShip;
    private int attemptLevel;
    private int attemptSuccess;
    private int levelReached;
    private String comments;

    public static Match getInstance(){return match;}

    // Default entries
    private Match() {
        reset();
        scoutName = "";
        matchNumber = 0;
    }

    public void reset() {
        matchNumber++;
        teamNumber = 0;
        noShow = false;
        startingLevel = 0;
        preload = 0;
        noAuto = false;
        movedForward = false;
        placedPiece = 0;
        placedLocation = 0;
        achievedNothing = false;
        dead = 0;
        attemptLevel = 0;
        attemptSuccess = 0;
        levelReached = 0;
        comments = "";
    }

    public String toString(Context ctxt) {
        // each instance variable separated by a comma

        return scoutName+","+  ctxt.getResources().getStringArray(R.array.matches)[matchNumber]+","+
                ctxt.getResources().getStringArray(R.array.teams)[teamNumber]+","+(noShow ? 1 : 0)
                +","+startingLevel+","+preload+","+(noAuto ? 1 : 0)+","+(movedForward ? 1 : 0)+","
                +placedPiece+","+ctxt.getResources().getStringArray(R.array.field_locations)[placedLocation]
                +","+(achievedNothing ? 1 : 0)+","+dead+","+attemptLevel+","+attemptSuccess+","+
                levelReached+","+(comments==null ? "" : comments);
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

    // Getters and setters for all match data
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

    public void setTeamNumber(int teamNumber) { this.teamNumber = teamNumber; }

    public boolean isNoShow() {
        return noShow;
    }

    public void setNoShow(boolean noShow) {
        this.noShow = noShow;
    }

    public int getPreload() { return preload; }

    public void setPreload(int preload) { this.preload = preload; }

    public int getStartingLevel() {
        return startingLevel;
    }

    public void setStartingLevel(int startingLevel) {
        this.startingLevel = startingLevel;
    }

    public boolean isNoAuto() { return noAuto; }

    public void setNoAuto(boolean noAuto) {
        this.noAuto = noAuto;
    }

    public boolean isMovedForward() {
        return movedForward;
    }

    public void setMovedForward(boolean movedForward) { this.movedForward = movedForward; }

    public int getPlacedPiece() {
        return placedPiece;
    }

    public void setPlacedPiece(int placedPiece) {
        this.placedPiece = placedPiece;
    }

    public int getPlacedLocation() {
        return placedLocation;
    }

    public void setPlacedLocation(int placedLocation) {
        this.placedLocation = placedLocation;
    }

    public boolean isAchievedNothing() {
        return achievedNothing;
    }

    public void setAchievedNothing(boolean achievedNothing) { this.achievedNothing = achievedNothing; }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public int[] getTeleopPiecePositions() {
        return teleopPiecePositions;
    }

    public void setTeleopPiecePositions(int[] teleopPiecePositions) {
        this.teleopPiecePositions = teleopPiecePositions;
    }

    public int getNumCargoL1() {
        return numCargoL1;
    }

    public void setNumCargoL1(int numCargoL1) {
        this.numCargoL1 = numCargoL1;
    }

    public int getNumCargoL2() {
        return numCargoL2;
    }

    public void setNumCargoL2(int numCargoL2) {
        this.numCargoL2 = numCargoL2;
    }

    public int getNumCargoL3() {
        return numCargoL3;
    }

    public void setNumCargoL3(int numCargoL3) {
        this.numCargoL3 = numCargoL3;
    }

    public int getNumHatchL1() {
        return numHatchL1;
    }

    public void setNumHatchL1(int numHatchL1) {
        this.numHatchL1 = numHatchL1;
    }

    public int getNumHatchL2() {
        return numHatchL2;
    }

    public void setNumHatchL2(int numHatchL2) {
        this.numHatchL2 = numHatchL2;
    }

    public int getNumHatchL3() {
        return numHatchL3;
    }

    public void setNumHatchL3(int numHatchL3) {
        this.numHatchL3 = numHatchL3;
    }

    public int getNumCargoShip() {
        return numCargoShip;
    }

    public void setNumCargoShip(int numCargoShip) {
        this.numCargoShip = numCargoShip;
    }

    public int getNumHatchShip() {
        return numHatchShip;
    }

    public void setNumHatchShip(int numHatchShip) {
        this.numHatchShip = numHatchShip;
    }

    public int getAttemptLevel() { return attemptLevel; }

    public void setAttemptLevel(int attemptLevel) { this.attemptLevel = attemptLevel; }

    public int getAttemptSuccess() { return attemptSuccess; }

    public void setAttemptSuccess(int attemptSuccess) { this.attemptSuccess = attemptSuccess; }

    public int getLevelReached() { return levelReached; }

    public void setLevelReached(int levelReached) { this.levelReached = levelReached; }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
