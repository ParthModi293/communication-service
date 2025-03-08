package com.clapcle.communication.service;

import com.clapcle.communication.config.CommunicationLocaleService;
import com.clapcle.communication.dto.SmsSenderMasterDto;
import com.clapcle.communication.entity.SmsSenderMaster;
import com.clapcle.communication.repository.SmsSenderMasterRepository;
import com.clapcle.communication.validator.SmsSenderMasterValidator;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.common.ResponseBean;
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
    private final CommunicationLocaleService communicationLocaleService;

    public SmsSenderMasterService(SmsSenderMasterValidator validator, SmsSenderMasterRepository senderMasterRepository, CommunicationLocaleService communicationLocaleService) {
        this.validator = validator;
        this.senderMasterRepository = senderMasterRepository;
        this.communicationLocaleService = communicationLocaleService;
    }

    /**
     * @param requestDTO {@link SmsSenderMasterDto}
     * @return {@link ResponseBean} containing the saved sender ID.
     * @apiNote Creates a new SMS Sender Master record.
     * Validates the request and saves sender details such as sender code, service provider ID, and status.
     * @author Parth
     */
    @Transactional
    public ResponseBean<Map<String, Object>> createSenderMaster(SmsSenderMasterDto requestDTO) {

        validator.validateSenderMasterRequest(requestDTO);

        SmsSenderMaster senderMaster = new SmsSenderMaster();
        senderMaster.setSenderCode(requestDTO.getSenderCode());
        senderMaster.setServiceProviderId(requestDTO.getServiceProviderId());
        senderMaster.setIsActive(requestDTO.getIsActive());
        senderMaster.setCreatedAt(Instant.now());
        senderMaster.setCreatedBy("1");
        senderMasterRepository.save(senderMaster);

        Map<String, Object> res = new HashMap<>();
        res.put("Id", senderMaster.getId());
        return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("SMS_SENDER_ADD"), communicationLocaleService.getMessage("SMS_SENDER_ADD"), res);

    }

}
