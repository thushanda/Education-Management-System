package com.developerStack.eduManage.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane contextDashboard;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        setData();
    }

    private void setData() {
        /*Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String textDate = simpleDateFormat.format(date);
        lblDate.setText(textDate);*/
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //lblTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));

        Timeline timeline = new Timeline(new KeyFrame(
           Duration.seconds(0),
           e -> {
               DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
               lblTime.setText(LocalTime.now().format(timeFormatter));
           }
        ),
          new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) contextDashboard.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void studentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("StudentForm");
    }

    public void teacherOnAction(ActionEvent actionEvent) throws IOException {
        setUi("TeacherForm");
    }

    public void programOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProgramForm");
    }

    public void intakeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("IntakeForm");
    }

    public void registrationOnAction(ActionEvent actionEvent) {
    }
}
