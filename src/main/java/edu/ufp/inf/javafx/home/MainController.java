package edu.ufp.inf.javafx.home;

import edu.ufp.inf.database.DataBase;
import edu.ufp.inf.database.DataBaseLog;
import edu.ufp.inf.paper_author.Author;
import edu.ufp.inf.paper_author.Paper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final String PATH_AUTHORS= "./data/authors.txt";

    private DataBase<Author, Paper> db = new DataBase<>();
    private DataBaseLog dbLog = new DataBaseLog(db);

// Link attributes to UI components
    @FXML
    private Button btn;
    public TableView<Author> authorsTable;
    public TableColumn<Author, String> nameCol;
    public TableColumn<Author, Integer> idNumberCol;
    public TableColumn<Author, String> birthDateCol;
    public TableColumn<Author, String> addressCol;
    public TableColumn<Author, String> penNameCol;
    public TableColumn<Author, String> affiliationCol;
    public TableColumn<Author, String> orcIDCol;
    public TableColumn<Author, String> scienceIDCol;
    public TableColumn<Author, String> googleScholarIDCol;
    public TableColumn<Author, String> scopusAuthorIDCol;

    public ComboBox<String> authorComboBox;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //how the data for each cell in the column is obtained
        idNumberCol.setCellValueFactory(new PropertyValueFactory<Author, Integer>("idNumber"));
        //how data is rendered
        idNumberCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        birthDateCol.setCellValueFactory(new PropertyValueFactory<Author, String>("birthDate"));
        birthDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        addressCol.setCellValueFactory(new PropertyValueFactory<Author, String>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());

        penNameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("penName"));
        penNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        affiliationCol.setCellValueFactory(new PropertyValueFactory<Author, String>("affiliation"));
        affiliationCol.setCellFactory(TextFieldTableCell.forTableColumn());

        orcIDCol.setCellValueFactory(new PropertyValueFactory<Author, String>("orcID"));
        orcIDCol.setCellFactory(TextFieldTableCell.forTableColumn());

        scienceIDCol.setCellValueFactory(new PropertyValueFactory<Author, String>("scienceID"));
        scienceIDCol.setCellFactory(TextFieldTableCell.forTableColumn());

        googleScholarIDCol.setCellValueFactory(new PropertyValueFactory<Author, String>("googleScholarID"));
        googleScholarIDCol.setCellFactory(TextFieldTableCell.forTableColumn());

        scopusAuthorIDCol.setCellValueFactory(new PropertyValueFactory<Author, String>("scopusAuthorID"));
        scopusAuthorIDCol.setCellFactory(TextFieldTableCell.forTableColumn());




        penNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getPenName());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });

       googleScholarIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getGoogleScholarID());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });

        scopusAuthorIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getScopusAuthorID());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });


        affiliationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getAffiliation());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });

        scienceIDCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getScienceID());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });

        affiliationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getAffiliation());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });

        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getName());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });

        idNumberCol.setCellFactory(new Callback<TableColumn<Author, Integer>, TableCell<Author, Integer>>() {
                                       @Override
                                       public TableCell<Author, Integer> call(TableColumn<Author, Integer> col) {
                                           return new TableCell<Author, Integer>() {
                                               @Override
                                               protected void updateItem(Integer id, boolean empty) {
                                                   super.updateItem(id, empty);
                                                   if (empty) {
                                                       setText(null);
                                                   } else {
                                                       setText(Integer.toString(id));
                                                   }
                                               }
                                           };
                                       }
                                   });
        birthDateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Author, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Author, String> cellDataFeatures) {
                if (cellDataFeatures.getValue() != null) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().getBirthDate().day()+"-" + cellDataFeatures.getValue().getBirthDate().month() + "-" + cellDataFeatures.getValue().getBirthDate().year());
                } else {
                    return new SimpleStringProperty("<No Info>");
                }
            }
        });



    /*    vehiclesTable.getItems().addListener( (ListChangeListener<? super Vehicle>)vehiclesChanges -> {
            System.out.println("ListChangeListener - vehiclesChanges = " + vehiclesChanges);
            ObservableList<? extends Vehicle> list = vehiclesChanges.getList();
            list.forEach( vehicle  -> {
                System.out.println("added "+vehicle);
            });
        });*/

        refreshTableView();

    }





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
        System.out.println(actionEvent.toString());
       // db.insert();
    }

    public void handleSelectVehicleAction(ActionEvent actionEvent) {
    }

    public void handleSelectDriverAction(ActionEvent actionEvent) {
    }

    private void refreshTableView(){
        dbLog.fillDBA();
        ObservableList<Author> authorList = FXCollections.observableList(db.listAuthors());
        System.out.println("ola");
        authorsTable.setItems(authorList);
    }


}
