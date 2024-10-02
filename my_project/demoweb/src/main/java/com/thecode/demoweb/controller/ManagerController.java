package com.thecode.demoweb.controller;

import com.thecode.demoweb.dao.UserDao;
import com.thecode.demoweb.entity.Job;
import com.thecode.demoweb.entity.User;
import com.thecode.demoweb.service.JobService;
import com.thecode.demoweb.service.JobServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final JobService jobService;

    @Autowired
    private UserDao userDao;


    public ManagerController(JobService theJobService) {
        this.jobService = theJobService;
    }

    @GetMapping("/manager-home")
    public String managerHome(Model theModel, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        // Long idUser = getCurrentId();

        // List<Job> theJob = jobService.findAll();

        Page<Job> theJob = jobService.getAllPage(pageNo);

        // add to the spring model
        theModel.addAttribute("totalPage",theJob.getTotalPages());
        theModel.addAttribute("currentPage",pageNo);
        theModel.addAttribute("jobs", theJob);
        return "manager/manager-form";
    }

    @GetMapping("/manager-addJob")
    public String adminAddUser(Model theModel) {

        Job theJob = new Job();

        theModel.addAttribute("jobs",theJob);

        return "manager/manager-addJob-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("id") Long theId, Model theModel){

        // get the job from the data
        List<Job> theJob = jobService.findById(theId);

        // set user in the model to prepopulate the form
        theModel.addAttribute("jobs", theJob);

        return "manager/manager-addJob-form";
    }

    @PostMapping("/processAddUser")
    public String processManagerForm(
            @RequestParam("status") String status,
            @Valid @ModelAttribute("jobs") Job theJob,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        if (theBindingResult.hasErrors()){
            return "manager/manager-addJob-form";
        }


        // create user account and store in the database
        jobService.save(theJob);

        // place user in the web http session for later use
        session.setAttribute("jobs", theJob);

        return "redirect:/manager/manager-home";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("id") int theId){

        // delete the user
        jobService.delete(theId);

        // redirect:/admin/home
        return "redirect:/manager/manager-home";

    }

    @GetMapping("/search")
    public String searchJobs(@RequestParam String searchTerm, Model model) {
        List<Job> searchResults = jobService.findByTitleContainingOrContentContaining(searchTerm,searchTerm);
        
        List<Job> jobForUser = new ArrayList<>();

        for (Job temp:searchResults) {
            if (temp.getIdUser() == getCurrentId()){
                jobForUser.add(temp);
            }
        }
        model.addAttribute("jobs", jobForUser);
        return "/manager/view-result"; // Trả về tên view để hiển thị kết quả tìm kiếm
    }

    public Long getCurrentId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);

        User theUser = userDao.findByUserName(username);
        return theUser.getId();
    }
}
