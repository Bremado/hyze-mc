package com.toddydev.auth.data;

import com.toddydev.auth.data.info.AuthInfo;

import java.util.UUID;

public abstract class DataAuth {

    public abstract void create(AuthInfo authInfo);
    public abstract void save(AuthInfo authInfo);
    public abstract Boolean exists(UUID uniqueId);

    public abstract AuthInfo getAuthInfo(UUID uniqueId);

}
