package frc.team449.deepspacescoutingapp.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.Endgame;
import frc.team449.deepspacescoutingapp.activities.Prematch;
import frc.team449.deepspacescoutingapp.activities.Teleop;

public class Match {

    private static Match match = new Match();

    private static Match otherMatch;
    private static String oldMatchString;
    private boolean isReplacement;
    private boolean isRecovery;

    // All data being collected
    private String scoutName;
    private int matchNumber;
    private int teamNumber;
    private int allianceColor;
    private int startingLevel;
    private int preload;
    private boolean noShow;
    private boolean movedForward;
    private boolean placedPiece;
    private int doubleAuto;
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
    private int droppedHatch;
    private int droppedCargo;
    private int attemptLevel;
    private int levelReached;
    private int climbTime;
    private boolean defense;
    private String comments;

    public static Match getInstance() {
        return match;
    }


    // Default entries
    private Match() {
        reset();
        scoutName = "";
        matchNumber = 0;
        allianceColor = -1;
    }

    public void reset() {
        if (!isReplacement()) {
            teamNumber = 0;
            noShow = false;
            startingLevel = -1;
            preload = -1;
            movedForward = false;
            placedPiece = false;
            doubleAuto = -1;
            dead = -1;
            teleopPiecePositions = new int[20];
            numCargoL1 = 0;
            numCargoL2 = 0;
            numCargoL3 = 0;
            numCargoShip = 0;
            numHatchL1 = 0;
            numHatchL2 = 0;
            numHatchL3 = 0;
            numHatchShip = 0;
            droppedCargo = 0;
            droppedHatch = 0;
            attemptLevel = -1;
            levelReached = -1;
            climbTime = 0;
            defense = false;
            comments = "";
        } else {
            match = otherMatch;
            otherMatch = null;
            oldMatchString = null;
        }
    }

    public void incMatch(Context ctxt) {
        if (!isRecovery) {
            String[] matches = ctxt.getResources().getStringArray(R.array.matches);
            if (matchNumber < matches.length - 1 && TextUtils.isDigitsOnly(matches[matchNumber])) {
                matchNumber++;
            }
        }
    }

    private String intArrayToString(int[] a) {
        List<String> t = new ArrayList<>();
        for (int i : a)
            t.add(String.valueOf(i));
        Log.i("!!!!toString!!!!",TextUtils.join(".",t));
        return TextUtils.join(".", t);
    }

    public String toString(Context ctxt) {
        SimpleDateFormat s = new SimpleDateFormat("dd.MM HH:mm:ss");
        // each instance variable separated by a comma
        return  (ctxt.getResources().getStringArray(R.array.teams)[teamNumber]+","+ // team #
                ctxt.getResources().getStringArray(R.array.matches)[matchNumber]+","+ // match #
                allianceColor + "," + //alliance color
                startingLevel+","+ // starting level
                preload+","+ // preload
                (noShow ? 1 : 0)+","+ // no show
                (movedForward ? 1 : 0)+","+ // moved forward
                (placedPiece ? 1 : 0)+","+ // auto placed piece
                doubleAuto+","+ // double auto
                numCargoShip+","+ // ship cargo
                numCargoL1+","+ // level 1 cargo
                numCargoL2+","+ // level 2 cargo
                numCargoL3+","+ // level 3 cargo
                numHatchShip+","+ // ship hatches
                numHatchL1+","+ // level 1 hatches
                numHatchL2+","+ // level 2 hatches
                numHatchL3+","+ // level 3 hatches
                droppedHatch+","+ // hatches dropped
                droppedCargo+","+ // cargo dropped
                attemptLevel+","+ // hab attempt level
                (attemptLevel != 0 ? (attemptLevel == levelReached ? 2 : 1) : 0)+","+ // hab success
                levelReached+","+ // hab level reached
                climbTime+","+ // climb time
                dead+","+ // dead
                (defense ? 1: 0) + "," + // defense
                (comments==null ? "" : comments.replace(',','.')) + "," + // comments
                scoutName.replace(',','.') + "," + // scout name
                s.format(new Date()) + "," +
                intArrayToString(teleopPiecePositions)).replace('\n','/').replace(';','.').replace('"','\'');
    }

