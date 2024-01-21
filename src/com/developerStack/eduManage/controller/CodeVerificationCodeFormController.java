package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.util.tools.VerificationCodeService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.mail.MessagingException;
import java.io.IOException;

public class CodeVerificationCodeFormController {
    public AnchorPane verifyContext;
    public TextField txtCode;
    public Label txtSelectedEmail;
    public Hyperlink changeEmailOnAction;
    public Label lblTimer;

    private static final int TIME_LIMIT_SECONDS = 10;
    private int remainingTime = TIME_LIMIT_SECONDS;
    private Timeline timer;

    public void initialize(){
        startTimer();
    }

    int code = 0;
    String selectedEmail = "";

    public void setUserData(int verificationCode, String email){
        System.out.println(verificationCode);
        System.out.println(email);
        code = verificationCode;
        selectedEmail = email;
        txtSelectedEmail.setText(email);
        resetTimer();
    }

    public void verifyCodeOnAction(ActionEvent actionEvent) throws IOException {
        if (String.valueOf(code).equals(txtCode.getText())){
            System.out.println("reset");
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("../view/ResetPasswordForm.fxml"));
            Parent parent = loader.load();
            ResetPasswordFormController controller = loader.getController();
            controller.setUserData(selectedEmail);
            Stage stage = (Stage) verifyContext.getScene().getWindow();
            stage.setScene(new Scene(parent));
            timer.stop();
        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong Verification Code").show();
            txtCode.clear();
        }

    }

    public void changeEmailOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ForgotPasswordForm");
        if (timer != null) {
            timer.stop();
        }
    }




    public void resendOnAction(ActionEvent actionEvent) {
        try {
            String fromEmail = "damjithfernando1@gmail.com";
            int newVerificationCode = VerificationCodeService.generateVerificationCode(5);
            VerificationCodeService.sendVerificationCodeByEmail(fromEmail, selectedEmail, newVerificationCode);
            code = newVerificationCode;
            resetTimer();
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingTime--;
            lblTimer.setText(String.valueOf(remainingTime));
            if (remainingTime == 0){
                timer.stop();
                handleTimeout();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void handleTimeout() {
        new Alert(Alert.AlertType.ERROR, "Time over. Please try again.").show();
    }
    private void resetTimer() {
        remainingTime = TIME_LIMIT_SECONDS;
        lblTimer.setText(String.valueOf(remainingTime));
        timer.playFromStart();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) verifyContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
