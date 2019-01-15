package frc.team449.scoutingappframe.Model;

/*
 * Database is a class that stores all data being collected.
 *
 * Used for preventing data loss between page flips and for submission.
 *
 * Created by Nate on 10/10/2017.
 */


import frc.team449.scoutingappframe.Activities.MainActivity;

public class Match {

    // All data being collected
    public String scoutName;
    public int matchNumber;
    public int teamNumber;
    public boolean noShow;
    public boolean noAuto;
    public boolean movedForward;
    public boolean achievedNothing;
    public int dead;
    public String comments;
    // the rest of the values for the game go here

    // Default entries
    public Match() {
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

    public String toString() {
        // each instance variable separated by a comma
        return scoutName+","+matchNumber+","+teamNumber+","+(noShow ? 1 : 0)+","+(noAuto ? 1 : 0)+","
                +(movedForward ? 1 : 0)+","+(achievedNothing ? 1 : 0)+","+dead+","+comments;
    }

    public static String checkData() {
        String errors = "";
        // check for errors
        if (MainActivity.match.scoutName.equals("")) {
            errors += "Scout name cannot be empty\n";
        }
        if (MainActivity.match.matchNumber == 0) {
            errors += "Please select a match number\n";
        }
        if (MainActivity.match.teamNumber == 0) {
            errors += "Please select a team number\n";
        }
        if (MainActivity.match.dead == 0) {
            errors += "Please select an option for deadness\n";
        }
        return errors;
    }
}
