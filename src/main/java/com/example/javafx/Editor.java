package com.example.javafx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Editor {

    public Editor() {
    }

    public void Display() throws IOException {
        Stage window = new Stage();
        ButtonUtils bu = new ButtonUtils();
        window.setTitle("Editor");

        Label title = new Label("Welcome to the Editing page");
        Label reminder = new Label("Note that changes made won't be saved until you click update");
        title.setAlignment(Pos.TOP_CENTER);

        String defaultPage = importCombos(true);

        TextArea textArea = new TextArea();
        textArea.setText(defaultPage);

        Button updateButton = bu.applyEffectsBoolWindow(new Button("Save Changes"));
        Button importButton = bu.applyEffectsBoolWindow(new Button("Import File"));
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

        VBox main = new VBox(title, reminder, textArea, buttonContainer);
        main.setSpacing(10);
        main.setAlignment(Pos.CENTER);

        Scene scene = new Scene(main, 600, 400);
        window.setScene(scene);

        window.show();
    }

    private String importCombos(boolean initialize) throws IOException {
        String done = "";

        try {
            if (initialize) {
            File defaultCheck = new File("src/main/resources/com/example/javafx/default.txt");

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

                return done;
            }

            }
            else {

            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;

    }

}
