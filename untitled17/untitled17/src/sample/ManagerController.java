package sample;

import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Created by Hai Pham on 4/18/2017.
 */
public class ManagerController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tenantTab;

    @FXML
    private Tab technicianTab;

    @FXML
    private Tab apartmentTab;

    @FXML
    private Tab buildingTab;

    @FXML
    private Tab agreementTab;

    @FXML
    private VBox labelBoxTenant;

    @FXML
    private VBox textFieldBoxTenant;

    @FXML
    private VBox labelBoxBuilding;

    @FXML
    private VBox textFieldBoxBuilding;

    @FXML
    private VBox labelBoxTechnician;

    @FXML
    private VBox textFieldBoxTechnician;

    @FXML
    private VBox labelBoxApartment;

    @FXML
    private VBox textFieldBoxApartment;

    @FXML
    private VBox labelBoxAgreement;

    @FXML
    private VBox textFieldBoxAgreement;

    @FXML
    private TextField tenantErrorField;

    @FXML
    private TextField buildingErrorField;

    @FXML
    private TextField technicianErrorField;

    @FXML
    private TextField apartmentErrorField;

    @FXML
    private TextField agreementErrorField;

    @FXML
    private BorderPane tenantInfoPane;

    @FXML
    private BorderPane buildingInfoPane;

    @FXML
    private BorderPane technicianInfoPane;

    @FXML
    private BorderPane apartmentInfoPane;

    @FXML
    private BorderPane agreementInfoPane;

    private DatabaseApplication databaseApplication = new DatabaseApplication();

    private boolean isTenantTabCreated = false;
    private boolean isBuildingTabCreated = false;
    private boolean isTechnicianTabCreated = false;
    private boolean isApartmentTabCreated = false;
    private boolean isAgreementTabCreated = false;

    private int managerID = 15070;

    public ManagerController() {}

    public void initialize() {
        tenantErrorField.setStyle("-fx-text-fill: red;");

        tabPane.getTabs().remove(technicianTab);
        tabPane.getTabs().remove(tenantTab);
        tabPane.getTabs().remove(apartmentTab);
        tabPane.getTabs().remove(buildingTab);
        tabPane.getTabs().remove(agreementTab);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    }

    @FXML
    private void openTenantTab() {
        if (!isTenantTabCreated) {
            try {
                createTenantTable();
                createTenantLeftSide();
                isTenantTabCreated = true;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (!tabPane.getTabs().contains(tenantTab)) tabPane.getTabs().add(tenantTab);
    }

    @FXML
    private void openBuildingTab() {
        if (!isBuildingTabCreated) {
            try {
                createBuildingTable();
                createBuildingLeftSide();
                isBuildingTabCreated = true;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (!tabPane.getTabs().contains(buildingTab)) tabPane.getTabs().add(buildingTab);
    }

    @FXML
    private void openTechnicianTab() {
        if (!isTechnicianTabCreated) {
            try {
                createTechnicianTable();
                createTechnicianLeftSide();
                isTechnicianTabCreated = true;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (!tabPane.getTabs().contains(technicianTab)) tabPane.getTabs().add(technicianTab);
    }

    @FXML
    private void openApartmentTab() {
        if (!isApartmentTabCreated) {
            try {
                createApartmentTable();
                createApartmentLeftSide();
                isApartmentTabCreated = true;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (!tabPane.getTabs().contains(apartmentTab)) tabPane.getTabs().add(apartmentTab);
    }

    @FXML
    private void openAgreementTab() {
        if (!isAgreementTabCreated) {
            try {
                createAgreementTable();
                createAgreementLeftSide();
                isAgreementTabCreated = true;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (!tabPane.getTabs().contains(agreementTab)) tabPane.getTabs().add(agreementTab);
    }
    //Tenant-----------------------------------------------------------------------------------------------------------
    @FXML
    private void updateTenantInfo() {
        String[] tenantInfo = this.getDataFromTextFieldBox(textFieldBoxTenant);
        if (tenantInfo == null) return;
        try {
            Tenant tenant = new Tenant(tenantInfo[0], tenantInfo[1], tenantInfo[2], tenantInfo[3], tenantInfo[4]);

            try {
                databaseApplication.setAutoCommit(false);
                tenant.update(databaseApplication);
                databaseApplication.commit();
                this.refreshTenantInfo();
            } catch (SQLException sqlException) {
                databaseApplication.rollback();
                this.setErrorMessage(sqlException.toString(), tenantErrorField);
            }
            databaseApplication.setAutoCommit(true);
        } catch (NumberFormatException formatException) {
            this.setErrorMessage(formatException.toString(), tenantErrorField);
        }
    }

    @FXML
    private void insertTenantInfo() {
        String[] tenantInfo = this.getDataFromTextFieldBox(textFieldBoxTenant);
        if (tenantInfo == null) return;

        try {
            Tenant tenant = new Tenant(tenantInfo[0], tenantInfo[1], tenantInfo[2], tenantInfo[3], tenantInfo[4], managerID, tenantInfo[5], tenantInfo[6]);

            try {
                tenant.insert();
                this.refreshTenantInfo();
            } catch (SQLException sqlException) {
                this.setErrorMessage(sqlException.toString(), tenantErrorField);
            }
        } catch (NumberFormatException formatException) {
            this.setErrorMessage(formatException.toString(), tenantErrorField);
        }
    }

    @FXML
    private void deleteTenantInfo() {
        String[] tenantInfo = this.getDataFromTextFieldBox(textFieldBoxTenant);
        if (tenantInfo == null) return;

        try {
            Tenant tenant = new Tenant(tenantInfo[0]);
            tenant.delete();
            this.refreshTenantInfo();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void filterTenantInfo() {
        String[] tenantInfo = this.getDataFromTextFieldBox(textFieldBoxTenant);
        if (tenantInfo == null) return;

        try {
            String tenantID = tenantInfo[0];
            String IDApartment = tenantInfo[1];
            String IDBuilding = tenantInfo[2];
            String bankAccount = tenantInfo[3];
            String name = tenantInfo[4];

            String query = "SELECT TenantID, ID_Apartment, ID_Building, BankAccount, First_name + ' ' + Last_name as Name FROM Tenant JOIN Person ON TenantID = SSN";
            String condition = "";
            if (!tenantID.isEmpty()) condition += " AND TenantID " + tenantID;
            if (!IDApartment.isEmpty()) condition += " AND ID_Apartment " + IDApartment;
            if (!IDBuilding.isEmpty()) condition += " AND ID_Building " + IDBuilding;
            if (!bankAccount.isEmpty()) condition += " AND BankAccount " + bankAccount;
            if (!name.isEmpty()) condition += " AND First_name + ' ' + Last_name as Name AND Name = " + name;

            createTable(query + condition, textFieldBoxTenant, tenantInfoPane, 0);
        } catch (SQLException | NumberFormatException exception) {
            this.setErrorMessage(exception.toString(), tenantErrorField);
        }
    }

    @FXML
    private void refreshTenantInfo() {
        try {
            this.createTenantTable();
        } catch (SQLException exception) {
            this.setErrorMessage(exception.toString(), tenantErrorField);
        }
    }
    //Building-------------------------------------------------------------------------------------------------------------
    @FXML
    private void updateBuildingInfo() {
        String[] buildingInfo = this.getDataFromTextFieldBox(textFieldBoxBuilding);
        if (buildingInfo == null) return;

        try {
            Building building = new Building(buildingInfo[0], buildingInfo[1], buildingInfo[2], buildingInfo[3]);

            try {
                //databaseApplication.reconnect();
                databaseApplication.setAutoCommit(false);
                building.update();
                databaseApplication.commit();
                this.refreshTenantInfo();
            } catch (SQLException sqlException) {
                databaseApplication.rollback();
                sqlException.printStackTrace();
            }
            databaseApplication.setAutoCommit(true);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void insertBuildingInfo() {
        String[] buildingInfo = this.getDataFromTextFieldBox(textFieldBoxBuilding);
        if (buildingInfo == null) return;

        try {
            Building building = new Building(buildingInfo[0], buildingInfo[1], buildingInfo[2], buildingInfo[3]);

            try {
                //databaseApplication.reconnect();
                databaseApplication.setAutoCommit(false);
                building.insert();
                databaseApplication.commit();
                this.refreshTenantInfo();
            } catch (SQLException sqlException) {
                databaseApplication.rollback();
                this.setErrorMessage(sqlException.toString(), buildingErrorField);
            }
            databaseApplication.setAutoCommit(true);
        } catch (NumberFormatException formatException) {
            this.setErrorMessage(formatException.toString(), buildingErrorField);
        }
    }

    @FXML
    private void filterBuildingInfo() {
        String[] buildingInfo = this.getDataFromTextFieldBox(textFieldBoxBuilding);
        if (buildingInfo == null) return;

        try {
            String buildingID = buildingInfo[0];
            String address = buildingInfo[1];
            String name = buildingInfo[2];
            String numRoom = buildingInfo[3];

            String query = "SELECT * FROM Building\nWHERE '1' = '1'";
            String condition = "";
            if (!buildingID.isEmpty()) condition += " AND TenantID " + buildingID;
            if (!address.isEmpty()) condition += " AND ID_Apartment " + address;
            if (!name.isEmpty()) condition += " AND ID_Building " + name;
            if (!numRoom.isEmpty()) condition += " AND BankAccount " + numRoom;

            createTable(query + condition, textFieldBoxBuilding, buildingInfoPane, 1);
        } catch (SQLException | NumberFormatException exception) {
            this.setErrorMessage(exception.toString(), buildingErrorField);
        }
    }

    @FXML
    private void refreshBuildingInfo() {
        try {
            this.createBuildingTable();
        } catch (SQLException exception) {
            this.setErrorMessage(exception.toString(), buildingErrorField);
        }
    }
    //-------------------------------------------------------------------------------------
    @FXML
    private void insertTechnicianInfo() {
        String[] technicianInfo = this.getDataFromTextFieldBox(textFieldBoxTechnician);
        if (technicianInfo == null) return;

        try {
            Technician technician = new Technician(technicianInfo[0], technicianInfo[1], technicianInfo[2], technicianInfo[3]);

            try {
                technician.insert();
                this.refreshTechnicianInfo();
            } catch (SQLException sqlException) {
                this.setErrorMessage(sqlException.toString(), technicianErrorField);
            }
        } catch (NumberFormatException formatException) {
            this.setErrorMessage(formatException.toString(), technicianErrorField);
        }
    }

    @FXML
    private void deleteTechnicianInfo() {
        String[] technicianInfo = this.getDataFromTextFieldBox(textFieldBoxTechnician);
        if (technicianInfo == null) return;

        try {
            Technician technician = new Technician(technicianInfo[0], technicianInfo[1], technicianInfo[2], technicianInfo[3]);

            try {
                technician.delete();
                this.refreshTechnicianInfo();
            } catch (SQLException sqlException) {
                this.setErrorMessage(sqlException.toString(), technicianErrorField);
            }
        } catch (NumberFormatException formatException) {
            this.setErrorMessage(formatException.toString(), technicianErrorField);
        }
    }

    @FXML
    private void updateTechnicianInfo() {
        String[] technicianInfo = this.getDataFromTextFieldBox(textFieldBoxTechnician);
        if (technicianInfo == null) return;

        try {
            Technician technician = new Technician(technicianInfo[0], technicianInfo[1], technicianInfo[2], technicianInfo[3]);

            try {
                databaseApplication.setAutoCommit(false);
                technician.update(databaseApplication);
                databaseApplication.commit();
                this.refreshTechnicianInfo();
            } catch (SQLException sqlException) {
                databaseApplication.rollback();
                this.setErrorMessage(sqlException.toString(), technicianErrorField);
            }
            databaseApplication.setAutoCommit(true);
        } catch (NumberFormatException formatException) {
            this.setErrorMessage(formatException.toString(), technicianErrorField);
        }
    }

    @FXML
    private void filterTechnicianInfo() {
        String[] technicianInfo = this.getDataFromTextFieldBox(textFieldBoxTechnician);
        if (technicianInfo == null) return;

        try {
            String technicianID = technicianInfo[0];
            String name = technicianInfo[1];
            String buildingID = technicianInfo[2];
            String salary = technicianInfo[3];

            String query = "SELECT TechnicianID, First_name + ' ' + Last_name as Name, ID_Building as BuildingID, Salary FROM Technician JOIN Work ON TechnicianID = Work.SSN JOIN Employee ON TechnicianID = Person_SSN JOIN Person ON TechnicianID = Person.SSN WHERE '1'='1'";
            String condition = "";
            if (!technicianID.isEmpty()) condition += " AND TechnicianID " + technicianID;
            if (!name.isEmpty()) condition += " AND First_name + ' ' + Last_name " + name;
            if (!buildingID.isEmpty()) condition += " AND ID_Building " + buildingID;
            if (!salary.isEmpty()) condition += " AND Salary " + salary;

            createTable(query + condition, textFieldBoxTechnician, technicianInfoPane, 3);
        } catch (SQLException | NumberFormatException exception) {
            this.setErrorMessage(exception.toString(), technicianErrorField);
        }
    }

    @FXML
    private void refreshTechnicianInfo() {
        try {
            this.createTechnicianTable();
        } catch (SQLException exception) {
            this.setErrorMessage(exception.toString(), technicianErrorField);
        }
    }
    //----------------------------------------------------------------------------------------
    @FXML
    private void insertApartmentInfo() {

    }

    @FXML
    private void updateApartmentInfo() {

    }

    @FXML
    private void filterApartmentInfo() {

    }

    @FXML
    private void refreshApartmentInfo() {

    }
    //---------------------------------------------------------------------------------------
    @FXML
    private void insertAgreementInfo() {

    }

    @FXML
    private void deleteAgreementInfo() {

    }

    @FXML
    private void updateAgreementInfo() {

    }

    @FXML
    private void filterAgreementInfo() {

    }

    @FXML
    private void refreshAgreementInfo() {

    }
    //--------------------------------------------------------------------------------------
    private void createTenantTable() throws SQLException {
        String query = "SELECT TenantID, First_name + ' ' + Last_name as Name, ID_Apartment as ApartmentID, ID_Building as BuildingID, BankAccount as [Bank Account] FROM Tenant JOIN Person ON TenantID = SSN";

        createTable(query, textFieldBoxTenant, tenantInfoPane, 0);
    }

    private void createTenantLeftSide() throws SQLException {
        String[] arr = {"TenantID", "Name", "ApartmentID", "BuildingID", "Bank Account", "Monthly Rent", "Duration"};
        Vector<String> column = new Vector<>(Arrays.asList(arr));

        createLeftSide(column, labelBoxTenant, textFieldBoxTenant);
    }

    private void createBuildingTable() throws SQLException {
        String query = "SELECT ID_Building as BuildingID, Address, Name, numRoom as [Number of room] FROM Building";

        createTable(query, textFieldBoxBuilding, buildingInfoPane, 1);
    }

    private void createBuildingLeftSide() throws SQLException {
        String query = "SELECT ID_Building as BuildingID, Address, Name, numRoom as [Number of room] FROM Building";

        createLeftSide(query, labelBoxBuilding, textFieldBoxBuilding);
    }

    private void createTechnicianTable() throws SQLException {
        String query = "SELECT TechnicianID, First_name + ' ' + Last_name as Name, ID_Building as BuildingID, Salary FROM Technician JOIN Work ON TechnicianID = Work.SSN JOIN Employee ON TechnicianID = Person_SSN JOIN Person ON TechnicianID = Person.SSN";

        createTable(query, textFieldBoxTechnician, technicianInfoPane, 3);
    }

    private void createTechnicianLeftSide() throws SQLException {
        String query = "SELECT TechnicianID, First_name + ' ' + Last_name as Name, ID_Building as BuildingID, Salary FROM Technician JOIN Work ON TechnicianID = Work.SSN JOIN Employee ON TechnicianID = Person_SSN JOIN Person ON TechnicianID = Person.SSN";

        createLeftSide(query, labelBoxTechnician, textFieldBoxTechnician);
    }

    private void createApartmentTable() throws SQLException {
        String query = "SELECT * FROM Apartment";

        createTable(query, textFieldBoxApartment, apartmentInfoPane, 2);
    }

    private void createApartmentLeftSide() throws SQLException {
        String query = "SELECT * FROM Apartment";

        createLeftSide(query, labelBoxApartment, textFieldBoxApartment);
    }

    private void createAgreementTable() throws SQLException {
        String query = "SELECT * FROM Agreement";

        createTable(query, textFieldBoxAgreement, agreementInfoPane, 4);
    }

    private void createAgreementLeftSide() throws SQLException {
        String query = "SELECT * FROM Agreement";

        createLeftSide(query, labelBoxAgreement, textFieldBoxAgreement);
    }
    //---------------------------------------------------------------------------------------------------
    private void createLeftSide(String query, VBox labelBox, VBox textFieldBox) throws SQLException {
        Vector< Vector<String> > data = databaseApplication.requestQueryVector(query);
        for (int i = 0; i < data.get(0).size(); i++) {
            labelBox.getChildren().add(newLabel(data.get(0).get(i)));
            textFieldBox.getChildren().add(newTextField(""));
        }
    }

    private void createLeftSide(Vector<String> column, VBox labelBox, VBox textFieldBox) {
        for (String columnName : column) {
            labelBox.getChildren().add(newLabel(columnName));
            textFieldBox.getChildren().add(newTextField(""));
        }
    }

    private void createTable(String query, VBox textFieldBox, BorderPane infoPane, int type) throws SQLException {
        TableView<ObservableList< String >> tableView = databaseApplication.requestQuery(query);

        tableView.setRowFactory(tv -> {
            TableRow< ObservableList<String> > row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    ObservableList<String> clickedRow = row.getItem();

                    for (int i = 0; i < clickedRow.size(); i++) {
                        TextField textField = (TextField) textFieldBox.getChildren().get(i);
                        textField.setText(clickedRow.get(i));
                    }

                    this.createSubTable(type, clickedRow, infoPane);
                }
            });
            return row;
        });

        infoPane.setCenter(tableView);
    }

    private void createSubTable(int type, ObservableList<String> clickedRow, BorderPane infoPane) { //1:building; 2:apartment

        String query = "";
        if (type == 1) {
            query = "SELECT * FROM Apartment WHERE ID_Building = ?";
        }
        if (type == 2) {
            query = "SELECT * FROM Tenant JOIN Person ON TenantID = SSN WHERE ID_Apartment = ?";
        }
        if (type == 3) {
            query = "SELECT Technician.TechnicianID, First_name + ' ' + Last_name as Name, Skill FROM Person JOIN Technician ON SSN = TechnicianID JOIN Employee ON Person_SSN = TechnicianID JOIN Skill ON Skill.TechnicianID = Technician.TechnicianID WHERE Technician.TechnicianID = ?";
        }
        if (type == 4) {
            query = "SELECT * FROM Tenant JOIN Person ON SSN = TenantID WHERE ID_Apartment = ? AND ID_Building = ?";
        }

        if (!query.isEmpty()) {
            try {
                if (type <= 3) {
                    int id = Integer.parseInt(clickedRow.get(0));
                    CallableStatement callableStatement = databaseApplication.prepareCall(query);
                    callableStatement.setInt(1, id);
                    TableView<ObservableList<String>> tableView = databaseApplication.requestQuery(callableStatement);
                    infoPane.setBottom(tableView);
                }
                else {
                    int buildingID = Integer.parseInt(clickedRow.get(1));
                    int apartmentID = Integer.parseInt(clickedRow.get(2));
                    CallableStatement callableStatement = databaseApplication.prepareCall(query);
                    callableStatement.setInt(1, buildingID);
                    callableStatement.setInt(2, apartmentID);
                    TableView<ObservableList<String>> tableView = databaseApplication.requestQuery(callableStatement);
                    infoPane.setBottom(tableView);
                }
            } catch (SQLException exception) {
                switch (type) {
                    case 1: this.setErrorMessage(exception.toString(), buildingErrorField); break;
                    case 2: this.setErrorMessage(exception.toString(), apartmentErrorField); break;
                }
            }
        }
    }

    private String[] getDataFromTextFieldBox(VBox textFieldBox) {
        boolean isEmpty = true;
        int size = textFieldBox.getChildren().size();
        String result[] = new String[size];

        for (int i = 0; i < textFieldBox.getChildren().size(); i++) {
            TextField textField = (TextField) textFieldBox.getChildren().get(i);
            result[i] = textField.getText();
            if (!result[i].isEmpty()) isEmpty = false;
        }

        return (isEmpty) ? null : result;
    }

    private void setErrorMessage(String message, TextField errorField) {
        errorField.setText(message);
    }

    private void clearErrorMessage() { tenantErrorField.clear(); }

    private Text newLabel(String label) {
        return (new Text(label));
    }

    private TextField newTextField(String data) {
        TextField dataTF = new TextField(data);
        dataTF.setPrefWidth(200);

        return dataTF;
    }

    private boolean filterEngine(String query) {
        return true;
    }
}

