package com.developerStack.eduManage.model;

import java.util.Date;

public class Intake {
    private String intakeId;
    private String intakeName;
    private Date startDate;
    private String programId;

    private boolean intakeCompleteness;

    public Intake() {
    }

    public Intake(String intakeId,String intakeName, Date startDate,  String programId, boolean intakeCompleteness) {
        this.intakeId = intakeId;
        this.intakeName = intakeName;
        this.startDate = startDate;
        this.programId = programId;
        this.intakeCompleteness = intakeCompleteness;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public boolean isIntakeCompleteness() {
        return intakeCompleteness;
    }

    public void setIntakeCompleteness(boolean intakeCompleteness) {
        this.intakeCompleteness = intakeCompleteness;
    }

    @Override
    public String toString() {
        return "Intake{" +
                "intakeId='" + intakeId + '\'' +
                ", startDate=" + startDate +
                ", intakeName='" + intakeName + '\'' +
                ", programId='" + programId + '\'' +
                ", intakeCompleteness=" + intakeCompleteness +
                '}';
    }
}
