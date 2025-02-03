package org.communication.repository;

import org.communication.entity.SmsProviderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsProviderMasterRepository extends JpaRepository<SmsProviderMaster, Integer> {
}