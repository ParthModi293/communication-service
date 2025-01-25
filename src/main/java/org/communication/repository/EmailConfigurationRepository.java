package org.communication.repository;

import org.communication.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, Integer> {
    EmailConfiguration findByUserName(String username);
}