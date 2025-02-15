package org.communication.service;

import org.common.common.Const;
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
import java.util.HashMap;
import java.util.Map;

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

    /**
     * @apiNote Creates a new SMS Sender Master record.
     * Validates the request and saves sender details such as sender code, service provider ID, and status.
     *
     * @param requestDTO {@link SmsSenderMasterDto}
     * @return {@link ResponseBean} containing the saved sender ID.
     * @author Parth
     */
    @Transactional
    public ResponseBean<Map<String,Object>> createSenderMaster(SmsSenderMasterDto requestDTO) {

        validator.validateSenderMasterRequest(requestDTO);

        SmsSenderMaster senderMaster = new SmsSenderMaster();
        senderMaster.setSenderCode(requestDTO.getSenderCode());
        senderMaster.setServiceProviderId(requestDTO.getServiceProviderId());
        senderMaster.setIsActive(requestDTO.getIsActive());
        senderMaster.setCreatedAt(Instant.now());
        senderMaster.setCreatedBy("1");
         senderMasterRepository.save(senderMaster);

        Map<String,Object> res = new HashMap<>();
        res.put("Id",senderMaster.getId());
        return new ResponseBean<>(HttpStatus.OK, Const.rCode.SUCCESS, messageService.getMessage("SMS_SENDER_ADD"),messageService.getMessage("SMS_SENDER_ADD"),res);

    }

}
