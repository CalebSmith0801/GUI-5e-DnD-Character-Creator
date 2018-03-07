/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.CharCreation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class BackgroundMenuController implements Initializable {

    private dnd.Character character;
    private ArrayList<String> prevWindows;
    @FXML private Button nextBut;
    @FXML private ComboBox<String> backgroundBox;
    @FXML private Label backgroundName;
    @FXML private Label knownSkillsLabel;
    @FXML private TextArea additionalNotes;
    @FXML private WebView backgroundInfoWebView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Sets scene on load to display the acolyte background by default
        backgroundBox.getSelectionModel().select(0);
        backgroundName.setText(backgroundBox.getValue());
        SetupBackgroundInfoWebView(backgroundBox.getValue());
        
        backgroundBox.valueProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
            SetupBackgroundInfoWebView(newValue);
            backgroundName.setText(newValue);
        });
    }
    
    public void SetupBackgroundInfoWebView(String chosenBackGround){
         backgroundInfoWebView.getEngine().loadContent(readDataFile("BackgroundInfo/"+ chosenBackGround));
         //Apply CSS to HTML Accordions
         backgroundInfoWebView.getEngine().setUserStyleSheetLocation(getClass().getResource("/dnd/CSS/Accordion.css").toString());
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

    @FXML
    private void nextButton(){
        prevWindows.add("HitPointsMenu.fxml");
        
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/BackgroundMenu.fxml"));
            root = loader.load();
            BackgroundMenuController backgroundCtrl = loader.getController();
            backgroundCtrl.setCharacter(character);
            backgroundCtrl.setPreviousWindows(prevWindows);
            switchScene(root);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    private void backButton(){
        Parent root;
        try{
            String prevWindow = prevWindows.get(prevWindows.size()-1);
            //Remove last element because when returning to previous window, the second to last
            //element in the array is now the current previous window
            prevWindows.remove(prevWindow);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/"+prevWindow));
            root = loader.load();
            AbilityScoresController abilityScoresCtrl = loader.getController();
            
            //reverse actions of previous screen
            //left modifiers untouched because they are never used/accessed and will be reset
            //when user hits next on that screen
            character.setStrScore(0);
            character.setDexScore(0);
            character.setConScore(0);
            character.setIntScore(0);
            character.setWisScore(0);
            character.setCharScore(0);            
            abilityScoresCtrl.setCharacter(character);
            abilityScoresCtrl.setPreviousWindows(prevWindows);
            switchScene(root);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void switchScene(Parent newRoot){
        Stage stage;
        stage = (Stage) nextBut.getScene().getWindow();
        double oldX = stage.getX(); 
        double oldY = stage.getY();
        double oldHeight = stage.getHeight();
        double oldWidth = stage.getWidth();
        stage.close();
        Scene newScene = new Scene(newRoot);
        stage.setScene(newScene);
        
        //Adjusts new stage position so that it is centered relative to
        //the previous stage
        stage.setOnShown((WindowEvent w) -> {
            if (stage.getWidth() > oldWidth)
                stage.setX(oldX - ((stage.getWidth() / 2.0) - (oldWidth / 2.0)));
            else
                stage.setX(oldX + (oldWidth / 2.0) - (stage.getWidth() / 2.0));
            if (stage.getHeight() > oldHeight)
                stage.setY(oldY - ((stage.getHeight() / 2.0) - (oldHeight / 2.0)));
            else
                stage.setY(oldY + (oldHeight / 2.0) - (stage.getHeight() / 2.0));
        });
        stage.show();
    }


    public void setCharacter(dnd.Character r){
        character = new dnd.Character(r);
        setSkills();
    }
    
    
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }

    private void setSkills(){
        Set<String> skills = new HashSet<>(character.getAllSkills());
        ArrayList<String> noDuplicates = new ArrayList<>(skills);
        String skillsStr = noDuplicates.get(0);
        for (int i = 1; i < noDuplicates.size(); i++){
            skillsStr += (", " + noDuplicates.get(i)); 
        }
        knownSkillsLabel.setText(skillsStr);
    }
    
}
