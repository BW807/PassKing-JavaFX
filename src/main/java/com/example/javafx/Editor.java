package com.example.javafx;

import com.example.javafx.Utils.Combo;
import com.example.javafx.Utils.FileUtils;
import com.example.javafx.Windows.BooleanWindow;
import com.example.javafx.Utils.ButtonUtils;
import com.example.javafx.Windows.ComboWindow;
import com.example.javafx.Windows.ErrorWindow;
import com.example.javafx.Windows.StringWindow;
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
    FileUtils fileUtils = new FileUtils();

    public Editor() {
    }

    public void Display() throws IOException {
        Stage window = new Stage();
        ButtonUtils bu = new ButtonUtils();
        window.setTitle("Editor");

        //Constructing menu
        Label title = new Label("Welcome to the Editing page");
        Label reminder = new Label("Note that changes made won't be saved until you click update");
        title.setAlignment(Pos.TOP_CENTER);

        String defaultPage = fileUtils.importCombos(true, null);

        fileUtils.getTextArea().setText(defaultPage);

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

        VBox main = new VBox(title, reminder, fileUtils.getCurrentFile(), fileUtils.getTextArea(), buttonContainer);
        main.setSpacing(10);
        main.setAlignment(Pos.CENTER);

        //Button logic
        updateButton.setOnAction(e -> {
            fileUtils.updateFile();
        });

        saveButton.setOnAction(e -> {
            StringWindow namePrompt = new StringWindow();

            fileUtils.saveCombos(namePrompt.Display("Name Selection", "Input File Name"), fileUtils.getTextArea());

        });

        clearButton.setOnAction(e -> {
            BooleanWindow booleanWindow = new BooleanWindow();
            if (booleanWindow.Display("?", "Are you sure you want to clear?")) {
                fileUtils.getTextArea().clear();
            }
        });

        importButton.setOnAction(e -> {
            try {
                StringWindow namePrompt = new StringWindow();
                String updatedList = fileUtils.importCombos(false, namePrompt.Display("Name Selection", "Input File Name"));

                if (updatedList != null) {
                    fileUtils.getTextArea().setText(updatedList);
                }
            } catch (IOException ex) {
                System.out.println("Feel free to ignore the error if exited early.");
                throw new RuntimeException(ex);
            }
        });

        addButton.setOnAction(e -> {
            ComboWindow cw = new ComboWindow();
            Combo newCombo = cw.Display(null);

            if (newCombo != null) {
                fileUtils.printCombo(newCombo);
            }

        });

        Scene scene = new Scene(main, 600, 400);
        window.setScene(scene);

        window.show();

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(window);
        });
    }

    public void closeProgram(Stage stage) {
        BooleanWindow bool = new BooleanWindow();

        if (bool.Display("Closing Program", "Save changes before exiting?")) {
            fileUtils.updateFile();
        }

        stage.close();
    }

}
