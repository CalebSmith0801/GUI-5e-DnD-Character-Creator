package dnd.gui.Races;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import dnd.Character;

/**
 * FXML Controller class
 *
 * @author calebs
 */
public class HobgoblinController implements Initializable {

    private Character character;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setCharacter(Character c){
        character = new Character(c);
    }
}
