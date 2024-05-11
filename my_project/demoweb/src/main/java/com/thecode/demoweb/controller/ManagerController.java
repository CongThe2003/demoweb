package com.thecode.demoweb.controller;

import com.thecode.demoweb.entity.Job;
import com.thecode.demoweb.entity.User;
import com.thecode.demoweb.service.JobService;
import com.thecode.demoweb.service.JobServiceImpl;
import com.thecode.demoweb.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final JobService jobService;

    public ManagerController(JobService theJobService) {
        this.jobService = theJobService;
    }

    @GetMapping("/manager-home")
    public String managerHome(Model theModel) {
        List<Job> theJob = jobService.findAll();

        // add to the spring model
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
    public String showFormForUpdate(@RequestParam("id") int theId, Model theModel){

        // get the job from the data
        Optional<Job> theJob = jobService.findById(theId);

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
        model.addAttribute("jobs", searchResults);
        return "/manager/view-result"; // Trả về tên view để hiển thị kết quả tìm kiếm
    }
}
