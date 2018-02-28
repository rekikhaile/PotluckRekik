package com.rekik.potluck.repository;

import com.rekik.potluck.model.Pledge;
import org.springframework.data.repository.CrudRepository;

public interface PledgeRepo extends CrudRepository<Pledge,Long> {
}
