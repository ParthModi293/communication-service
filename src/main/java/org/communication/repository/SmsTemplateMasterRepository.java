package org.communication.repository;

import jakarta.validation.constraints.Size;
import org.communication.entity.SmsTemplateMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsTemplateMasterRepository extends JpaRepository<SmsTemplateMaster, Integer> {
    Optional<SmsTemplateMaster> findByServiceProviderTemplateCode(String serviceProviderTemplateCode);

    Optional<SmsTemplateMaster> findTopByServiceProviderTemplateCodeOrderByCreatedAtDesc( String serviceProviderTemplateCode);
}