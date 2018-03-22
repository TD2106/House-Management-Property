package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Vector;

public class MainController {

    @FXML
    private Pane mainPane;

    public MainController() {

    }

    private DatabaseApplication databaseApplication = new DatabaseApplication();

    @FXML
    private void managerEvent() throws Exception {
        boolean isInputTrue = true;
        for (int i = 0; i < 2; i++) {
            String id = requestID(isInputTrue);
            isInputTrue = checkIDUser(id, 1);
            if (isInputTrue) break;
        }

        if (isInputTrue) {
            Parent manager = FXMLLoader.load(getClass().getResource("manager.fxml"));
            this.setScene(manager, "Manager Control Panel");
        }
    }

    @FXML
    private void technicianEvent() throws Exception {
        boolean isInputTrue = true;
        for (int i = 0; i < 2; i++) {
            String id = requestID(isInputTrue);
            isInputTrue = checkIDUser(id, 2);
            if (isInputTrue) break;
        }

        if (isInputTrue) {
            Parent technician = FXMLLoader.load(getClass().getResource("technician.fxml"));
            this.setScene(technician, "Technician Control Panel");
        }
    }

    @FXML
    private void tenantEvent() throws Exception {
        boolean isInputTrue = true;
        for (int i = 0; i < 2; i++) {
            String id = requestID(isInputTrue);
            isInputTrue = checkIDUser(id, 3);
            if (isInputTrue) break;
        }

        if (isInputTrue) {
            Parent tenant = FXMLLoader.load(getClass().getResource("tenant.fxml"));
            this.setScene(tenant, "Tenant Control Panel");
        }
    }

    private String requestID(boolean isInputTrue) {
        TextInputDialog dialog = new TextInputDialog("Your ID here: ");

        dialog.setTitle("Request Dialog");
        dialog.setContentText("Enter Your ID: ");
        if (!isInputTrue) dialog.setHeaderText("Your ID wrong or you are another kind of person, Please enter it again or choose another option");

        Optional<String> id = dialog.showAndWait();
        if (id.isPresent()) {
            return id.get();
        }

        return null;
    }

    private void setScene(Parent root, String title) {
        Stage primaryStage = (Stage) mainPane.getScene().getWindow();

        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private boolean checkIDUser(String id, int type) { //1: manager; 2:technician; 3:tenant
        String query;
        if (type == 1) {
            query = "SELECT * FROM Manager\nWhere Manager_ID = ?";
        } else if (type == 2) {
            query = "SELECT * FROM Technician\nWhere TechnicianID = ?";
        } else {
            query = "SELECT * FROM Tenant\nWhere TenantID = ?";
        }
        try {
            int idInt = Integer.parseInt(id);
            CallableStatement callableStatement = databaseApplication.prepareCall(query);
            callableStatement.setInt(1, idInt);
            return databaseApplication.check(callableStatement);
        } catch (NumberFormatException formatException) {
            return false;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
}
