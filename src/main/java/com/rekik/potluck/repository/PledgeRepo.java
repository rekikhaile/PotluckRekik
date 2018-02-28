package com.rekik.potluck.repository;

import com.rekik.potluck.model.PledgeItems;
import org.springframework.data.repository.CrudRepository;

public interface PledgeRepo extends CrudRepository<PledgeItems,Long> {
}
