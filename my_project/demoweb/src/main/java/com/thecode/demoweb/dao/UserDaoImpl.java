package com.thecode.demoweb.dao;

import com.thecode.demoweb.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public class UserDaoImpl implements UserDao{

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public ArrayList<User> findAll() {
        TypedQuery<User> theQuery = entityManager.createQuery("from User",User.class);
        ArrayList<User> theUser = (ArrayList<User>) theQuery.getResultList();

        return theUser;
    }

    @Override
    public User findByUserName(String theUserName) {

        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName and enabled=true", User.class);
        theQuery.setParameter("uName",theUserName);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        }catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    public User findById(int theId) {
        TypedQuery<User> theQuery = entityManager.createQuery("from User where id=:theId and enabled=true", User.class);
        theQuery.setParameter("theId",theId);

        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        }catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    @Transactional
    public void save(User theUser) {
        entityManager.merge(theUser);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        // get User
        User theUser = entityManager.find(User.class, theId);

        // delete User
        entityManager.remove(theUser);
    }
}
