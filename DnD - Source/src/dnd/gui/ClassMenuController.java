
package dnd.gui;

import dnd.Character;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

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
    @FXML private Label popupTitleLabel;
    @FXML private Label popupContentLabel;
    @FXML private WebView wv;
    @FXML private ImageView classPic;
    @FXML private Button nextBut;
    @FXML private Button backBut;
    @FXML private AnchorPane popup;
    @FXML private AnchorPane window;
    
    
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
    
    }
    
    @FXML
    private void popupClose(ActionEvent event){
        popup.setVisible(false);
        window.setEffect(null);
    }
}
