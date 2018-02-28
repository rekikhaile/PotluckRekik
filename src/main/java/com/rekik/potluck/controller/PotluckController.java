package com.rekik.potluck.controller;

import com.rekik.potluck.model.AppUser;
import com.rekik.potluck.model.PledgeItems;
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
import java.util.HashSet;

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



    @RequestMapping("/getMyPledges")
    public String getMyPledges(Authentication auth, Model model)
    {

       HashSet<AppUser> myUsers = new HashSet<>();
        myUsers.add(userRepo.findAppUserByUsername(auth.getName()));

        //AppUser myUser = userRepo.findAppUserByUsername(auth.getName());

        HashSet <PledgeItems> myPledges = pledgeRepo.findPledgeItemsByPusersIn(myUsers);

        System.out.println(myPledges.toString());
        model.addAttribute("pledgelist",myPledges);
        return "viewmypledges";
    }


    @GetMapping("/addpledge")
    public String addPledge(Model model){
        model.addAttribute("aPledge", new PledgeItems());
        return "pledgeform";
    }

    @PostMapping("/addpledge")
    public String saveAddpledge(@Valid @ModelAttribute("aPledge") PledgeItems pledge, BindingResult result, Authentication auth)
    {
        if(result.hasErrors())
        {
            return "pledgeform";
        }
        pledgeRepo.save(pledge);
        AppUser currentUser =  userRepo.findAppUserByUsername(auth.getName());


        return "redirect:/";
    }

    @RequestMapping("/listpledges")
    public String listPledges(Model model)
    {
        model.addAttribute("pledgelist",pledgeRepo.findAll());
        return "listpledges";
    }


    @RequestMapping("/addusertopledge")
    public String showUsersForPledge(HttpServletRequest request, Model model)
    {
        String pledgeid = request.getParameter("pledgeid");
        model.addAttribute("newpledge",pledgeRepo.findOne(new Long(pledgeid)));

        //Make users disappear from add form when they are already included (Set already makes it impossible to add multiple)
        model.addAttribute("userList",userRepo.findAll());

        return "addusertopledge";
    }

    @PostMapping("/saveusertopledge")
    public String addUsertoPledge(HttpServletRequest request, @ModelAttribute("newpledge") PledgeItems pledge)
    {
        String userid = request.getParameter("userid");
        System.out.println("Pledge id from add user to pledge:"+pledge.getId()+" User id:"+userid);
        pledge.addUsertoPledge(userRepo.findOne(new Long(userid)));
        pledgeRepo.save(pledge);
        return "redirect:/listpledges";
    }

    @RequestMapping("/viewpledgeusers")
    public String viewPledgeUsers(HttpServletRequest request, Model model)
    {
        String pledgeid = request.getParameter("pledgeid");
        PledgeItems pledge = pledgeRepo.findOne(new Long(pledgeid));
        if(pledge.getPusers().size()<1)
            return "redirect:/";
        model.addAttribute("newpledge",pledge);
        return "viewpledgeusers";
    }



}
