package org.communication.validator;

import org.common.common.Const;
import org.common.common.Enum;
import org.common.exception.ValidationException;
import org.communication.config.MessageService;
import org.communication.dto.SmsSenderMasterDto;
import org.communication.repository.SmsProviderMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SmsSenderMasterValidator {


    private final SmsProviderMasterRepository smsProviderMasterRepository;
    private final MessageService messageService;

    public SmsSenderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository, MessageService messageService) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.messageService = messageService;
    }

    public void validateSenderMasterRequest(SmsSenderMasterDto dto) {

        if (!StringUtils.hasText(dto.getSenderCode())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SENDER_CODE_REQUIRED"),
                    messageService.getMessage("SENDER_CODE_REQUIRED"), null);
        }

        if(dto.getSenderCode().length() > 45){
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SENDER_CODE_MAX_LENGTH"),
                    messageService.getMessage("SENDER_CODE_MAX_LENGTH"), null);
        }

        if (dto.getServiceProviderId() == null || dto.getServiceProviderId() <=0  || !smsProviderMasterRepository.existsById(dto.getServiceProviderId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("INVALID_SERVICE_PROVIDER_ID"),
                    messageService.getMessage("INVALID_SERVICE_PROVIDER_ID"), null);
        }

        if (!StringUtils.hasText(dto.getIsActive())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("ACTIVE_STATUS_REQUIRED"),
                    messageService.getMessage("ACTIVE_STATUS_REQUIRED"), null);
        }

        /*if (dto.getCreatedBy() != null && dto.getCreatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST,
                    messageService.getMessage("CREATED_BY_MAX_LENGTH"),
                    messageService.getMessage("CREATED_BY_MAX_LENGTH"), null);
        }

        if (dto.getUpdatedBy() != null && dto.getUpdatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST,
                    messageService.getMessage("UPDATED_BY_MAX_LENGTH"),
                    messageService.getMessage("UPDATED_BY_MAX_LENGTH"), null);
        }*/
    }
}
