//============================================================================//
//PREVIOUS WINDOW: ClassMenu                                                  //
//Loads when Class = Barbarian                                                //
//                                                                            //
//NEXT WINDOW: AbilityScores                                                  //
//                                                                            //
//Changes to Character in this Window:                                        //
//---gain two skills                                                          //
//============================================================================//
package dnd.CharCreation.ClassMenus;

import dnd.CharCreation.AbilityScoresController;
import dnd.CharCreation.ClassMenuController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class BarbarianSkillsMenuController implements Initializable {

    private dnd.Character character;
    private ArrayList<String> prevWindows;
    private ArrayList<CheckBox> selectedCB = new ArrayList<>();
    private ArrayList<CheckBox> unselectedCB;
    private ArrayList<CheckBox> knownSkills = new ArrayList<>();
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private CheckBox animalCB;
    @FXML private CheckBox athleticsCB;
    @FXML private CheckBox intimidationCB;
    @FXML private CheckBox natureCB;
    @FXML private CheckBox perceptionCB;
    @FXML private CheckBox survivalCB;
    private int choices = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        unselectedCB = new ArrayList<>(Arrays.asList(animalCB, athleticsCB, intimidationCB, natureCB, perceptionCB, survivalCB));
        unselectedCB.forEach((checkbox) -> {
            checkbox.selectedProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.equals(true)){
                selectedCB.add(checkbox);
                unselectedCB.remove(checkbox);
                choices++;
            }
            else{
                selectedCB.remove(checkbox);
                unselectedCB.add(checkbox);
                choices--;
            }
            if (choices == 2){
                unselectedCB.forEach((cb) -> {
                    cb.setDisable(true);
                });
                nextBut.setDisable(false);
            }
            else{
                unselectedCB.forEach((cb) -> {
                    if (!knownSkills.contains(cb))
                        cb.setDisable(false);
                });
                nextBut.setDisable(true);
            }
            });
        });
    }
    
    @FXML
    private void nextButton(){
        prevWindows.add("ClassMenus/BarbarianSkillsMenu.fxml");
        selectedCB.forEach((cb) -> {
            character.addSkill(cb.getText());
        });
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/AbilityScores.fxml"));
            root = loader.load();
            AbilityScoresController hitPointsCtrl = loader.getController();
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
        //Disables checkboxes for known skills
        for(int i = 0; i < unselectedCB.size(); i++){
            if(r.getAllSkills().contains(unselectedCB.get(i).getText())){
                knownSkills.add(unselectedCB.get(i));
                unselectedCB.get(i).setDisable(true);
            }
                
        }
    }
   
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    //when a back button is pressed that returns to this window, load previous choice(s)
    public void setCheckBoxOnSceneReload(String skill){
        switch(skill){
            case "Animal Handling":
                animalCB.setSelected(true);
                break;
            case "Intimidation":
                intimidationCB.setSelected(true);
                break;
            case "Perception":
                perceptionCB.setSelected(true);
                break;
            case "Athletics":
                athleticsCB.setSelected(true);
                break;
            case "Nature":
                natureCB.setSelected(true);
                break;
            case "Survival":
                survivalCB.setSelected(true);
                break;
            default:
                System.out.println("Misspelling");
                break;
        }
    }
    
}
