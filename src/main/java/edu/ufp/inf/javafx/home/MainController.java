package edu.ufp.inf.javafx.home;

import edu.princeton.cs.algs4.Date;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final String PATH_AUTHORS= "./data/db.txt";

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
    public TableColumn papersCol;


    public ComboBox<String> authorComboBox;

    public TextField nameField;
    public TextField birthDateField;
    public TextField addressField;
    public TextField penNameField;
    public TextField affiliationField;
    public TextField orcIDField;
    public TextField scienceIDField;
    public TextField googleScholarIDField;
    public TextField scopusAuthorIDField;







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


        papersCol.setCellValueFactory(new PropertyValueFactory<>("papers"));
        papersCol.setCellFactory(col -> {
            TableCell<Author, ArrayList<String>> cell = new TableCell<>() {
                private final ComboBox<String> comboBox = new ComboBox<>();

                {
                    comboBox.setEditable(false);
                    comboBox.setOnAction(event -> {
                        String selectedPaper = comboBox.getSelectionModel().getSelectedItem();
                        if (selectedPaper != null) {
                            Author author = getTableView().getItems().get(getIndex());
                            // Handle the selection of the paper
                            System.out.println("Selected paper: " + selectedPaper + " for author: " + author.getName());
                        }
                    });
                }

                @Override
                protected void updateItem(ArrayList<String> papers, boolean empty) {
                    super.updateItem(papers, empty);
                    if (empty || papers == null || papers.isEmpty()) {
                        setGraphic(null);
                    } else {
                        comboBox.getItems().setAll(papers);
                        comboBox.setMaxSize(150, 150);
                        setGraphic(comboBox);
                    }
                }
            };
            return cell;
        });


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

    public void handleAddAuthorAction(ActionEvent actionEvent) {
        System.out.println(actionEvent.toString());
        String name =  nameField.getText().trim();
        String[] birthDate = birthDateField.getText().trim().split("-");
        String address = addressField.getText().trim();

        Author a = getAuthor(birthDate, name, address);
        if(a != null){
            db.insert(a);
            System.out.println(name);
            System.out.println(a.getIdNumber());
        }

        System.out.println(db.listAuthors());


        //clear fields
        nameField.setText("");
        birthDateField.setText("");
        addressField.setText("");
        penNameField.setText("");
        affiliationField.setText("");
        orcIDField.setText("");
        scienceIDField.setText("");
        googleScholarIDField.setText("");
        scopusAuthorIDField.setText("");
        dbLog.saveAuthorsTxt("./data/db.txt");
        refreshTableView();
       // db.insert();
    }

    private Author getAuthor(String[] birthDate, String name, String address) {
        String penName = penNameField.getText().trim();
        String affiliation = affiliationField.getText().trim();
        String orcID = orcIDField.getText().trim();
        String scienceID = scienceIDField.getText().trim();
        String googleScholarID = googleScholarIDField.getText().trim();
        String scopusAuthorID = scopusAuthorIDField.getText().trim();

        try {
            Date d = new Date(Integer.parseInt(birthDate[1]), Integer.parseInt(birthDate[0]),Integer.parseInt( birthDate[2]));
            System.out.println(d);
            return new Author(d, name, address, penName, affiliation, orcID, scienceID, googleScholarID, scopusAuthorID);

        }catch(Exception e){
            System.out.println("Date not supported ");
            return null;
        }
    }

    /**
     * Handler para acção de edição dos dados dos veículos na vehiclesTable.
     *
     * @param authorStringCellEditEvent
     */
    public void handleEditAuthorAction(TableColumn.CellEditEvent<Author, Object> authorStringCellEditEvent) {
        int col=authorStringCellEditEvent.getTablePosition().getColumn();
        switch (col) {
            case 1:
                authorStringCellEditEvent.getRowValue().setBirthDate(new Date((String) authorStringCellEditEvent.getNewValue()));
                break;
            case 2:
                authorStringCellEditEvent.getRowValue().setName((String) authorStringCellEditEvent.getNewValue());
                break;
            case 3:
                authorStringCellEditEvent.getRowValue().setAddress((String) authorStringCellEditEvent.getNewValue());
                break;
            case 4:
                authorStringCellEditEvent.getRowValue().setPenName((String) authorStringCellEditEvent.getNewValue());
                break;
            case 5:
                authorStringCellEditEvent.getRowValue().setAffiliation((String) authorStringCellEditEvent.getNewValue());
                break;
            case 6:
                authorStringCellEditEvent.getRowValue().setOrcID((String) authorStringCellEditEvent.getNewValue());
                break;
            case 7:
                authorStringCellEditEvent.getRowValue().setScienceID((String) authorStringCellEditEvent.getNewValue());
                break;
            case 8:
                authorStringCellEditEvent.getRowValue().setGoogleScholarID((String) authorStringCellEditEvent.getNewValue());
                break;
            case 9:
                authorStringCellEditEvent.getRowValue().setScopusAuthorID((String) authorStringCellEditEvent.getNewValue());
                break;
        }
        dbLog.saveAuthorsTxt("./data/db.txt");
        refreshTableView();
    }





    public void handleSelectVehicleAction(ActionEvent actionEvent) {
    }

    public void handleSelectDriverAction(ActionEvent actionEvent) {
    }

    private void refreshTableView(){
        dbLog.fillDB("./data/db.txt");
        ObservableList<Author> authorList = FXCollections.observableList(db.listAuthors());
        authorsTable.setItems(authorList);
    }


}
