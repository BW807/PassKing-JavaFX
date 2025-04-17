package com.example.javafx;

import com.example.javafx.Utils.ButtonUtils;
import com.example.javafx.Utils.Combo;
import com.example.javafx.Windows.BooleanWindow;
import com.example.javafx.Windows.ComboWindow;
import com.example.javafx.Windows.ErrorWindow;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Generator extends Main {
    ArrayList<String> wordlist = new ArrayList<>();
    ArrayList<String> charlistSafe = new ArrayList<>();
    ArrayList<String> charlistRisky = new ArrayList<>();


    ArrayList<String> chosenWordList = new ArrayList<>();
    ArrayList<String> chosenCharList = new ArrayList<>();

    // Not very CSE 205 of me to use all ArrayLists but it wouldn't make much sense to use anything else here.

    //I just realized the top three arraylists could all have been a fixed array
    //But if it aint broke dont fix it

    private String finalPass = "";
    private int length = 1;

    public Generator() {
    }

    ButtonUtils utils = new ButtonUtils();

    public void Display() {
        Stage window = new Stage();
        window.setTitle("Generator");
        window.initModality(Modality.APPLICATION_MODAL);

        Label Title = new Label("Click below to generate a unique password");

        TextField textField = new TextField();
        Button set = new Button("Set Password Length");
        textField.setMaxSize(100, 50);
        Label numFields = new Label("Currently: " + length);
        HBox lengthSetter = new HBox(textField, set, numFields);
        lengthSetter.setSpacing(10);
        lengthSetter.setAlignment(Pos.CENTER);

        set.setOnAction(e -> {
            try {
                if (Integer.parseInt(textField.getText()) < 100) {
                    length = Integer.parseInt(textField.getText());
                    numFields.setText("Currently: " + length);
                }
                else {
                    numFields.setText("Too large");
                }

            } catch (NumberFormatException ex) {
                numFields.setText("Invalid");
                System.out.println("Error : " + ex.getMessage());
            }
        });

        ChoiceBox<String> mode = new ChoiceBox<>();
        mode.getItems().addAll("Words", "Characters");
        mode.getSelectionModel().select("Words");

        ChoiceBox<String> modeRisk = new ChoiceBox<>();
        modeRisk.getItems().addAll("Filtered Chars", "All Chars");
        modeRisk.getSelectionModel().select("All Chars");

        Button Generator = utils.applyEffectsGenerator(new Button("Generate Password"));

        Button addToFile = utils.applyEffectsGenerator(new Button("Save as \"default.txt\""));

        Label Password = new Label("Nothing has been generated");

        Button copy = utils.applyEffectsGenerator(new Button("Copy to Clipboard"));

        copy.setDisable(true);

        VBox main = new VBox(Title, lengthSetter, Password, copy, addToFile, Generator, mode);
        main.setSpacing(10);
        main.setAlignment(Pos.CENTER);

        Scene scene = new Scene(main, 400, 400);
        window.setScene(scene);

        window.show();

        Generator.setOnAction(e -> {
            Password.setText("null");

            if (mode.getSelectionModel().getSelectedItem().equals("Words")) {
                finalPass = wordPassword(length);
                Password.setText(finalPass);
            }
            else {
                finalPass = charPassword(length, modeRisk.getValue());
                Password.setText(finalPass);
            }

            copy.setDisable(false);
        });

        mode.setOnAction(e -> {
            if (mode.getSelectionModel().getSelectedItem().equals("Characters")) {
                main.getChildren().add(modeRisk);
            }
            else {
                main.getChildren().remove(modeRisk);
            }
        });

        copy.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(finalPass);
            clipboard.setContent(content);
        });


        addToFile.setOnAction(e -> {
            //Only realized finishing this project i could do this, oops

            BooleanWindow bw = new BooleanWindow();

            if (bw.Display("Warning", "Warning! This will clear\n" +
                    "default.txt, continue?")) {
                ComboWindow cw = new ComboWindow();
                Combo newCombo = cw.Display(finalPass);

                if (newCombo != null) {
                    edit.fileUtils.printCombo(newCombo);
                    edit.fileUtils.updateFile();
                }
            }
        });

        try {
            importChars();
            importWords();
        }
        catch (FileNotFoundException e) {
            ErrorWindow ew = new ErrorWindow();
            ew.Display("Error", e.getMessage());
            System.out.println("Error : File not found " + e.getMessage());
            window.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importWords() throws FileNotFoundException {

        chosenWordList.clear();

        FileInputStream fileInputStream = new FileInputStream("src/main/resources/com/example/javafx/words.txt");
        Scanner filescnr = new Scanner(fileInputStream);

        while (filescnr.hasNextLine()) {
            this.wordlist.add(filescnr.nextLine());
        }

        filescnr.close();
    }

    public void importChars() throws IOException {

        chosenCharList.clear();

        FileInputStream fileInputStreamSafe = new FileInputStream("src/main/resources/com/example/javafx/safechars.txt");
        Scanner filescnrsafe = new Scanner(fileInputStreamSafe);

        while (filescnrsafe.hasNextLine()) {
            this.charlistSafe.add(filescnrsafe.nextLine());
        }

        fileInputStreamSafe.close();

        FileInputStream fileInputStreamRisky = new FileInputStream("src/main/resources/com/example/javafx/riskychars.txt");
        Scanner filescnrrisky = new Scanner(fileInputStreamRisky);

        while (filescnrrisky.hasNextLine()) {
            this.charlistRisky.add(filescnrrisky.nextLine());
        }

        fileInputStreamRisky.close();

    }

    public String wordPassword(int numWords) {
        Random random = new Random();
        finalPass = "";

        chosenCharList.clear();
        chosenWordList.clear();

        int i;
        for (i = 0; i < numWords; ++i) {
            int tempVal = random.nextInt(100);
            String tempWord = this.wordlist.get(tempVal);
            this.chosenWordList.add(tempWord);
        }

        for (i = 0; i < this.chosenWordList.size(); ++i) {
            String tempWord = this.chosenWordList.get(i);
            this.finalPass = tempWord + " " + this.finalPass;
        }

        return finalPass;
    }

    public String charPassword(int numChars, String charChoice) {
        Random random = new Random();
        finalPass = "";

        chosenCharList.clear();
        chosenWordList.clear();

        if (charChoice.equals("Filtered Chars")) {
            int i;
            for (i = 0; i < numChars; ++i) {
                int tempVal = random.nextInt(55);
                String tempChar = this.charlistSafe.get(tempVal);
                this.chosenCharList.add(tempChar);
            }

            for (i = 0; i < this.chosenCharList.size(); ++i) {
                String tempChar = this.chosenCharList.get(i);
                this.finalPass = tempChar + this.finalPass;
            }

        } else {
            int i;
            for (i = 0; i < numChars; ++i) {
                int tempVal = random.nextInt(83);
                String tempChar = this.charlistRisky.get(tempVal);
                this.chosenCharList.add(tempChar);
            }

            for (i = 0; i < this.chosenCharList.size(); ++i) {
                String tempChar = this.chosenCharList.get(i);
                this.finalPass = tempChar + this.finalPass;
            }

        }

        return finalPass;
    }
}
