package com.example.javafx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Editor {

    public Editor() {
    }

    private TextArea textArea = new TextArea();
    private Label currentFile = new Label("");
    private String rawFileName = "default.txt";

    private void updateCurrentFile(String message, String fileName) {
        currentFile.setText(message);
        rawFileName = fileName;
    }

    public void Display() throws IOException {
        Stage window = new Stage();
        ButtonUtils bu = new ButtonUtils();
        window.setTitle("Editor");

        //Constructing menu
        Label title = new Label("Welcome to the Editing page");
        Label reminder = new Label("Note that changes made won't be saved until you click update");
        title.setAlignment(Pos.TOP_CENTER);

        String defaultPage = importCombos(true, null);

        textArea.setText(defaultPage);

        //Buttons
        Button updateButton = bu.applyEffectsBoolWindow(new Button("Save Changes"));
        Button importButton = bu.applyEffectsBoolWindow(new Button("Open File"));
        Button saveButton = bu.applyEffectsBoolWindow(new Button("Save as File"));
        Button clearButton = bu.applyEffectsBoolWindow(new Button("Clear"));
        Button addButton = bu.applyEffectsBoolWindow(new Button("Add Combo"));

        HBox buttonRow = new HBox(updateButton, addButton);
        HBox buttonRowBelow = new HBox(importButton, saveButton, clearButton);

        buttonRow.setAlignment(Pos.BOTTOM_CENTER);
        buttonRow.setSpacing(15);

        buttonRowBelow.setAlignment(Pos.BOTTOM_CENTER);
        buttonRowBelow.setSpacing(15);

        VBox buttonContainer = new VBox(buttonRow, buttonRowBelow);
        buttonContainer.setSpacing(10);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox main = new VBox(title, reminder, currentFile, textArea, buttonContainer);
        main.setSpacing(10);
        main.setAlignment(Pos.CENTER);

        //Button logic

        updateButton.setOnAction(e -> {
            updateFile();
        });

        clearButton.setOnAction(e -> {
            BooleanWindow booleanWindow = new BooleanWindow();
            if (booleanWindow.Display("?", "Are you sure you want to clear?")) {
                textArea.clear();
            }
        });

        importButton.setOnAction(e -> {
            try {
                StringWindow namePrompt = new StringWindow();
                String updatedList = importCombos(false, namePrompt.Display("Name Selection", "Input File Name"));

                if (updatedList != null) {
                    textArea.setText(updatedList);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(main, 600, 400);
        window.setScene(scene);

        window.show();
    }

    private String importCombos(boolean initialize, String name) throws IOException {
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
                if (name == null) {
                    return null;
                }

                if (name.contains(" ")) {
                    ErrorWindow ew = new ErrorWindow();
                    ew.Display("Error", "Name cannot contain spaces");
                    return null;
                }

                if (!name.contains(".txt")) {
                    System.out.println("Critical error, missing .txt, adding it for user");
                    name = name + ".txt";
                }

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

    private void updateFile() {
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
