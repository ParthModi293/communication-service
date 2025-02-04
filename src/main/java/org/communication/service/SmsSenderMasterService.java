package org.communication.service;

import org.common.common.ResponseBean;
import org.communication.config.MessageService;
import org.communication.dto.SmsSenderMasterDto;
import org.communication.entity.SmsSenderMaster;
import org.communication.repository.SmsSenderMasterRepository;
import org.communication.validator.SmsSenderMasterValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class SmsSenderMasterService {

    private final SmsSenderMasterValidator validator;
    private final SmsSenderMasterRepository senderMasterRepository;
    private final MessageService messageService;

    public SmsSenderMasterService(SmsSenderMasterValidator validator, SmsSenderMasterRepository senderMasterRepository, MessageService messageService) {
        this.validator = validator;
        this.senderMasterRepository = senderMasterRepository;
        this.messageService = messageService;
    }

    @Transactional
    public ResponseBean<SmsSenderMaster> createSenderMaster(SmsSenderMasterDto requestDTO) {

        validator.validateSenderMasterRequest(requestDTO);

        SmsSenderMaster senderMaster = new SmsSenderMaster();
        senderMaster.setSenderCode(requestDTO.getSenderCode());
        senderMaster.setServiceProviderId(requestDTO.getServiceProviderId());
        senderMaster.setIsActive(requestDTO.getIsActive());
        senderMaster.setCreatedAt(Instant.now());
//        senderMaster.setUpdatedAt(Instant.now());
        senderMaster.setCreatedBy("1");
//        senderMaster.setUpdatedBy("1");
         senderMasterRepository.save(senderMaster);
        return new ResponseBean<>(HttpStatus.OK, messageService.getMessage("SMS_SENDER_ADD"),messageService.getMessage("SMS_SENDER_ADD"),senderMaster);

    }

}
