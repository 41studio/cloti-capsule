package com.fourtyonestudio.cloticapsuleloopback.services;

import com.fourtyonestudio.cloticapsuleloopback.model.Moments;
import com.fourtyonestudio.cloticapsuleloopback.model.Users;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.User;

/**
 * Created by Riris.
 */
public class UserRepository extends ModelRepository<Users> {

    public UserRepository() {
        super("User", null, Users.class);
    }
}

