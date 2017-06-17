package com.example.chie.notifitest0429;

/**
 * Created by chie on 2017/06/11.
 */

public class User {
    public long createdAt;
    public long endedAt;
    public String  token;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }

    public User(long createdAt, long endedAt, String token) {
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.token = token;
    }

}
