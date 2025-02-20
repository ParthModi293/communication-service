package com.clapcle.communication.repository;

import com.clapcle.communication.entity.SmsProviderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsProviderMasterRepository extends JpaRepository<SmsProviderMaster, Integer> {

    boolean existsByName(String name);

    boolean existsByApiKey(String apiKey);

    boolean existsByUrl(String url);
}