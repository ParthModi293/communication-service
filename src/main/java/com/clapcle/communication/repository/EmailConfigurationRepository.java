package com.clapcle.communication.repository;

import com.clapcle.communication.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, Integer> {
}