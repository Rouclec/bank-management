package com.example.test.app.repository;

import com.example.test.app.models.Savings;
import com.example.test.app.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
