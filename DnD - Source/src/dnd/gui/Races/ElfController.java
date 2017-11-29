/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.gui.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author calebs
 */
public class ElfController implements Initializable {

    private Character character;
    @FXML Button nextBut;
    @FXML Button backBut;
    @FXML WebView wv;
    @FXML ListView<String> lv;
    @FXML Label spellName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set first element of list view to be default and display spell name/info accordingly
        lv.getSelectionModel().select(0);
        spellName.setText(lv.getSelectionModel().getSelectedItem());
        wv.getEngine().loadContent(readDataFile("Spells/"+lv.getSelectionModel().getSelectedItem()));

        lv.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
            wv.getEngine().loadContent(readDataFile("Spells/"+newValue));
            spellName.setText(newValue);
        });
    }    
    
    
    public String readDataFile(String Name){
        String line = "";
        try {
            Scanner scanner = new Scanner(new FileInputStream("Data/"+Name+".html"));
            
            while (scanner.hasNextLine()){
                line += scanner.nextLine();
            }
                
           
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return line;
    }
    
    public void setCharacter(dnd.Character c){
        character = new Character(c);
    }
    
    public void nextButton(){
        Stage stage;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage = (Stage) nextBut.getScene().getWindow();
        stage.close();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/dnd/gui/raceSelectionMenu.fxml"));
        try{
        Parent root = (Parent) loader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }
        //stage.setX((screenBounds.getWidth() - w) / 2); 
        //stage.setY((screenBounds.getHeight() - h) / 2);
        stage.show();
    }
    
    public void backButton(){
        Stage stage;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage = (Stage) nextBut.getScene().getWindow();
        stage.close();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/dnd/gui/raceSelectionMenu.fxml"));
        try{
        Parent root = (Parent) loader.load();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }
        //stage.setX((screenBounds.getWidth() - w) / 2); 
        //stage.setY((screenBounds.getHeight() - h) / 2);
        stage.show();
    }
}
