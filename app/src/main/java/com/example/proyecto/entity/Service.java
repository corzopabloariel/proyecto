package com.example.proyecto.entity;

import java.util.Date;

public class Service {
    private String _title;
    private String _description;
    private Date _create;
    private User _user;

    public Service() {}

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public Date get_create() {
        return _create;
    }

    public void set_create(Date _create) {
        this._create = _create;
    }

    public User get_user() {
        return _user;
    }

    public void set_user(User _user) {
        this._user = _user;
    }
}
