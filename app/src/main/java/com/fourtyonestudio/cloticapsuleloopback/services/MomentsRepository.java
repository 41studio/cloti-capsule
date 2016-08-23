package com.fourtyonestudio.cloticapsuleloopback.services;

import com.fourtyonestudio.cloticapsuleloopback.model.Moments;
import com.strongloop.android.loopback.ModelRepository;

/**
 * Created by Riris.
 */
public class MomentsRepository extends ModelRepository<Moments> {
//    public interface LoginCallback extends UserRepository.LoginCallback<Moments> {
//    }

    public MomentsRepository() {
        super("moment", null, Moments.class);
    }
}