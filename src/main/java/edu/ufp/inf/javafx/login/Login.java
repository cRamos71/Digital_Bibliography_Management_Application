package edu.ufp.inf.javafx.login;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Login");
        primaryStage.setResizable(true);
        GridPane layout = createLayout();
        Scene s = new Scene(layout, 800, 800);
        //s.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        primaryStage.setScene(s);
        primaryStage.show();
    }

    private GridPane createLayout() {
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(25, 25, 10, 25));

        Text t = new Text("Welcome!");
        t.setId("welcome-text");
        layout.add(t, 0, 0 ,2, 1);

        layout.add(new Label("UserName:"), 0, 1);

        layout.add(new TextField(), 1, 1);

        layout.add(new Label("Password:"), 0, 2);

        layout.add(new PasswordField(), 1, 2);

        HBox button = createButtons();
        button.setSpacing(10.0d);
        layout.add(button, 1, 4);

        Text t2 = new Text();
        t2.setId("textActionTarget");
        t2.setWrappingWidth(170.0);
        t2.setId("actiontarget-text");
        layout.add(t2, 1, 6);

        //TilePane buttons = createButtons();
        return layout;
    }

    private HBox createButtons() {
        HBox button = new HBox();
        //button.setAlignment(new Pos());

        Button btn = new Button("Sign-In");

        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> System.out.println(event.getEventType()));
        button.getChildren().add(btn);

        return button;
    }
}


