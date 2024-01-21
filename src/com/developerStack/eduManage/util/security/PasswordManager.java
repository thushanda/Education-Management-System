package com.developerStack.eduManage.util.security;

import org.mindrot.BCrypt;

public class PasswordManager {
    public String encode(String rawPassword){
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(10));
    }


    public boolean checkPass(String rawPassword, String hashPassword) {
        return BCrypt.checkpw(rawPassword, hashPassword);
    }
}
