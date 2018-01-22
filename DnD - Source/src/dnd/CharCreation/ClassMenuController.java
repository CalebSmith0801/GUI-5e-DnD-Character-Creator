package dnd.CharCreation;

import dnd.CharCreation.Races.DwarfToolProficiencyMenuController;
import dnd.CharCreation.Races.KenkuController;
import dnd.CharCreation.Races.LizardfolkController;
import dnd.Character;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class ClassMenuController implements Initializable {
    private Character character;
    private ArrayList<String> prevWindows;
    @FXML private ComboBox<String> classBox;
    @FXML private ComboBox<String> subclassBox;
    @FXML private Label className;
    @FXML private Label subLabel;
    @FXML private WebView wv;
    @FXML private ImageView classPic;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Sets scene on load to display the barbarian race by default
        classBox.getSelectionModel().select(0);
        className.setText(classBox.getValue());
        SetupClassInfoWebView(classBox.getValue());
        classPicDisplay();
        
        classBox.valueProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
            SetupClassInfoWebView(newValue);
            className.setText(newValue);
            classPicDisplay();
        });
    }
    
    public void SetupClassInfoWebView(String chosenClass){
         wv.getEngine().loadContent(readDataFile("ClassInfo/"+ chosenClass));
         //Apply CSS to HTML Accordions
         wv.getEngine().setUserStyleSheetLocation(getClass().getResource("/dnd/CSS/Accordion.css").toString());
    }
    
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

    public void classPicDisplay(){
        String img = "file:Data/ClassInfo/" + className.getText().trim() + ".png";
        Image image = new Image(img.trim());
        classPic.setImage(image);              
    }
    
    public void setCharacter(Character r){
        character = new Character(r);
    }
   
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
    
    @FXML
    private void nextButton(ActionEvent event){
    
    }
    
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
            ArrayList<String> chosenSkills;
            switch(prevWindow){                
                case "Races/DwarfToolProficiencyMenu.fxml":                    
                    DwarfToolProficiencyMenuController dwarfCtrl = loader.getController();
                    String chosenToolProf = character.getProficiency(character.getProficienciesSize()-1);
                    character.RemoveProficiency(chosenToolProf);
                    dwarfCtrl.setSceneOnReload(chosenToolProf);
                    dwarfCtrl.setCharacter(character);
                    dwarfCtrl.setPreviousWindows(prevWindows);
                    break;
                case "raceSelectionMenu.fxml":
                    RaceSelectionMenuController raceSelCtrl = loader.getController();
                    raceSelCtrl.ReloadScene(character.getraceName(), character.getsubraceName());
                    break;
                case "ExtraLanguageMenu.fxml":
                    ExtraLanguageMenuController extraLangCtrl = loader.getController();
                    String chosenLanguage = character.getLanguage(character.getLanguagesSize()-1);
                    character.RemoveLanuguage(chosenLanguage);
                    extraLangCtrl.setSceneOnReload(chosenLanguage);               
                    extraLangCtrl.setCharacter(character);
                    extraLangCtrl.setPreviousWindows(prevWindows);
                    break;
                case "Races/Kenku.fxml":
                    KenkuController kenkuCtrl = loader.getController();
                    chosenSkills = new ArrayList<>();
                    if (character.hasProficiency("Acrobatics"))
                        chosenSkills.add("Acrobatics");
                    if (character.hasProficiency("Deception"))
                        chosenSkills.add("Deception");
                    if (character.hasProficiency("Stealth"))
                        chosenSkills.add("Stealth");
                    if (character.hasProficiency("Sleight of Hand"))
                        chosenSkills.add("Sleight of Hand");
                    chosenSkills.forEach((skill) -> {
                        character.RemoveProficiency(skill);
                    });
                    kenkuCtrl.setSceneOnReload(chosenSkills);
                    kenkuCtrl.setCharacter(character);
                    kenkuCtrl.setPreviousWindows(prevWindows);
                    break;
                case "Races/Lizardfolk.fxml":
                    LizardfolkController lizCtrl = loader.getController();
                    chosenSkills = new ArrayList<>();
                    if (character.hasProficiency("Animal Handling"))
                        chosenSkills.add("Animal Handling");
                    if (character.hasProficiency("Nature"))
                        chosenSkills.add("Nature");
                    if (character.hasProficiency("Stealth"))
                        chosenSkills.add("Stealth");
                    if (character.hasProficiency("Perception"))
                        chosenSkills.add("Perception");
                    if (character.hasProficiency("Survival"))
                        chosenSkills.add("Survival");
                    chosenSkills.forEach((skill) -> {
                        character.RemoveProficiency(skill);
                    });
                    lizCtrl.setSceneOnReload(chosenSkills);
                    lizCtrl.setCharacter(character);
                    lizCtrl.setPreviousWindows(prevWindows);
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
}
