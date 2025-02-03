package org.communication.validator;

import org.common.common.Const;
import org.common.exception.ValidationException;
import org.communication.dto.SmsTemplateMasterDto;
import org.communication.repository.SmsMasterRepository;
import org.communication.repository.SmsSenderMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SmsTemplateMasterValidator {

    private final SmsSenderMasterRepository smsSenderMasterRepository;
    private final SmsMasterRepository smsMasterRepository;

    public SmsTemplateMasterValidator(SmsSenderMasterRepository smsSenderMasterRepository, SmsMasterRepository smsMasterRepository) {
        this.smsSenderMasterRepository = smsSenderMasterRepository;
        this.smsMasterRepository = smsMasterRepository;
    }

    public void validateSmsTemplateRequest(SmsTemplateMasterDto dto) {
        if (!StringUtils.hasText(dto.getBody())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Body cannot be empty!", "Body cannot be empty!", null);
        }
        if (!StringUtils.hasText(dto.getGovTemplateCode())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Gov Template Code is required!", "Gov Template Code is required!", null);
        }
        if (!StringUtils.hasText(dto.getIsActive())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Status is required!", "Status is required!", null);
        }

        if(dto.getSenderId()!=null && !smsSenderMasterRepository.existsById(dto.getSenderId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Sender ID is not available!", "Sender ID is not available!", null);
        }
        if(dto.getSmsMasterId()!=null && !smsMasterRepository.existsById(dto.getSmsMasterId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Sms ID is not available!", "Sms ID is not available!", null);
        }

        if (dto.getSenderId() == null || dto.getSenderId() <= 0 || ObjectUtils.isEmpty(dto.getSenderId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Sender ID is required!", "Sender ID is required!", null);
        }
        if (dto.getSmsMasterId() == null || dto.getSmsMasterId() <= 0 || ObjectUtils.isEmpty(dto.getSmsMasterId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "SMS master ID is required!", "SMS master ID is required!", null);
        }
        if (!StringUtils.hasText(dto.getVersionType())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Version type is required!", "Version type is required!", null);
        }
    }
}
