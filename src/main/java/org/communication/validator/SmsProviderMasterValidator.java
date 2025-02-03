package org.communication.validator;

import org.common.common.Const;
import org.communication.dto.SmsProviderMasterDto;
import org.communication.repository.SmsProviderMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.common.exception.ValidationException;


@Service
public class SmsProviderMasterValidator {

    private final SmsProviderMasterRepository smsProviderMasterRepository;

    public SmsProviderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
    }

    public void validateSmsProviderRequest(SmsProviderMasterDto dto) {
        if (!StringUtils.hasText(dto.getName())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Provider name cannot be empty!", "Provider name cannot be empty!", null);
        }

        if (!StringUtils.hasText(dto.getApiKey())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "API key is required!", "API key is required!", null);
        }

        if (!StringUtils.hasText(dto.getUrl())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "URL is required!", "URL is required!", null);
        }

        if (dto.getName().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Provider name must be at most 255 characters!", "Provider name must be at most 255 characters!", null);
        }

        if (dto.getApiKey().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "API key must be at most 255 characters!", "API key must be at most 255 characters!", null);
        }

        if (dto.getUrl().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "URL must be at most 255 characters!", "URL must be at most 255 characters!", null);
        }

        if (dto.getCreatedBy() != null && dto.getCreatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Created by must be at most 255 characters!", "Created by must be at most 255 characters!", null);
        }

        if (dto.getUpdatedBy() != null && dto.getUpdatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Updated by must be at most 255 characters!", "Updated by must be at most 255 characters!", null);
        }

        if(smsProviderMasterRepository.existsByName(dto.getName())){
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Provider Name is already exists", "Provider Name is already exists", null);

        }

        if(smsProviderMasterRepository.existsByApiKey(dto.getApiKey())){
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Provider Api  key is already exists", "Provider Api key is already exists", null);

        }
        if(smsProviderMasterRepository.existsByUrl(dto.getUrl())){
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Provider url is already exists", "Provider url is already exists", null);

        }

    }
}