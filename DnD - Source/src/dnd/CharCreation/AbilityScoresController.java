package dnd.CharCreation;

import dnd.CharCreation.ClassMenus.HitPointsController;
import dnd.Character;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Caleb
 */
public class AbilityScoresController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private Button customBut;
    @FXML private Button simpleBut;
    @FXML private Button pointBut;
    @FXML private Button rollBut;
    @FXML Label raceBonus1;
    @FXML Label raceBonus2;
    @FXML Label strModLabel;
    @FXML Label dexModLabel;
    @FXML Label conModLabel;
    @FXML Label intModLabel;
    @FXML Label wisModLabel;
    @FXML Label charModLabel;
    @FXML Label simpleStr;
    @FXML Label simpleDex;
    @FXML Label simpleCon;
    @FXML Label simpleInt;
    @FXML Label simpleWis;
    @FXML Label simpleChar;
    @FXML Label pointStr;
    @FXML Label pointDex;
    @FXML Label pointCon;
    @FXML Label pointInt;
    @FXML Label pointWis;
    @FXML Label pointChar;
    @FXML Label pointsRemaining;
    @FXML Label rollLabel1;
    @FXML Label rollLabel2;
    @FXML Label rollLabel3;
    @FXML Label rollLabel4;
    @FXML Label rollLabel5;
    @FXML Label rollLabel6;
    @FXML Label rollStr;
    @FXML Label rollDex;
    @FXML Label rollCon;
    @FXML Label rollInt;
    @FXML Label rollWis;
    @FXML Label rollChar;
    @FXML Line customLine;
    @FXML Line simpleLine;
    @FXML Line pointLine;
    @FXML Line rollLine;
    @FXML GridPane customPane;
    @FXML GridPane simplePane;
    @FXML AnchorPane pointPane;
    @FXML AnchorPane rollPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
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
    }

    @FXML
    private void nextButton(){
        prevWindows.add("ClassMenus/BarbarianSkillsMenu.fxml");
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dnd/CharCreation/ClassMenus/HitPoints.fxml"));
            root = loader.load();
            HitPointsController hitPointsCtrl = loader.getController();
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
            ClassMenuController classMenuCtrl = loader.getController();
            character.setHitDice("");
            character.RemoveLastArmorProficiency(); //shields
            character.RemoveLastArmorProficiency(); //medium armor
            character.RemoveLastArmorProficiency(); //light armor
            character.RemoveLastWeaponProficiency(); //martial weapons
            character.RemoveLastWeaponProficiency(); //simple weapons
            character.RemoveSave("Strength");
            character.RemoveSave("Constitution");
            character.RemoveTrait("Rage");
            character.RemoveTrait("Unarmored Defense");
            classMenuCtrl.setCharacter(character);
            classMenuCtrl.setPreviousWindows(prevWindows);
            classMenuCtrl.ReloadScene(character.getclassName());
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
    }
   
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
}
