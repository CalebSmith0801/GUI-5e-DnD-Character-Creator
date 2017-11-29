/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.gui.Races;

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
public class HalfElfVariantSpellDescriptionsController implements Initializable {

    @FXML private WebView wv;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wv.getEngine().loadContent(readDataFile("HalfElfVariantSpells"));
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
}
