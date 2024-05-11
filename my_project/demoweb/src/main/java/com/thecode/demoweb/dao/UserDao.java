package com.thecode.demoweb.dao;

import com.thecode.demoweb.entity.User;

import java.util.ArrayList;

public interface UserDao {

    ArrayList<User> findAll();

    User findByUserName(String userName);

    User findById(int theId);

    void save(User theUser);


    void deleteById(int theId);

}
