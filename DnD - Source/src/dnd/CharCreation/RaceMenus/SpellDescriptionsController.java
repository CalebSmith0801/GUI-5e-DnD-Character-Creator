package dnd.CharCreation.RaceMenus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Caleb
 */
public class SpellDescriptionsController implements Initializable {

    @FXML private WebView wv;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setFile(String fileName){
        wv.getEngine().loadContent(readDataFile(fileName));
    }
    
    //Given relative file path from the Data folder
    //returns html of that specific file
    public String readDataFile(String Name){
        String line = "";
        try {
            Scanner scanner = new Scanner(new FileInputStream("Data/Spells/"+Name+".html"));
            
            while (scanner.hasNextLine()){
                line += scanner.nextLine();
            }
           
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return line;
    }
}
