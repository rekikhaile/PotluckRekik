package com.rekik.potluck.repository;

import com.rekik.potluck.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepo extends CrudRepository<AppUser, Long> {
    AppUser findAppUserByUsername(String username);
}
