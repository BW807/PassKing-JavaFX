package com.example.javafx.Utils;

import java.util.Scanner;

public class Combo {

    private String website;
    private String user;
    private String email;
    private String pass;

    public Combo(String Website, String Username, String Email, String Password) {
        this.website = Website;
        this.user = Username;
        this.email = Email;
        this.pass = Password;
    }

    public Combo() {
    }

    //This whole system was kind of bad before. Moved it into combo window and redone it all

    public String toString() {
        String done = "";

        done = done + "----->\n";

        if (website != null) {
            done = done + "Website: " + website + "\n";
        }

        if (email != null) {
            done = done + "Email: " + email + "\n";
        }

        if (user != null) {
            done = done + "Username: " + user + "\n";
        }

        if (pass != null) {
            done = done + "Password: " + pass + "\n";
        }

        done = done + "<-----\n";
        done = done + "\n";
        return done;
    }

    public void printConsoleCombo() {
        if (website != null) {
            System.out.println("Website: " + website);
        }

        if (email != null) {
            System.out.println("Email: " + email);
        }

        if (user != null) {
            System.out.println("Username: " + user);
        }

        if (pass != null) {
            System.out.println("Password: " + pass);
        }

        System.out.println();
    }
}
