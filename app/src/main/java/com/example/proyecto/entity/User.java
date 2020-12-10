package com.example.proyecto.entity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Map;

public class User {

    private String _id;
    private String _name;
    private String _email;
    private String _pass;
    private Integer _dni;
    private Map<String, Object> servicio;
    private boolean _isAdmin;
    private FirebaseAuth _mAuth;
    private DatabaseReference _mDataBase;

    public User(String name, String email, String pass) {
        this._name = name;
        this._email = email;
        this._pass = pass;
    }

    public String welcome() {
        return "Hola " + this._name;
    }

    /* GETTERS */
    public String get_id() {
        return _id;
    }
    public String get_name() {
        return _name;
    }
    public String get_email() {
        return _email;
    }
    public void set_email(String _email) {
        this._email = _email;
    }
    public String get_pass() {
        return _pass;
    }
    public Integer get_dni() {
        return _dni;
    }
    public boolean is_isAdmin() {
        return _isAdmin;

    }

    /* SETTERS */
    public void set_id(String _id) {
        this._name = _id;
    }
    public void set_name(String _name) {
        this._name = _name;
    }
    public void set_pass(String _pass) {
        this._pass = _pass;
    }
    public void set_dni(Integer _dni) {
        this._dni = _dni;
    }
    public void set_isAdmin(boolean _isAdmin) {
        this._isAdmin = _isAdmin;
    }
    /* SETTERS */
}
