//============================================================================//
//PREVIOUS WINDOW: raceSelectionMenu (Loads when Race = Half-Elf)             //
//                 HalfElfVariant (Loads when chosen trait = Wizard Cantrip   //
//                                                                            //
//NEXT WINDOW: extraLanguageMenu                                              //
//                                                                            //
//Changes to Character in this Window:                                        //
//---Add chosen Wizard Cantrip to Spells								      //
//---Add trait (Cantrip)		                                    		  //
//============================================================================//
package dnd.gui.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import dnd.gui.ExtraLanguageMenuController;
import dnd.gui.RaceSelectionMenuController;
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
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * FXML Controller class
 *
 * @author calebs
 */
public class WizardCantripMenuController implements Initializable {

    private Character character;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private WebView wv;
    @FXML private ListView<String> lv;
    @FXML private Label spellName;
    private ArrayList<String> prevWindows;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set first element of list view to be default and display spell name/info accordingly
        lv.getSelectionModel().select(0);
        spellName.setText(lv.getSelectionModel().getSelectedItem());
        wv.getEngine().loadContent(readDataFile("Spells/"+lv.getSelectionModel().getSelectedItem()));

        //Displays Corresponding spell based on selection from list view
        lv.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
            wv.getEngine().loadContent(readDataFile("Spells/"+newValue));
            spellName.setText(newValue);
        });
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
    
    public void setCharacter(Character c){
        character = new Character(c);
    }
    
    public void setCantrip(String spell){
        lv.getSelectionModel().select(spell);
        spellName.setText(lv.getSelectionModel().getSelectedItem());
        wv.getEngine().loadContent(readDataFile("Spells/"+lv.getSelectionModel().getSelectedItem()));
    }
    
    @FXML
    public void nextButton(){
        character.addSpell(lv.getSelectionModel().getSelectedItem());
		character.addTrait("Cantrip", "You know the " + lv.getSelectionModel().getSelectedItem() + " cantrip from the wizard spell list. Intelligence is your spellcasting ability for it.");
        prevWindows.add("Races/WizardCantripMenu.fxml");
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/gui/ExtraLanguageMenu.fxml"));
            root = loader.load();
            ExtraLanguageMenuController extraLangMenuCtrl = loader.getController();
            extraLangMenuCtrl.setCharacter(character);
            extraLangMenuCtrl.setPreviousWindows(prevWindows);
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }
    }
    
    @FXML
    public void backButton(){
        Parent root;
        try{
            String prevWindow = prevWindows.get(prevWindows.size()-1);
            //Remove last element because when returning to previous window, the second to last
            //element in the array is now the current previous window
            prevWindows.remove(prevWindow);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/gui/"+prevWindow));
            root = loader.load();
            switch(prevWindow){                
                case "raceSelectionMenu.fxml":
                    RaceSelectionMenuController raceSelCtrl = loader.getController();
                    raceSelCtrl.ReloadScene(character.getraceName(), character.getsubraceName());
                    break;
                case "Races/HalfElfVariant.fxml":
                    HalfElfVariantController HalfElfVarCtrl = loader.getController();
                    HalfElfVarCtrl.setCharacter(character);
                    HalfElfVarCtrl.setSceneOnReload("Wizard Cantrip");
                    HalfElfVarCtrl.setPreviousWindows(prevWindows);
                    break;
            }
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
    
   //Needed so back button knows which window to transition to since the Elf window
   //can be loaded from the raceSelectionMenu or the HalfElfVariant windo
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
}
