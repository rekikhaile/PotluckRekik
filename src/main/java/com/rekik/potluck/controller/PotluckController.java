package com.rekik.potluck.controller;

import com.rekik.potluck.model.AppUser;
import com.rekik.potluck.model.Pledge;
import com.rekik.potluck.repository.AppRoleRepo;
import com.rekik.potluck.repository.AppUserRepo;
import com.rekik.potluck.repository.PledgeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class PotluckController {
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;

    @Autowired
    PledgeRepo pledgeRepo;

    @RequestMapping("/")
    public String showHome(Model model){
        model.addAttribute("pledges",pledgeRepo.findAll());
        return "index";
    }

    @RequestMapping("/login")
    public String showLogin(Model model){
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model)
    {
        model.addAttribute("newuser",new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("newuser")AppUser user,
                           BindingResult result, HttpServletRequest request)
    {
        if(result.hasErrors()){
            return "register";
        }

        if(request.getParameter("isAdmin")!=null) // where does that come from
            user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        else
            user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/addpledge")
    public String addPledge(Model model){
        model.addAttribute("aPledge", new Pledge());
        return "pledgeform";
    }

    @PostMapping("/addpledge")
    public String saveAddpledge(@Valid @ModelAttribute("aPledge") Pledge pledge, BindingResult result, Authentication auth)
    {
        if(result.hasErrors())
        {
            return "pledgeform";
        }
        pledgeRepo.save(pledge);
        AppUser currentUser =  userRepo.findAppUserByUsername(auth.getName());


        return "redirect:/";
    }


}
