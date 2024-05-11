package com.thecode.demoweb.dao;

import com.thecode.demoweb.entity.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, Integer> {
    @Transactional
    void deleteByUserId(int userId);

    List<UsersRoles> findAllByUserId(int theId);
}
