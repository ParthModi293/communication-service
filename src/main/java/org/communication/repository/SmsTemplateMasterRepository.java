package org.communication.repository;

import org.communication.entity.SmsTemplateMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmsTemplateMasterRepository extends JpaRepository<SmsTemplateMaster, Integer> {
    Optional<SmsTemplateMaster> findByServiceProviderTemplateCode(String serviceProviderTemplateCode);

    Optional<SmsTemplateMaster> findTopByServiceProviderTemplateCodeOrderByCreatedAtDesc( String serviceProviderTemplateCode);

        @Query(value = "SELECT tm FROM SmsTemplateMaster tm JOIN SmsMaster sm ON tm.smsMasterId=sm.id WHERE (LOWER(sm.templateName) like lower(concat('%',:searchText,'%') ) OR LOWER(tm.body) like lower(concat('%',:searchText,'%') ))")
        Page<SmsTemplateMaster> findByTemplateNameAndBodyLike(@Param("searchText") String searchText, Pageable pageable);

}