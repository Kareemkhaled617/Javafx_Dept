
package department;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AboutUsController implements Initializable {

    @FXML
    public void Back(ActionEvent ev) throws IOException {

        Parent next1 = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene loginview = new Scene(next1);
        Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();
        window.setScene(loginview);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
