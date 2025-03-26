package com.example.javafx;

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

    public Combo(String Website, String Email, String Password) {
        this.website = Website;
        this.email = Email;
        this.pass = Password;
    }

    public Combo(String Email, String Password) {
        this.email = Email;
        this.pass = Password;
    }

    public Combo(String Password) {
        this.pass = Password;
    }

    public Combo() {

    }

    public String returnWebsite() {
        return website;
    }

    public String returnPassword() {
        return pass;
    }

    public String returnUsername() {
        return user;
    }

    public String returnEmail() {
        return email;
    }


    public void createCombo() {
        Scanner scnr = new Scanner(System.in);

        String textWebsite;
        String textUser;
        String textEmail;
        String textPassword;

        System.out.print("Input Website: ");
        textWebsite = scnr.nextLine();
        website = textWebsite;

        System.out.print("Input Username: ");
        textUser = scnr.nextLine();
        user = textUser;

        System.out.print("Input Email: ");
        textEmail = scnr.nextLine();
        email = textEmail;

        System.out.print("Input Password: ");
        textPassword = scnr.nextLine();
        pass = textPassword;
    }

    public void createCombo(String password) {
        Scanner scnr = new Scanner(System.in);

        String textWebsite;
        String textUser;
        String textEmail;

        System.out.print("Input Website: ");
        textWebsite = scnr.nextLine();
        website = textWebsite;

        System.out.print("Input Username: ");
        textUser = scnr.nextLine();
        user = textUser;

        System.out.print("Input Email: ");
        textEmail = scnr.nextLine();
        email = textEmail;

        pass = password;
    }

    public void printCombo() {
        if (user != null && email != null) {
            System.out.println("Username: " + user);
            System.out.println("Email: " + email);
            System.out.println("Password: " + pass);
        }
        else if (email != null) {
            System.out.println("Email: " + email);
            System.out.println("Password: " + pass);
        }
        else {
            System.out.println("Password: " + pass);
        }
    }

    public String stringConversion() {

        String textWebsite;
        String textUser;
        String textEmail;
        String textPassword;

        if (website != null && user != null && email != null) {
            textWebsite = this.website;
            textUser = this.user;
            textEmail = this.email;
            textPassword = this.pass;

            return textWebsite + "\n" + textUser + "\n" +  textEmail + "\n" + textPassword;
        }
        else if (user != null && email != null) {
            textUser = this.user;
            textEmail = this.email;
            textPassword = this.pass;

            return textUser + "\n" +  textEmail + "\n" + textPassword;
        }
        else if (email != null) {
            textEmail = this.email;
            textPassword = this.pass;

            return textEmail + "\n" + textPassword;
        }
        else {
            textPassword = this.pass;
            return textPassword;
        }
    }
}
