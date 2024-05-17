package edu.ufp.inf.javafx.loginFxml;

import javafx.event.ActionEvent;

public class LoginFXMLController {
    public void handleSubmitButtonAction(ActionEvent event) {
        System.out.println(event.getEventType());
    }
}
