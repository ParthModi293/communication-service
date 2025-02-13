package org.communication.validator;

import org.common.common.Const;
import org.common.common.Enum;
import org.common.exception.ValidationException;
import org.communication.config.MessageService;
import org.communication.dto.SmsTemplateMasterDto;
import org.communication.repository.SmsMasterRepository;
import org.communication.repository.SmsSenderMasterRepository;
import org.communication.repository.SmsTemplateMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


@Service
public class SmsTemplateMasterValidator {

    private final SmsSenderMasterRepository smsSenderMasterRepository;
    private final SmsMasterRepository smsMasterRepository;
    private final MessageService messageService;
    private final SmsTemplateMasterRepository smsTemplateMasterRepository;

    public SmsTemplateMasterValidator(SmsSenderMasterRepository smsSenderMasterRepository, SmsMasterRepository smsMasterRepository, MessageService messageService, SmsTemplateMasterRepository smsTemplateMasterRepository) {
        this.smsSenderMasterRepository = smsSenderMasterRepository;
        this.smsMasterRepository = smsMasterRepository;
        this.messageService = messageService;
        this.smsTemplateMasterRepository = smsTemplateMasterRepository;
    }

    public void validateSmsTemplateRequest(SmsTemplateMasterDto dto) {
        if (!StringUtils.hasText(dto.getBody())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("BODY_REQUIRED"),
                    messageService.getMessage("BODY_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getGovTemplateCode())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("GOV_TEMPLATE_CODE_REQUIRED"),
                    messageService.getMessage("GOV_TEMPLATE_CODE_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getIsActive())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("STATUS_REQUIRED"),
                    messageService.getMessage("STATUS_REQUIRED"), null);
        }


        if (dto.getSenderId() != null && !smsSenderMasterRepository.existsById(dto.getSenderId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SENDER_ID_NOT_AVAILABLE"),
                    messageService.getMessage("SENDER_ID_NOT_AVAILABLE"), null);
        }

        if (dto.getSmsMasterId() != null && !smsMasterRepository.existsById(dto.getSmsMasterId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SMS_ID_NOT_AVAILABLE"),
                    messageService.getMessage("SMS_ID_NOT_AVAILABLE"), null);
        }

        if (dto.getSenderId() == null || dto.getSenderId() <= 0 || ObjectUtils.isEmpty(dto.getSenderId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SENDER_ID_REQUIRED"),
                    messageService.getMessage("SENDER_ID_REQUIRED"), null);
        }

        if (dto.getSmsMasterId() == null || dto.getSmsMasterId() <= 0 || ObjectUtils.isEmpty(dto.getSmsMasterId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SMS_MASTER_ID_REQUIRED"),
                    messageService.getMessage("SMS_MASTER_ID_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getVersionType())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("VERSION_TYPE_REQUIRED"),
                    messageService.getMessage("VERSION_TYPE_REQUIRED"), null);
        }
    }

    public void validateSmsTemplateId(Integer smsTemplateMasterId) {

        if(!smsTemplateMasterRepository.existsById(smsTemplateMasterId)) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SMS_TEMPLATE_ID_NOT_AVAILABLE"),
                    messageService.getMessage("SMS_TEMPLATE_ID_NOT_AVAILABLE"), null);
        }

        if(smsTemplateMasterId <= 0){
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("INVALID_SMS_TEMPLATE_ID"),
                    messageService.getMessage("INVALID_SMS_TEMPLATE_ID"), null);
        }

    }
}
