package com.thecode.demoweb.controller;

import com.thecode.demoweb.entity.User;
import com.thecode.demoweb.service.UserService;
import com.thecode.demoweb.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService theUserService) {
        userService = theUserService;
    }

    @GetMapping("/admin-home")
    public String adminHome(Model theModel) {
        List<User> theUser = userService.findAll();

        // add to the spring model
        theModel.addAttribute("users", theUser);
        return "admin/admin-home-form";
    }

    @GetMapping("/admin-addUser")
    public String adminAddUser(Model theModel) {

        User theUser = new User();

        theModel.addAttribute("user",theUser);

        return "admin/admin-addUser-form";
    }

    @PostMapping("/processAddUser")
    public String processRegistrationForm(
            @RequestParam("status") String status,
            @Valid @ModelAttribute("user") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        if (theBindingResult.hasErrors()) {
            return "admin/admin-addUser-form";
        }

        String userName = theWebUser.getUserName();

        if (status.equals("update")) {
            User existing = userService.findByUserName(userName);
            if (existing != null) {
                // Update existing user
                userService.save(theWebUser);
            }
            // Use a redirect to prevent duplicate submissions
            return "redirect:/admin/admin-home";
        }

        // Check if the user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            theModel.addAttribute("user", new WebUser());
            theModel.addAttribute("addUserError", "User name already exists.");
            return "admin/admin-addUser-form";
        } else {
            // Create user account and store in the database
            userService.save(theWebUser);


            // Place user in the web http session for later use
            session.setAttribute("user", theWebUser);

            return "redirect:/admin/admin-home";
        }
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("userId") int theId, Model theModel){

        // get the user form the service
        User theUser = userService.findByID(theId);

        // set user in the model to prepopulate the form
        theModel.addAttribute("user", theUser);

        return "admin/admin-addUser-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") int theId){

        // delete the user
        userService.deleteById(theId);

        // redirect:/admin/home
        return "redirect:/admin/admin-home";

    }

}
