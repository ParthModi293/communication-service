package org.communication.validator;

import org.common.common.Const;
import org.communication.config.MessageService;
import org.communication.dto.SmsProviderMasterDto;
import org.communication.repository.SmsProviderMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.common.exception.ValidationException;


@Service
public class SmsProviderMasterValidator {

    private final SmsProviderMasterRepository smsProviderMasterRepository;
    private final MessageService messageService;

    public SmsProviderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository, MessageService messageService) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
        this.messageService = messageService;
    }

    public void validateSmsProviderRequest(SmsProviderMasterDto dto) {
        if (!StringUtils.hasText(dto.getName())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("PROVIDER_NAME_REQUIRED"),
                    messageService.getMessage("PROVIDER_NAME_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getApiKey())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("API_KEY_REQUIRED"),
                    messageService.getMessage("API_KEY_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getUrl())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("URL_REQUIRED"),
                    messageService.getMessage("URL_REQUIRED"), null);
        }

        if (dto.getName().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("PROVIDER_NAME_MAX_LENGTH"),
                    messageService.getMessage("PROVIDER_NAME_MAX_LENGTH"), null);
        }

        if (dto.getApiKey().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("API_KEY_MAX_LENGTH"),
                    messageService.getMessage("API_KEY_MAX_LENGTH"), null);
        }

        if (dto.getUrl().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("URL_MAX_LENGTH"),
                    messageService.getMessage("URL_MAX_LENGTH"), null);
        }

        /*if (dto.getCreatedBy() != null && dto.getCreatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("CREATED_BY_MAX_LENGTH"),
                    messageService.getMessage("CREATED_BY_MAX_LENGTH"), null);
        }

        if (dto.getUpdatedBy() != null && dto.getUpdatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("UPDATED_BY_MAX_LENGTH"),
                    messageService.getMessage("UPDATED_BY_MAX_LENGTH"), null);
        }*/

        if (smsProviderMasterRepository.existsByName(dto.getName())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("PROVIDER_NAME_EXISTS"),
                    messageService.getMessage("PROVIDER_NAME_EXISTS"), null);
        }

        if (smsProviderMasterRepository.existsByApiKey(dto.getApiKey())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("API_KEY_EXISTS"),
                    messageService.getMessage("API_KEY_EXISTS"), null);
        }

        if (smsProviderMasterRepository.existsByUrl(dto.getUrl())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("URL_EXISTS"),
                    messageService.getMessage("URL_EXISTS"), null);
        }

    }
}