package edu.ufp.inf.javafx.loginFxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.io.IOException;
import java.util.Objects;

public class LoginFXML extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            URL url = LoginFXML.class.getResource("login.fxml");
            System.out.println("url = "+url);
            Parent root = FXMLLoader.load(Objects.requireNonNull(url));

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
