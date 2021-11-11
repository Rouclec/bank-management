package com.example.test.app.repository;

import com.example.test.app.models.Transactions;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findByCreatedBy(String createdBy, Pageable pageable);
}
