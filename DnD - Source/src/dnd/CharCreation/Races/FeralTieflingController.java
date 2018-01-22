//============================================================================//
//PREVIOUS WINDOW: raceSelectionMenu                                          //
//Loads when Race = Tiefling and subrace = Feral Tiefling                     //
//                                                                            //
//NEXT WINDOW: ClassMenu                                                      //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Replaces Infernal Legacy Trait with chosen trait or no change            //
//============================================================================//
package dnd.CharCreation.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import dnd.CharCreation.ClassMenuController;
import dnd.CharCreation.RaceSelectionMenuController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class FeralTieflingController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    @FXML private Label DevilsTongueLabel;
    @FXML private Label HellfireLabel;
    @FXML private Label WingedLabel;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private Button spellDescriptions;
    @FXML private ListView<String> lv;
    private Stage SpellStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpellStage = new Stage();
        lv.getSelectionModel().select(0);
        DevilsTongueLabel.setVisible(true);
        spellDescriptions.setVisible(true);
        
        lv.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
            switch(newValue){
                case "Devil's Tongue":
                    DevilsTongueLabel.setVisible(true);
                    spellDescriptions.setVisible(true);
                    break;
                case "Hellfire":
                    HellfireLabel.setVisible(true);
                    spellDescriptions.setVisible(true);
                    break;
                case "Winged":
                    WingedLabel.setVisible(true);
                    spellDescriptions.setVisible(false);
                    break;
                case "None":
                    spellDescriptions.setVisible(false);
                    break;
                default:
                    System.out.println("Mispelling");
                    break;
            }
            
            switch(oldValue){
                case "Devil's Tongue":
                    DevilsTongueLabel.setVisible(false);
                    break;
                case "Hellfire":
                    HellfireLabel.setVisible(false);
                    break;
                case "Winged":
                    WingedLabel.setVisible(false);
                    break;
                default:
                    break;
            }
        });
    }

    @FXML
    private void nextButton(){
        Parent root;
        prevWindows.add("Races/FeralTiefling.fxml");
        SpellStage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/ClassMenu.fxml"));
            root = loader.load();
            ClassMenuController classMenuCtrl = loader.getController();
            switch(lv.getSelectionModel().getSelectedItem()){
                case "Devil's Tongue":
                    character.RemoveTrait("Infernal Legacy");
                    character.RemoveSpell("Thaumaturgy");
                    character.addTrait("Devil's Tongue", "You know the Vicious Mockery cantrip. Charisma is your spellcasting ability for it.");
                    character.addSpell("Vicious Mockery");
                    break;
                case "Hellfire":
                    character.RemoveTrait("Infernal Legacy");
                    character.addTrait("Infernal Legacy (Hellfire)", "You know the Thaumaturgy cantrip. Charisma is your spellcasting ability for it.");
                    break;
                case "Winged":
                    character.RemoveTrait("Infernal Legacy");
                    character.RemoveSpell("Thaumaturgy");
                    character.addTrait("Winged", "You have bat-like wings sprouting from your shoulder blades. You have a flying speed of 30 feet.");
                    break;
                case "None":
                    break;
                default:
                    System.out.println("Misspelling");
                    break;
            }
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

    @FXML
    public void SpellDescriptionButton(){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/Races/SpellDescriptions.fxml"));
            root = loader.load();
            SpellDescriptionsController spellDescripCtrl = loader.getController();
            if (lv.getSelectionModel().getSelectedItem().equals("Devil's Tongue")){
                spellDescripCtrl.setFile("Feral Tiefling Devil's Tongue");
            }
            else{
                spellDescripCtrl.setFile("Burning Hands");
            }
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }
        Scene newScene = new Scene(root);
        SpellStage.setScene(newScene);
        //Stage was stretched a little and had white space to the right of the scrollbar,
        //so i adjusted it accordingly
        SpellStage.setHeight(590);
        SpellStage.setWidth(570);
        SpellStage.setResizable(false);
        SpellStage.show();        
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
    
    //when a back button is pressed that returns to this window, load previous choice(s)
    public void setSceneOnReload(String choice){
        lv.getSelectionModel().select(choice);
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
