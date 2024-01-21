package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.db.DbConnection;
import com.developerStack.eduManage.model.User;
import com.developerStack.eduManage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class ResetPasswordFormController {
    public AnchorPane resetContext;
    public TextField txtPassword;

    String selectedEmail = "";
    public void setUserData( String email){
        selectedEmail = email;
    }

    public void changePasswordOnAction(ActionEvent actionEvent) throws IOException {
        Optional<User> selectedUser = DbConnection.userTable.stream().filter(user -> user.getEmail().equals(selectedEmail)).findFirst();
        if (selectedUser.isPresent()){
            selectedUser.get().setPassword(new PasswordManager().encode(txtPassword.getText()));
            setUi("LoginForm");
        }else {
            new Alert(Alert.AlertType.ERROR,"Not Found").show();
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) resetContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
