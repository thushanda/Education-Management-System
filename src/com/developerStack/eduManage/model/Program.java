package com.developerStack.eduManage.model;

import java.util.Arrays;

public class Program {
    private String id;
    private String name;
    private String[] technologies;
    private String teacherId;
    private double cost;

    public Program() {
    }

    public Program(String id, String name, String[] technologies, String teacherId, double cost) {
        this.id = id;
        this.name = name;
        this.technologies = technologies;
        this.teacherId = teacherId;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String[] technologies) {
        this.technologies = technologies;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", technologies=" + Arrays.toString(technologies) +
                ", teacherId=" + teacherId +
                ", cost=" + cost +
                '}';
    }
}
