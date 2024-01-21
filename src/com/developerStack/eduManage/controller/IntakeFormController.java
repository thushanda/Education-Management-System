package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.db.DbConnection;
import com.developerStack.eduManage.model.Intake;
import com.developerStack.eduManage.model.Program;
import com.developerStack.eduManage.view.tm.IntakeTm;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class IntakeFormController {
    public AnchorPane intakeContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtSearch;
    public DatePicker txtStartDate;
    public Button btn;
    public TableView<IntakeTm> tblIntake;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colStartDate;
    public TableColumn colProgramName;
    public TableColumn colComplete;
    public TableColumn colOption;
    public ComboBox<String> cmbProgram;

    String searchTxt = "";

    public void initialize(){
        setIntakeCode();
        setProgram();
        loadTable(searchTxt);


        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("prName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("program"));
        colComplete.setCellValueFactory(new PropertyValueFactory<>("completeStatus"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        //search
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTxt = newValue;
            loadTable(searchTxt);
        });

        tblIntake.getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
            if (null != newValue){
                setData(newValue);
            }
        });
    }

    private void setData(IntakeTm tm) {
        txtId.setText(tm.getCode());
        txtName.setText(tm.getPrName());
        txtStartDate.setValue(LocalDate.parse(tm.getStartDate()));
        cmbProgram.setValue(String.valueOf(tm.getProgram()));
        btn.setText("Update Student");
    }

    ArrayList<String> programArray = new ArrayList<>();
    private void setProgram(){
        for (Program program: DbConnection.programTable
             ) {
            programArray.add(program.getName());
        }
        ObservableList<String> obList = FXCollections.observableArrayList(programArray);
        cmbProgram.setItems(obList);
    }

    private void loadTable(String searchTxt){
        ObservableList<IntakeTm> obList = FXCollections.observableArrayList();
        for (Intake i: DbConnection.intakeTable
             ) {
            if (i.getIntakeName().toLowerCase().contains(searchTxt) || i.getIntakeId().toLowerCase().contains(searchTxt)){
                Button btn = new Button("Delete");
                String completenessStatus = i.isIntakeCompleteness() ? "Complete" : "Incomplete";
                IntakeTm im = new IntakeTm(
                        i.getIntakeId(),
                        i.getIntakeName(),
                        new SimpleDateFormat("yyyy-MM-dd").format(i.getStartDate()),
                        i.getProgramId(),
                        completenessStatus,
                        btn
                );


                //delete
                btn.setOnAction(event -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Do you want delete student?",
                            ButtonType.YES,ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)){
                        DbConnection.intakeTable.remove(i);
                        new Alert(Alert.AlertType.INFORMATION,"Successfully Deleted!").show();
                        loadTable(searchTxt);
                    }
                });
                obList.add(im);
            }


        }
        tblIntake.setItems(obList);
    }

    private void setIntakeCode(){
        if (!DbConnection.intakeTable.isEmpty()){
            Intake lastIntake = DbConnection.intakeTable.get(
                    DbConnection.intakeTable.size()-1
            );
            String lastId = lastIntake.getIntakeId();
            String[] splitData = lastId.split("-");
            String lastIdIntegerNumber = splitData[1];
            int lastIdInt = Integer.parseInt(lastIdIntegerNumber);
            lastIdInt++;
            String generateId = "#IOMI-"+lastIdInt;
            txtId.setText(generateId);
        }else {
            txtId.setText("#IOMI-1");
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveOnAction(ActionEvent actionEvent) {
        LocalDate localDate = txtStartDate.getValue();
        Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (btn.getText().equalsIgnoreCase("Save Intake")){
                Intake i = new Intake(
                        txtId.getText(),
                        txtName.getText(),
                        startDate,
                        cmbProgram.getValue(),
                        false
                );
                System.out.println(i);
                DbConnection.intakeTable.add(i);
                loadTable(searchTxt);
                setIntakeCode();
                clear();
                new Alert(Alert.AlertType.INFORMATION,"Student Successfully Added!").show();
            }else{
                for (Intake i:DbConnection.intakeTable
                     ) {
                    if (i.getIntakeId().equals(txtId.getText())){
                        i.setIntakeName(txtName.getText());
                        i.setStartDate(startDate);
                        i.setProgramId(cmbProgram.getValue());
                        new Alert(Alert.AlertType.INFORMATION,"Successfully Updated!").show();
                        loadTable(searchTxt);
                        clear();
                        setIntakeCode();
                        btn.setText("Save Intake");
                        return;
                    }
                }
            }
    }

    public void newIntakeOnAction(ActionEvent actionEvent) {
        clear();
        setIntakeCode();
        btn.setText("Save Intake");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) intakeContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void clear(){
        txtName.clear();
        txtStartDate.setValue(null);
        cmbProgram.setValue(null);
    }
}
