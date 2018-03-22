package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Hai Pham on 4/18/2017.
 */
public class TenantController {
    @FXML
    private TextField bankAccount;

    @FXML
    private TextField nextOfKin;

    @FXML
    private TextField firstPhone;

    @FXML
    private TextField secondPhone;

    @FXML
    private TextField thirdPhone;

    @FXML
    private BorderPane leftPane;

    private TableView<ObservableList<String>> tableView;

    private DatabaseApplication databaseApplication = new DatabaseApplication();

    private int tenantID = 2;

    private Vector<Vector<String>> data;
    private CallableStatement statement;
    private String phone1, phone2, phone3;

    public void initialize() throws SQLException {

        String query = "SELECT * FROM Agreement WHERE ID_Agreement = (SELECT Agreement.ID_Agreement FROM Agreement JOIN Signs ON Agreement.ID_Agreement = Signs.ID_Agreement JOIN Tenant ON Signs.TenantID = Tenant.TenantID WHERE Tenant.TenantID = ?)";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setInt(1, tenantID);
            tableView = databaseApplication.requestQuery(statement);
            leftPane.setCenter(tableView);


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        query = "DECLARE @Tenantid INT \n" +
                "SELECT @Tenantid = ?; \n" +
                "WITH Building_Apartment(Building, Apartment) AS (SELECT ID_Building, ID_Apartment FROM Tenant WHERE TenantID = @Tenantid)\n" +
                "SELECT TOP 1 Person.First_name + ' ' + Person.Last_name AS Name, PhoneNumber.PhoneNumber \n" +
                "FROM Person JOIN PhoneNumber ON Person.SSN = PhoneNumber.SSN \n" +
                "WHERE Person.SSN IN (SELECT Tenant.TenantID \n" +
                "\t\t\t\t\tFROM Tenant \n" +
                "\t\t\t\t\tJOIN Building_Apartment \n" +
                "\t\t\t\t\tON Tenant.ID_Building = Building_Apartment.Building AND Tenant.ID_Apartment = Building_Apartment.Apartment \n" +
                "\t\t\t\t\tWHERE Tenant.TenantID != @Tenantid AND Tenant.ID_Building = Building_Apartment.Building AND Tenant.ID_Apartment = Building_Apartment.Apartment)";
                statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setInt(1, tenantID);
            tableView = databaseApplication.requestQuery(statement);
            leftPane.setBottom(tableView);
        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "SELECT TOP 1 BankAccount FROM Tenant WHERE TenantID = ?";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setInt(1, tenantID);
            data = databaseApplication.requestQueryVector(statement);
            bankAccount.setText(data.get(1).get(0));
        }catch (SQLException e){
            e.printStackTrace();
        }
        query = "SELECT TOP 1 Next_of_kin.PhoneNumber FROM Tenant JOIN Next_of_kin ON Tenant.TenantID = Next_of_kin.TenantID WHERE Tenant.TenantID = ?";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setInt(1, tenantID);
            data = databaseApplication.requestQueryVector(statement);
            nextOfKin.setText(data.get(1).get(0));
        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "SELECT PhoneNumber.PhoneNumber FROM Tenant JOIN PhoneNumber ON Tenant.TenantID = PhoneNumber.SSN WHERE Tenant.TenantID = ?";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setInt(1, tenantID);
            data = databaseApplication.requestQueryVector(statement);
            phone1 = data.get(1).get(0);
            phone2 = data.get(2).get(0);
            firstPhone.setText(data.get(1).get(0));
            secondPhone.setText(data.get(2).get(0));
            //thirdPhone.setText(data.get(3).get(0));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void updateBankAccount(){
        String bankAcc = bankAccount.getText();
        String query = "UPDATE Tenant\n" +
                "SET BankAccount = ?\n" +
                "WHERE TenantID = ?";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setString(1, bankAcc);
            statement.setInt(2, tenantID);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void updateNextOfKin(){
        String phone = nextOfKin.getText();
        String query = "UPDATE Next_of_kin SET PhoneNumber = ? WHERE TenantID = ?";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setString(1, phone);
            statement.setInt(2, tenantID);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void updateFirstPhone(){
        String phone = firstPhone.getText();
        String query = "UPDATE PhoneNumber SET PhoneNumber.PhoneNumber = ? WHERE PhoneNumber.PhoneNumber = ? AND PhoneNumber.SSN = (SELECT Tenant.TenantID FROM Tenant WHERE Tenant.TenantID = ?)";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setString(1, phone);
            statement.setString(2, phone1);
            statement.setInt(3, tenantID);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        phone1 = phone;
    }

    @FXML
    public void updateSecondPhone(){
        String phone = secondPhone.getText();
        String query = "UPDATE PhoneNumber SET PhoneNumber.PhoneNumber = ? WHERE PhoneNumber.PhoneNumber = ? AND PhoneNumber.SSN = (SELECT Tenant.TenantID FROM Tenant WHERE Tenant.TenantID = ?)";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setString(1, phone);
            statement.setString(2, phone2);
            statement.setInt(3, tenantID);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        phone2 = phone;
    }
}
