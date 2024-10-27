package com.inventory.manager.repository;

import com.inventory.manager.model.Consecutive;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsecutiveRepository extends JpaRepository<Consecutive, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COALESCE(MAX(c.consecutive), 0) FROM Consecutive c WHERE c.prefix.prefix = :prefix")
    int findMaxConsecutive(@Param("prefix") String prefix);
}
