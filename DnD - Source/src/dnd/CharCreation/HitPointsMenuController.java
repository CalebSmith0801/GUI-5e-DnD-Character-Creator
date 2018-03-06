package dnd.CharCreation;

import dnd.CharCreation.AbilityScoresController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class HitPointsMenuController implements Initializable {

    private dnd.Character character;
    private ArrayList<String> prevWindows;
    private int hitpoints;
    private int maxDiceValue;
    @FXML private Button nextBut;
    @FXML private TextField conModifierTF;
    @FXML private TextField miscBonusTF;
    @FXML private Label hitPointsLabel;
    @FXML private Label hitDiceLabel; //of the form 1d10, for example
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //Make textfield only accept digits
        miscBonusTF.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d+"))
                miscBonusTF.setText(newValue.replaceAll("[^\\d]", ""));
            
            //calculate new hit points when new digit is entered in textfield
            else{
                hitpoints = Integer.parseInt(hitPointsLabel.getText()) + Integer.parseInt(miscBonusTF.getText());
                hitPointsLabel.setText(Integer.toString(hitpoints));
            }
        
            //limits text field to 2 characters
            if (miscBonusTF.getText().length() > 2){
                String s = miscBonusTF.getText().substring(0, 2);
                miscBonusTF.setText(s);
            }          
        });
        
        //limiting the length of textfield causes problems with undo/redo, so I removed the shortcuts
        miscBonusTF.setOnKeyPressed((KeyEvent event) -> {
            if ((event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y) && event.isShortcutDown())
                event.consume();
        });
        
        //make hitpoints never < 1
        hitPointsLabel.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(Integer.parseInt(newValue) < 1)
                hitPointsLabel.setText("1");
        });
        
    }
    
    @FXML 
    private void maxButton(){
        hitpoints = maxDiceValue + Integer.parseInt(conModifierTF.getText());
        hitPointsLabel.setText(Integer.toString(hitpoints));
    }
    
    //According to DnD rules, average is just half the max hit dice + 1
    @FXML 
    private void avgButton(){
        hitpoints = (maxDiceValue / 2) + 1 + Integer.parseInt(conModifierTF.getText());
        hitPointsLabel.setText(Integer.toString(hitpoints));
    }
    
    @FXML 
    private void rollButton(){
        Random rand = new Random();
        hitpoints = (rand.nextInt((maxDiceValue - 1) + 1) + 1) + Integer.parseInt(conModifierTF.getText());
        hitPointsLabel.setText(Integer.toString(hitpoints));
    }
    
    @FXML
    private void nextButton(){
        prevWindows.add("HitPointsMenu.fxml");
        character.setHitPoints(hitpoints);
        character.printCharacter();
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
        setScene();
    }
    
    private void setScene(){
        hitDiceLabel.setText(character.getHitDice());
        conModifierTF.setText(Integer.toString(character.getConMod()));
        
        //removes "1d" in the example of "1d10" to get the max dice roll
        maxDiceValue = Integer.parseInt(hitDiceLabel.getText().split("d")[1]);
        hitPointsLabel.setText(Integer.toString(maxDiceValue + character.getConMod()));
    }
   
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
}
