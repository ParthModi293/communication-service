package org.communication.validator;

import org.common.common.Const;
import org.common.exception.ValidationException;
import org.communication.config.MessageService;
import org.communication.dto.TemplateMastDto;
import org.communication.repository.MailEventRepository;
import org.communication.repository.TemplateMastRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TemplateMastValidator {

    private final TemplateMastRepository templateMastRepository;
    private final MailEventRepository mailEventRepository;
    private final MessageService messageService;

    public TemplateMastValidator(TemplateMastRepository templateMastRepository, MailEventRepository mailEventRepository, MessageService messageService) {
        this.templateMastRepository = templateMastRepository;
        this.mailEventRepository = mailEventRepository;
        this.messageService = messageService;
    }

    public void validateTemplateMast(TemplateMastDto templateMastDto) {

        if (!mailEventRepository.existsById(templateMastDto.getEventId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, messageService.getMessage("EVENT_NOT_AVAILABLE"), messageService.getMessage("EVENT_NOT_AVAILABLE"), templateMastDto);
        }
        if (templateMastDto.getId() != null && templateMastDto.getEventId() > 0) {
            if (templateMastRepository.existsByTemplateNameAndEventIdAndIdNot(templateMastDto.getTemplateName(), templateMastDto.getEventId(), templateMastDto.getId())) {
                throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, messageService.getMessage("TEMPLATE_EXIST"), messageService.getMessage("TEMPLATE_EXIST"), templateMastDto);
            }
        } else {
            if (templateMastRepository.existsByTemplateNameAndEventId(templateMastDto.getTemplateName(), templateMastDto.getEventId())) {
                throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, messageService.getMessage("TEMPLATE_EXIST"), messageService.getMessage("TEMPLATE_EXIST"), templateMastDto);
            }
        }
    }
}
