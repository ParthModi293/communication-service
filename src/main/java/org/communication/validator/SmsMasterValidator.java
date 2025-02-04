package org.communication.validator;

import org.common.common.Const;
import org.common.exception.ValidationException;
import org.communication.config.MessageService;
import org.communication.dto.SmsMasterDto;
import org.communication.entity.SmsMaster;
import org.communication.repository.MailEventRepository;
import org.communication.repository.SmsMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SmsMasterValidator {

    private final SmsMasterRepository smsMasterRepository;
    private final MessageService messageService;
    private final MailEventRepository mailEventRepository;

    public SmsMasterValidator(SmsMasterRepository smsMasterRepository, MessageService messageService, MailEventRepository mailEventRepository) {
        this.smsMasterRepository = smsMasterRepository;
        this.messageService = messageService;
        this.mailEventRepository = mailEventRepository;
    }

    public void validateSmsMaster(SmsMasterDto dto) {

        if (!StringUtils.hasText(dto.getTemplateName())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("TEMPLATE_NAME_REQUIRED"),
                    messageService.getMessage("TEMPLATE_NAME_REQUIRED"), null);
        }

        if (!StringUtils.hasText(dto.getPriority().getValue())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SMS_PRIORITY_REQUIRED"),
                    messageService.getMessage("SMS_PRIORITY_REQUIRED"), null);
        }

        if (dto.getEventId() == null || dto.getEventId() <= 0 || ObjectUtils.isEmpty(dto.getEventId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("EVENT_ID_REQUIRED"),
                    messageService.getMessage("EVENT_ID_REQUIRED"), null);
        }

        if (dto.getId() != null && dto.getId() <= 0) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("SMS_ID_REQUIRED"),
                    messageService.getMessage("SMS_ID_REQUIRED"), null);
        }

        if (dto.getEventId() != null && !mailEventRepository.existsById(dto.getEventId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,
                    messageService.getMessage("EVENT_ID_NOT_AVAILABLE"),
                    messageService.getMessage("EVENT_ID_NOT_AVAILABLE"), null);
        }

      /*  if(dto.getTemplateName() !=null && smsMasterRepository.existsByTemplateName((dto.getTemplateName()))){
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Template name already exists!", "Provider name already exists!", null);

        }*/

    }
}
