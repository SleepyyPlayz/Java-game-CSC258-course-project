package entities;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class LoginInteractor {
    private HashMap<String, String> accounts; // a HashMap mapping usernames to passwords for all registered accounts
    private final String filepath; // a String of the csv file containing all registered accounts
    private String currentUser; // a String of which, if any, user is currently logged in

    /**
     * Constructor.
     * @param filepath a String of the csv file containing all registered accounts
     */
    public LoginInteractor(String filepath) {
        // save the name of the file that LoginInteractor will read from and write to
        this.filepath = filepath;
        // read csv file and create hashmap mapping usernames to passwords
        String line = "";
        // assign new HashMap to accounts attribute
        accounts = new HashMap<>();
        // read csv file and map usernames to passwords in <accounts>
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while ((line = br.readLine()) != null) {
                String[] accountInfo = line.split(",");
                // Puts key (a username) and value (a password) in accounts
                accounts.put(accountInfo[0], accountInfo[1]);
            }
        } catch (IOException e) { System.out.println(e); }
    }

    /**
     * Getter method for accounts, the HashMap mapping usernames to passwords.
     * @return <accounts>.
     */
    public HashMap<String, String> getAccounts() {
        return accounts;
    }

    /**
     * Getter method for filepath, the String containing the path to the csv with account data.
     * @return <filepath>.
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * Getter method for currentUser, the String containing the username of the player that is currently logged in.
     * @return <currentUser>.
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Check whether the user can log into an account with <username> and <password>.
     * @param username a String of a username attempt
     * @param password a String of a password attempt
     * @return true iff <username> and <password> represent a created account.
     */
    public boolean validateLogin(String username, String password) {
        boolean result = (accounts.containsKey(username)) && (Objects.equals(accounts.get(username), password));
        if (result) {
            currentUser = username;
        }
        return result;
    }

    /**
     * Create and record a new account. Log into the new account.
     * @param username a String of a proposed username for a new account
     * @param password a String of a proposed password for a new account
     */
    public void createAccount(String username, String password) {
        if (validateNewUsername(username) && validateNewPassword(password)) {
            try {
                // create new FileWriter
                FileWriter fw = new FileWriter(filepath, true);
                fw.write("\n" + username + "," + password);
                fw.close();
                // update <accounts> so that LoginInteractor doesn't have to be reconstructed
                accounts.put(username, password);
                validateLogin(username,password);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check that an account with <username> does not already exist.
     * @param username a String of a proposed username for a new account
     * @return true iff the proposed <username> is unique.
     */
    private boolean validateNewUsername(String username) {
        return !accounts.containsKey(username);
    }

    /**
     * Check that the proposed <password> is strong enough.
     * @param password a String of a proposed password for a new account
     * @return true iff the proposed <password> is at least 8 characters long.
     */
    private boolean validateNewPassword(String password) {
        return password.length() > 7;
    }

    /**
     * Records that no user is currently logged in.
     */
    public void logOut() {
        currentUser = null;
    }

    /**
     * Checks whether a user is currently logged in.
     * @return true iff a user is currently logged in.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}