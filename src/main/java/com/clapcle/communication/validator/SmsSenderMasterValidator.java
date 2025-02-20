package com.clapcle.communication.validator;

import com.clapcle.communication.config.MessageService;
import com.clapcle.communication.dto.SmsSenderMasterDto;
import com.clapcle.communication.repository.SmsProviderMasterRepository;
import com.clapcle.communication.repository.SmsSenderMasterRepository;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SmsSenderMasterValidator {


    private final SmsProviderMasterRepository smsProviderMasterRepository;
    private final MessageService messageService;
    private final SmsSenderMasterRepository smsSenderMasterRepository;

    public SmsSenderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository, MessageService messageService, SmsSenderMasterRepository smsSenderMasterRepository) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.messageService = messageService;
        this.smsSenderMasterRepository = smsSenderMasterRepository;
    }

    public void validateSenderMasterRequest(SmsSenderMasterDto dto) {

        if (!StringUtils.hasText(dto.getSenderCode())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SENDER_CODE_REQUIRED"),
                    messageService.getMessage("SENDER_CODE_REQUIRED"), null);
        }

        if (smsSenderMasterRepository.existsBySenderCode(dto.getSenderCode())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SENDER_CODE_EXISTS"),
                    messageService.getMessage("SENDER_CODE_EXISTS"), null);
        }

        if (dto.getServiceProviderId() == null || dto.getServiceProviderId() <= 0 || !smsProviderMasterRepository.existsById(dto.getServiceProviderId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("INVALID_SERVICE_PROVIDER_ID"),
                    messageService.getMessage("INVALID_SERVICE_PROVIDER_ID"), null);
        }

        if (!StringUtils.hasText(dto.getIsActive())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("ACTIVE_STATUS_REQUIRED"),
                    messageService.getMessage("ACTIVE_STATUS_REQUIRED"), null);
        }

    }
}
