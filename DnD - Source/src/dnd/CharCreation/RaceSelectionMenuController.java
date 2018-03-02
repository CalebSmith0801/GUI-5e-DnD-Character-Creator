package dnd.CharCreation;

import dnd.CharCreation.Races.FeralTieflingController;
import dnd.CharCreation.Races.DwarfToolProficiencyMenuController;
import dnd.CharCreation.Races.WizardCantripMenuController;
import dnd.CharCreation.Races.KenkuController;
import dnd.CharCreation.Races.LizardfolkController;
import dnd.CharCreation.Races.MartialWeaponProfMenuController;
import dnd.CharCreation.Races.AbilityRaceBonusMenuController;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
import dnd.Character;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.stage.WindowEvent;
import javax.swing.text.StyleConstants;

/**
 *
 * @author calebs
 */
public class RaceSelectionMenuController implements Initializable {
    
    @FXML private ComboBox<String> raceBox;
    @FXML private ComboBox<String> subraceBox;
    @FXML private Label raceName;
    @FXML private Label subLabel;
    @FXML private WebView wv;
    @FXML private ImageView racePic;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private Button plus;
    @FXML private Button minus;
    private Character character;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        character = new Character();             
        
        //default is to display Aarakocra race + info
        raceBox.getSelectionModel().select(0);
        raceName.setText(raceBox.getValue());
        SetupRaceInfoWebView(raceBox.getValue());
        racePicDisplay();
        
