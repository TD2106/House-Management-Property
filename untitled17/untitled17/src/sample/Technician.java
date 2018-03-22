package sample;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 * Created by Hai Pham on 4/29/2017.
 */
public class Technician {
    private int technicianID;
    private int buildingID;
    private double salary;
    private String firstName, lastName;
    private DatabaseApplication databaseApplication = new DatabaseApplication();

    public Technician(int technicianID, String name, int buildingID, double salary) {
        this.technicianID = technicianID;
        this.salary = salary;
        this.buildingID = buildingID;

        StringTokenizer st = new StringTokenizer(name," ");
        String firstName = "";
        StringBuilder lastName = new StringBuilder();
        if (st.hasMoreTokens()) {
            firstName = st.nextToken();
        }
        if (st.hasMoreTokens()) {
            lastName = new StringBuilder(st.nextToken());
        }
        while (st.hasMoreTokens()) {
            lastName.append(" ").append(st.nextToken());
        }
        this.firstName = firstName;
        if (!lastName.toString().equals(" ")) this.lastName = lastName.toString();
    }

    public Technician(String technicianID, String name, String buildingID, String salary) throws NumberFormatException {
        this.technicianID = Integer.parseInt(technicianID);
        this.buildingID = Integer.parseInt(buildingID);
        this.salary = Double.parseDouble(salary);

        StringTokenizer st = new StringTokenizer(name," ");
        String firstName = "";
        StringBuilder lastName = new StringBuilder();
        if (st.hasMoreTokens()) {
            firstName = st.nextToken();
        }
        if (st.hasMoreTokens()) {
            lastName = new StringBuilder(st.nextToken());
        }
        while (st.hasMoreTokens()) {
            lastName.append(" ").append(st.nextToken());
        }
        this.firstName = firstName;
        if (!lastName.equals(" ")) this.lastName = lastName.toString();
    }

    public void update(DatabaseApplication databaseApplication) throws SQLException {
        this.updateBuildingID(databaseApplication);
        this.updateSalary(databaseApplication);
        this.updateName(databaseApplication);
    }

    private void updateBuildingID(DatabaseApplication databaseApplication) throws SQLException {
        String query = "UPDATE Work SET ID_Building = ? WHERE SSN = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setInt(1, buildingID);
        statement.setInt(2, technicianID);
        statement.executeUpdate();
    }

    private void updateSalary(DatabaseApplication databaseApplication) throws SQLException {
        String query = "UPDATE Employee SET Salary = ? WHERE Person_SSN = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setDouble(1, salary);
        statement.setInt(2, technicianID);
        statement.executeUpdate();
    }

    private void updateName(DatabaseApplication databaseApplication) throws SQLException {
        String query = "UPDATE Person\nSET First_name = ?, Last_name = ?\nWHERE SSN = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setInt(3, technicianID);
        statement.executeUpdate();
    }

    public void insert() throws SQLException {
        String query = "exec dbo.insTechnician ?,?,?,?,?";
        CallableStatement callableStatement = databaseApplication.prepareCall(query);
        callableStatement.setInt(1, technicianID);
        callableStatement.setString(2, firstName);
        callableStatement.setString(3, lastName);
        callableStatement.setInt(4, buildingID);
        callableStatement.setDouble(5, salary);
        callableStatement.execute();
    }

    public void delete() throws SQLException {
        String query = "exec dbo.delTechnician ?";
        CallableStatement callableStatement = databaseApplication.prepareCall(query);
        callableStatement.setInt(1, technicianID);
        callableStatement.execute();
    }

    public void insertSkill(String skill) throws SQLException {
        String query = "INSERT INTO Skill VALUES (?, ?)";
        CallableStatement callableStatement = databaseApplication.prepareCall(query);
        callableStatement.setString(1, skill);
        callableStatement.setInt(2, technicianID);
        callableStatement.execute();
    }

    public void deleteSkill(String skill) throws SQLException {
        String query = "DELETE FROM Skill WHERE Skill = ? AND TechnicianID = ?";
        CallableStatement callableStatement = databaseApplication.prepareCall(query);
        callableStatement.setString(1, skill);
        callableStatement.setInt(2, technicianID);
        callableStatement.execute();
    }
}