    public ErrorInfo checkData() {
        ErrorInfo error = new ErrorInfo();
        // check for errors
        if (scoutName.equals("")) {
            error.addToErrorString("Scout name cannot be empty");
            error.addPageToGoTo(Prematch.class);
        }
        if (matchNumber == 0) {
            error.addToErrorString("Please select a match number");
            error.addPageToGoTo(Prematch.class);
        }
        if (teamNumber == 0) {
            error.addToErrorString("Please select a team number");
            error.addPageToGoTo(Prematch.class);
        }
        if (allianceColor == -1) {
            error.addToErrorString("Please select the alliance color");
            error.addPageToGoTo(Prematch.class);
        }
        if (dead == -1) {
            dead = 0;
        }
        if (startingLevel == -1){
            if (noShow) startingLevel = 0;
            else {
                error.addToErrorString("Please select a starting level");
                error.addPageToGoTo(Prematch.class);
            }
        }
        if (preload == -1) {
            if (noShow) preload = 0;
            else {
                error.addToErrorString("Please select a preloaded piece");
                error.addPageToGoTo(Prematch.class);
            }
        }
        if (preload == 0 && placedPiece){
            error.addToErrorString("How did they place the preloaded piece if they didn't preload?");
        }
        if (doubleAuto == -1)
            doubleAuto = 0;
        if (attemptLevel == -1) {
            if (noShow) {
                attemptLevel = 0;
                levelReached = 0;
            } else {
                error.addToErrorString("Please select a HAB level attempt");
                error.addPageToGoTo(Endgame.class);
            }
        } else if (levelReached == -1) {
            if (attemptLevel != 0) {
                error.addToErrorString("What HAB level was reached?");
                error.addPageToGoTo(Endgame.class);
            } else levelReached = 0;
        }
        if (climbTime == 0 && (levelReached > 1)) {
            error.addToErrorString("How long did it take to climb?");
            error.addPageToGoTo(Endgame.class);
        }
        if (levelReached < 2 && climbTime != 0)
            climbTime = 0;
        return error;
    }

    public ErrorInfo softCheck() {
        ErrorInfo error = new ErrorInfo();
        if (climbTime != 0 && ((climbTime < 2 && levelReached == 2) || climbTime < 7)) {
            error.addToErrorString("Did they really climb in " + climbTime + " seconds?");
            error.addPageToGoTo(Endgame.class);
        }
        if (climbTime > 30) {
            error.addToErrorString("Did it really take " + climbTime + " seconds to climb?");
            error.addPageToGoTo(Endgame.class);
        }
        int totalHatch = numHatchShip + numHatchL1 + numHatchL2 + numHatchL3;
        int totalCargo = numCargoShip + numCargoL1 + numCargoL2 + numCargoL3;
        if (totalHatch + totalCargo > 9) {
            error.addToErrorString("Did one robot really place " + (totalHatch > 0 ? totalHatch + " hatches" : "") +
                    (totalHatch > 0 && totalCargo > 0 ? " and " : "") + (totalCargo > 0 ? totalCargo + " cargo" : "") + "?");
            error.addPageToGoTo(Teleop.class);
        }
        return error;
    }

    public void loadFromString(String data, Context ctxt) {
        otherMatch = cloneMatch(match);
        oldMatchString = data;

        String[] dataArray = data.split(",");
        isReplacement = true;

        teamNumber = Arrays.asList(ctxt.getResources().getStringArray(R.array.teams)).indexOf(dataArray[0]);
        matchNumber = Arrays.asList(ctxt.getResources().getStringArray(R.array.matches)).indexOf(dataArray[1]);
        allianceColor = Integer.parseInt(dataArray[2]);
        startingLevel = Integer.parseInt(dataArray[3]);
        preload = Integer.parseInt(dataArray[4]);
        noShow = dataArray[5].equals("1");
        movedForward = dataArray[6].equals("1");
        placedPiece = dataArray[7].equals("1");
        doubleAuto   = Integer.parseInt(dataArray[8]);
        numCargoShip = Integer.parseInt(dataArray[9]);
        numCargoL1   = Integer.parseInt(dataArray[10]);
        numCargoL2   = Integer.parseInt(dataArray[11]);
        numCargoL3   = Integer.parseInt(dataArray[12]);
        numHatchShip = Integer.parseInt(dataArray[13]);
        numHatchL1   = Integer.parseInt(dataArray[14]);
        numHatchL2   = Integer.parseInt(dataArray[15]);
        numHatchL3   = Integer.parseInt(dataArray[16]);
        droppedHatch = Integer.parseInt(dataArray[17]);
        droppedCargo = Integer.parseInt(dataArray[18]);
        attemptLevel = Integer.parseInt(dataArray[19]);
        levelReached = Integer.parseInt(dataArray[21]); //yup, 20 is supposed to get skipped
        climbTime    = Integer.parseInt(dataArray[22]);
        dead         = Integer.parseInt(dataArray[23]);
        defense = dataArray[24].equals("1");
        comments = dataArray[25];
        scoutName = dataArray[26];
        String[] stringTeleopPiecePositions = dataArray[28].split("\\.");
        teleopPiecePositions = new int[stringTeleopPiecePositions.length];
        for (int i = 0; i < stringTeleopPiecePositions.length; i++) {
            String s = stringTeleopPiecePositions[i];
            teleopPiecePositions[i] = Integer.parseInt(s);
        }
    }

