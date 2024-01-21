package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.util.tools.VerificationCodeGenerator;
import com.developerStack.eduManage.util.tools.VerificationCodeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class ForgotPasswordFormController {
    public AnchorPane context;
    public TextField txtEmail;

    public void goBackLoginOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void sendCodeOnAction(ActionEvent actionEvent) {
        try {
           int verificationCode =  new VerificationCodeGenerator().getCode(5);
           String fromEmail = "damjithfernando1@gmail.com";
           String toEmail = txtEmail.getText();

            VerificationCodeService.sendVerificationCodeByEmail(fromEmail,toEmail,verificationCode);

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("../view/CodeVerificationCodeForm.fxml"));
            Parent parent = loader.load();
            CodeVerificationCodeFormController controller = loader.getController();
            controller.setUserData(verificationCode,txtEmail.getText());
            Stage stage = (Stage) context.getScene().getWindow();
            stage.setScene(new Scene(parent));

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
