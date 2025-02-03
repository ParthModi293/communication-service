package org.communication.repository;

import org.communication.entity.SmsTemplateMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsTemplateMasterRepository extends JpaRepository<SmsTemplateMaster, Integer> {
}