//============================================================================//
//PREVIOUS WINDOWS: raceSelectionMenu                                         //
//                 (loads when race = Hobgoblin)                              //
//                                                                            //
//NEXT WINDOW: ClassMenu                                                      //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Add two Martial weapon proficiencies                                     //
//============================================================================//
package dnd.CharCreation.RaceMenus;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import dnd.CharCreation.ClassMenuController;
import dnd.CharCreation.RaceSelectionMenuController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class MartialWeaponProfMenuController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    private final ArrayList<CheckBox> selectedCB = new ArrayList<>();
    private ArrayList<CheckBox> unselectedCB;
    private int choices = 0;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML ArrayList<CheckBox> cblist;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AddListener(cblist);
        unselectedCB = new ArrayList<>(cblist);
    }

    private void AddListener(ArrayList<CheckBox> list){
        for(CheckBox cb : list){
            cb.selectedProperty().addListener((observable, oldValue, newValue) ->{
            if (newValue.equals(true)){
                selectedCB.add(cb);
                unselectedCB.remove(cb);
                choices++;
            }
            else{
                selectedCB.remove(cb);
                unselectedCB.add(cb);
                choices--;
            }
            if (choices == 2){
                unselectedCB.forEach((checkbox) -> {
                    checkbox.setDisable(true);
                });
                nextBut.setDisable(false);
            }
            else{
                unselectedCB.forEach((checkbox) -> {
                    checkbox.setDisable(false);
                });
                nextBut.setDisable(true);
            }
        });
        }
    }
    
    @FXML
    private void nextButton(){
        prevWindows.add("RaceMenus/MartialWeaponProfMenu.fxml");
        selectedCB.forEach((checkbox) -> {
            character.addWeaponProficiency(checkbox.getText());
        });
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/ClassMenu.fxml"));
            root = loader.load();
            ClassMenuController classMenuCtrl = loader.getController();
            classMenuCtrl.setCharacter(character);
            classMenuCtrl.setPreviousWindows(prevWindows);
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
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
            RaceSelectionMenuController raceSelCtrl = loader.getController();
            raceSelCtrl.ReloadScene(character.getraceName(), character.getsubraceName());
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
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
    
    public void setCharacter(Character c){
        character = new Character(c);
    }
    
    public void setSceneOnReload(ArrayList<String> prevWeaponChoices){
        cblist.forEach((CheckBox checkbox) -> {
            prevWeaponChoices.forEach((String weapon) -> { 
                if (checkbox.getText().equals(weapon))
                    checkbox.setSelected(true);
            });
        });
    }
    
    //Needed so back button knows which window to transition to since the ExtraLanguageMenu
    //is used in multiple combinations
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
}
