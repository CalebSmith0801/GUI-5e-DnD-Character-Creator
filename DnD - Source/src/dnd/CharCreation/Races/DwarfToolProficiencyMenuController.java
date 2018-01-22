//============================================================================//
//PREVIOUS WINDOW: raceSelectionMenu                                          //
//Loads when Race = Dwarf                                                     //
//                                                                            //
//NEXT WINDOW: ClassMenu                                                      //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Add proficiency                                                          //
//============================================================================//
package dnd.CharCreation.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import dnd.Character;
import dnd.CharCreation.ClassMenuController;
import dnd.CharCreation.RaceSelectionMenuController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class DwarfToolProficiencyMenuController implements Initializable {
    @FXML private AnchorPane dwarf;
    @FXML private Button dwarfNextBut;
    @FXML private Button dwarfBackBut;
    @FXML private ToggleGroup choices;
    private Character character;
    private ArrayList<String> prevWindows;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choices.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                dwarfNextBut.setDisable(false);
            }
        });

    }    
    
    @FXML
    private void dwarfBack(ActionEvent event){
        Parent root;
        String prevWindow = prevWindows.get(prevWindows.size()-1);
        //Remove last element because when returning to previous window, the second to last
        //element in the array is now the current previous window
        prevWindows.remove(prevWindow);
        try{
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
    
    @FXML
    private void dwarfNext(ActionEvent event){
        RadioButton SelectedRB = (RadioButton) choices.getSelectedToggle();
        String[] selection = SelectedRB.getText().split("-"); //Example text of RadioButton text is 'Smith's tools - (Metalwork)'
        character.addTrait("Tool Proficiency", "You gain proficiency with " + selection[0].trim() +
                      "\nLet's you add your proficiency to any ability checks you make using the those tools.");
        character.addProficiency(selection[0].trim());
        prevWindows.add("Races/DwarfToolProficiencyMenu.fxml");

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
    
    public void switchScene(Parent newRoot){
        Stage stage;
        stage = (Stage) dwarfNextBut.getScene().getWindow();
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
    
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    //when a back button is pressed that returns to this window, load previous choice(s)
    public void setSceneOnReload(String chosenProf){
        switch (chosenProf){
            case "Smith's Tools":
                choices.getToggles().get(0).setSelected(true);
                break;
            case "Brewer's Supplies":
                choices.getToggles().get(1).setSelected(true);
                break;
            case "Mason's Tools":
                choices.getToggles().get(2).setSelected(true);
                break;
            default:
                break;
        }
    }
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
    
}
