package dnd.CharCreation;

import dnd.CharCreation.RaceMenus.ExtraLanguageMenuController;
import dnd.CharCreation.ClassMenus.BarbarianSkillsMenuController;
import dnd.CharCreation.RaceMenus.DwarfToolProficiencyMenuController;
import dnd.CharCreation.RaceMenus.FeralTieflingController;
import dnd.CharCreation.RaceMenus.KenkuController;
import dnd.CharCreation.RaceMenus.LizardfolkController;
import dnd.CharCreation.RaceMenus.MartialWeaponProfMenuController;
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
        prevWindows.add("ClassMenu.fxml");
        character.setclassName(classBox.getValue());
        /*if(!subraceBox.isDisabled())
            character.setsubraceName(subraceBox.getValue());*/
            
        try{
            FXMLLoader loader;// = new FXMLLoader(getClass().getResource("ClassMenu.fxml"));
            Parent root = null;// = loader.load();
            //ClassMenuController classMenuCtrl = loader.getController();*/
            switch(classBox.getValue()){
                case "Barbarian":
                    setBarbarianTraits();
                    loader = new FXMLLoader(getClass().getResource("ClassMenus/BarbarianSkillsMenu.fxml"));
                    root = loader.load();
                    BarbarianSkillsMenuController barbSkillCtrl = loader.getController();
                    barbSkillCtrl.setCharacter(character);
                    barbSkillCtrl.setPreviousWindows(prevWindows);
                    break;
                default:
                    break;
            }
            /*classMenuCtrl.setCharacter(character);
            classMenuCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));*/
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }    
    }
    
    //When going back you must reverse the actions of the previous window which can sometimes
    //mean adding something back that was removed
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
                case "RaceMenus/DwarfToolProficiencyMenu.fxml":                    
                    DwarfToolProficiencyMenuController dwarfCtrl = loader.getController();
                    String chosenToolProf = character.getToolProficiency(character.getToolProficienciesSize()-1);
                    character.RemoveToolProficiency(chosenToolProf);
                    dwarfCtrl.setSceneOnReload(chosenToolProf);
                    dwarfCtrl.setCharacter(character);
                    dwarfCtrl.setPreviousWindows(prevWindows);
                    break;
                case "raceSelectionMenu.fxml":
                    RaceSelectionMenuController raceSelCtrl = loader.getController();
                    raceSelCtrl.ReloadScene(character.getraceName(), character.getsubraceName());
                    break;
                case "RaceMenus/ExtraLanguageMenu.fxml":
                    ExtraLanguageMenuController extraLangCtrl = loader.getController();
                    String chosenLanguage = character.getLanguage(character.getLanguagesSize()-1);
                    character.RemoveLanuguage(chosenLanguage);
                    extraLangCtrl.setSceneOnReload(chosenLanguage);               
                    extraLangCtrl.setCharacter(character);
                    extraLangCtrl.setPreviousWindows(prevWindows);
                    break;
                case "RaceMenus/Kenku.fxml":
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
                        character.RemoveSkill(skill);
                    });
                    kenkuCtrl.setSceneOnReload(chosenSkills);
                    kenkuCtrl.setCharacter(character);
                    kenkuCtrl.setPreviousWindows(prevWindows);
                    break;
                case "RaceMenus/Lizardfolk.fxml":
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
                        character.RemoveSkill(skill);
                    });
                    lizCtrl.setSceneOnReload(chosenSkills);
                    lizCtrl.setCharacter(character);
                    lizCtrl.setPreviousWindows(prevWindows);
                    break;
                case "RaceMenus/FeralTiefling.fxml":
                    FeralTieflingController ferTiefCtrl = loader.getController();
                    if (character.hasTrait("Devil's Tongue")){
                        character.RemoveTrait("Devil's Tongue");
                        character.RemoveSpell("Vicious Mockery");
                        character.addTrait("Infernal Legacy", "You know the Thaumaturgy cantrip. Charisma is your spellcasting ability for it");
                        character.addSpell("Thaumaturgy");
                        ferTiefCtrl.setSceneOnReload("Devil's Tongue");
                    }
                    else if (character.hasTrait("Infernal Legacy (Hellfire)")){
                        character.RemoveTrait("Infernal Legacy (Hellfire)");
                        character.addTrait("Infernal Legacy", "You know the Thaumaturgy cantrip. Charisma is your spellcasting ability for it");
                        ferTiefCtrl.setSceneOnReload("Hellfire");
                    }
                    else if (character.hasTrait("Winged")){
                        character.RemoveTrait("Winged");
                        character.addTrait("Infernal Legacy", "You know the Thaumaturgy cantrip. Charisma is your spellcasting ability for it");
                        character.addSpell("Thaumaturgy");
                        ferTiefCtrl.setSceneOnReload("Winged");
                    }
                    else{
                        ferTiefCtrl.setSceneOnReload("None");
                    }
                    ferTiefCtrl.setCharacter(character);
                    ferTiefCtrl.setPreviousWindows(prevWindows);
                    break;
                case "RaceMenus/MartialWeaponProfMenu.fxml":
                    MartialWeaponProfMenuController mwProfCtrl = loader.getController();
                    chosenSkills = new ArrayList<>();
                    int charWeaponProfsize = character.getWeaponProficienciesSize();
                    chosenSkills.add(character.getWeaponProficiency(charWeaponProfsize-1));
                    chosenSkills.add(character.getWeaponProficiency(charWeaponProfsize-2));
                    character.RemoveLastWeaponProficiency(); character.RemoveLastWeaponProficiency();
                    mwProfCtrl.setSceneOnReload(chosenSkills);
                    mwProfCtrl.setCharacter(character);
                    mwProfCtrl.setPreviousWindows(prevWindows);
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
    
    public void ReloadScene(String c){
        classBox.setValue(c);
        //subBox.setValue(sub);
    }
    
    
    
    
    public void setBarbarianTraits(){
        character.setHitDice("1d12");
        //duplicates are removed at very end of character creation
        character.addArmorProficiency("Light Armor", "Medium Armor", "Shields");
        character.addWeaponProficiency("Simple Weapons", "Martial Weapons");
        character.addSaves("Strength", "Constitution");
        character.addTrait("Rage", "In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action.\n"
                + "   While raging, you gain the following benefits if you aren't wearing heavy armor:\n"
                + "     -You have advantage on Strength checks and Strength saving throws.\n"
                + "     -When you make a melee weapon attack using Strength, you gain a bonus +2 to the damage roll (increases with level).\n"
                + "     -You have resistance to bludgeoning, piercing, and slashing damage.\n"
                + "If you are able to cast spells, you can't cast them or concentrate on them while raging.\n"
                + "   Your rage lasts for 1 minute. It ends early if you are knocked unconscious or if your turn ends and "
                + "you haven't attacked a hostile creature since your last turn or taken damage since then. You can also end your rage on "
                + "your turn as a bonus action.\n"
                + "   Once you have raged 2 times you must finish a long rest before you can rage again.");
        character.addTrait("Unarmored Defense", "While you are not wearing any armor, your Armor Class equals 10 + your Dexterity modifier + your "
                + "Constitution modifier. You can use a shield and still gain this benefit.");
    }
}
