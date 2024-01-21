package com.developerStack.eduManage.controller;

import com.developerStack.eduManage.db.DbConnection;
import com.developerStack.eduManage.model.Program;
import com.developerStack.eduManage.model.Teacher;
import com.developerStack.eduManage.view.tm.ProgramTm;
import com.developerStack.eduManage.view.tm.TechAddTm;
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
import java.util.ArrayList;
import java.util.Optional;

public class ProgramFormController {
    public AnchorPane programContext;
    public TextField txtProgramId;
    public TextField txtProgramName;
    public TableView<TechAddTm> tblTechnology;
    public TextField txtCost;
    public TableView<ProgramTm> tblProgram;
    public TableColumn colId;
    public TableColumn colRemove;
    public TableColumn colCode;
    public TableColumn colProgram;
    public TableColumn comTeacher;
    public TableColumn colTechno;
    public TableColumn colCost;
    public TableColumn colOption;
    public TextField txtSearch;
    public TextField txtTechnology;
    public Button btn;
    public ComboBox<String> cmbTeachers;
    public TableColumn colTechTechnology;
    public Button btnProgram;

    String searchProgram = "";


    public void initialize(){
        setProgramCode();
        setTeachers();
        loadTable(searchProgram);

        colCode.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        comTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colTechno.setCellValueFactory(new PropertyValueFactory<>("btnTech"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        //-------------
        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTechTechnology.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));

        //Search
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchProgram = newValue;
            loadTable(searchProgram);
        });

        //select program
        tblProgram.getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
            if (null != newValue){
                setData(newValue);
                loadTechnologiesForProgram(DbConnection.programTable.stream()
                        .filter(program -> program.getId().equals(newValue.getProgramId()))
                        .findFirst()
                        .orElse(null));
            }
        });


    }


    ArrayList<String> teacherArray = new ArrayList<>();
    private void setTeachers() {
        for (Teacher t: DbConnection.teacherTable
             ) {
            teacherArray.add(t.getId()+"  "+t.getName());
        }
        ObservableList<String> obList = FXCollections.observableArrayList(teacherArray);
        cmbTeachers.setItems(obList);

    }

    private void setData(ProgramTm pm){
        txtProgramId.setText(pm.getProgramId());
        txtProgramName.setText(pm.getProgramName());
        cmbTeachers.setValue(pm.getTeacher());
        txtCost.setText(String.valueOf(pm.getCost()));
        btnProgram.setText("Update Program");

    }


    private void setProgramCode() {
        if (!DbConnection.programTable.isEmpty()){
            Program lastProgram = DbConnection.programTable.get(
                    DbConnection.programTable.size()-1
            );
            String lastId = lastProgram.getId();
            String[] splitData = lastId.split("-");
            String lastIdIntegerNumber = splitData[1];
            int lastIdInt = Integer.parseInt(lastIdIntegerNumber);
            lastIdInt++;
            String generateId = "#IOMP-"+lastIdInt;
            txtProgramId.setText(generateId);
        }else {
            txtProgramId.setText("#IOMP-1");
        }
    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
        clear();
        setProgramCode();
        btnProgram.setText("Save Program");
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String[] selectedTech = new String[tmList.size()];
        int pointer = 0;
        for (TechAddTm t:tmList
             ) {
            selectedTech[pointer] = t.getName();
            pointer++;
        }

        if (btnProgram.getText().equals("Save Program")){
            Program program = new Program(
                    txtProgramId.getText(),
                    txtProgramName.getText(),
                    selectedTech,
                    cmbTeachers.getValue().split("\\ ")[0],
                    Double.parseDouble(txtCost.getText())
            );
            DbConnection.programTable.add(program);
            setProgramCode();
            loadTable(searchProgram);
            clear();
            new Alert(Alert.AlertType.INFORMATION,"Successfully Added!").show();

        }else {
            for (Program p: DbConnection.programTable
                 ) {
                if (p.getId().equals(txtProgramId.getText())){
                    p.setName(txtProgramName.getText());
                    p.setCost(Double.parseDouble(txtCost.getText()));
                    p.setTeacherId(cmbTeachers.getValue());
                    new Alert(Alert.AlertType.INFORMATION,"Student Successfully Updated!").show();
                    loadTable(searchProgram);
                    clear();
                    setProgramCode();
                    btnProgram.setText("Save Program");
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING,"Not Found").show();
        }
    }

    private void loadTable(String searchProgram){
        ObservableList<ProgramTm> obList = FXCollections.observableArrayList();
        for (Program p:DbConnection.programTable
             ) {
            if (p.getId().toLowerCase().contains(searchProgram) || p.getName().toLowerCase().contains(searchProgram)){
                Button techBtn = new Button("Show Technologies");
                Button dltBtn = new Button("Delete");
                ProgramTm tm = new ProgramTm(
                        p.getId(),
                        p.getName(),
                        p.getTeacherId(),
                        techBtn,
                        p.getCost(),
                        dltBtn
                );

                dltBtn.setOnAction(event -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Do you want delete program?",
                            ButtonType.YES,ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)){
                        DbConnection.programTable.remove(p);
                        new Alert(Alert.AlertType.INFORMATION,"Successfully Deleted!").show();
                        loadTable(searchProgram);
                    }
                });
                obList.add(tm);
            }


        }
        tblProgram.setItems(obList);
    }

    private void clear(){
        txtProgramName.clear();
        txtTechnology.clear();
        txtCost.clear();
        cmbTeachers.setValue(null);
        tmList.clear();
        technologyId = 1;
        tblTechnology.refresh();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) programContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }


    ObservableList<TechAddTm> tmList = FXCollections.observableArrayList();
    private int technologyId = 1;
    public void addTechOnAction(ActionEvent actionEvent) {
        if (!isExists(txtTechnology.getText().trim())){
            Button btn = new Button("Remove");

            TechAddTm tm = new TechAddTm(
                    technologyId, txtTechnology.getText().trim(), btn
            );

            btn.setOnAction(event -> {
                Alert alert = new Alert(
                  Alert.AlertType.CONFIRMATION,
                  "Do you want delete?",
                  ButtonType.YES,ButtonType.NO
                );
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){
                    tmList.remove(tm);
                    for (int i = 0; i < tmList.size(); i++) {
                        tmList.get(i).setCode(i + 1);
                    }
                    tblTechnology.refresh();

                }
            });
            tmList.add(tm);
            tblTechnology.setItems(tmList);
            txtTechnology.clear();
            technologyId++;
        }else {
            txtTechnology.selectAll();
            new Alert(Alert.AlertType.WARNING,"Already Exists!").show();
        }
    }

    private boolean isExists(String tech){
        for (TechAddTm tm:tmList
             ) {
            if (tm.getName().equals(tech)){
                return true;
            }
        }
        return false;
    }

    private void loadTechnologiesForProgram(Program selectedProgram) {
        tmList.clear();
        technologyId = 1;
        if (selectedProgram != null) {
            for (String technology : selectedProgram.getTechnologies()) {
                Button btn = new Button("Remove");
                TechAddTm tm = new TechAddTm(technologyId, technology, btn);

                btn.setOnAction(event -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Do you want to delete?",
                            ButtonType.YES, ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        tmList.remove(tm);
                        for (int i = 0; i < tmList.size(); i++) {
                            tmList.get(i).setCode(i + 1);
                        }
                        tblTechnology.refresh();
                    }
                });

                tmList.add(tm);
                technologyId++;
            }
        }
        tblTechnology.setItems(tmList);
    }
}
