package com.example.javafx.Utils;

import com.example.javafx.Windows.BooleanWindow;
import com.example.javafx.Windows.ErrorWindow;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtils {
    private TextArea textArea = new TextArea();
    private Label currentFile = new Label("");
    private String rawFileName = "default.txt";

    public FileUtils() {
        textArea.setMaxWidth(500);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public Label getCurrentFile() {
        return currentFile;
    }

    public String getCurrentFileName() {
        return rawFileName;
    }

    private void updateCurrentFile(String message, String fileName) {
        currentFile.setText(message);
        rawFileName = fileName;
    }

    public void printCombo(Combo currentCombo) {
        String init = textArea.getText();

        if (init.trim().equals("Your default workspace is currently empty\n" +
                "Click the add button to make your first combo!")) {
            textArea.setText(currentCombo.toString());
            return;
        }

        init = init + currentCombo.toString();
        textArea.setText(init);
    }

    public void saveCombos(String rawFileName, TextArea textArea) {
        try {
            String name = fixNameForProcessing(rawFileName);

            System.out.println("searching for " + name);

            File searchingFor = new File(name);

            if (searchingFor.exists()) {
                System.out.println("Found " + name + " at " + searchingFor.getAbsolutePath());

                BooleanWindow doubleCheck = new BooleanWindow();

                if (doubleCheck.Display("?", "" + name + " already exists, are you sure\n" +
                        "you want to override it?")) {

                    PrintWriter pw = new PrintWriter(name);
                    Scanner scnr = new Scanner(textArea.getText());

                    while (scnr.hasNextLine()) {
                        pw.println(scnr.nextLine());
                    }

                    pw.close();
                    scnr.close();
                    sendPathMessage(searchingFor.getName(), searchingFor.getAbsolutePath());
                    return;
                }
                else {
                    return;
                }
            }
            else {
                System.out.println("File does not exist yet.");
                File saveFile = new File(name);

                PrintWriter pw = new PrintWriter(name);
                Scanner scnr = new Scanner(textArea.getText());

                while (scnr.hasNextLine()) {
                    pw.println(scnr.nextLine());
                }

                pw.close();
                scnr.close();
                System.out.println("Saved " + saveFile.getName() + " at " + saveFile.getAbsolutePath());
                sendPathMessage(saveFile.getName(), saveFile.getAbsolutePath());
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void sendPathMessage(String name, String path) {
        ErrorWindow ew = new ErrorWindow();
        ew.Display("Success", "Saved " + name + " at: \n" + path, 500, 150);
    }

    public String importCombos(boolean initialize, String name) throws IOException {
        String done = "";

        try {
            if (initialize) {
                File defaultCheck = new File("default.txt");
                updateCurrentFile("Current File: " + defaultCheck.getName(), defaultCheck.getName());

                if (defaultCheck.createNewFile()) {
                    System.out.println("Default file not found, creating new one");
                    System.out.println("Doing so at : " + defaultCheck.getAbsolutePath());
                    return "Your default workspace is currently empty\nClick the add button to make your first combo!";
                }
                else {
                    Scanner fileScnr = new Scanner(defaultCheck);

                    while (fileScnr.hasNextLine()) {
                        done = done + fileScnr.nextLine() + "\n";
                    }

                    if (done.equals("")) {
                        done = "Your default workspace is currently empty\nClick the add button to make your first combo!";
                    }

                    fileScnr.close();
                    return done;
                }

            }
            else {
                name = fixNameForProcessing(name);

                System.out.println("done, searching for " + name);

                File searchingFor = new File(name);

                if (searchingFor.exists()) {
                    System.out.println("Found " + name + " at " + searchingFor.getAbsolutePath());

                    Scanner fileScnr = new Scanner(searchingFor);

                    while (fileScnr.hasNextLine()) {
                        done = done + fileScnr.nextLine() + "\n";
                    }

                    updateCurrentFile("Current File: " + searchingFor.getName(), searchingFor.getName());

                    fileScnr.close();
                    return done;
                }
                else {
                    ErrorWindow ew = new ErrorWindow();
                    ew.Display("NotFoundError", "The File " + name +
                            " was not found in: \n" + searchingFor.getAbsolutePath(), 300, 150);
                }
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }

    private String fixNameForProcessing(String originalName) {

        String newName = null;

        if (originalName == null) {
            return null;
        }

        if (originalName.contains(" ")) {
            ErrorWindow ew = new ErrorWindow();
            ew.Display("Error", "Name cannot contain spaces");
            return null;
        }

        if (!originalName.contains(".txt")) {
            System.out.println("Critical error, missing .txt, adding it for user");
            newName = originalName + ".txt";
        }

        return newName;
    }

    public void updateFile() {
        try {
            File file = new File(rawFileName);

            if (file.exists()) {
                PrintWriter pw = new PrintWriter(file);
                Scanner scnr = new Scanner(textArea.getText());

                while (scnr.hasNextLine()) {
                    pw.println(scnr.nextLine());
                }

                pw.close();
                scnr.close();
            }
            else throw new FileNotFoundException();

        } catch (FileNotFoundException e) {
            ErrorWindow ew = new ErrorWindow();
            ew.Display("Error", "Cannot find a file named " + rawFileName);
            throw new RuntimeException(e);
        }
    }
}
