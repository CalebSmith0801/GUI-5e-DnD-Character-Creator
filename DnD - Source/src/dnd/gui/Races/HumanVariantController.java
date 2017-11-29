/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.gui.Races;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;

/**
 * FXML Controller class
 *
 * @author Caleb
 */
public class HumanVariantController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setCharacter(dnd.Character c){
        character = new Character(c);
    }
    
    //Needed so back button knows which window to transition to since the ExtraLanguageMenu
    //is used in multiple combinations
    public void setPreviousWindows(ArrayList<String> list){
        prevWindows = new ArrayList(list);
    }
    
    public void testPrint(){
        character.printCharacter();
        System.out.println(prevWindows.toString());
    }
    
}
