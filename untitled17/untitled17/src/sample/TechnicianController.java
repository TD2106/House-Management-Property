package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Created by Hai Pham on 4/18/2017.
 */
public class TechnicianController {
    @FXML
    private BorderPane borderTechnician;

    private TableView<ObservableList<String>> tableView;

    private DatabaseApplication databaseApplication = new DatabaseApplication();

    private int technicianID = 8;

    private CallableStatement statement;

    public void initialize(){
        String query = "SELECT Person.First_name + ' ' + Person.Last_name AS Name, PhoneNumber.PhoneNumber, Employee.Salary, Skill.Skill \n" +
                "FROM PhoneNumber \n" +
                "JOIN Person ON PhoneNumber.SSN = Person.SSN \n" +
                "JOIN Employee ON Person.SSN = Employee.Person_SSN \n" +
                "JOIN Skill ON Employee.Person_SSN = Skill.TechnicianID \n" +
                "WHERE Skill.TechnicianID = ?";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setInt(1, technicianID);
            tableView = databaseApplication.requestQuery(statement);
            borderTechnician.setCenter(tableView);
        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "DECLARE @technician INT \n" +
                "SELECT @technician = ?;\n" +
                "SELECT Person.First_name + ' ' + Person.Last_name AS Name, Skill.TechnicianID, Skill.Skill \n" +
                "FROM Person \n" +
                "JOIN Skill ON Person.SSN = Skill.TechnicianID \n" +
                "JOIN Work ON Skill.TechnicianID = Work.SSN \n" +
                "WHERE Skill.TechnicianID != @technician AND Work.ID_Building IN (SELECT Work.ID_Building FROM Work JOIN Technician ON Work.SSN = TechnicianID WHERE Technician.TechnicianID = @technician)";
        statement = null;
        try {
            statement = databaseApplication.prepareCall(query);
            statement.setInt(1, technicianID);
            tableView = databaseApplication.requestQuery(statement);
            borderTechnician.setBottom(tableView);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
