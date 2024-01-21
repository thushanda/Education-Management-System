package com.developerStack.eduManage.view.tm;


import javafx.scene.control.Button;

public class IntakeTm {
    private String code;
    private String prName;
    private String startDate;
    private String program;
    private String completeStatus;
    private Button btn;

    public IntakeTm() {
    }

    public IntakeTm(String code, String prName, String startDate, String program, String completeStatus, Button btn) {
        this.code = code;
        this.prName = prName;
        this.startDate = startDate;
        this.program = program;
        this.completeStatus = completeStatus;
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "IntakeTm{" +
                "code='" + code + '\'' +
                ", prName='" + prName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", program='" + program + '\'' +
                ", completeStatus='" + completeStatus + '\'' +
                ", btn=" + btn +
                '}';
    }
}
