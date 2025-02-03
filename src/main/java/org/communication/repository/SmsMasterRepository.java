package org.communication.repository;

import org.communication.entity.SmsMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsMasterRepository extends JpaRepository<SmsMaster, Integer> {
}