package com.clapcle.communication.service;

import com.clapcle.communication.config.CommunicationLocaleService;
import com.clapcle.communication.dto.SmsProviderMasterDto;
import com.clapcle.communication.entity.SmsProviderMaster;
import com.clapcle.communication.repository.SmsProviderMasterRepository;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.common.ResponseBean;
import com.clapcle.communication.validator.SmsProviderMasterValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsProviderMasterService {


    private final SmsProviderMasterRepository smsProviderMasterRepository;
    private final SmsProviderMasterValidator smsProviderMasterValidator;
    private final CommunicationLocaleService communicationLocaleService;

    public SmsProviderMasterService(SmsProviderMasterRepository smsProviderMasterRepository, SmsProviderMasterValidator smsProviderMasterValidator, CommunicationLocaleService communicationLocaleService) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.smsProviderMasterValidator = smsProviderMasterValidator;
        this.communicationLocaleService = communicationLocaleService;
    }

    /**
     * @param smsProviderMasterDto {@link SmsProviderMasterDto}
     * @return {@link ResponseBean} containing the saved provider ID.
     * @apiNote Creates a new SMS Provider record.
     * Validates the request before saving the provider details.
     * @author Parth
     */
    @Transactional
    public ResponseBean<Map<String, Object>> createSmsProvider(SmsProviderMasterDto smsProviderMasterDto) {
        smsProviderMasterValidator.validateSmsProviderRequest(smsProviderMasterDto);
        SmsProviderMaster provider = new SmsProviderMaster();
        provider.setName(smsProviderMasterDto.getName());
        provider.setApiKey(smsProviderMasterDto.getApiKey());
        provider.setUrl(smsProviderMasterDto.getUrl());
        provider.setCreatedAt(Instant.now());
        provider.setCreatedBy("1");
        smsProviderMasterRepository.save(provider);

        Map<String, Object> res = new HashMap<>();
        res.put("Id", provider.getId());

        return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("SMS_PROVIDER_ADD"), communicationLocaleService.getMessage("SMS_PROVIDER_ADD"), res);

    }
}
