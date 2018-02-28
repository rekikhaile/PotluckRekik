package com.rekik.potluck.setup;

import com.rekik.potluck.model.*;
import com.rekik.potluck.repository.AppRoleRepo;
import com.rekik.potluck.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;

    @Override
    public void run(String... strings) throws Exception {
        //Add all data that should be in the database at the beginning of the application

        AppRole role = new AppRole();
        role.setRoleName("ADMIN");
        roleRepo.save(role);

        role = new AppRole();
        role.setRoleName("USER");
        roleRepo.save(role);

//Add test data for users

        AppUser user = new AppUser();
        user.setUsername("rekik");
        user.setPassword("password");
        user.setFirstName("Rekik");
        user.setLastName("Haile");
        user.setImage("http://www.nurseryrhymes.org/nursery-rhymes-styles/images/john-jacob-jingleheimer-schmidt.jpg");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);


        user = new AppUser();
        user.setUsername("admin");
        user.setPassword("password");
        user.setFirstName("PotAdmin");
        user.setLastName("CoolGal");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        userRepo.save(user);



    }

}
