package com.fourtyonestudio.cloticapsuleloopback.model;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.User;
import com.strongloop.android.loopback.UserRepository;

/**
 * Created by Riris.
 */
public class Users extends Model {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

//public class Users extends User {
//    private String username;
//    private String password;
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//}

