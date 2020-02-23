package com.ts.server.mask.controller.manage.vo;

public class LoginVo<T> {
    private final String token;
    private final T user;

    public LoginVo(String token, T user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public T getUser() {
        return user;
    }
}
