package dnd.gui.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class TieflingVarController implements Initializable {

    private Character character;
    private ArrayList<String> prevWindows;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setCharacter(Character c){
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
