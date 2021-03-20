package department;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class MainController implements Initializable {

    @FXML
    private TextField id;

    @FXML
    private TextField Deptname;

    @FXML
    private TextField Age;

    @FXML
    private TextField Address;

    @FXML
    private TableView<Record> table;

    @FXML
    private TableColumn<Record, Integer> col_id;

    @FXML
    private TableColumn<Record, String> col_dept;

    @FXML
    private TableColumn<Record, Integer> col_age;

    @FXML
    private TableColumn<Record, String> col_address;

    Connection con;
    PreparedStatement pst;
    Statement state;

  

    public static Connection connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/dept", "root", "");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : " + ex.getMessage());
            return null;
        }
    }

    @FXML
    public void Add(ActionEvent event) {

        int ID = Integer.parseInt(id.getText());
        String NAME = Deptname.getText();
        int AGE = Integer.parseInt(Age.getText());
        String ADDRESS = Address.getText();

        String sql = "INSERT INTO `department`(`id`, `name`, `address`, `age`) VALUES (? , ? , ? ,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, ID);
            pst.setString(2, NAME);
            pst.setString(3, ADDRESS);
            pst.setInt(4, AGE);

            int status = pst.executeUpdate();
            if (status == 1) {
                JOptionPane.showMessageDialog(null, "Data Added Sucssecfully");
                table.setItems(getAlldata());
                id.setText("");
                Deptname.setText("");
                Age.setText("");
                Address.setText("");
                id.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete() {

        String sql = "delete from department where id = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(id.getText()));
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            id.setText("");
            Deptname.setText("");
            Age.setText("");
            Address.setText("");
            id.requestFocus();
            table.setItems(getAlldata());
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void clickTable(Event e) {
        Record p = (Record) table.getSelectionModel().getSelectedItem();
        id.setText(p.getId() + "");
        Deptname.setText(p.getDept_name());
        Address.setText(p.getAddress());
        Age.setText(p.getAge() + "");

        

    }

    public void Update(Event e) {

        
        Record u = new Record();
        try {
            int ID = Integer.parseInt(id.getText());
            String NAME = Deptname.getText();
            int AGE = Integer.parseInt(Age.getText());
            String ADDRESS = Address.getText();
            String sql = "UPDATE `department` SET `id`='" + ID + "',`name`='" + NAME + "',`address`='" + ADDRESS + "',`age`='" + AGE + "'where `id`='" + ID + "'";

            pst = con.prepareStatement(sql);
            pst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(null, "Update");
        id.setText("");
        Deptname.setText("");
        Age.setText("");
        Address.setText("");
        id.requestFocus();
        table.setItems(getAlldata());

    }

    public ObservableList<Record> getAlldata() {
        ObservableList<Record> data = FXCollections.observableArrayList();
        try {
            state = connection().createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM `department`");

            while (result.next()) {

                Record pr = new Record();
                pr.setId(result.getInt(1));
                pr.setDept_name(result.getString(2));
                pr.setAddress(result.getString(3));
                pr.setAge(result.getInt(4));

                data.add(pr);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);

        }

        return data;
    }

    @FXML
    public void Exit(ActionEvent ev) throws IOException {

       
        Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();
        JOptionPane.showMessageDialog(null, "Thank you for using our service");
        window.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = connection();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_dept.setCellValueFactory(new PropertyValueFactory<>("Dept_name"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("Age"));

        table.setItems(getAlldata());

    }

}
