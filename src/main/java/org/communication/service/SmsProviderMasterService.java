package org.communication.service;

import org.common.common.Const;
import org.common.common.ResponseBean;
import org.communication.config.MessageService;
import org.communication.dto.SmsProviderMasterDto;
import org.communication.entity.SmsProviderMaster;
import org.communication.repository.SmsProviderMasterRepository;
import org.communication.validator.SmsProviderMasterValidator;
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
    private final MessageService messageService;

    public SmsProviderMasterService(SmsProviderMasterRepository smsProviderMasterRepository, SmsProviderMasterValidator smsProviderMasterValidator, MessageService messageService) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.smsProviderMasterValidator = smsProviderMasterValidator;
        this.messageService = messageService;
    }

    /**
     * @apiNote Creates a new SMS Provider record.
     * Validates the request before saving the provider details.
     * @param smsProviderMasterDto {@link SmsProviderMasterDto}
     * @return {@link ResponseBean} containing the saved provider ID.
     * @author Parth
     */
    @Transactional
    public ResponseBean<Map<String,Object>> createSmsProvider(SmsProviderMasterDto smsProviderMasterDto) {
        smsProviderMasterValidator.validateSmsProviderRequest(smsProviderMasterDto);
        SmsProviderMaster provider = new SmsProviderMaster();
        provider.setName(smsProviderMasterDto.getName());
        provider.setApiKey(smsProviderMasterDto.getApiKey());
        provider.setUrl(smsProviderMasterDto.getUrl());
        provider.setCreatedAt(Instant.now());
        provider.setCreatedBy("1");
        smsProviderMasterRepository.save(provider);

        Map<String,Object> res = new HashMap<>();
        res.put("Id",provider.getId());

        return new ResponseBean<>(HttpStatus.OK, Const.rCode.SUCCESS, messageService.getMessage("SMS_PROVIDER_ADD"),messageService.getMessage("SMS_PROVIDER_ADD"),res);

    }
}
