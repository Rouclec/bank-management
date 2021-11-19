package com.example.test.app.repository;

import com.example.test.app.models.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution,Long> {

    Optional<Institution> findByInstitutionName(String institutionName);
}