        //Configures scene to display chose race from ComboBox
        //Updates racePic, raceName, and the WebView where the race HTML data file is displayed
        raceBox.valueProperty().addListener((ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
            SetupRaceInfoWebView(newValue);
            raceName.setText(newValue);
            racePicDisplay();
           
            //Makes subrace Combobox show correct subraces based on race picked from raceBox
            //default chooses first subrace
            MakeSubraceComboBox(newValue);
            subraceBox.getSelectionModel().select(0);
        });
  
    }
    
   @FXML
    private void nextButton(ActionEvent event){     
        character.setraceName(raceBox.getValue());
        if(!subraceBox.isDisabled())
            character.setsubraceName(subraceBox.getValue());
            
        //default is to go to class selection screen unless race has a special trait
        //that requires user choice
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassMenu.fxml"));
            Parent root = loader.load();
            ClassMenuController classMenuCtrl = loader.getController();
            switch(raceBox.getValue()){
                case "Aarakocra":
                    setAarakocraTraits();
                    break;
                case "Aasimar":
                    setAasimarTraits();
                    break;
                case "Bugbear":
                    setBugbearTraits();
                    break;
                case "Dragonborn":
                    setDragonbornTraits();
                    break;
                case "Dwarf":
                    setDwarfTraits();
                    loader = new FXMLLoader(getClass().getResource("Races/DwarfToolProficiencyMenu.fxml"));
                    root = loader.load();
                    DwarfToolProficiencyMenuController dwarfCtrl = loader.getController();
                    dwarfCtrl.setCharacter(character);
                    dwarfCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    /*Stage newStage = new Stage();
                    Scene newScene = new Scene(root);
                    newStage.setScene(newScene);
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    newStage.showAndWait();*/
                    break;
                case "Elf":
                    setElfTraits();
                    if(subraceBox.getValue().equals("High Elf")){
                        loader = new FXMLLoader(getClass().getResource("Races/WizardCantripMenu.fxml"));
                        root = loader.load();
                        WizardCantripMenuController elfCtrl = loader.getController();
                        elfCtrl.setCharacter(character);
                        elfCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    }
                    break;
                case "Firbolg":
                    setFirbolgTraits();
                    break;
                case "Genasi":
                    setGenasiTraits();
                    break;
                case "Gnome":
                    setGnomeTraits();
                    break;
                case "Goblin":
                    setGoblinTraits();
                    break;
                case "Goliath":
                    setGoliathTraits();
                     break;
                case "Half-Elf":
                    setHalfElfTraits();
                    loader = new FXMLLoader(getClass().getResource("Races/AbilityRaceBonusMenu.fxml"));
                    root = loader.load();
                    AbilityRaceBonusMenuController raceBonusCtrl = loader.getController();
                    raceBonusCtrl.setCharacter(character);
                    raceBonusCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    break;
                case "Halfling":
                    setHalflingTraits();
                    break;
                case "Half-Orc":
                    setHalfOrcTraits();
                    break;
                case "Hobgoblin":
                    setHobgoblinTraits();
                    loader = new FXMLLoader(getClass().getResource("Races/MartialWeaponProfMenu.fxml"));
                    root = loader.load();
                    MartialWeaponProfMenuController weaponProfCtrl = loader.getController();
                    weaponProfCtrl.setCharacter(character);
                    weaponProfCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    break;
                case "Human":
                    setHumanTraits();
                    if (character.getsubraceName().equals("Default")){
                        loader = new FXMLLoader(getClass().getResource("ExtraLanguageMenu.fxml"));
                        root = loader.load();
                        ExtraLanguageMenuController extraLanguageCtrl = loader.getController();
                        extraLanguageCtrl.setCharacter(character);
                        extraLanguageCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    }
                    else{
                        character.RemoveAllRaceBonuses();
                        loader = new FXMLLoader(getClass().getResource("Races/AbilityRaceBonusMenu.fxml"));//Choose Two Abilities window
                        root = loader.load();
                        AbilityRaceBonusMenuController raceBonCtrl = loader.getController();
                        raceBonCtrl.setCharacter(character);
                        raceBonCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    }
                    break;
                case "Kenku":
                    setKenkuTraits();
                    loader = new FXMLLoader(getClass().getResource("Races/Kenku.fxml"));
                    root = loader.load();
                    KenkuController kenkuCtrl = loader.getController();
                    kenkuCtrl.setCharacter(character);
                    kenkuCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    break;
                case "Kobold":
                    setKoboldTraits();
                    break;
                case "Lizardfolk":
                    setLizardfolkTraits();
                    loader = new FXMLLoader(getClass().getResource("Races/Lizardfolk.fxml"));
                    root = loader.load();
                    LizardfolkController lizardfolkCtrl = loader.getController();
                    lizardfolkCtrl.setCharacter(character);
                    lizardfolkCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    break;
                case "Orc":
                    setOrcTraits();
                    break;
                case "Tabaxi":
                    setTabaxiTraits();
                    loader = new FXMLLoader(getClass().getResource("ExtraLanguageMenu.fxml"));
                    root = loader.load();
                    ExtraLanguageMenuController extraLanguageCtrl2 = loader.getController();
                    extraLanguageCtrl2.setCharacter(character);
                    extraLanguageCtrl2.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    break;
                case "Tiefling":
                    setTieflingTraits();
                    if (subraceBox.getValue().equals("Feral Tiefling")){
                        loader = new FXMLLoader(getClass().getResource("Races/FeralTiefling.fxml"));
                        root = loader.load();
                        FeralTieflingController feralTieflingCtrl = loader.getController();
                        feralTieflingCtrl.setCharacter(character);
                        feralTieflingCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
                    }
                    break;
                case "Tortle":
                    setTortleTraits();
                    break;
                case "Triton":
                    setTritonTraits();
                    break;
                case "Yuan-ti Pureblood":
                    setYuantiTraits();
                    break;
                default:
                    break;
            }
            classMenuCtrl.setCharacter(character);
            classMenuCtrl.setPreviousWindows(new ArrayList<>(Arrays.asList("raceSelectionMenu.fxml")));
            switchScene(root);
        }
        catch(IOException e){
            System.out.println("Stage could not be loaded\nIOException");
        }    
    }
    
    public void SetupRaceInfoWebView(String race){
        wv.getEngine().loadContent(readDataFile("RaceInfo/"+race));
         
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
    
    public void racePicDisplay(){
        String img = "file:Data/RaceInfo/" + raceName.getText().trim() + ".png";
        Image image = new Image(img.trim());
        racePic.setImage(image);              
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
    
    //Used when clicking a back button to return to this window
    //if user wanted to make changes
    //loads up their previous choice
    public void ReloadScene(String r, String sub){
        raceBox.setValue(r);
        subraceBox.setValue(sub);
    }
    
    public void MakeSubraceComboBox(String race){
        switch (race){
            case "Aasimar":
                subLabel.setText("Subrace:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Protector Aasimar", "Scourge Aasimar", "Fallen Aasimar");
                break;
                
            case "Dragonborn":
                subLabel.setText("Ancestry:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Black", "Blue", "Brass", "Bronze", "Copper", "Gold", "Green", "Red", "Silver", "White" );
                break;
                
            case "Dwarf":
                subLabel.setText("Subrace:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Hill Dwarf", "Mountain Dwarf", "Gray Dwarf");
                break;
                    
            case "Elf":
                subLabel.setText("Subrace:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("High Elf", "Wood Elf", "Dark Elf", "Eladrin");
                break;
                    
            case "Genasi":
                subLabel.setText("Subrace:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Air Genasi", "Earth Genasi", "Fire Genasi", "Water Genasi");
                break;
                    
            case "Gnome":
                subLabel.setText("Subrace:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Forest Gnome", "Rock Gnome", "Deep Gnome");
                break;
                    
            case "Half-Elf":
                subLabel.setText("Variant:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Default", "Half-Elf Variant");
                break;
                    
            case "Halfling":
                subLabel.setText("Subrace:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Lightfoot Halfling", "Stout Halflling", "Ghostwise Halfling");
                break;
                
            case "Human":
                subLabel.setText("Variant:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Default", "Human Variant");
                break;
                
            case "Tiefling":
                subLabel.setText("Variant:");
                subraceBox.setDisable(false);
                subraceBox.getItems().clear();
                subraceBox.getItems().addAll("Default", "Feral Tiefling");
                break;
                    
            default:
                subLabel.setText("Subrace:");
                subraceBox.getItems().clear();
                subraceBox.setDisable(true);
                break;
        }
    }
    
    //increase html body font size
    @FXML
    private void plusButton(ActionEvent event){ 
        changeHtmlTextSize("plus");
    }
    
    @FXML
    private void minusButton(ActionEvent event){ 
        changeHtmlTextSize("minus");
    }
    
    private void changeHtmlTextSize(String direction){
        File[] files = new File("Data/RaceInfo").listFiles(new FilenameFilter(){ 
            @Override 
            public boolean accept(File dir, String name){ 
                return name.endsWith(".html"); 
            } 
        });
        
        for (File file : files){
            File raceFile = file;
            String oldContent = "";
            String newContent = "";
            try{
                BufferedReader reader = new BufferedReader(new FileReader(raceFile));
                String line = reader.readLine();
                int currentSize = Integer.parseInt(line.replaceAll("[\\W+]", "").trim());
                while(line != null){
                    oldContent = oldContent + line + System.lineSeparator();
                    line = reader.readLine();
                }
                
                if (direction.equals("plus") && currentSize < 14){
                    newContent = oldContent.replace("<!-- " + currentSize +" -->", "<!-- " + (currentSize+1) +" -->")
                                           .replace("<body style = \"Font-family: Segoe UI; Font-size:" + currentSize + "px\";>", 
                                                    "<body style = \"Font-family: Segoe UI; Font-size:" + (currentSize+1) +"px\";>");
                }
                
                else if (direction.equals("minus") && currentSize > 11){
                    newContent = oldContent.replace("<!-- " + currentSize +" -->", "<!-- " + (currentSize-1) +" -->")
                                           .replace("<body style = \"Font-family: Segoe UI; Font-size:" + currentSize + "px\";>", 
                                                    "<body style = \"Font-family: Segoe UI; Font-size:" + (currentSize-1) +"px\";>");
                }
                
                if (!newContent.equals("")){
                    FileWriter writer = new FileWriter(raceFile);
                    writer.write(newContent);
                    writer.close();
                }
                reader.close();
            }
            catch (Exception e){
                System.out.println("Error reading/modifying/writing contents");
            }
        }
        SetupRaceInfoWebView(raceName.getText());
    }
    
    
    

    
    //***********************Setting Character Traits***********************//
    private void setAarakocraTraits(){
        character.setDexRaceBonus(2);
        character.setWisRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(25);
        character.addTrait("Flight", "You have a flying speed of 50 feet. To use this speed, you can't be wearing"
                + " medium or heavy armor.");
        character.addTrait("Talons", "You are proficient with your unarmed strikes, which deal 1d4 slashing damage"
                + " on a hit.");
        character.addSkill("Unarmed");
        character.addLanguage("Common", "Aarakocra", "Auran");
    }
    
    private void setAasimarTraits(){
        character.setCharRaceBonus(2);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "Blessed with a radiant soul, your vision can easily cut through darkness."
                + " You can see in dim light within 60 feet of you as if it were bright light, and in darkness as if "
                + "it were dim light. You can't discern color in darkness, only shades of gray.");
        character.addTrait("Celestial Resistance", "You have resistance to necrotic damage and radiant damage.\n"
                + "Necrotic and radiant damage taken is halved.");
        character.addTrait("Healing Hands", "As an action, you can touch a creature and cause it to regain a number "
                + "of hit points equal to your level. Once you use this trait, you can't use it again until you finish"
                + " a long rest.");
        character.addTrait("Light Bearer", "You know the Light cantrip. Charisma is your spellcasting ability for it.");
        character.addSpell("Light");
        
        switch(subraceBox.getValue()){
            case "Protector Aasimar":
                character.setWisRaceBonus(1);
                break;
            case "Scourge Aasimar":
                character.setConRaceBonus(1);
                break;
            case "Fallen Aasimar":
                character.setStrRaceBonus(1);
                break;
            default:
                break;
        }
    }
    
    private void setBugbearTraits(){
        character.setStrRaceBonus(2);
        character.setDexRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright light, and in"
                + " darkness as if it were dim light. You can't discern color in darkness, only shades of gray.");
        character.addTrait("LongLimbed", "When you make a melee attack on your turn, your reach for it is 5 feet greater than normal.");
        character.addTrait("Powerful Build", "You count as one size larger when determining your carrying capacity and the weight you "
                + "can push, drag, or lift.");
        character.addTrait("Sneaky", "You are proficient in the Stealth skill.");
        character.addTrait("Surprise Attack", "If you surprise a creature and hit it with an attack on your first turn in combat, "
                + "the attack deals an extra 2d6 damage to it. You can use this trait only once per combat.");
        character.addSkill("Stealth");
        character.addLanguage("Common", "Goblin");
    }
    
    private void setDragonbornTraits(){
        character.setStrRaceBonus(2);
        character.setCharRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        String breathWeapon;
        String resistance;
        switch(subraceBox.getValue()){
            case "Black":
            case "Copper":
                breathWeapon = "Acid (5 by 30 ft. line)";
                resistance = "Acid";
                break;
            case "Blue":
            case "Bronze":
                breathWeapon = "Lightning (5 by 30 ft. line)";
                resistance = "Lightning";
                break;
            case "Brass":
                breathWeapon = "Fire (5 by 30 ft. line)";
                resistance = "Fire";
                break;
            case "Gold":
            case "Red":
                breathWeapon = "Fire (15 ft. cone)";
                resistance = "Fire";
                break;
            case "Green":
                breathWeapon = "Poison (15 ft. cone)";
                resistance = "Poison";
                break;
            case "Silver":
            case "White":
                breathWeapon = "Cold (15 ft. cone)";
                resistance = "Cold";
                break;
            default:
                breathWeapon = "";
                resistance = "";
                break;
                
        }
        character.addTrait("Breath Weapon", "You can use your action to exhale " + breathWeapon + "/nWhen you use your breath weapon, "
                + "each creature in the area of the exhalation must make a saving throw, the type of which is determined by your "
                + "draconic ancestry. The DC for this saving throw equals 8 + your Constitution modifier + your proficiency bonus. "
                + "A creature takes 2d6 damage on a failed save, and half as much damage on a successful one. The damage increases to "
                + "3d6 at 6th level, 4d6 at 11th level, and 5d6 at 16th level.\n"
                + "After you use your breath weapon, you can't use it again until you complete a short or long rest.");
        character.addTrait(resistance + " Resistance", "You have resistance to " +resistance.toLowerCase() + " damage.\n" 
                + resistance + " damage taken is halved");
        character.addLanguage("Common", "Draconic");
        
    }
      
    private void setDwarfTraits(){
        character.setConRaceBonus(2);
        character.setSpeed(25);
        character.setSize("Medium");
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Dwarven Resilience", "You have advantage on saving throws against poison, and you "
                + "have resistance against poison damage. Damage from poison is halved.");
        character.addTrait("Stonecunning", "Whenever you make an Intelligence (History) check related to the origin"
                + " of stonework, you are considered proficient in the History skill and add double your "
                + "proficiency bonus to the check, instead of your normal proficiency bonus.");
        character.addWeaponProficiency("Battleaxe", "Handaxe", "Light Hammer", "Warhammer");
        character.addLanguage("Common", "Dwarvish");    
        
        switch(subraceBox.getValue()){
            case "Hill Dwarf":
                character.setWisRaceBonus(1);
                character.addTrait("Dwarven Toughness", "Your hit point maximum increases by 1, and it increases "
                        + "by 1 every time you gain a level.");
                break;
            case "Mountain Dwarf":
                character.setStrRaceBonus(2);
                character.addArmorProficiency("Light Armor", "Medium Armor");
                break;
            case "Gray Dwarf":
                character.setStrRaceBonus(1);
                character.RemoveTrait("Darkvision");
                character.addTrait("Superior Darkvision", "You can see in dim light within 120 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
                character.addTrait("Duergar Resilience", "You have advantage on saving throws against illusions and against"
                        + " being charmed or paralyzed.");
                character.addTrait("Sunlight Sensitivity", "You have disadvantage on attack rolls and on Wisdom (Perception) "
                        + "checks that rely on sight when you, the target of your attack, or whatever you are trying to "
                        + "perceive is in direct sunlight.");
                character.setDarkVision(120);
                character.addLanguage("Undercommon");
                break;
            default:
                break;
        }
    }
    
    public void setElfTraits(){
        character.setDexRaceBonus(2);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Keen Senses", "You are proficient in the Perception skill.");
        character.addTrait("Fey Ancestry", "You have advantage on saving throws against being charmed, and magic can't put you to sleep.");
        character.addTrait("Trance", "Elves don't need to sleep. Instead, they meditate deeply, remaining semiconscious, for 4 hours a "
                + "day. After resting in this way, you gain the same benefit that a human does from 8 hours of sleep.");
        character.addSkill("Perception");
        character.addLanguage("Common", "Elvish");
        
        switch(subraceBox.getValue()){
            case "High Elf":
                character.setIntRaceBonus(1);
                character.addWeaponProficiency("Longsword", "Shortsword", "Shortbow", "Longbow");
                break;
            case "Wood Elf":
                character.setWisRaceBonus(1);
                character.addWeaponProficiency("Longsword", "Shortsword", "Shortbow", "Longbow");
                character.setSpeed(35);
                character.addTrait("Mask of the Wild", "You can attempt to hide even when you are only lightly obscured by foliage,"
                        + " heavy rain, falling snow, mist, and other natural phenomena.");
                break;
            case "Dark Elf":
                character.setCharRaceBonus(1);
                character.RemoveTrait("Darkvision");
                character.addTrait("Superior Darkvision", "You can see in dim light within 120 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
                character.addTrait("Sunlight Sensitivity", "You have disadvantage on attack rolls and on Wisdom (Perception) "
                        + "checks that rely on sight when you, the target of your attack, or whatever you are trying to "
                        + "perceive is in direct sunlight.");
                character.addTrait("Drow Magic", "You know the Dancing Lights cantrip. Charisma is your spellcasting ability for it.");
                character.addSpell("Dancing Lights");
                character.setDarkVision(120);
                character.addWeaponProficiency("Rapier", "Shortsword", "Hand Crossbow");
                break;
            case "Eladrin":
                character.setIntRaceBonus(1);
                character.addWeaponProficiency("Longsword", "Shortsword", "Shortbow", "Longbow");
                character.addTrait("Fey Step", "You can cast the Misty Step spell once using this trait. You regain the ability to do"
                        + " so when you finish a short or long rest.");
                character.addSpell("Misty Step");
            default:
                break;
        }
    }
    
    public void setFirbolgTraits(){
        character.setWisRaceBonus(2);
        character.setStrRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addTrait("Hidden Step", "As a bonus action, you can magically turn invisible until the start of your next turn"
                + "or until you attack, make a damage roll, or force someone to make a saving throw. Once you use this trait, you can't"
                + " use it again until you finish a short or long rest.");
        character.addTrait("Powerful Build", "You count as one size larger when determining your carrying capacity and the weight you can"
                + " push, drag, or lift.");
        character.addTrait("Speech of Beast and Leaf", "You have the ability to communicate in a limited manner with beasts and plants. They can"
                + " understand the meaning of your words, though you have no special ability to understand them in return. You have advantage on "
                + "all Charisma checks you make to influence them.");
        character.addTrait("Firebolg Magic", "You can cast Detect Magic and Disguise Self with this trait, using Wisdom as your"
                + " spell casting ability for them. Once you cast either spell, you can't cast it again with this trait until you"
                + " finish a short or long rest. When you use this version of disguise self, you can seem up to 3 feet shorter than "
                + "normal, allowing you to more easily blend in with humans and elves.");
        character.addSpell("Detect Magic", "Disguise Self");
        character.addLanguage("Common", "Elvish", "Giant");
    }
    
    public void setGenasiTraits(){
        character.setConRaceBonus(2);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addLanguage("Common", "Primordial");
        
        switch(subraceBox.getValue()){
            case "Air Genasi":
                character.setDexRaceBonus(1);
                character.addTrait("Unending Breath", "You can hold your breath indefinitely while you're not incapacitated.");
                character.addTrait("Mingle With the Wind", "You can cast the Levitate spell once with this trait, requiring no material "
                        + "components, and you regain the ability to cast it this way when you finish a long rest. Constitution is your"
                        + " spellcasting ability for this spell.");
                character.addSpell("Levitate");
                break;
            case "Earth Genasi":
                character.setStrRaceBonus(1);
                character.addTrait("Earth Walk", "You can move across difficult terrain made of earth or stone without expending extra movement.");
                character.addTrait("Merge With Stone", "You can cast the Pass Without Trace spell once with this trait, requiring no "
                        + "material components, and you regain the ability to cast it this way when you finish a long rest. Constitution "
                        + "is your spellcasting ability for this spell.");
                character.addSpell("Pass Without Trace");
                break;
            case "Fire Genasi":
                character.setIntRaceBonus(1);
                character.setDarkVision(60);
                character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
                character.addTrait("Fire Resistance", "You have resistance to fire damage.\nDamage taken from fire is halved.");
                character.addTrait("Reach to the Blaze", "You know the Produce Flame cantrip. Constitution is your spellcasting ability for it.");
                character.addSpell("Produce Flame");
                break;
            case "Water Genasi":
                character.setWisRaceBonus(1);
                character.addTrait("Acid Resistance", "You have resistance to acid damage.\nDamage taken from acid is halved.");
                character.addTrait("Amphibious", "You can breathe air and water.");
                character.addTrait("Swim", "You have a swimming speed of 30 feet.");
                character.addTrait("Call to the Wave", "You know the Shape Water cantrip. Constitution is your spellcasting ability for it.");
                character.addSpell("Shape Water");
                break;
            default:
                break;
        }
    }
    
    public void setGnomeTraits(){
        character.setIntRaceBonus(2);
        character.setSize("Small");
        character.setSpeed(25);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Gnome Cunning", "You have advantage on all Intelligence, Wisdom, and Charisma saving throws against magic.");
        character.addLanguage("Common", "Gnomish");
        
        switch(subraceBox.getValue()){
            case "Forest Gnome":
                character.setDexRaceBonus(1);
                character.addTrait("Speak With Small Beasts", "Through sounds and gestures, you can communicate simple ideas with Small"
                        + " or smaller beasts. Forest gnomes love animals and often keep squirrels, badgers, rabbits, moles, woodpeckers,"
                        + " and other creatures as beloved pets.");
                character.addTrait("Natural Illusionist", "You know the Minor Illusion cantrip. Intelligence is your spellcasting ability for it.");
                character.addSpell("Minor Illusion");
                break;
            case "Rock Gnome":
                character.setConRaceBonus(1);
                character.addTrait("Artificer's Lore", "Whenever you make an Intelligence (History) check related to magic items, "
                        + "alchemical objects, or technological devices, you can add twice your proficiency bonus, instead of any "
                        + "proficiency bonus you normally apply.");
                character.addTrait("Tinker", "You have proficiency with artisan's tools (tinker's tools). Using those tools, you can spend "
                        + "1 hour and 10 gp worth of materials to construct a Tiny clockwork device (AC 5, 1 hp). The device ceases to "
                        + "function after 24 hours (unless you spend 1 hour repairing it to keep the device functioning), or when you use"
                        + " your action to dismantle it; at that time, you can reclaim the materials used to create it. You can have up to "
                        + "three such devices active at a time.\n" 
                        + "When you create a device, choose one of the following options:\n\n" 
                        + "Clockwork Toy: This toy is a clockwork animal, monster, or person, such as a frog, mouse, bird, dragon, or "
                        + "soldier. When placed on the ground, the toy moves 5 feet across the ground on each of your turns in a random "
                        + "direction. It makes noises as appropriate to the creature it represents.\n\n" 
                        + "Fire Starter: The device produces a miniature flame, which you can use to light a candle, torch, or campfire. "
                        + "Using the device requires your action.\n\n"
                        + "Music Box: When opened, this music box plays a single song at a moderate volume. The box stops playing when it "
                        + "reaches the song's end or when it is closed.");
                character.addToolProficiency("Artisan's Tools (Tinker's Tools");
                break;
            case "Deep Gnome":
                character.setDexRaceBonus(1);
                character.setDarkVision(120);
                character.RemoveTrait("Darkvision");
                character.addTrait("Superior Darkvision", "You can see in dim light within 120 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
                character.addTrait("Stone Camouflage", "You have advantage on Dexterity (stealth) checks to hide in rocky terrain.");
                break;
            default:
                break;
        }
    }
    
    public void setGoblinTraits(){
        character.setDexRaceBonus(2);
        character.setConRaceBonus(1);
        character.setSize("Small");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Fury of the Small", "When you damage a creature with an attack or a spell and the creature's size is "
                + "larger than yours, you can cause the attack or spell to deal extra damage to the creature. The extra damage equals "
                + "your level. Once you use this trait, you can't use it again until you finish a short or long rest.");
        character.addTrait("Nimble Escape", "You can take the Disengage or Hide action as a bonus action on each of your turns.\n\n" 
                + "Disengage: Your Movement doesn't provoke opportunity attacks for the rest of the turn.\n"
                + "Hide: You make a Dexterity (Stealth) check in an attempt to hide. If successful, you gain advantage on attacks for "
                + "enemies that can't see you, and enemies have a disadvantage on attacks toward you.");
        character.addLanguage("Common", "Goblin");
    }
    
    public void setGoliathTraits(){
        character.setStrRaceBonus(2);
        character.setConRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addTrait("Natural Athlete", "You have proficiency in the Athletics skill.");
        character.addSkill("Athletics");
        character.addTrait("Stone's Endurance", "You can focus yourself to occasionally shrug off injury. When you take damage, you can use"
                + " your reaction to roll a d12. Add your Constitution modifier to the number rolled, and reduce the damage by that total. "
                + "After you use this trait, you can't use it again until you finish a short or long rest.");
        character.addTrait("Powerful Build", "You count as one size larger when determining your carrying capacity and the weight you can "
                + "push, drag, or lift.");
        character.addTrait("Mountain Born", "You're acclimated to high altitude, including elevations above 20,000 feet. You're also "
                + "naturally adapted to cold climates, as described in chapter 5 of the Dungeon Master's Guide.\n" 
                + "From Ch. 5: You don't have to roll a DC 10 Constitution saving throw in cold environments.");
        character.addLanguage("Common", "Giant");
    }
    
    public void setHalfElfTraits(){
        character.setCharRaceBonus(2);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Fey Ancestry", "You have advantage on saving throws against being charmed, and magic can't put you to sleep.");
        character.addLanguage("Common", "Elvish");
    }
    
    public void setHalflingTraits(){
        character.setDexRaceBonus(2);
        character.setSpeed(25);
        character.setSize("Small");
        character.addTrait("Lucky", "When you roll a 1 on an attack roll, ability check, or saving throw, you can reroll the die and "
                + "must use the new roll.");
        character.addTrait("Brave", "You have advantage on saving throws against being frightened.");
        character.addTrait("Halfling Nimbleness", "You can move through the space of any creature that is of a size larger than yours.");
        character.addLanguage("Common", "Halfling");
        
        switch(subraceBox.getValue()){
            case "Lightfoot Halfling":
                character.setCharRaceBonus(1);
                character.addTrait("Naturally Stealthy", "You can attempt to hide even when you are obscured only by a creature that is "
                        + "at least one size larger than you.");
                break;
            case "Stout Halfling":
                character.setConRaceBonus(1);
                character.addTrait("Stout Resilience", "You have advantage on saving throws against poison, and you have resistance against"
                        + " poison damage.\nDamage from poison is halved.");
                break;
            case "Ghostwise Halfling":
                character.setWisRaceBonus(1);
                character.addTrait("Silent Speech", "You can speak telepathically to any creature within 30 feet of you. The creature "
                        + "understands you only if the two of you share a language. You can speak telepathically in this way to one "
                        + "creature at a time.");
                break;
            default:
                break;
        }
    }
    
    public void setHalfOrcTraits(){
        character.setStrRaceBonus(2);
        character.setConRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Menacing", "You gain proficiency in the Intimidation skill.");
        character.addSkill("Intimidation");
        character.addTrait("Relentless Endurance", "When you are reduced to 0 hit points but not killed outright, you can drop to 1 hit "
                + "point instead. You can't use this feature again until you finish a long rest.");
        character.addTrait("Savage Attacks", "When you score a critical hit with a melee weapon attack, you can roll one of the weapon's "
                + "damage dice one additional time and add it to the extra damage of the critical hit.");
        character.addLanguage("Common", "Orc");
    }
    
    public void setHobgoblinTraits(){
        character.setConRaceBonus(2);
        character.setIntRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addArmorProficiency("Light Armor");
        character.addTrait("Saving Face", "Hobgoblins are careful not to show weakness in front of their allies, for fear of "
                + "losing status. If you miss with an attack roll or fail an ability check or a saving throw, you can gain a "
                + "bonus to the roll equal to the number of allies you can see within 30 feet of you (maximum bonus of +5). "
                + "Once you use this trait, you can't use it again until you finish a short or long rest.");
        character.addLanguage("Common", "Goblin");
    }
    
    public void setHumanTraits(){
        character.setStrRaceBonus(1);
        character.setDexRaceBonus(1);
        character.setConRaceBonus(1);
        character.setIntRaceBonus(1);
        character.setWisRaceBonus(1);
        character.setConRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addLanguage("Common");
    }
    
    public void setKenkuTraits(){
        character.setDexRaceBonus(2);
        character.setWisRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addTrait("Expert Forgery", "You can duplicate other creatures' handwriting and craftwork. You have advantage on all "
                + "checks made to produce forgeries or duplicates of existing objects");
        character.addTrait("Mimicry", "You can mimic sounds you have heard, including voices. A creature that hears the sounds you make"
                + " can tell they are imitations with a successful Wisdom (Insight) check opposed by your Charisma (Deception) check. You can only speak using this trait.");
        character.addLanguage("Common", "Auran");
    }
    
    public void setKoboldTraits(){
        character.setDexRaceBonus(2);
        character.setStrRaceBonus(-2);
        character.setSize("Small");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Grovel, Cower, and Beg", "As an action on your turn, you can cower pathetically to distract nearby foes. "
                + "Until the end of your next turn, your allies gain advantage on attack rolls against enemies within 10 feet of "
                + "you that can see you. Once you use this trait, you can't use it again until you finish a short or long rest.");
        character.addTrait("Pack Tactics", "You have advantage on an attack roll against a creature if at least one of your allies "
                + "is within 5 feet of the creature and the ally isn't incapacitated.");
        character.addTrait("Sunlight Sensitivity", "You have disadvantage on attack rolls and on Wisdom (Perception) "
                        + "checks that rely on sight when you, the target of your attack, or whatever you are trying to "
                        + "perceive is in direct sunlight.");
        character.addLanguage("Common", "Draconic");
    }
    
    public void setLizardfolkTraits(){
        character.setConRaceBonus(2);
        character.setWisRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addTrait("Bite", "Your fanged maw is a natural weapon, which you can use to make unarmed strikes. If you hit with it,"
                + " you deal piercing damage equal to 1d6 + your Strength modifier, instead of the bludgeoning damage normal for"
                + " an unarmed strike.");
        character.addTrait("Cunning Artisan", "As part of a short rest, you can harvest bone and hide from a slain beast, construct, "
                + "dragon, monstrosity, or plant creature of size Small or larger to create one of the following items: a shield, a club,"
                + " a javelin, or ld4 darts or blowgun needles. To use this trait, you need a blade, such as a dagger, or appropriate "
                + "artisan's tools, such as leatherworker's tools.");
        character.addTrait("Hold Breath", "You can hold your breath for up to 15 minutes at a time.");
        character.addTrait("Natural Armor", "You have tough, scaly skin. When you aren't wearing armor, your AC is 13 + your Dexterity "
                + "modifier. You can use your natural armor to determine your AC if the armor you wear would leave you with a lower AC. "
                + "A shield's benefits apply as normal while you use your natural armor.");
        character.addTrait("Hungry Jaws", "In battle, you can throw yourself into a vicious feeding frenzy. As a bonus action, you can make"
                + " a special attack with your bite. If the attack hits, it deals its normal damage, and you gain temporary hit points "
                + "(minimum of 1) equal to your Constitution modifier, and you can't use this trait again until	you finish a short or long"
                + " rest.");
        character.addLanguage("Common", "Draconic");
    }
    
    public void setOrcTraits(){
        character.setStrRaceBonus(2);
        character.setConRaceBonus(1);
        character.setIntRaceBonus(-2);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Aggressive", "As a bonus action, you can move up to your speed toward an enemy of your choice"
                + " that you can see or hear. You must end this move closer to the enemy than you started.");
        character.addTrait("Menacing", "You are proficient in the Intimidation skill.");
        character.addSkill("Intimidation");
        character.addTrait("Powerful Build", "You count as one size larger when determining your carrying capacity and the "
                + "weight you can push, drag, or lift.");
        character.addLanguage("Common", "Orc");
    }
    
    public void setTabaxiTraits(){
        character.setDexRaceBonus(2);
        character.setCharRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Feline Agility", "Your reflexes and agility allow you to move with a burst of speed. When you move "
                + "on your tum in combat, you can double your speed until the end of the tum. Once you use this trait, "
                + "you can't use it again until you move 0 feet on one of your turns.");
        character.addTrait("Cat's Claws", "Because of your claws, you have a climbing speed of 20 feet. In addition, your claws are natural"
                + " weapons, which you can use to make unarmed strikes. If you hit with them, you deal slashing damage equal to ld4 + your"
                + " Strength modifier, instead of the bludgeoning damage normal for an unarmed strike.");
        character.addTrait("Cat's Talent", "You have proficiency in the Perception and Stealth skills.");
        character.addSkill("Perception", "Stealth");
        character.addLanguage("Common");
    }
    
    public void setTieflingTraits(){
        if (character.getsubraceName().equals("Default")){
            character.setCharRaceBonus(2);
            character.setIntRaceBonus(1);
        }
        else{
            character.setDexRaceBonus(2);
            character.setIntRaceBonus(1);
        }        
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Hellish Resistance", "You have resistance to fire damage.\nDamage taken from fire is halved.");
        character.addTrait("Infernal Legacy", "You know the Thaumaturgy cantrip. Charisma is your spellcasting ability for it");
        character.addSpell("Thaumaturgy");
        character.addLanguage("Common", "Infernal");
    }
    
    public void setTortleTraits(){
        character.setStrRaceBonus(2);
        character.setWisRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addTrait("Claws", "Your claws are natural weapons, which you can use to make unarmed strikes. If you hit with them, "
                + "you deal slashing damage equal to 1d4 + your Strength modifier, instead of the bludgeoning damage normal for an "
                + "unarmed strike.");
        character.addTrait("Hold Breath", "You can hold your breath for up to 1 hour at a time. Tortles aren't natural swimmers, but "
                + "they can remain underwater for some time before needing to come up for air.");
        character.addTrait("Natural Armor", "Due to your shell and the shape of your body, you are ill-suited to wearing armor. Your "
                + "shell provides ample protection, however; it gibes you a base AC of 17 (your Dexterity modifier doesn't affect this "
                + "number). You gain no benefit from wearing armor, but if you are using a shield, you can apply the shield's bonus "
                + "as normal.");
        character.addTrait("Shell Defense", "You can withdraw into your shell as an action. Until you emerge, you gain a +4 bonus to AC,"
                + " and you have advantage on Strength and Constitution saving throws. While in your shell, you are prone, your speed "
                + "is 0 and can't increase, you have disadvantage on Dexterity saving throws, you can't take reactions, and the only "
                + "action you can take is a bonus action to emerge from your shell.");
        character.addTrait("Survival Instinct", "You gain proficiency in the Survival skill. Tortles have finely honed survival "
                + "instincts.");
        character.addSkill("Survival");
        character.addLanguage("Aquan", "Common");
    }
    
    public void setTritonTraits(){
        character.setStrRaceBonus(1);
        character.setConRaceBonus(1);
        character.setCharRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.addTrait("Amphibious", "You can breathe air and water.");
        character.addTrait("Control Air and Water", "A child of the sea, you can call on the magic of elemental air and water. "
                + "You can cast Fog Cloud with this trait. Once you cast a spell with this trait, you can't do so again	until you "
                + "finish a long rest. Charisma is your spellcasting ability for it.");
        character.addSpell("Fog Cloud");
        character.addTrait("Emissary of the Sea", "Aquatic beasts have an extraordinary affinity with your people. You can communicate"
                + " simple ideas with beasts that can breathe water. They can understand the meaning of your words, though you have no"
                + " special ability to understand them in return.");
        character.addTrait("Guardians of the Depths", "Adapted to even the most extreme ocean depths, you have resistance to cold "
                + "damage, and you ignore any of the drawbacks caused by a deep, underwater environment.");
        character.addLanguage("Common", "Primordial");
    }
    
    public void setYuantiTraits(){
        character.setCharRaceBonus(2);
        character.setIntRaceBonus(1);
        character.setSize("Medium");
        character.setSpeed(30);
        character.setDarkVision(60);
        character.addTrait("Darkvision", "You can see in dim light within 60 feet of you as if it were bright "
                + "light, and in darkness as if it were dim light. You can't discern color in darkness, "
                + "only shades of gray.");
        character.addTrait("Innate Spellcasting", "You know the Poison Spray cantrip. You can cast Animal Friendship an unlimited "
                + "number of times with this trait, but you can target only snakes with it. Charisma is your spellcasting ability for "
                + "these spells.");
        character.addSpell("Poison Spray", "Animal Friendship");
        character.addTrait("Magic Resistance", "You have advantage on saving throws against spells and other magical effects.");
        character.addTrait("Poison Immunity", "You are immune to poison damage and the poisoned condition.");
        character.addLanguage("Common", "Abyssal", "Draconic");
    }
}
