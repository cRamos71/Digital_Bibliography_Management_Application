package edu.ufp.inf.javafx.home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button btn;

    @FXML
    private void openWindow() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Window");

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);

        stage.showAndWait();
    }

    public void handleSaveBinFileAction(ActionEvent actionEvent) {
    }

    public void handleEditVehicleAction(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void handleAddVehicleAction(ActionEvent actionEvent) {
    }

    public void handleSelectVehicleAction(ActionEvent actionEvent) {
    }

    public void handleSelectDriverAction(ActionEvent actionEvent) {
    }
}
