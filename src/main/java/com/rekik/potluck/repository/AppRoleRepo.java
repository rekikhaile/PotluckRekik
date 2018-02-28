package com.rekik.potluck.repository;

import com.rekik.potluck.model.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepo extends CrudRepository<AppRole, Long> {
    AppRole findAppRoleByRoleName(String roleName);
}
