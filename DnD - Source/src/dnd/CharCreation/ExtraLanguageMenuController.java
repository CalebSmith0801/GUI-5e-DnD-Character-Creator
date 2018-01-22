//============================================================================//
//PREVIOUS WINDOWS: WizardCantripMenu                                         //
//                  AbilityRaceBonusMenu                                      //
//                  raceSelectionMenu                                         //
//                                                                            //
//NEXT WINDOW: ClassMenu                                                      //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Adds a language                                                          //
//============================================================================//
package dnd.CharCreation;

import dnd.Character;
import dnd.CharCreation.Races.WizardCantripMenuController;
import dnd.CharCreation.Races.AbilityRaceBonusMenuController;
import dnd.CharCreation.Races.HalfElfVariantController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class ExtraLanguageMenuController implements Initializable {
    private Character character;
    @FXML private ToggleGroup tg;
    @FXML private Button nextBut;
    @FXML private ArrayList<RadioButton> rbList;
    private ArrayList<String> Languages;
    private ArrayList<String> prevWindows;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                nextBut.setDisable(false);
            }
        });        
    }    
    
   public void setCharacter(Character c){
        character = new Character(c);
        //Disables radiobuttons for known languages
        for(int i = 0; i < rbList.size(); i++){
            if(c.getAllLanguages().contains(rbList.get(i).getText()))
                rbList.get(i).setDisable(true);
        }
    }
   
   //Needed so back button knows which window to transition to since the ExtraLanguageMenu
   //is used in multiple combinations
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    //when a back button is pressed that returns to this window, load previous choice(s)
    public void setSceneOnReload(String chosenLang){
        for(int i = 0; i < rbList.size(); i++){
            if (rbList.get(i).getText().equals(chosenLang)){
                int rbInToggleGroup = tg.getToggles().indexOf(rbList.get(i));
                tg.getToggles().get(rbInToggleGroup).setSelected(true);
                break;
            }
        }
    }

   
   /*Returns to previous window
   If Elf window -> will reload the Elf cantrip selection window with the cantrip they chose.
                    Will then remove the spell the user chosen from the character object from the previous window, so they can choose a different one
   If RaceSelectionMenu -> will reload the race selection scene with just the user's chosen race and subrace, so they can make changes. 
                           This will delete all data of the character object since the race selection is the first step of character creation   
   If HalfElf window -> will reload the HalfElf window by determining what the chosen abilites were and setting those checkbox'es to be checked on load.
                        Will then remove those chosen abiltity bonuses from the character object, so the user can change their choice.
*/
   @FXML
    private void backButton(ActionEvent event){
        Parent root;
        try{
            String prevWindow = prevWindows.get(prevWindows.size()-1);
            //Remove last element because when returning to previous window, the second to last
            //element in the array is now the current previous window
            prevWindows.remove(prevWindow);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/"+prevWindow));
            root = loader.load();
            switch(prevWindow){                
                case "Races/WizardCantripMenu.fxml":                    
                    WizardCantripMenuController WizardCantripCtrl = loader.getController();
                    WizardCantripCtrl.setCantrip(character.getLastSpell());
                    character.RemoveLastSpell();
                    WizardCantripCtrl.setCharacter(character);
                    WizardCantripCtrl.setPreviousWindows(prevWindows);
                    break;
                case "raceSelectionMenu.fxml":
                    RaceSelectionMenuController raceSelCtrl = loader.getController();
                    raceSelCtrl.ReloadScene(character.getraceName(), character.getsubraceName());
                    break;
                case "Races/AbilityRaceBonusMenu.fxml":
                    AbilityRaceBonusMenuController raceBonusCtrl = loader.getController();
                    if(character.getStrRaceBonus() == 1)
                        raceBonusCtrl.setCheckBoxOnSceneReload("Strength");
                    if(character.getDexRaceBonus() == 1)
                        raceBonusCtrl.setCheckBoxOnSceneReload("Dexterity");
                    if(character.getConRaceBonus() == 1)
                        raceBonusCtrl.setCheckBoxOnSceneReload("Constitution");
                    if(character.getIntRaceBonus() == 1)
                        raceBonusCtrl.setCheckBoxOnSceneReload("Intelligence");
                    if(character.getWisRaceBonus() == 1)
                        raceBonusCtrl.setCheckBoxOnSceneReload("Wisdom");
                    
                    //Remove the two chosen race bonuses from previous window
                    character.RemoveAllRaceBonuses();
                    character.setCharRaceBonus(2); //set to 1 since half elf's charRaceBonus is always 2
                    raceBonusCtrl.setCharacter(character);
                    raceBonusCtrl.setPreviousWindows(prevWindows);
                    break;
                case "Races/HalfElfVariant.fxml":
                    HalfElfVariantController halfElfVarCtrl = loader.getController();
                    if (character.hasProficiency("Perception")){
                        character.RemoveProficiency("Perception");
                        character.RemoveTrait("Keen Senses");
                        halfElfVarCtrl.setSceneOnReload("Keen Senses");
                    }
                    else if(character.hasProficiency("Longsword")){
                        character.RemoveProficiency("Longsword", "Shortsword", "Shortbow", "Longbow");
                        halfElfVarCtrl.setSceneOnReload("Elf Weapon Training");
                    }
                    else if(character.getSpeed() == 35){
                        character.setSpeed(30);
                        halfElfVarCtrl.setSceneOnReload("Fleet of Foot");
                    }
                    else if(character.hasTrait("Drow Magic")){
                        character.RemoveTrait("Drow Magic");
                        character.RemoveSpell("Dancing Lights");
                        halfElfVarCtrl.setSceneOnReload("Dark Elf Spells");
                    }
                    else if(character.hasTrait("Mask of the Wild")){
                        character.RemoveTrait("Mask of the Wild");
                        halfElfVarCtrl.setSceneOnReload("Mask of the Wild");
                    }
                    else{
                        character.RemoveTrait("Swim Speed");
                        halfElfVarCtrl.setSceneOnReload("Swim Speed");
                    }
                    halfElfVarCtrl.setCharacter(character);
                    halfElfVarCtrl.setPreviousWindows(prevWindows);
                    break;
                default:
                    break;
            }
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }
    }
    
    @FXML
    private void nextButton(ActionEvent event){
        RadioButton SelectedRB = (RadioButton) tg.getSelectedToggle();
        character.addLanguage(SelectedRB.getText());
        prevWindows.add("ExtraLanguageMenu.fxml");
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
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
}
