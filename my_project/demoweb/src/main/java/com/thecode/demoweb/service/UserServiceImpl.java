package com.thecode.demoweb.service;

import com.thecode.demoweb.dao.RoleDao;
import com.thecode.demoweb.dao.UserDao;
import com.thecode.demoweb.dao.UsersRolesRepository;
import com.thecode.demoweb.entity.Role;
import com.thecode.demoweb.entity.User;
import com.thecode.demoweb.entity.UsersRoles;
import com.thecode.demoweb.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    private RoleDao roleDao;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsersRolesRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ArrayList<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public com.thecode.demoweb.entity.User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public User findByID(int theId) {
        return userDao.findById(theId);
    }

    @Transactional
    @Override
    public void save(WebUser webUser) {
        User user = new User();
        
        user.setUserName(webUser.getUserName());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());
        user.setEmail(webUser.getEmail());
        user.setEnabled(true);

        if (webUser.getRoles().equals("ROLE_MANAGER")){
            // Default registration then role is "ROLE_EMPLOYEE"
            user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_MANAGER")));

            // save user in the database
            userDao.save(user);
        }else if (webUser.getRoles().equals("ROLE_ADMIN")){
            // Default registration then role is "ROLE_EMPLOYEE"
            user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_ADMIN")));

            // save user in the database
            userDao.save(user);
        } else {
            // Default registration then role is "ROLE_EMPLOYEE"
            user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

            // save user in the database
            userDao.save(user);
        }

    }



    @Override
    public void deleteById(int theId) {
        // Lấy người dùng từ userDao
        User theUser = userDao.findById(theId);

        // Lấy danh sách UsersRoles dựa trên userId
        List<UsersRoles> usersRoles = userRoleRepository.findAllByUserId(theId);

        // Xóa các bản ghi từ bảng user_role
        userRoleRepository.deleteAll(usersRoles);

        // Xóa người dùng từ bảng user
        userDao.deleteById(theId);
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }
}
