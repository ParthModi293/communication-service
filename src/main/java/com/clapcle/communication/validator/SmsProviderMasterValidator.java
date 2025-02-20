package com.clapcle.communication.validator;

import com.clapcle.communication.config.LocaleService;
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
    private final LocaleService localeService;

    public SmsProviderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository, LocaleService localeService) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.localeService = localeService;
    }

    public void validateSmsProviderRequest(SmsProviderMasterDto dto) {
        if (!StringUtils.hasText(dto.getName())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("PROVIDER_NAME_REQUIRED"),
                    localeService.getMessage("PROVIDER_NAME_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getApiKey())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("API_KEY_REQUIRED"),
                    localeService.getMessage("API_KEY_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getUrl())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("URL_REQUIRED"),
                    localeService.getMessage("URL_REQUIRED"), null);
        }

        if (smsProviderMasterRepository.existsByName(dto.getName())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("PROVIDER_NAME_EXISTS"),
                    localeService.getMessage("PROVIDER_NAME_EXISTS"), null);
        }

        if (smsProviderMasterRepository.existsByApiKey(dto.getApiKey())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("API_KEY_EXISTS"),
                    localeService.getMessage("API_KEY_EXISTS"), null);
        }

        if (smsProviderMasterRepository.existsByUrl(dto.getUrl())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("URL_EXISTS"),
                    localeService.getMessage("URL_EXISTS"), null);
        }

    }
}