package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.*;
import java.util.Vector;

/**
 * Created by Hai Pham on 4/19/2017.
 */
public class DatabaseApplication {
    private Connection connection;
    private Statement statement;

    public DatabaseApplication() {
        String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        try {
            Class.forName( JDBC_DRIVER );
            this.connectToMyDB();
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver is not load!");
        } catch (SQLException e) {
            System.out.println("Can't create statement!");
        }
    }

    private void connectToDB(String servername, String username, String password, int port, String database) throws SQLException, ClassNotFoundException {
        String DATABASE_URL = "jdbc:sqlserver://"+ servername + ": " + port + ";DatabaseName=" + database + ";User=" + username + ";Password=" + password;
        connection = DriverManager.getConnection( DATABASE_URL );
    }

    private void connectToMyDB() {
        String servername = "HAI07890"; // Server name
        int port = 1433;	// TCP Port
        String username = "hai07890";
        String password = "Hai12345652";
        String database = "mydb";

        try {
            connectToDB(servername, username, password, port, database);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void reconnect() throws SQLException {
        if (connection.isClosed()) {
            connectToMyDB();
            statement = connection.createStatement();
        } else if (statement.isClosed()) {
            statement = connection.createStatement();
        }
    }

    public TableView<ObservableList<String>> requestQuery(CallableStatement callableStatement) throws SQLException {
        callableStatement.execute();

        ResultSet resultSet = callableStatement.getResultSet();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        TableView<ObservableList< String >> tableView = new TableView<>();
        ObservableList<ObservableList< String >> data = FXCollections.observableArrayList();

        for ( int i = 1; i <= numberOfColumns; i++ ) {
            final int j = i;
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(metaData.getColumnName(i));
            tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>,String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> row) {
                    return new SimpleStringProperty(row.getValue().get(j - 1));
                }
            });

            tableView.getColumns().add(tableColumn);
        }

        while ( resultSet.next() ) {

            ObservableList<String> row = FXCollections.observableArrayList();
            for ( int i = 1; i <= numberOfColumns; i++ ) {
                row.add(resultSet.getString( i ));
            }

            data.add(row);
        }

        tableView.setItems(data);
        resultSet.close();
        return tableView;
    }

    public TableView<ObservableList<String>> requestQuery(String query) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(query);

        return this.requestQuery(callableStatement);
    }

    public Vector< Vector<String> > requestQueryVector(CallableStatement callableStatement) throws SQLException {
        callableStatement.execute();

        ResultSet resultSet = callableStatement.getResultSet();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        Vector<String> columnName = new Vector<>();
        Vector< Vector<String> > data = new Vector<>();
        for ( int i = 1; i <= numberOfColumns; i++ ) {
            columnName.add(metaData.getColumnName(i));
        }
        data.add(columnName);

        while ( resultSet.next() ) {

            Vector<String> row = new Vector<>();
            for ( int i = 1; i <= numberOfColumns; i++ ) {
                row.add(resultSet.getString( i ));
            }

            data.add(row);
        }

        resultSet.close();
        return data;
    }

    public Vector< Vector< String >> requestQueryVector(String query) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall(query);

        return this.requestQueryVector(callableStatement);
    }

    private void closeConnection() throws SQLException {
        if (!connection.isClosed()) connection.close();
        if (!statement.isClosed()) statement.close();
    }

    public CallableStatement prepareCall(String query) throws SQLException {
        return connection.prepareCall(query);
    }

    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getAutoCommit() throws SQLException {
        return connection.getAutoCommit();
    }

    public void commit() throws SQLException {
        connection.commit();
        //this.closeConnection();
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        //this.closeConnection();
    }

    public boolean check(String query) throws SQLException {
        Vector<Vector<String>> data = this.requestQueryVector(query);

        return data.size() > 1;
    }

    public boolean check(CallableStatement callableStatement) throws SQLException {
        Vector<Vector<String>> data = this.requestQueryVector(callableStatement);

        return data.size() > 1;
    }
}