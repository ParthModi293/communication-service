package org.communication.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.communication.entity.SmsProviderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsProviderMasterRepository extends JpaRepository<SmsProviderMaster, Integer> {

    boolean existsByName(String name);

    boolean existsByApiKey(String apiKey);

    boolean existsByUrl( String url);
}