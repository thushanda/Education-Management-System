package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.db.DbConnection;
import com.developerStack.eduManage.model.Student;
import com.developerStack.eduManage.model.Teacher;
import com.developerStack.eduManage.view.tm.StudentTm;
import com.developerStack.eduManage.view.tm.TeacherTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

public class TeacherFormController {
    public AnchorPane teacherContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSearch;
    public Button btn;
    public TableView<TeacherTm> tblTeacher;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colOption;
    public TextField txtContact;
    public TableColumn colContact;

    String searchText = "";

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setTeacherId();
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

//        select student
        tblTeacher.getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (null != newValue){
                        setData(newValue);
                    }
                });
    }

    private void setData(TeacherTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtContact.setText(tm.getPhoneNo());
        txtAddress.setText(tm.getAddress());
        btn.setText("Update Teacher");
    }

    private void setTeacherId() {
        if (!DbConnection.teacherTable.isEmpty()) {
            Teacher lastTeacher = DbConnection.teacherTable.get(
                    DbConnection.teacherTable.size() - 1
            );
            String lastId = lastTeacher.getId();
            String[] splitData = lastId.split("-");

            if (splitData.length >= 2) {
                String lastIdIntegerNumber = splitData[1];
                int lastIdInt = Integer.parseInt(lastIdIntegerNumber);
                lastIdInt++;
                String generateId = "#IOMT-" + lastIdInt;
                txtId.setText(generateId);
            } else {
                // Handle the situation when the split array doesn't have at least two elements
                // You can set a default value or throw an exception, depending on your requirements.
                // For now, setting a default value.
                txtId.setText("#IOMT-1");
            }
        } else {
            txtId.setText("#IOMT-1");
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
        btn.setText("Save Teacher");
        clearField();
        setTeacherId();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (btn.getText().equalsIgnoreCase("Save Teacher")){
            Teacher teacher = new Teacher(

                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtAddress.getText()
            );
            System.out.println(teacher);
            DbConnection.teacherTable.add(teacher);
            setTeacherId();
            clearField();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION,"Teacher Successfully Added!").show();
        }else {
//            update student
            for (Teacher teacher: DbConnection.teacherTable
            ) {
                if (teacher.getId().equals(txtId.getText())){
                    teacher.setName(txtName.getText());
                    teacher.setPhoneNo(txtContact.getText());
                    teacher.setAddress(txtAddress.getText());
                    new Alert(Alert.AlertType.INFORMATION,"Teacher Successfully Updated!").show();
                   setTableData(searchText);
                   clearField();
                    setTeacherId();
                    btn.setText("Save Teacher");
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING,"Not Found").show();
        }
    }

    private void setTableData(String searchText) {
        ObservableList<TeacherTm> observableList = FXCollections.observableArrayList();
        for (Teacher teacher: DbConnection.teacherTable
        ) {
            if (teacher.getName().toLowerCase().contains(searchText) || teacher.getId().toLowerCase().contains(searchText)){
                Button button = new Button("Delete");   //---->> Delete button
                TeacherTm tm = new TeacherTm(
                        teacher.getId(),
                        teacher.getName(),
                        teacher.getPhoneNo(),
                        teacher.getAddress(),
                        button
                );

//                delete student
                button.setOnAction(event -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Do you want delete student?",
                            ButtonType.YES,ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)){
                        DbConnection.teacherTable.remove(teacher);
                        new Alert(Alert.AlertType.INFORMATION,"Successfully Deleted!").show();
                        setTableData(searchText);
                    }
                });

                observableList.add(tm);
            }

        }
         tblTeacher.setItems(observableList);
    }

    private void clearField() {
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) teacherContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
