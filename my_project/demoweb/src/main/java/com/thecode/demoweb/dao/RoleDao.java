package com.thecode.demoweb.dao;

import com.thecode.demoweb.entity.Role;

public interface RoleDao {

    public Role findRoleByName(String theRoleName);

}
