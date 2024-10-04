package com.loonds.acl.repository;

import com.loonds.acl.model.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Query, String> {
}
