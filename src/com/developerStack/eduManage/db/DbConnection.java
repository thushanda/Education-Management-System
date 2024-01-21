package com.developerStack.eduManage.db;

import com.developerStack.eduManage.model.*;
import com.developerStack.eduManage.util.security.PasswordManager;

import java.util.ArrayList;

public class DbConnection {
    public static ArrayList<User> userTable = new ArrayList<>();
    public static ArrayList<Student> studentTable = new ArrayList<>();
    public static ArrayList<Teacher> teacherTable = new ArrayList<>();
    public static ArrayList<Program> programTable = new ArrayList<>();
    public static ArrayList<Intake> intakeTable = new ArrayList<>();



    static {
        userTable.add(
                new User("Thushan","Damjith",
                        "thushan@gmail.com",
                        new PasswordManager().encode("1234"))
        );
    }
}
