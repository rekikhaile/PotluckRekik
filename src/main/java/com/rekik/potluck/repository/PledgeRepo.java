package com.rekik.potluck.repository;

import com.rekik.potluck.model.AppUser;
import com.rekik.potluck.model.PledgeItems;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;

public interface PledgeRepo extends CrudRepository<PledgeItems,Long> {
    HashSet<PledgeItems> findPledgeItemsByPusersIn(HashSet <AppUser> pusers);
    HashSet<PledgeItems> findPledgeItemsByPusers(AppUser appUser);
}
