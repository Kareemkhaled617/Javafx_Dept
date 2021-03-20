/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package department;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class LoginController implements Initializable {
 @FXML
    TextField user;
    @FXML
    TextField pass;

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res;
    
    
     @FXML
    private void login(ActionEvent ev) {

        con =MainController.connection();
        String sql = "Select * from user where username = ? and password = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, user.getText());
            pst.setString(2, pass.getText());

            res = pst.executeQuery();
            
            

            if (res.next()) {
                JOptionPane.showMessageDialog(null, "Wellcome..........!");
                Parent next1 = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene loginview = new Scene(next1);
                Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();
                window.setScene(loginview);
                window.show();
                window.setTitle("Teck_Comp");

            } else {
                JOptionPane.showMessageDialog(null, "Login Failed , Check Your Username or Password ");
                user.setText("");
                pass.setText("");
                user.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    
    @FXML
    public void About(ActionEvent ev) throws IOException {

        Parent next1 = FXMLLoader.load(getClass().getResource("About us.fxml"));
        Scene loginview = new Scene(next1);
        Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();
        window.setScene(loginview);
        window.show();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
