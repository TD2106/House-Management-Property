package sample;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by Hai Pham on 4/28/2017.
 */
public class Building {
    private int buildingID;
    private String address;
    private String name;
    private int numRoom;
    private DatabaseApplication databaseApplication = new DatabaseApplication();

    public Building(int buildingID, String address, String name, int numRoom) {
        this.buildingID = buildingID;
        this.address = address;
        this.name = name;
        this.numRoom = numRoom;

        databaseApplication.setAutoCommit(false);
    }

    public Building(String buildingID, String address, String name, String numRoom) throws NumberFormatException {
        this.buildingID = Integer.parseInt(buildingID);
        this.address = address;
        this.name = name;
        this.numRoom = Integer.parseInt(numRoom);

        databaseApplication.setAutoCommit(false);
    }

    public int getBuildingID() {
        return buildingID;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getNumRoom() {
        return numRoom;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumRoom(int numRoom) {
        this.numRoom = numRoom;
    }

    public void update() throws SQLException {
        this.updateAddress();
        this.updateName();
        this.updateNumRoom();
    }

    private void updateAddress() throws SQLException {
        String query = "UPDATE Building\nSET Address = ?\nWHERE ID_Building = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setString(1, address);
        statement.setInt(2, buildingID);
        statement.executeUpdate();
    }

    private void updateName() throws SQLException {
        String query = "UPDATE Building\nSET Name = ?\nWHERE ID_Building = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setString(1, name);
        statement.setInt(2, buildingID);
        statement.executeUpdate();
    }

    private void updateNumRoom() throws SQLException {
        String query = "UPDATE Building\nSET numRoom = ?\nWHERE ID_Building = ?";
        CallableStatement statement = databaseApplication.prepareCall(query);
        statement.setInt(1, numRoom);
        statement.setInt(2, buildingID);
        statement.executeUpdate();
    }

    public void insert() throws SQLException {
        String query = "INSERT INTO Building\nVALUES (?,?,?,?)";
        CallableStatement callableStatement = databaseApplication.prepareCall(query);
        callableStatement.setInt(1, buildingID);
        callableStatement.setString(2, address);
        callableStatement.setString(3, name);
        callableStatement.setInt(4, numRoom);
        callableStatement.executeUpdate();
    }
}
