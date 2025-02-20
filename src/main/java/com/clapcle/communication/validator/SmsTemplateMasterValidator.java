package com.clapcle.communication.validator;

import com.clapcle.communication.config.LocaleService;
import com.clapcle.communication.dto.SmsTemplateMasterDto;
import com.clapcle.communication.repository.SmsMasterRepository;
import com.clapcle.communication.repository.SmsSenderMasterRepository;
import com.clapcle.communication.repository.SmsTemplateMasterRepository;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


@Service
public class SmsTemplateMasterValidator {

    private final SmsSenderMasterRepository smsSenderMasterRepository;
    private final SmsMasterRepository smsMasterRepository;
    private final LocaleService localeService;
    private final SmsTemplateMasterRepository smsTemplateMasterRepository;

    public SmsTemplateMasterValidator(SmsSenderMasterRepository smsSenderMasterRepository, SmsMasterRepository smsMasterRepository, LocaleService localeService, SmsTemplateMasterRepository smsTemplateMasterRepository) {
        this.smsSenderMasterRepository = smsSenderMasterRepository;
        this.smsMasterRepository = smsMasterRepository;
        this.localeService = localeService;
        this.smsTemplateMasterRepository = smsTemplateMasterRepository;
    }

    public void validateSmsTemplateRequest(SmsTemplateMasterDto dto) {
        if (!StringUtils.hasText(dto.getBody())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("BODY_REQUIRED"),
                    localeService.getMessage("BODY_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getGovTemplateCode())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("GOV_TEMPLATE_CODE_REQUIRED"),
                    localeService.getMessage("GOV_TEMPLATE_CODE_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getIsActive())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("STATUS_REQUIRED"),
                    localeService.getMessage("STATUS_REQUIRED"), null);
        }


        if (dto.getSenderId() != null && !smsSenderMasterRepository.existsById(dto.getSenderId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("SENDER_ID_NOT_AVAILABLE"),
                    localeService.getMessage("SENDER_ID_NOT_AVAILABLE"), null);
        }

        if (dto.getSmsMasterId() != null && !smsMasterRepository.existsById(dto.getSmsMasterId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("SMS_ID_NOT_AVAILABLE"),
                    localeService.getMessage("SMS_ID_NOT_AVAILABLE"), null);
        }

        if (dto.getSenderId() == null || dto.getSenderId() <= 0 || ObjectUtils.isEmpty(dto.getSenderId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("SENDER_ID_REQUIRED"),
                    localeService.getMessage("SENDER_ID_REQUIRED"), null);
        }

        if (dto.getSmsMasterId() == null || dto.getSmsMasterId() <= 0 || ObjectUtils.isEmpty(dto.getSmsMasterId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("SMS_MASTER_ID_REQUIRED"),
                    localeService.getMessage("SMS_MASTER_ID_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getVersionType())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("VERSION_TYPE_REQUIRED"),
                    localeService.getMessage("VERSION_TYPE_REQUIRED"), null);
        }
        if (!StringUtils.hasText(dto.getServiceProviderTemplateCode())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("SERVICE_PROVIDER_TEMPLATE_CODE_REQUIRED"),
                    localeService.getMessage("SERVICE_PROVIDER_TEMPLATE_CODE_REQUIRED"), null);
        }
    }

    public void validateSmsTemplateId(Integer smsTemplateMasterId) {

        if (!smsTemplateMasterRepository.existsById(smsTemplateMasterId)) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("SMS_TEMPLATE_ID_NOT_AVAILABLE"),
                    localeService.getMessage("SMS_TEMPLATE_ID_NOT_AVAILABLE"), null);
        }

        if (smsTemplateMasterId <= 0) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    localeService.getMessage("INVALID_SMS_TEMPLATE_ID"),
                    localeService.getMessage("INVALID_SMS_TEMPLATE_ID"), null);
        }

    }
}
