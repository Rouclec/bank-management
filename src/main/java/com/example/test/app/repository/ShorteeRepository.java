package com.example.test.app.repository;

import com.example.test.app.models.Shortee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShorteeRepository extends JpaRepository<Shortee, Long> {
    Optional<Shortee> findById(Long id);
}
