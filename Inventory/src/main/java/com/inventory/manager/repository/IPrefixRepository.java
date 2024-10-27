package com.inventory.manager.repository;

import com.inventory.manager.model.Prefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrefixRepository extends JpaRepository<Prefix, Long> {
}
