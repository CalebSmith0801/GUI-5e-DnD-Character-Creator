//============================================================================//
//PREVIOUS WINDOWS: BarbarianSkillsMenuController                             //
//                                                                            //
//NEXT WINDOW: HitPoints                                                      //
//                                                                            //
//Changes to Character in this Window:                                        //
//---sets ability scores and modifiers                                        //
//============================================================================//

package dnd.CharCreation;

import dnd.CharCreation.ClassMenus.BarbarianSkillsMenuController;
import dnd.Character;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
Divided into 4 tabs for the different methods to calculate ability scores
-Custom: let's you enter whatever numbers you want
-Simple: Gives you default scores of (15, 14, 13, 12, 10, 8) but let's you choose
which ability gets what score
-Point Buy: all abilities start at 8 but you have 27 points to spend to increase them
-Roll: randomize the 6 scores between [3,18] and assign the values to the abilities you prefer
*/

public class AbilityScoresController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    private ArrayList<Integer> raceBonuses;
    private ArrayList<Integer> abilities;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private Button customBut;
    @FXML private Button simpleBut;
    @FXML private Button pointBut;
    @FXML private Button rollBut;
    @FXML private Label raceBonusLabel;
    @FXML private Label strModLabel, dexModLabel, conModLabel;
    @FXML private Label intModLabel, wisModLabel, charModLabel;
    @FXML private TextField customStrTF, customDexTF, customConTF;
    @FXML private TextField customIntTF, customWisTF, customCharTF;
    @FXML private Label pointStrScoreLabel, pointDexScoreLabel, pointConScoreLabel;
    @FXML private Label pointIntScoreLabel, pointWisScoreLabel, pointCharScoreLabel;
    @FXML private Label pointsRemaining;
    @FXML private Label rollAbilityValue1; //the number Labels that represent the generated ability scores
    @FXML private Label rollAbilityValue2; //column 0 of the rollPane gridpane
    @FXML private Label rollAbilityValue3;
    @FXML private Label rollAbilityValue4;
    @FXML private Label rollAbilityValue5;
    @FXML private Label rollAbilityValue6;
    @FXML private Line customLine;
    @FXML private Line simpleLine;
    @FXML private Line pointLine;
    @FXML private Line rollLine;
    @FXML private GridPane customPane;
    @FXML private GridPane simplePane;
    @FXML private AnchorPane pointPane; //used to display all content of point buy menu
    @FXML private GridPane pointGridPane; //where the values are stored/modified
    ArrayList<Button> pointBuyPlusButtons; //used to easily turn off all the plus buttons of the pointGridPane
    @FXML private AnchorPane rollPane; //used to display all content of roll menu
    @FXML private GridPane rollGridPane; //where the values are stored/modified
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MakeIntTextField(customStrTF);
        MakeIntTextField(customDexTF);
        MakeIntTextField(customConTF);
        MakeIntTextField(customIntTF);
        MakeIntTextField(customWisTF);
        MakeIntTextField(customCharTF);
        setPointBuyLimit(pointStrScoreLabel);
        setPointBuyLimit(pointDexScoreLabel);
        setPointBuyLimit(pointConScoreLabel);
        setPointBuyLimit(pointIntScoreLabel);
        setPointBuyLimit(pointWisScoreLabel);
        setPointBuyLimit(pointCharScoreLabel);
        pointBuyPlusButtons = new ArrayList<>(Arrays.asList((Button) getNodeFromGridPane(pointGridPane, 3, 0), 
                                                            (Button) getNodeFromGridPane(pointGridPane, 3, 1),
                                                            (Button) getNodeFromGridPane(pointGridPane, 3, 2),
                                                            (Button) getNodeFromGridPane(pointGridPane, 3, 3),
                                                            (Button) getNodeFromGridPane(pointGridPane, 3, 4),
                                                            (Button) getNodeFromGridPane(pointGridPane, 3, 5)));
        
        //Disables all plus buttons and makes nextBut enabled when points remaining = 0 for Points Buy Pane
        pointsRemaining.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.equals("0")){
                for (Button button : pointBuyPlusButtons){
                    button.setDisable(true);
                    nextBut.setDisable(false);
                }
            }
            else if (newValue.equals("1")){
                ArrayList<Label> pointBuyAbilityLabels= new ArrayList<>(Arrays.asList(pointStrScoreLabel, pointDexScoreLabel, pointConScoreLabel,
                                                                                      pointIntScoreLabel, pointWisScoreLabel, pointCharScoreLabel));
                for (int i = 0; i < 6; i++){
                    //DnD rules: Ability caps at 15 for this method
                    if (!pointBuyAbilityLabels.get(i).getText().equals("15"))
                        pointBuyPlusButtons.get(i).setDisable(false);
                }
                    
            }
        });
    }
    
    
    private void MakeIntTextField(final TextField tf){
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d+"))
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            
            //calculate ability modifiers when new digit is entered in textfield
            //0 + textfield because sometimes the textfield can be left empty
            else
                setModifiers(Integer.parseInt(0 + customStrTF.getText()), Integer.parseInt(0 + customDexTF.getText()), Integer.parseInt(0 + customConTF.getText()), 
                             Integer.parseInt(0 + customIntTF.getText()), Integer.parseInt(0+ customWisTF.getText()), Integer.parseInt(0 + customCharTF.getText()));
        
            
            //limits text field to 2 characters
            if (tf.getText().length() > 2){
                String s = tf.getText().substring(0, 2);
                tf.setText(s);
            }
            
            if (tf.getText().length() == 0)
               nextBut.setDisable(true);
            else
               nextBut.setDisable(false);
            
        });
        RemoveUndoRedoShortcut(tf);
    }
    
    //limiting the length of textfield causes problems with undo/redo, so I removed the shortcuts
    private void RemoveUndoRedoShortcut(final TextField tf){
        tf.setOnKeyPressed((KeyEvent event) -> {
            if ((event.getCode() == KeyCode.Z || event.getCode() == KeyCode.Y) && event.isShortcutDown())
                event.consume();
        });
    }
    
    private void setPointBuyLimit(Label abilityLabel){
        abilityLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Button minusButton = (Button) getNodeFromGridPane(pointGridPane, 1, GridPane.getRowIndex(abilityLabel));
                Button plusButton = (Button) getNodeFromGridPane(pointGridPane, 3, GridPane.getRowIndex(abilityLabel));
                if (newValue.equals("8"))
                   minusButton.setDisable(true);               
                else if (newValue.equals("15"))
                   plusButton.setDisable(true);
                else{
                    minusButton.setDisable(false);
                    plusButton.setDisable(false);
                }
                
            }
        }); 
    }
    
    //Definately feels hacky, probably a better way but it created a string of the form +2 Dex, +1 Wis from race bonuses.
    private void setupRaceBonusLabels(){
        ArrayList<String> abilityNames = new ArrayList<>(Arrays.asList("Str", "Dex", "Con", "Int", "Wis", "Char"));
        ArrayList<String> bonusConstruct = new ArrayList<>();
        String bonus = "";
        for (int i = 0; i < 6; i++){
            if (raceBonuses.get(i) < 0)
                bonusConstruct.add(raceBonuses.get(i) + " " + abilityNames.get(i));
            else if (raceBonuses.get(i) > 0)
                bonusConstruct.add("+" + raceBonuses.get(i) + " " + abilityNames.get(i));
        }
        bonus = bonusConstruct.get(0);
        for (int i = 1; i < bonusConstruct.size(); i++)
            bonus += ", " + bonusConstruct.get(i);
        raceBonusLabel.setText(bonus);
    }
    
    //bolds the button and line for current "tab".
    //sets the appropriate pane to visible and updates modifiers according to that pane
    @FXML
    private void customButton(){
        simplePane.setVisible(false);
        pointPane.setVisible(false);
        rollPane.setVisible(false);
        customPane.setVisible(true);
        customBut.setDisable(true);
        simpleBut.setDisable(false);
        pointBut.setDisable(false);
        rollBut.setDisable(false);
        customBut.setStyle("-fx-font-weight: bold");
        simpleBut.setStyle("-fx-font-weight: normal");
        pointBut.setStyle("-fx-font-weight: normal");
        rollBut.setStyle("-fx-font-weight: normal");
        customLine.setStrokeWidth(3);
        simpleLine.setStrokeWidth(1);
        pointLine.setStrokeWidth(1);
        rollLine.setStrokeWidth(1);
        setModifiers(Integer.parseInt(0 + customStrTF.getText()), Integer.parseInt(0 + customDexTF.getText()), Integer.parseInt(0 + customConTF.getText()), 
                     Integer.parseInt(0 + customIntTF.getText()), Integer.parseInt(0+ customWisTF.getText()), Integer.parseInt(0 + customCharTF.getText())); 
        
        //diable next button if any of the textfields are left blank
        if (customStrTF.getText().equals("") || customDexTF.getText().equals("") || customConTF.getText().equals("") || 
            customIntTF.getText().equals("") || customWisTF.getText().equals("") || customCharTF.getText().equals(""))
            nextBut.setDisable(true);
    }
    
    @FXML
    private void simpleButton(){
        pointPane.setVisible(false);
        rollPane.setVisible(false);
        customPane.setVisible(false);
        simplePane.setVisible(true);
        customBut.setDisable(false);
        simpleBut.setDisable(true);
        pointBut.setDisable(false);
        rollBut.setDisable(false);
        customBut.setStyle("-fx-font-weight: normal;");
        simpleBut.setStyle("-fx-font-weight: bold;");
        pointBut.setStyle("-fx-font-weight: normal;");
        rollBut.setStyle("-fx-font-weight: normal;");
        customLine.setStrokeWidth(1);
        simpleLine.setStrokeWidth(3);
        pointLine.setStrokeWidth(1);
        rollLine.setStrokeWidth(1);
        abilities = getAbilitiesFromGrid(simplePane);
        setModifiers(abilities.get(0), abilities.get(1), abilities.get(2), abilities.get(3), abilities.get(4), abilities.get(5));
        nextBut.setDisable(false);
    }
    
    @FXML
    private void pointButton(){
        simplePane.setVisible(false);
        rollPane.setVisible(false);
        customPane.setVisible(false);
        pointPane.setVisible(true);
        customBut.setDisable(false);
        simpleBut.setDisable(false);
        pointBut.setDisable(true);
        rollBut.setDisable(false);
        customBut.setStyle("-fx-font-weight: normal");
        simpleBut.setStyle("-fx-font-weight: normal");
        pointBut.setStyle("-fx-font-weight: bold");
        rollBut.setStyle("-fx-font-weight: normal");
        customLine.setStrokeWidth(1);
        simpleLine.setStrokeWidth(1);
        pointLine.setStrokeWidth(3);
        rollLine.setStrokeWidth(1);
        setModifiers(Integer.parseInt(pointStrScoreLabel.getText()), Integer.parseInt(pointDexScoreLabel.getText()), 
                     Integer.parseInt(pointConScoreLabel.getText()), Integer.parseInt(pointIntScoreLabel.getText()), 
                     Integer.parseInt(pointWisScoreLabel.getText()), Integer.parseInt(pointCharScoreLabel.getText()));
        if (!pointsRemaining.getText().equals("0"))
            nextBut.setDisable(true);
    }
    
    @FXML
    private void rollButton(){
        simplePane.setVisible(false);
        pointPane.setVisible(false);
        customPane.setVisible(false);
        rollPane.setVisible(true);
        customBut.setDisable(false);
        simpleBut.setDisable(false);
        pointBut.setDisable(false);
        rollBut.setDisable(true);
        customBut.setStyle("-fx-font-weight: normal");
        simpleBut.setStyle("-fx-font-weight: normal");
        pointBut.setStyle("-fx-font-weight: normal");
        rollBut.setStyle("-fx-font-weight: bold");
        customLine.setStrokeWidth(1);
        simpleLine.setStrokeWidth(1);
        pointLine.setStrokeWidth(1);
        rollLine.setStrokeWidth(3);
        nextBut.setDisable(false);
        abilities = getAbilitiesFromGrid(simplePane);
        setModifiers(abilities.get(0), abilities.get(1), abilities.get(2), abilities.get(3), abilities.get(4), abilities.get(5));
        nextBut.setDisable(false);
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && row == GridPane.getRowIndex(node)) {
                return node;
            }
        }
        return null;
    }
    
    //used to get the ability score from the simple and roll gridpanes
    //return an array of format (strengthScore, dexterityScore, constitutionScore, intelligenceScore, wisdomScore, charismaScore)
    private ArrayList<Integer> getAbilitiesFromGrid(GridPane gp){
        ArrayList<Integer> abilityArray = new ArrayList<>(Arrays.asList(0,0,0,0,0,0));
        for (int i = 0; i < 6; i++){
            //Ability scores are stored in columnIndex 0, ability names are stored in columnIndex 2
            String ability = ((Label) getNodeFromGridPane(gp, 2, i)).getText();
            String abilityScore = ((Label) getNodeFromGridPane(gp, 0,i)).getText();
            switch (ability){
                case "Strength":
                    abilityArray.set(0, Integer.parseInt(abilityScore));
                    break;
                case "Dexterity":
                    abilityArray.set(1, Integer.parseInt(abilityScore));
                    break;
                case "Constitution":
                    abilityArray.set(2, Integer.parseInt(abilityScore));
                    break;
                case "Intelligence":
                    abilityArray.set(3, Integer.parseInt(abilityScore));
                    break;
                case "Wisdom":
                    abilityArray.set(4, Integer.parseInt(abilityScore));
                    break;
                case "Charisma":
                    abilityArray.set(5, Integer.parseInt(abilityScore));
                    break;
                default:
                    System.out.println("Misspelling");
                    break;
            }
        }            
        return abilityArray;
    }
    
    //Function from DnD rulebook on how to create modifiers
    private void setModifiers(int str, int dex, int con, int iq, int wis, int cha){       
        strModLabel.setText(Integer.toString((int) Math.floor((str + raceBonuses.get(0) - 10) / 2.0)));
        dexModLabel.setText(Integer.toString((int) Math.floor((dex + raceBonuses.get(1) - 10) / 2.0)));
        conModLabel.setText(Integer.toString((int) Math.floor((con + raceBonuses.get(2) - 10) / 2.0)));
        intModLabel.setText(Integer.toString((int) Math.floor((iq + raceBonuses.get(3)- 10) / 2.0)));
        wisModLabel.setText(Integer.toString((int) Math.floor((wis + raceBonuses.get(4) - 10) / 2.0)));
        charModLabel.setText(Integer.toString((int) Math.floor((cha + raceBonuses.get(5) - 10) / 2.0)));
    }
    
    //first down and second up
    @FXML
    private void moveFirstDownSimplePane(ActionEvent ae){
        switchAbilityLabels(simplePane, "firstDown");
    }
    
    //second down and third up
    @FXML
    private void moveSecondDownSimplePane(){
        switchAbilityLabels(simplePane, "secondDown");
    }
    
    //third down and fourth up
    @FXML
    private void moveThirdDownSimplePane(){
        switchAbilityLabels(simplePane, "thirdDown");
    }
    
    //fourth down and fifth up
    @FXML
    private void moveFourthDownSimplePane(){
        switchAbilityLabels(simplePane, "fourthDown");
    }
    
    //fifth down and sixth up
    @FXML
    private void moveFifthDownSimplePane(){
        switchAbilityLabels(simplePane, "fifthDown");
    }
    
    //first down and second up
    @FXML
    private void moveFirstDownRollPane(ActionEvent ae){
        switchAbilityLabels(rollGridPane, "firstDown");
    }
    
    //second down and third up
    @FXML
    private void moveSecondDownRollPane(){
        switchAbilityLabels(rollGridPane, "secondDown");
    }
    
    //third down and fourth up
    @FXML
    private void moveThirdDownRollPane(){
        switchAbilityLabels(rollGridPane, "thirdDown");
    }
    
    //fourth down and fifth up
    @FXML
    private void moveFourthDownRollPane(){
        switchAbilityLabels(rollGridPane, "fourthDown");
    }
    
    //fifth down and sixth up
    @FXML
    private void moveFifthDownRollPane(){
        switchAbilityLabels(rollGridPane, "fifthDown");
    }
    
    public void switchAbilityLabels(GridPane gp, String buttonPressed){
        Node node1;
        Node node2;
        switch(buttonPressed){
            case "firstDown":
                node1 = getNodeFromGridPane(gp, 2, 0);
                node2 = getNodeFromGridPane(gp, 2, 1);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 0);
                gp.add(node1, 2, 1);
                break;
            case "secondDown":
                node1 = getNodeFromGridPane(gp, 2, 1);
                node2 = getNodeFromGridPane(gp, 2, 2);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 1);
                gp.add(node1, 2, 2);
                break;
            case "thirdDown":
                node1 = getNodeFromGridPane(gp, 2, 2);
                node2 = getNodeFromGridPane(gp, 2, 3);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 2);
                gp.add(node1, 2, 3);
                break;
            case "fourthDown":
                node1 = getNodeFromGridPane(gp, 2, 3);
                node2 = getNodeFromGridPane(gp, 2, 4);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 3);
                gp.add(node1, 2, 4);
                break;
            case "fifthDown":
                node1 = getNodeFromGridPane(gp, 2, 4);
                node2 = getNodeFromGridPane(gp, 2, 5);
                gp.getChildren().removeAll(node1, node2);
                gp.add(node2, 2, 4);
                gp.add(node1, 2, 5);
                break;
            default:
                System.out.println("Misspelling");
        }
        abilities = getAbilitiesFromGrid(gp);
        setModifiers(abilities.get(0), abilities.get(1), abilities.get(2), abilities.get(3), abilities.get(4), abilities.get(5));
    }
    
    @FXML
    private void increaseStr(){
        increaseAbilitiyPoint(pointStrScoreLabel);
    }
    
    @FXML
    private void increaseDex(){
        increaseAbilitiyPoint(pointDexScoreLabel);
    }
    
    @FXML
    private void increaseCon(){
        increaseAbilitiyPoint(pointConScoreLabel);
    }
    
    @FXML
    private void increaseInt(){
        increaseAbilitiyPoint(pointIntScoreLabel);
    }
    
    @FXML
    private void increaseWis(){
        increaseAbilitiyPoint(pointWisScoreLabel);
    }
    
    @FXML
    private void increaseChar(){
        increaseAbilitiyPoint(pointCharScoreLabel);
    }
    
    @FXML
    private void decreaseStr(){
        decreaseAbilitiyPoint(pointStrScoreLabel);
    }
    
    @FXML
    private void decreaseDex(){
        decreaseAbilitiyPoint(pointDexScoreLabel);
    }
    
    @FXML
    private void decreaseCon(){
        decreaseAbilitiyPoint(pointConScoreLabel);
    }
    
    @FXML
    private void decreaseInt(){
        decreaseAbilitiyPoint(pointIntScoreLabel);
    }
    
    @FXML
    private void decreaseWis(){
        decreaseAbilitiyPoint(pointWisScoreLabel);
    }
    
    @FXML
    private void decreaseChar(){
        decreaseAbilitiyPoint(pointCharScoreLabel);
    }
    
    private void increaseAbilitiyPoint(Label abilityLabel){
        abilityLabel.setText(Integer.toString(Integer.parseInt(abilityLabel.getText())+1));
        pointsRemaining.setText(Integer.toString(Integer.parseInt(pointsRemaining.getText())-1));
        setModifiers(Integer.parseInt(pointStrScoreLabel.getText()), Integer.parseInt(pointDexScoreLabel.getText()), 
                     Integer.parseInt(pointConScoreLabel.getText()), Integer.parseInt(pointIntScoreLabel.getText()), 
                     Integer.parseInt(pointWisScoreLabel.getText()), Integer.parseInt(pointCharScoreLabel.getText()));
    }
    
    private void decreaseAbilitiyPoint(Label abilityLabel){
        abilityLabel.setText(Integer.toString(Integer.parseInt(abilityLabel.getText())-1));
        pointsRemaining.setText(Integer.toString(Integer.parseInt(pointsRemaining.getText())+1));
        setModifiers(Integer.parseInt(pointStrScoreLabel.getText()), Integer.parseInt(pointDexScoreLabel.getText()), 
                     Integer.parseInt(pointConScoreLabel.getText()), Integer.parseInt(pointIntScoreLabel.getText()), 
                     Integer.parseInt(pointWisScoreLabel.getText()), Integer.parseInt(pointCharScoreLabel.getText()));
    }
    
    //According to DnD rules, you're supposed to roll 4d6 and drop the lowest
    //Do this for all 6 abilities and then assign the values to whichever you want
    //Instead of generating 4 numbers and following those steps, this just
    //generates 6 random numbers between [3, 18]
    //This method has the chance to get the highest score (18) but also the lowest (3)
    @FXML
    private void rollRandomizeScores(){
        Random rand = new Random();
        rollAbilityValue1.setText(Integer.toString(rand.nextInt((18 - 3) + 1) + 3)); //[3,18]
        rollAbilityValue2.setText(Integer.toString(rand.nextInt((18 - 3) + 1) + 3));
        rollAbilityValue3.setText(Integer.toString(rand.nextInt((18 - 3) + 1) + 3));
        rollAbilityValue4.setText(Integer.toString(rand.nextInt((18 - 3) + 1) + 3));
        rollAbilityValue5.setText(Integer.toString(rand.nextInt((18 - 3) + 1) + 3));
        rollAbilityValue6.setText(Integer.toString(rand.nextInt((18 - 3) + 1) + 3));
        
        //update ability modifiers from changes
        abilities = getAbilitiesFromGrid(rollGridPane);
        setModifiers(abilities.get(0), abilities.get(1), abilities.get(2), abilities.get(3), abilities.get(4), abilities.get(5));
    }
    
    @FXML
    private void nextButton(){
        prevWindows.add("AbilityScores.fxml");
        if (customPane.isVisible()){
            character.setStrScore(Integer.parseInt(customStrTF.getText()) + character.getStrRaceBonus());
            character.setDexScore(Integer.parseInt(customDexTF.getText()) + character.getDexRaceBonus());
            character.setConScore(Integer.parseInt(customConTF.getText()) + character.getConRaceBonus());
            character.setIntScore(Integer.parseInt(customIntTF.getText()) + character.getIntRaceBonus());
            character.setWisScore(Integer.parseInt(customWisTF.getText()) + character.getWisRaceBonus());
            character.setCharScore(Integer.parseInt(customCharTF.getText()) + character.getCharRaceBonus());
            character.calculateAndSetModifiers();
        }
        else if (simplePane.isVisible()){
            abilities = getAbilitiesFromGrid(simplePane);
            character.setStrScore(abilities.get(0) + character.getStrRaceBonus());
            character.setDexScore(abilities.get(1) + character.getDexRaceBonus());
            character.setConScore(abilities.get(2) + character.getConRaceBonus());
            character.setIntScore(abilities.get(3) + character.getIntRaceBonus());
            character.setWisScore(abilities.get(4) + character.getWisRaceBonus());
            character.setCharScore(abilities.get(5) + character.getCharRaceBonus());
            character.calculateAndSetModifiers();
        }
        else if (pointPane.isVisible()){
            character.setStrScore(Integer.parseInt(pointStrScoreLabel.getText()) + character.getStrRaceBonus());
            character.setDexScore(Integer.parseInt(pointDexScoreLabel.getText()) + character.getDexRaceBonus());
            character.setConScore(Integer.parseInt(pointConScoreLabel.getText()) + character.getConRaceBonus());
            character.setIntScore(Integer.parseInt(pointIntScoreLabel.getText()) + character.getIntRaceBonus());
            character.setWisScore(Integer.parseInt(pointWisScoreLabel.getText()) + character.getWisRaceBonus());
            character.setCharScore(Integer.parseInt(pointCharScoreLabel.getText()) + character.getCharRaceBonus());
            character.calculateAndSetModifiers();
        }
        else if (rollPane.isVisible()){
            abilities = getAbilitiesFromGrid(rollGridPane);
            character.setStrScore(abilities.get(0) + character.getStrRaceBonus());
            character.setDexScore(abilities.get(1) + character.getDexRaceBonus());
            character.setConScore(abilities.get(2) + character.getConRaceBonus());
            character.setIntScore(abilities.get(3) + character.getIntRaceBonus());
            character.setWisScore(abilities.get(4) + character.getWisRaceBonus());
            character.setCharScore(abilities.get(5) + character.getCharRaceBonus());
            character.calculateAndSetModifiers();
        }
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/HitPointsMenu.fxml"));
            root = loader.load();
            HitPointsMenuController hitPointsCtrl = loader.getController();
            hitPointsCtrl.setCharacter(character);
            hitPointsCtrl.setPreviousWindows(prevWindows);
            switchScene(root);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
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
            
            switch (prevWindow){
                case "ClassMenus/BarbarianSkillsMenu.fxml":
                    BarbarianSkillsMenuController barbSkillsMenuCtrl = loader.getController();
                    //set BarbarianSkillsMenu checkboxes to be user's previous choice
                    //remove previous skills from character
                    barbSkillsMenuCtrl.setCheckBoxOnSceneReload(character.getLastSkill());
                    character.RemoveLastSkill();
                    barbSkillsMenuCtrl.setCheckBoxOnSceneReload(character.getLastSkill());
                    character.RemoveLastSkill();
                    barbSkillsMenuCtrl.setCharacter(character);
                    barbSkillsMenuCtrl.setPreviousWindows(prevWindows); 
            }
            
            switchScene(root);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
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


    public void setCharacter(dnd.Character r){
        character = new dnd.Character(r);
        raceBonuses = new ArrayList<>(Arrays.asList(character.getStrRaceBonus(), character.getDexRaceBonus(), character.getConRaceBonus(), 
                                                    character.getIntRaceBonus(), character.getWisRaceBonus(), character.getCharRaceBonus()));
        setupRaceBonusLabels();
        setModifiers(Integer.parseInt(0 + customStrTF.getText()), Integer.parseInt(0 + customDexTF.getText()), 
                     Integer.parseInt(0 + customConTF.getText()), Integer.parseInt(0 + customIntTF.getText()), 
                     Integer.parseInt(0 + customWisTF.getText()), Integer.parseInt(0 + customCharTF.getText()));             
    }
   
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
}
