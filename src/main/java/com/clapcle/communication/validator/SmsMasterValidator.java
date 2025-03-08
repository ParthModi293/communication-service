package com.clapcle.communication.validator;

import com.clapcle.communication.config.CommunicationLocaleService;
import com.clapcle.communication.dto.SmsMasterDto;
import com.clapcle.communication.repository.MailEventRepository;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SmsMasterValidator {


    private final CommunicationLocaleService communicationLocaleService;
    private final MailEventRepository mailEventRepository;

    public SmsMasterValidator(CommunicationLocaleService communicationLocaleService, MailEventRepository mailEventRepository) {
        this.communicationLocaleService = communicationLocaleService;
        this.mailEventRepository = mailEventRepository;
    }

    public void validateSmsMaster(SmsMasterDto dto) {

        if (!StringUtils.hasText(dto.getTemplateName())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("TEMPLATE_NAME_REQUIRED"),
                    communicationLocaleService.getMessage("TEMPLATE_NAME_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getPriority().getValue())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("SMS_PRIORITY_REQUIRED"),
                    communicationLocaleService.getMessage("SMS_PRIORITY_REQUIRED"), null);
        }

        if (dto.getEventId() == null || dto.getEventId() <= 0 || ObjectUtils.isEmpty(dto.getEventId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("EVENT_ID_REQUIRED"),
                    communicationLocaleService.getMessage("EVENT_ID_REQUIRED"), null);
        }

        if (dto.getId() != null && dto.getId() <= 0) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("SMS_ID_REQUIRED"),
                    communicationLocaleService.getMessage("SMS_ID_REQUIRED"), null);
        }

        if (dto.getEventId() != null && !mailEventRepository.existsById(dto.getEventId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    communicationLocaleService.getMessage("EVENT_ID_NOT_AVAILABLE"),
                    communicationLocaleService.getMessage("EVENT_ID_NOT_AVAILABLE"), null);
        }
    }
}
