package com.thecode.demoweb.service;

import com.thecode.demoweb.entity.User;
import com.thecode.demoweb.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

public interface UserService extends UserDetailsService {

    public ArrayList<User> findAll();
    public com.thecode.demoweb.entity.User findByUserName(String userName);

    public com.thecode.demoweb.entity.User findByID(int theId);

    void save(WebUser webUser);


    void deleteById(int theId);

}
