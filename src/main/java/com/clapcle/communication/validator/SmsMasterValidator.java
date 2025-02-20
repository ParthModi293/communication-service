package com.clapcle.communication.validator;

import com.clapcle.communication.config.MessageService;
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


    private final MessageService messageService;
    private final MailEventRepository mailEventRepository;

    public SmsMasterValidator(MessageService messageService,MailEventRepository mailEventRepository) {
        this.messageService = messageService;
        this.mailEventRepository = mailEventRepository;
    }

    public void validateSmsMaster(SmsMasterDto dto) {

        if (!StringUtils.hasText(dto.getTemplateName())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("TEMPLATE_NAME_REQUIRED"),
                    messageService.getMessage("TEMPLATE_NAME_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getPriority().getValue())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SMS_PRIORITY_REQUIRED"),
                    messageService.getMessage("SMS_PRIORITY_REQUIRED"), null);
        }

        if (dto.getEventId() == null || dto.getEventId() <= 0 || ObjectUtils.isEmpty(dto.getEventId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("EVENT_ID_REQUIRED"),
                    messageService.getMessage("EVENT_ID_REQUIRED"), null);
        }

        if (dto.getId() != null && dto.getId() <= 0) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SMS_ID_REQUIRED"),
                    messageService.getMessage("SMS_ID_REQUIRED"), null);
        }

        if (dto.getEventId() != null && !mailEventRepository.existsById(dto.getEventId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("EVENT_ID_NOT_AVAILABLE"),
                    messageService.getMessage("EVENT_ID_NOT_AVAILABLE"), null);
        }
    }
}
