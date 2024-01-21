package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.db.DbConnection;
import com.developerStack.eduManage.model.User;
import com.developerStack.eduManage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupFormController {
    public AnchorPane context;
    public TextField txtFirstName;
    public PasswordField txtPass;
    public TextField txtEmail;
    public TextField txtLastName;

    public void signupOnAction(ActionEvent actionEvent) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText().toLowerCase();
        String password = new PasswordManager().encode( txtPass.getText().trim());
        DbConnection.userTable.add(
          new User(firstName, lastName, email, password)
        );
        new Alert(Alert.AlertType.INFORMATION,"Welcome to System!").show();
        setUi("LoginForm");

    }

    public void alreadyHaveAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

}
