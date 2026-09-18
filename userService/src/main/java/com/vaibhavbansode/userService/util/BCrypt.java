package com.vaibhavbansode.userService.util;

import static org.mindrot.jbcrypt.BCrypt.checkpw;

public class BCrypt {

    public static String hashPassword(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }
    public static boolean checkPassword(String password, String hashedPassword) {
        return checkpw(password, hashedPassword);
    }
}
