package com.developerStack.eduManage.view.tm;

import javafx.scene.control.Button;

import java.util.Arrays;

public class ProgramTm {
    private String programId;
    private String programName;

    private String teacher;
    private Button btnTech;
    private double cost;
    private Button btn;

    public ProgramTm() {
    }

    public ProgramTm(String programId, String programName, String teacher, Button btnTech, double cost, Button btn) {
        this.programId = programId;
        this.programName = programName;
        this.teacher = teacher;
        this.btnTech = btnTech;
        this.cost = cost;
        this.btn = btn;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Button getBtnTech() {
        return btnTech;
    }

    public void setBtnTech(Button btnTech) {
        this.btnTech = btnTech;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ProgramTm{" +
                "programId='" + programId + '\'' +
                ", programName='" + programName + '\'' +
                ", teacher='" + teacher  + '\'' +
                ", btnTech=" + btnTech +
                ", cost=" + cost +
                ", btn=" + btn +
                '}';
    }
}
