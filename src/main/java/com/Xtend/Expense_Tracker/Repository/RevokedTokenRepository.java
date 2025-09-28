package com.Xtend.Expense_Tracker.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Xtend.Expense_Tracker.Entity.RevokedToken;

import java.util.Optional;
@Repository
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {
    Optional<RevokedToken> findByToken(String token);
}

