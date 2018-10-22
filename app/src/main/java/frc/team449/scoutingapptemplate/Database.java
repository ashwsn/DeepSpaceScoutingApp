package frc.team449.scoutingapptemplate;

/*
 * PowerUpDatabase is a class that stores all data being collected.
 *
 * Used for preventing data loss between page flips and for submission.
 *
 * Created by Nate on 10/10/2017.
 */

public class Database {                         // game will change each year

    // All data being collected
    public String scoutName;
    public int matchNumber;
    public int teamNumber;
    public boolean noShow;
    // the rest of the values for the game go here

    // Default entries
    public Database() {
        scoutName = "";
        matchNumber = 0;
        teamNumber = 0;
        noShow = false;
        // the rest of the values for the game go here
    }

    public String toString() {
        // each instance variable separated by a comma
        return "";
    }

    public static String checkData() {
        String errors = "";
        // check for errors
        return errors;
    }
}
