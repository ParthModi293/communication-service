package org.communication.service;

import org.common.common.ResponseBean;
import org.communication.dto.SmsProviderMasterDto;
import org.communication.entity.SmsProviderMaster;
import org.communication.repository.SmsProviderMasterRepository;
import org.communication.validator.SmsProviderMasterValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class SmsProviderMasterService {


    private final SmsProviderMasterRepository smsProviderMasterRepository;
    private final SmsProviderMasterValidator smsProviderMasterValidator;

    public SmsProviderMasterService(SmsProviderMasterRepository smsProviderMasterRepository, SmsProviderMasterValidator smsProviderMasterValidator) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.smsProviderMasterValidator = smsProviderMasterValidator;
    }


    @Transactional
    public ResponseBean<SmsProviderMaster> createSmsProvider(SmsProviderMasterDto smsProviderMasterDto) {
        smsProviderMasterValidator.validateSmsProviderRequest(smsProviderMasterDto);
        SmsProviderMaster provider = new SmsProviderMaster();
        provider.setName(smsProviderMasterDto.getName());
        provider.setApiKey(smsProviderMasterDto.getApiKey());
        provider.setUrl(smsProviderMasterDto.getUrl());
        provider.setCreatedAt(Instant.now());
        provider.setUpdatedAt(Instant.now());
        provider.setCreatedBy(smsProviderMasterDto.getCreatedBy());
        provider.setUpdatedBy(smsProviderMasterDto.getUpdatedBy());
         smsProviderMasterRepository.save(provider);

        return new ResponseBean<>(HttpStatus.OK,"Sms provider added successfully","Sms provider added successfully",provider);

    }
}
