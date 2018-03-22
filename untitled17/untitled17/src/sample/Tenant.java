package sample;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

public class Tenant {
    private int tenantID;
    private int apartmentID;
    private int buildingID;
    private String bankAccount;
    private String firstName, lastName;
    private int managerID;
    private double monthlyRent;
    private int duration;
    private DatabaseApplication databaseApplication = new DatabaseApplication();

    public Tenant() {}

    public Tenant(int tenantID, String name, int apartmentID, int buildingID, String bankAccount, int managerID, double monthlyRent, int duration) { //duration: month
        this.tenantID = tenantID;
        this.apartmentID = apartmentID;
        this.buildingID = buildingID;
        this.bankAccount = bankAccount;
        this.managerID = managerID;
        this.monthlyRent = monthlyRent;
        this.duration = duration;

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

    public Tenant(String tenantID, String name, String apartmentID, String buildingID, String bankAccount, int managerID, String monthlyRent, String duration) throws NumberFormatException {
        this.tenantID = Integer.parseInt(tenantID);
        this.apartmentID = Integer.parseInt(apartmentID);
        this.buildingID = Integer.parseInt(buildingID);
        this.bankAccount = bankAccount;
        this.managerID = managerID;
        this.monthlyRent = Double.parseDouble(monthlyRent);
        this.duration = Integer.parseInt(duration);

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
        if(!lastName.toString().equals(" ")) this.lastName = lastName.toString();
    }

    public Tenant(String tenantID, String name, String apartmentID, String buildingID, String bankAccount) throws NumberFormatException {
        this.tenantID = Integer.parseInt(tenantID);
        this.apartmentID = Integer.parseInt(apartmentID);
        this.buildingID = Integer.parseInt(buildingID);
        this.bankAccount = bankAccount;

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
        if(!lastName.toString().equals(" ")) this.lastName = lastName.toString();
    }

    public Tenant(String tenantID) throws NumberFormatException {
        this.tenantID = Integer.parseInt(tenantID);
    }

    public void update(DatabaseApplication databaseApplication) throws SQLException {
        this.updateApartmentID(databaseApplication);
        this.updateBuildingID(databaseApplication);
        this.updateBankAccount(databaseApplication);
        this.updateName(databaseApplication);
    }

    private void updateApartmentID(DatabaseApplication databaseApplication) throws SQLException {
        String query = "UPDATE Tenant SET ID_Apartment = ? WHERE TenantID = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setInt(1, apartmentID);
        statement.setInt(2, tenantID);
        statement.executeUpdate();
    }

    private void updateBuildingID(DatabaseApplication databaseApplication) throws SQLException {
        String query = "UPDATE Tenant SET ID_Building = ? WHERE TenantID = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setInt(1, buildingID);
        statement.setInt(2, tenantID);
        statement.executeUpdate();
    }

    private void updateBankAccount(DatabaseApplication databaseApplication) throws SQLException {
        String query = "UPDATE Tenant SET BankAccount = ? WHERE TenantID = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setString(1, bankAccount);
        statement.setInt(2, tenantID);
        statement.executeUpdate();
    }

    private void updateName(DatabaseApplication databaseApplication) throws SQLException {
        String query = "UPDATE Person SET First_name = ?, Last_name = ? WHERE SSN = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setInt(3, tenantID);
        statement.executeUpdate();
    }

    public void insert() throws SQLException {
        String query = "EXEC dbo.insTenant ?,?,?,?,?,?,?,?,?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setInt(1, tenantID);
        statement.setString(2, firstName);
        statement.setString(3, lastName);
        statement.setInt(4, apartmentID);
        statement.setInt(5, buildingID);
        statement.setString(6, bankAccount);
        statement.setInt(7, managerID);
        statement.setDouble(8, monthlyRent);
        statement.setInt(9, duration);
        statement.execute();
    }

    public void delete() throws SQLException {
        String query = "EXEC dbo.delTenant ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setInt(1, tenantID);
        statement.execute();
    }
}