    private static Match cloneMatch(Match m) {
        Match newmatch = new Match();

        newmatch.isRecovery = true;

        newmatch.setScoutName(m.getScoutName());
        newmatch.setMatchNumber(m.getMatchNumber());
        newmatch.setTeamNumber(m.getTeamNumber());
        newmatch.setAllianceColor(m.getAllianceColor());
        newmatch.setStartingLevel(m.getStartingLevel());
        newmatch.setPreload(m.getPreload());
        newmatch.setNoShow(m.isNoShow());
        newmatch.setMovedForward(m.isMovedForward());
        newmatch.setPlacedPiece(m.isPlacedPiece());
        newmatch.setDoubleAuto(m.getDoubleAuto());
        newmatch.setDead(m.getDead());
        newmatch.setTeleopPiecePositions(m.getTeleopPiecePositions().clone());
        newmatch.setNumCargoL1(m.getNumCargoL1());
        newmatch.setNumCargoL2(m.getNumCargoL2());
        newmatch.setNumCargoL3(m.getNumCargoL3());
        newmatch.setNumHatchL1(m.getNumHatchL1());
        newmatch.setNumHatchL2(m.getNumHatchL2());
        newmatch.setNumHatchL3(m.getNumHatchL3());
        newmatch.setNumCargoShip(m.getNumCargoShip());
        newmatch.setNumHatchShip(m.getNumHatchShip());
        newmatch.setDroppedHatch(m.getDroppedHatch());
        newmatch.setDroppedCargo(m.getDroppedCargo());
        newmatch.setAttemptLevel(m.getAttemptLevel());
        newmatch.setLevelReached(m.getLevelReached());
        newmatch.setClimbTime(m.getClimbTime());
        newmatch.setDefense(m.getDefense());
        newmatch.setComments(m.getComments());
        return newmatch;
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

    public boolean isMovedForward() {
        return movedForward;
    }

    public void setMovedForward(boolean movedForward) { this.movedForward = movedForward; }

    public boolean isPlacedPiece() {
        return placedPiece;
    }

    public void setPlacedPiece(boolean placedPiece) {
        this.placedPiece = placedPiece;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public int[] getTeleopPiecePositions() { return teleopPiecePositions; }

    public void setTeleopPiecePositions(int[] teleopPiecePositions) { this.teleopPiecePositions = teleopPiecePositions; }

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

    public int getLevelReached() { return levelReached; }

    public void setLevelReached(int levelReached) { this.levelReached = levelReached; }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getAllianceColor() {
        return allianceColor;
    }

    public void setAllianceColor(int allianceColor) {
        this.allianceColor = allianceColor;
    }

    public boolean getDefense() {
        return defense;
    }

    public void setDefense(boolean defense) {
        this.defense = defense;
    }

    public int getDroppedHatch() {
        return droppedHatch;
    }

    public void setDroppedHatch(int droppedHatch) {
        this.droppedHatch = droppedHatch;
    }

    public int getDroppedCargo() {
        return droppedCargo;
    }

    public void setDroppedCargo(int droppedCargo) {
        this.droppedCargo = droppedCargo;
    }

    public void incrementDroppedHatch(int change){
        this.droppedHatch += change;
        if (this.droppedHatch < 0) this.droppedHatch = 0;
    }

    public void incrementDroppedCargo(int change){
        this.droppedCargo += change;
        if (this.droppedCargo < 0) this.droppedCargo = 0;
    }

    public int getClimbTime() {
        return climbTime;
    }

    public void setClimbTime(int climbTime) {
        this.climbTime = climbTime;
    }

    public int getDoubleAuto() {
        return doubleAuto;
    }

    public void setDoubleAuto(int doubleAuto) {
        this.doubleAuto = doubleAuto;
    }

    public boolean isReplacement() {
        return isReplacement;
    }

    public static String getOldMatchString() {
        return oldMatchString;
    }
}
