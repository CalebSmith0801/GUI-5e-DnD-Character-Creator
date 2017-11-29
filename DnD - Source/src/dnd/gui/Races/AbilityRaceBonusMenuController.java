//============================================================================//
//PREVIOUS WINDOW: raceSelectionMenu                                          //
//Loads when Race = Half-Elf or Human when subrace is human variant           //
//                                                                            //
//NEXT WINDOW:                                                                //
//  if subrace = half-elf or Human Variant -> ExtraLanguageMenu               //
//  if subrace = half-elf variant ->  HalfElfVariant                          //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Two ability raceBonus's become +1                                        //
//============================================================================//
package dnd.gui.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import dnd.gui.ExtraLanguageMenuController;
import dnd.gui.RaceSelectionMenuController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class AbilityRaceBonusMenuController implements Initializable {

    private Character character;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private CheckBox strCB;
    @FXML private CheckBox dexCB;
    @FXML private CheckBox conCB;
    @FXML private CheckBox intCB;
    @FXML private CheckBox wisCB;
    @FXML private CheckBox chaCB;
    @FXML private Label halfElflabel;
    private ArrayList<CheckBox> selectedCB = new ArrayList<>();
    private ArrayList<CheckBox> unselectedCB;
    private ArrayList<String> prevWindows;
    private int choices = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AddListener(strCB);
        AddListener(dexCB);
        AddListener(conCB);
        AddListener(intCB);
        AddListener(wisCB);
        AddListener(chaCB);
        unselectedCB = new ArrayList<>(Arrays.asList(strCB, dexCB, conCB, intCB, wisCB, chaCB));
    }
    
    private void AddListener(CheckBox cb){
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
    
    public void setCharacter(Character c){
        character = new Character(c);
        //Changes a few elements of scene if race is human or half-elf
        if (c.getraceName().equals("Human")){
            chaCB.setVisible(true);
            halfElflabel.setVisible(false);
        }
    }
    
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }

    
    public void nextButton(){
        Parent root;
        prevWindows.add("Races/AbilityRaceBonusMenu.fxml");
        for(int i = 0; i < selectedCB.size(); i++){
            switch(selectedCB.get(i).getText()){
                case "Strength":
                    character.setStrRaceBonus(1);
                    break;
                case "Dexterity":
                    character.setDexRaceBonus(1);
                    break;
                case "Constitution":
                    character.setConRaceBonus(1);
                    break;
                case "Intelligence":
                    character.setIntRaceBonus(1);
                    break;
                case "Wisdom":
                    character.setWisRaceBonus(1);
                    break;
                case "Charisma":
                    character.setCharRaceBonus(1);
                    break;
                default:
                    System.out.println("Misspelling");
                    break;
            }
        }
        try{
            if (character.getsubraceName().equals("Half-Elf Variant")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/gui/Races/HalfElfVariant.fxml"));
                root = loader.load();
                HalfElfVariantController HalfElfVarCtrl = loader.getController();
                HalfElfVarCtrl.setCharacter(character);
                HalfElfVarCtrl.setPreviousWindows(prevWindows);
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/gui/ExtraLanguageMenu.fxml"));
                root = loader.load();
                ExtraLanguageMenuController extraLangMenuCtrl = loader.getController();
                extraLangMenuCtrl.setCharacter(character);
                extraLangMenuCtrl.setPreviousWindows(prevWindows);
            }
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }
    }
    
    public void backButton(){
        Parent root;
        String prevWindow = prevWindows.get(prevWindows.size()-1);
        //Remove last element because when returning to previous window, the second to last
        //element in the array is now the current previous window
        prevWindows.remove(prevWindow);

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/gui/"+prevWindow));
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
    
    public void setCheckBoxOnSceneReload(String bonus){
        switch(bonus){
            case "Strength":
                strCB.selectedProperty().set(true);
                break;
            case "Dexterity":
                dexCB.selectedProperty().set(true);
                break;
            case "Constitution":
                conCB.selectedProperty().set(true);
                break;
            case "Intelligence":
                intCB.selectedProperty().set(true);
                break;
            case "Wisdom":
                wisCB.selectedProperty().set(true);
                break;
            default:
                System.out.println("Misspelling");
                break;
        }
    }
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
}
