package dnd.CharCreation.ClassMenus;

import dnd.CharCreation.ClassMenuController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class HitPointsController implements Initializable {

    private dnd.Character character;
    private ArrayList<String> prevWindows;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void nextButton(){
        prevWindows.add("ClassMenus/BarbarianSkillsMenu.fxml");
        
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/ClassMenus/HitPoints.fxml"));
            root = loader.load();
            HitPointsController hitPointsCtrl = loader.getController();
            hitPointsCtrl.setCharacter(character);
            hitPointsCtrl.setPreviousWindows(prevWindows);
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
            ClassMenuController classMenuCtrl = loader.getController();
            character.setHitDice("");
            character.RemoveLastArmorProficiency(); //shields
            character.RemoveLastArmorProficiency(); //medium armor
            character.RemoveLastArmorProficiency(); //light armor
            character.RemoveLastWeaponProficiency(); //martial weapons
            character.RemoveLastWeaponProficiency(); //simple weapons
            character.RemoveSave("Strength");
            character.RemoveSave("Constitution");
            character.RemoveTrait("Rage");
            character.RemoveTrait("Unarmored Defense");
            classMenuCtrl.setCharacter(character);
            classMenuCtrl.setPreviousWindows(prevWindows);
            classMenuCtrl.ReloadScene(character.getclassName());
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
    }
   
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
}
