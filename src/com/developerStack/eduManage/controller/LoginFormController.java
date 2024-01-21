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
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {
    public AnchorPane contextLogin;
    public TextField txtEmail;
    public PasswordField txtPass;



    public void forgotPassOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ForgotPasswordForm");
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().toLowerCase();
        String pass = txtPass.getText().trim();

        /*if (email.isEmpty() || pass.isEmpty()) {
            new Alert(Alert.AlertType.ERROR,"Enter email and Password!").show();
            return;
        }else {
            for (User user : DbConnection.userTable) {
                if (user.getEmail().equals(email)){
                    if (user.getPassword().equals(pass)){
                        System.out.println(user.toString());
                        return;
                    }else{
                        new Alert(Alert.AlertType.ERROR,"Wrong Password!").show();
                        return;
                    }
                }
            }
            new Alert(Alert.AlertType.WARNING,String.format("User not found (%s)",email)).show();
        }*/


//        second way
        Optional<User> selectedUser =
                DbConnection.userTable.stream().filter(user -> user.getEmail().equals(email)).findFirst();

        if (selectedUser.isPresent()){
            if (new PasswordManager().checkPass(pass,selectedUser.get().getPassword())){
                      //System.out.println(selectedUser.get().toString());
                      setUi("DashboardForm");

                   }else{
                      new Alert(Alert.AlertType.ERROR,"Wrong Password!").show();
            }

        }else {
            new Alert(Alert.AlertType.WARNING,String.format("User not found (%s)",email)).show();
        }

    }

    public void createAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) contextLogin.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }


}
