//============================================================================//
//PREVIOUS WINDOWS: AbilityRaceBonusMenu                                      //
//                 (loads when subrace = Half-Elf Variant)                    //
//                                                                            //
//NEXT WINDOW: ExtraLanguageMenu                                              //
//             WizardCantripMenu (loads when chosen trait = Wizard Cantrip)   //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Replaces Skill Versatility Trait with either:                            //
//   The Keen Senses trait (adds Perception proficiency)                      //
//   Longsword, Shortsword, Shortbow & Longbow proficiencies                  //
//   Increases Walking speed to 35                                            //
//   Dark Elf Spells                                                          //
//   The Mask of the Wild trait                                               //  
//   A Wizard Cantrip (only loads window, doesn't add spell in this window)   //
//   Increase swimming speed to 35                                            //
//============================================================================//
package dnd.CharCreation.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import dnd.CharCreation.ExtraLanguageMenuController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
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
 * @author Caleb
 */
public class HalfElfVariantController implements Initializable {

    private Character character;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private Button spellDescriptions;
    @FXML private ListView<String> lv;
    @FXML private Label KeenSensesLabel;
    @FXML private Label WeaponTrainingLabel;
    @FXML private Label FleetOfFootLabel;
    @FXML private Label MaskOfTheWildLabel;
    @FXML private Label CantripLabel;
    @FXML private Label DarkElfSpells;
    @FXML private Label SwimmingLabel;
    private ArrayList<String> prevWindows;
    private Stage SpellStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpellStage = new Stage();
        lv.getSelectionModel().select(0);
        KeenSensesLabel.setVisible(true);        
        
        lv.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
            switch(newValue){
                case "Keen Senses":
                    KeenSensesLabel.setVisible(true);
                    break;
                case "Elf Weapon Training":
                    WeaponTrainingLabel.setVisible(true);
                    break;
                case "Fleet of Foot":
                    FleetOfFootLabel.setVisible(true);
                    break;
                case "Dark Elf Spells":
                    DarkElfSpells.setVisible(true);
                    spellDescriptions.setVisible(true);
                    break;
                case "Mask of the Wild":
                    MaskOfTheWildLabel.setVisible(true);
                    break;
                case "Wizard Cantrip":
                    CantripLabel.setVisible(true);
                    break;
                case "Swim Speed":
                    SwimmingLabel.setVisible(true);
                    break;
                default:
                    System.out.println("Mispelling");
                    break;
            }
            
            switch(oldValue){
                case "Keen Senses":
                    KeenSensesLabel.setVisible(false);
                    break;
                case "Elf Weapon Training":
                    WeaponTrainingLabel.setVisible(false);
                    break;
                case "Fleet of Foot":
                    FleetOfFootLabel.setVisible(false);
                    break;
                case "Dark Elf Spells":
                    DarkElfSpells.setVisible(false);
                    spellDescriptions.setVisible(false);
                    break;
                case "Mask of the Wild":
                    MaskOfTheWildLabel.setVisible(false);
                    break;
                case "Wizard Cantrip":
                    CantripLabel.setVisible(false);
                    break;
                case "Swim Speed":
                    SwimmingLabel.setVisible(false);
                    break;
                default:
                    break;
            }
        });
    }

    public void setCharacter(Character c){
        character = new Character(c);
    }
    
    @FXML
    private void nextButton(){
        Parent root;
        prevWindows.add("Races/HalfElfVariant.fxml");
        SpellStage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/ExtraLanguageMenu.fxml"));
            root = loader.load();
            ExtraLanguageMenuController extraLangCtrl = loader.getController();
            switch(lv.getSelectionModel().getSelectedItem()){
                case "Keen Senses":
                    character.addTrait("Keen Senses", "You are proficient in the Perception skill.");
                    character.addSkill("Perception");
                    break;
                case "Elf Weapon Training":
                    character.addWeaponProficiency("Longsword", "Shortsword", "Shortbow", "Longbow");
                    break;
                case "Fleet of Foot":
                    character.setSpeed(35);
                    break;
                case "Dark Elf Spells":
                    character.addTrait("Drow Magic", "You know the Dancing Lights cantrip. Charisma is your spellcasting ability for it.");
                    character.addSpell("Dancing Lights");
                    break;
                case "Mask of the Wild":
                    character.addTrait("Mask of the Wild", "You can attempt to hide even when you are only lightly obscured by foliage,"
                        + " heavy rain, falling snow, mist, and other natural phenomena.");
                    break;
                case "Wizard Cantrip":
                    loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/Races/WizardCantripMenu.fxml"));
                    root = loader.load();
                    WizardCantripMenuController WizardCantripCtrl = loader.getController();
                    WizardCantripCtrl.setPreviousWindows(prevWindows);
                    WizardCantripCtrl.setCharacter(character);
                    break;
                case "Swim Speed":
                    character.addTrait("Swim Speed", "You have a swimming speed of 30 feet.");
                    break;
                default:
                    System.out.println("Misspelling");
                    break;
            }
            extraLangCtrl.setCharacter(character);
            extraLangCtrl.setPreviousWindows(prevWindows);
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
            spellDescripCtrl.setFile("HalfElfVariantSpells");
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
    
    @FXML
    private void backButton(){
        Parent root;
        SpellStage.close();
        String prevWindow = prevWindows.get(prevWindows.size()-1);
        //Remove last element because when returning to previous window, the second to last
        //element in the array is now the current previous window
        prevWindows.remove(prevWindow);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/"+prevWindow));
            root = loader.load();
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
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }
    }
    
    //Given relative file path from the Data folder
    //returns html of that specific file
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
    
    //when a back button is pressed that returns to this window, load previous choice(s)
    public void setSceneOnReload(String choice){
        lv.getSelectionModel().select(choice);
    }
    
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
}
