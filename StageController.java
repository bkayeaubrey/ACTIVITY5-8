package activity5_8;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author WiNDows
 */
public class StageController implements Initializable {
    
    @FXML
    private TableView<Faculty> facultytable;
    @FXML
    private TableColumn<Faculty, Integer> colID;
    @FXML
    private TableColumn<Faculty, String> colFName;
    @FXML
    private TableColumn<Faculty, String> colMName;
    @FXML
    private TableColumn<Faculty, String> colLName;
    @FXML
    private TableColumn<Faculty, String> colsuffix;
    @FXML
    private TableColumn<Faculty, String> colsex;
    @FXML
    private TableColumn<Faculty, String> colcontact;
    @FXML
    private TableColumn<Faculty, String> colgmail;
    @FXML
    private TableColumn<Faculty, String> coladdress;
    @FXML
    private TableColumn<Faculty, Void> colEdit;
    @FXML
    private TextField searchBar;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfFName;
    @FXML
    private TextField tfMName;
    @FXML
    private TextField tfLName;
    @FXML
    private TextField tfSuffix;
    @FXML
    private TextField tfSex;
    @FXML
    private TextField tfContact;
    @FXML
    private TextField tfGmail;
    @FXML
    private TextField tfAddress;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private AnchorPane rootPane;
    private boolean isDarkMode = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showFaculty();
        toggleButton.setOnAction(event -> toggleMode());
        
