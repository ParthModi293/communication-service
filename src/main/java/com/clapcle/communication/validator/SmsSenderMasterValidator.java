package com.clapcle.communication.validator;

import com.clapcle.communication.config.CommunicationLocaleService;
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
    private final CommunicationLocaleService communicationLocaleService;
    private final SmsSenderMasterRepository smsSenderMasterRepository;

    public SmsSenderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository, CommunicationLocaleService communicationLocaleService, SmsSenderMasterRepository smsSenderMasterRepository) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.communicationLocaleService = communicationLocaleService;
        this.smsSenderMasterRepository = smsSenderMasterRepository;
    }

    public void validateSenderMasterRequest(SmsSenderMasterDto dto) {

        if (!StringUtils.hasText(dto.getSenderCode())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("SENDER_CODE_REQUIRED"),
                    communicationLocaleService.getMessage("SENDER_CODE_REQUIRED"), null);
        }

        if (smsSenderMasterRepository.existsBySenderCode(dto.getSenderCode())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("SENDER_CODE_EXISTS"),
                    communicationLocaleService.getMessage("SENDER_CODE_EXISTS"), null);
        }

        if (dto.getServiceProviderId() == null || dto.getServiceProviderId() <= 0 || !smsProviderMasterRepository.existsById(dto.getServiceProviderId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("INVALID_SERVICE_PROVIDER_ID"),
                    communicationLocaleService.getMessage("INVALID_SERVICE_PROVIDER_ID"), null);
        }

        if (!StringUtils.hasText(dto.getIsActive())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("ACTIVE_STATUS_REQUIRED"),
                    communicationLocaleService.getMessage("ACTIVE_STATUS_REQUIRED"), null);
        }

    }
}
