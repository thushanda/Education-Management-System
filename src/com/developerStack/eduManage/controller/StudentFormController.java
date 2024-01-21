package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.db.DbConnection;
import com.developerStack.eduManage.model.Student;
import com.developerStack.eduManage.view.tm.StudentTm;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class StudentFormController {
    public AnchorPane studentContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSearch;
    public DatePicker txtDob;
    public TableView<StudentTm> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colOption;
    public Button btn;

    String searchTxt = "";

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setStudentId();
        setTableData(searchTxt);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTxt = newValue;
            setTableData(searchTxt);
        });

//        select student
        tblStudent.getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    if (null != newValue){
                        setData(newValue);
                    }
        });
    }

    private void setData(StudentTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtDob.setValue(LocalDate.parse(tm.getDob()));
        txtAddress.setText(tm.getAddress());
        btn.setText("Update Student");
    }

    private void setTableData(String searchTxt) {
        ObservableList<StudentTm> observableList = FXCollections.observableArrayList();
        for (Student student: DbConnection.studentTable
             ) {
            if (student.getFullName().toLowerCase().contains(searchTxt) || student.getStudentId().toLowerCase().contains(searchTxt)){
                Button button = new Button("Delete");   //---->> Delete button
                StudentTm tm = new StudentTm(
                        student.getStudentId(),
                        student.getFullName(),
                        new SimpleDateFormat("yyyy-MM-dd").format(student.getDateOfBirth()),
                        student.getAddress(),
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
                        DbConnection.studentTable.remove(student);
                        new Alert(Alert.AlertType.INFORMATION,"Successfully Deleted!").show();
                        setTableData(searchTxt);
                    }
                });

                observableList.add(tm);
            }

        }
        tblStudent.setItems(observableList);
    }

    private void setStudentId() {
        if (!DbConnection.studentTable.isEmpty()){
            Student lastStudent = DbConnection.studentTable.get(
                    DbConnection.studentTable.size()-1
            );
            String lastId = lastStudent.getStudentId();
            String[] splitData = lastId.split("-");
            String lastIdIntegerNumber = splitData[1];
            int lastIdInt = Integer.parseInt(lastIdIntegerNumber);
            lastIdInt++;
            String generateId = "#IOMS-"+lastIdInt;
            txtId.setText(generateId);
        }else {
            txtId.setText("#IOMS-1");
        }
    }

//    add new student
    public void saveOnAction(ActionEvent actionEvent) {
        LocalDate localDate = txtDob.getValue();
        Date dob = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (btn.getText().equalsIgnoreCase("Save Student")){
            Student student = new Student(
                    txtId.getText(),
                    txtName.getText(),
                    dob,
                    txtAddress.getText()
            );
            System.out.println(student);
            DbConnection.studentTable.add(student);
            setStudentId();
            clearField();
            setTableData(searchTxt);
            new Alert(Alert.AlertType.INFORMATION,"Student Successfully Added!").show();
        }else {
//            update student
            for (Student st: DbConnection.studentTable
                 ) {
                if (st.getStudentId().equals(txtId.getText())){
                    st.setFullName(txtName.getText());
                    st.setDateOfBirth(dob);
                    st.setAddress(txtAddress.getText());
                    new Alert(Alert.AlertType.INFORMATION,"Student Successfully Updated!").show();
                    setTableData(searchTxt);
                    clearField();
                    setStudentId();
                    btn.setText("Save Student");
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING,"Not Found").show();
        }


    }

    public void clearField(){
        txtName.clear();
        txtDob.setValue(null);
        txtAddress.clear();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) studentContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clearField();
        setStudentId();
        btn.setText("Save Student");
    }
}
