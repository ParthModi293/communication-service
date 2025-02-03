package org.communication.validator;

import org.common.common.Const;
import org.common.exception.ValidationException;
import org.communication.dto.SmsSenderMasterDto;
import org.communication.repository.SmsProviderMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SmsSenderMasterValidator {


    private final SmsProviderMasterRepository smsProviderMasterRepository;

    public SmsSenderMasterValidator(SmsProviderMasterRepository smsProviderMasterRepository) {
        this.smsProviderMasterRepository = smsProviderMasterRepository;
    }

    public void validateSenderMasterRequest(SmsSenderMasterDto dto) {

        if (!StringUtils.hasText(dto.getSenderCode())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Sender code cannot be empty!", "Sender code cannot be empty!", null);
        }

        if (dto.getServiceProviderId() == null || !smsProviderMasterRepository.existsById(dto.getServiceProviderId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Invalid service provider ID!", "Invalid service provider ID!", null);
        }

        if (!StringUtils.hasText(dto.getIsActive())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Active status is required!", "Active status is required!", null);
        }

        if (dto.getCreatedBy() != null && dto.getCreatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Created by must be at most 255 characters!", "Created by must be at most 255 characters!", null);
        }

        if (dto.getUpdatedBy() != null && dto.getUpdatedBy().length() > 255) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Updated by must be at most 255 characters!", "Updated by must be at most 255 characters!", null);
        }
    }
}
