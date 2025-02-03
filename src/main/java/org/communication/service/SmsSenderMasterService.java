package org.communication.service;

import org.common.common.ResponseBean;
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

    public SmsSenderMasterService(SmsSenderMasterValidator validator, SmsSenderMasterRepository senderMasterRepository) {
        this.validator = validator;
        this.senderMasterRepository = senderMasterRepository;
    }

    @Transactional
    public ResponseBean<SmsSenderMaster> createSenderMaster(SmsSenderMasterDto requestDTO) {

        validator.validateSenderMasterRequest(requestDTO);

        SmsSenderMaster senderMaster = new SmsSenderMaster();
        senderMaster.setSenderCode(requestDTO.getSenderCode());
        senderMaster.setServiceProviderId(requestDTO.getServiceProviderId());
        senderMaster.setIsActive(requestDTO.getIsActive());
        senderMaster.setCreatedAt(Instant.now());
        senderMaster.setUpdatedAt(Instant.now());
        senderMaster.setCreatedBy(requestDTO.getCreatedBy());
        senderMaster.setUpdatedBy(requestDTO.getUpdatedBy());
         senderMasterRepository.save(senderMaster);
        return new ResponseBean<>(HttpStatus.OK,"Sms Sender added successfully","Sms Sender added successfully",senderMaster);

    }

}
