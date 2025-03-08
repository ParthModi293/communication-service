package com.clapcle.communication.validator;

import com.clapcle.communication.config.CommunicationLocaleService;
import com.clapcle.communication.dto.SmsProviderMasterDto;
import com.clapcle.communication.repository.SmsProviderMasterRepository;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class SmsProviderMasterValidator {

    private final SmsProviderMasterRepository smsProviderMasterRepository;
    private final CommunicationLocaleService communicationLocaleService;

    public SmsProviderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository, CommunicationLocaleService communicationLocaleService) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.communicationLocaleService = communicationLocaleService;
    }

    public void validateSmsProviderRequest(SmsProviderMasterDto dto) {
        if (!StringUtils.hasText(dto.getName())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("PROVIDER_NAME_REQUIRED"),
                    communicationLocaleService.getMessage("PROVIDER_NAME_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getApiKey())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("API_KEY_REQUIRED"),
                    communicationLocaleService.getMessage("API_KEY_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getUrl())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("URL_REQUIRED"),
                    communicationLocaleService.getMessage("URL_REQUIRED"), null);
        }

        if (smsProviderMasterRepository.existsByName(dto.getName())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("PROVIDER_NAME_EXISTS"),
                    communicationLocaleService.getMessage("PROVIDER_NAME_EXISTS"), null);
        }

        if (smsProviderMasterRepository.existsByApiKey(dto.getApiKey())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("API_KEY_EXISTS"),
                    communicationLocaleService.getMessage("API_KEY_EXISTS"), null);
        }

        if (smsProviderMasterRepository.existsByUrl(dto.getUrl())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("URL_EXISTS"),
                    communicationLocaleService.getMessage("URL_EXISTS"), null);
        }

    }
}