        btnSave.setOnAction(e -> saveData());
        btnDelete.setOnAction(e -> deleteData());
        btnClear.setOnAction(e -> clearData()); 
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> searchData());
        btnUpdate.setOnAction(event -> updateData());

    colID.setCellValueFactory(new PropertyValueFactory<>("facultyID"));
    colFName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    colMName.setCellValueFactory(new PropertyValueFactory<>("middlename"));
    colLName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
    colsuffix.setCellValueFactory(new PropertyValueFactory<>("suffix"));
    colsex.setCellValueFactory(new PropertyValueFactory<>("sex"));
    colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    colgmail.setCellValueFactory(new PropertyValueFactory<>("gmail"));
    coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));

    colEdit.setCellFactory(param -> new TableCell<>() {
    private final Button editButton = new Button("Edit");

    {
        editButton.setOnAction(event -> {
            Faculty faculty = getTableView().getItems().get(getIndex());

            tfID.setText(String.valueOf(faculty.getFacultyID()));
            tfFName.setText(faculty.getFirstname());
            tfMName.setText(faculty.getMiddlename());
            tfLName.setText(faculty.getLastname());
            tfSuffix.setText(faculty.getSuffix());
            tfSex.setText(faculty.getSex());
            tfContact.setText(faculty.getContact());
            tfGmail.setText(faculty.getGmail());
            tfAddress.setText(faculty.getAddress());
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(editButton);
        }
    }
});

            } 
    
    private void setLightMode() {

        rootPane.getStyleClass().remove("dark-mode");
        rootPane.getStyleClass().add("light-mode");
 
        isDarkMode = false; // Update mode state
        toggleButton.setText("Dark Mode");
    }

    private void setDarkMode() {
       
        rootPane.getStyleClass().remove("light-mode");
        rootPane.getStyleClass().add("dark-mode");
        
        isDarkMode = true; // Update mode state
        toggleButton.setText("Light Mode");
    }

    // Method to toggle between modes
    private void toggleMode() {
        if (isDarkMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    public Connection getConnection(){
        Connection connect;
        try{
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/schedulingsystemdatabase", "root", "");
            return connect;
        }catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    
    public ObservableList<Faculty> getFacultyList(){
        ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
        Connection connect = getConnection();
        String query = "SELECT * FROM faculty";
        Statement st;
        ResultSet rs;
        
        try{
            st = connect.createStatement();
            rs = st.executeQuery(query);
            Faculty faculty;
            while(rs.next()){
                faculty = new Faculty(rs.getInt("facultyID"), rs.getString("firstname"),rs.getString("middlename"),rs.getString("lastname"), rs.getString("suffix"), rs.getString("sex"), rs.getString("contact"), rs.getString("gmail"), rs.getString("address"));
                facultyList.add(faculty);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return facultyList;
    }
    
    public void showFaculty(){
        ObservableList<Faculty> list = getFacultyList();
        
        colID.setCellValueFactory(new PropertyValueFactory<Faculty, Integer>("facultyID"));
        colFName.setCellValueFactory(new PropertyValueFactory<Faculty, String>("firstname"));
        colMName.setCellValueFactory(new PropertyValueFactory<Faculty, String>("middlename"));
        colLName.setCellValueFactory(new PropertyValueFactory<Faculty, String>("lastname"));
        colsuffix.setCellValueFactory(new PropertyValueFactory<Faculty, String>("suffix"));
        colsex.setCellValueFactory(new PropertyValueFactory<Faculty, String>("sex"));
        colcontact.setCellValueFactory(new PropertyValueFactory<Faculty, String>("contact"));
        colgmail.setCellValueFactory(new PropertyValueFactory<Faculty, String>("gmail"));
        coladdress.setCellValueFactory(new PropertyValueFactory<Faculty, String>("address"));
      
        facultytable.setItems(list);
    }
    

    private void searchData() {
    if (searchBar.getText().isEmpty()) {
        showFaculty(); // Display all records when the search bar is empty
        return;
    }

    ObservableList<Faculty> filteredList = FXCollections.observableArrayList();
    for (Faculty faculty : getFacultyList()) {
        if (faculty.getFirstname().toLowerCase().contains(searchBar.getText().toLowerCase())) {
            filteredList.add(faculty);
        }
    }
    facultytable.setItems(filteredList);
}

    
    private void clearData() {
    TextField[] textFields = {tfID, tfFName, tfMName, tfLName, tfSuffix, tfSex, tfContact, tfGmail, tfAddress};
    for (TextField field : textFields) {
        field.clear(); 
    }
}
    private void updateData() {
    String query = "UPDATE `faculty` SET "
            + "`firstname`='" + tfFName.getText() + "', "
            + "`middlename`='" + tfMName.getText() + "', "
            + "`lastname`='" + tfLName.getText() + "', "
            + "`suffix`='" + tfSuffix.getText() + "', "
            + "`sex`='" + tfSex.getText() + "', "
            + "`contact`='" + tfContact.getText() + "', "
            + "`gmail`='" + tfGmail.getText() + "', "
            + "`address`='" + tfAddress.getText() + "' "
            + "WHERE `facultyID`='" + tfID.getText() + "'";

    executeQuery(query);
    showFaculty();
}

   private boolean doesEntryExist(String gmail, String firstName, String lastName, String middleName) {
    Connection connection = getConnection();
    Statement statement = null;
    ResultSet resultSet = null;
    boolean entryExists = false;
    
    try {
        statement = connection.createStatement();
        String query = "SELECT * FROM faculty WHERE gmail = '" + gmail + "' AND firstname = '" + firstName +
                       "' AND lastname = '" + lastName + "' AND middlename = '" + middleName + "'";
        resultSet = statement.executeQuery(query);
        
        // If any result is found, the entry already exists
        entryExists = resultSet.next();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        // Close resources
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    return entryExists;
}

   private void saveData() {
    if (doesEntryExist(tfGmail.getText(), tfFName.getText(), tfLName.getText(), tfMName.getText())) {
       System.out.println("Entry already exists!");
    }else
    {

    String query = "INSERT INTO `faculty` VALUES ('" + tfID.getText() + "','" + tfFName.getText() + "','" + tfMName.getText() + "','" +
               tfLName.getText() + "','" + tfSuffix.getText() + "','" + tfSex.getText() + "','" +
               tfContact.getText() + "','" + tfGmail.getText() + "','" + tfAddress.getText() + "', '1')";
    
    executeQuery(query);
    clearData();
    showFaculty();
}
    }

    
    private void deleteData(){
        String query = "DELETE FROM faculty WHERE facultyID =" + tfID.getText() + "";
        clearData();
        executeQuery(query);
     
        showFaculty();
    }


    private void executeQuery(String query) {
        Connection connect = getConnection();
        Statement st;
        
        try{
            st = connect.createStatement();
            st.executeUpdate(query);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
            
       
}
