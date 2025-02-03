package org.communication.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.communication.entity.SmsMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsMasterRepository extends JpaRepository<SmsMaster, Integer> {
    boolean existsByTemplateName(String templateName);
}