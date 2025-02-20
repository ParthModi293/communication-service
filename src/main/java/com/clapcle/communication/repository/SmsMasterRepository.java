package com.clapcle.communication.repository;

import com.clapcle.communication.dto.SmsDetailsDto;
import com.clapcle.communication.entity.SmsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmsMasterRepository extends JpaRepository<SmsMaster, Integer> {
    boolean existsByTemplateName(String templateName);

    @Query("""
            select new com.clapcle.communication.dto.SmsDetailsDto(s.priority,s.id,stm.version,stm.govTemplateCode,stm.serviceProviderTemplateCode,ssm.senderCode,spm.apiKey,spm.url) from SmsMaster s 
            inner join SmsTemplateMaster stm on s.id = stm.smsMasterId
            inner join SmsSenderMaster ssm on stm.senderId = ssm.id
            inner join SmsProviderMaster spm on ssm.serviceProviderId = spm.id
            where s.id = :id and stm.isActive = 'Y' and ssm.isActive = 'Y'
            order by stm.createdAt desc 
            """)
    SmsDetailsDto getSmsDetails(@Param("id") int id);


    Optional<SmsMaster> findByTemplateName(String templateName);
}