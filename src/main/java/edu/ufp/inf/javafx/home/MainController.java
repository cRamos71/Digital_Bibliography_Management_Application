package edu.ufp.inf.javafx.home;

import edu.ufp.inf.Util.Date;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final String PATH_DB= "./data/db.txt";
    private static final String PATH_DB_AUTHORS_BIN= "./data/authors.bin";
    private static final String PATH_DB_PAPERS_BIN= "./data/papers.bin";


    public Button addPaper;
    public TextField idNumberField;
    public Button q1Authors;
    private DataBase<Author, Paper> db = new DataBase<>();
    private DataBaseLog dbLog = new DataBaseLog(db);

// Link attributes to UI components
    @FXML
    private Button btn;
    public Button dbTxt;
    public Button dbBin;
    public  SplitMenuButton querySplit;
    public TextArea promptText;
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


    //Queries
    public TextField aID;
    public DatePicker sDate;
    public DatePicker eDate;

    public ComboBox<String> PapersComboBox;

    private String selectedPaper;



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

                }

                @Override
                protected void updateItem(ArrayList<String> papers, boolean empty) {
                    super.updateItem(papers, empty);
                    if (empty || papers == null || papers.isEmpty()) {
                        setGraphic(null);
                    } else {
                        System.out.println(papers);
                        comboBox.getItems().setAll(papers);
                        comboBox.setMaxSize(30, 30);
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
        dbLog.fillDB(PATH_DB);

        promptText.setEditable(false);
        promptText.setWrapText(true);

        //db.paperAuthorByIdPeriodIn()
        //querySplit.getItems().add(authorsQueryItem);


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
        dbLog.saveDBTxt(PATH_DB);
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
        String paper = selectedPaper;
        System.out.println(paper);

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
     * Handler para acção de edição dos dados dos autores
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
        Author a = authorStringCellEditEvent.getRowValue();
        db.update(a);
      //  refreshTableView();
    }





    public void handleSelectVehicleAction(ActionEvent actionEvent) {
    }

    public void handleSelectDriverAction(ActionEvent actionEvent) {
    }

    private void refreshTableView(){
        ObservableList<Author> authorList = FXCollections.observableList(db.listAuthors());
        authorsTable.setItems(authorList);
        //System.out.println(db.listPapers());
        //PapersComboBox.getItems().clear();
       // PapersComboBox.getItems().addAll(db.listPapers());
        for (String s : db.listPapers()){
            if(!PapersComboBox.getItems().contains(s)){
                PapersComboBox.getItems().add(s);
            }
        }

    }


    public void handleSaveDBtxtAction(ActionEvent actionEvent) {
        //System.out.println(actionEvent);
        dbLog.saveDBTxt(PATH_DB);
    }

    public void handleSelectPaperAction(ActionEvent actionEvent) {
        selectedPaper = PapersComboBox.getSelectionModel().getSelectedItem();
        System.out.println("Selected paper: " + selectedPaper);

    }

    public void handleAddPaperAuthorAction(ActionEvent actionEvent) {
        try{
            System.out.println(idNumberField.getText());
            Integer id = Integer.parseInt(idNumberField.getText().trim());
            //extract DOI with ' '
            String paperDOI = selectedPaper.split("=")[1].split(",")[0].trim();
            String paperD = paperDOI.substring(1, paperDOI.length() - 1);
            db.getMapUID().get(id).addPaper(db.getMapDOI().get(paperD));
            refreshTableView();
        }catch(Exception e){
            System.out.println("Id not supported");

        }

        idNumberField.setText("");


    }

    public void handleSaveDBbinAction(ActionEvent actionEvent) {
        System.out.println(actionEvent);
        dbLog.saveDBBin(PATH_DB_AUTHORS_BIN, PATH_DB_PAPERS_BIN);
    }

    public void handleQueryAction(ActionEvent actionEvent) {
    }

    public void handleAllPapersWrittenQuery(ActionEvent actionEvent) {
        System.out.println(promptText.getText());
    }

    public void handlePapersWrittenAuthor(ActionEvent actionEvent) {
       // System.out.println(actionEvent.toString());
        LocalDate s1 = this.sDate.getValue();
        LocalDate s2 = this.eDate.getValue();

        try {
            int id  = Integer.parseInt(aID.getText().trim());
            Date sDate = new Date(s1.getMonthValue(), s1.getDayOfMonth(), s1.getYear());
            Date eDate = new Date(s2.getMonthValue(), s2.getDayOfMonth(), s2.getYear());

          ArrayList<String> result = db.paperAuthorByIdPeriodIn(id, sDate,eDate);
            if(result == null){
                promptText.setText("None");
                return;
            }
            String w = " ";
          for(String r : result) {
              w += r + "\n";
          }

            promptText.setText(w);

        }catch ( Exception e){
            System.out.println("Invalid query format");
        }

        //clear fields
        aID.setText("");
    }

    public void HandleAllPapersNoViewNoDownload(ActionEvent actionEvent) {
        LocalDate l = LocalDate.now();
        Date df = new Date(l.getMonthValue(), l.getDayOfMonth(), l.getYear());
        Date di = new Date(1, 1, 1900);
        ArrayList<String> result = db.papersNotDownloadedNotViewed(di, df);
        if(result.isEmpty()){
            promptText.setText("None");
            return;
        }

        String w = " ";
        for(String r : result) {
            w += r + "\n";
        }

        promptText.setText(w);
    }



    public void handleTop3PapersQuery(ActionEvent actionEvent) {
        Paper[] result = db.top3PapersMostDownloads();
        /*if(result.isEmpty()){
            promptText.setText("None");
            return;
        }*/

        String w = " ";
        for(Paper r : result) {
            w += r + "\n";
        }

        promptText.setText(w);
    }
}
