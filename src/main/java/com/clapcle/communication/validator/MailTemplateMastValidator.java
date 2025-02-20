package com.clapcle.communication.validator;

import com.clapcle.communication.config.MessageService;
import com.clapcle.communication.dto.MailTemplateMastDto;
import com.clapcle.communication.repository.MailEventRepository;
import com.clapcle.communication.repository.MailTemplateMastRepository;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MailTemplateMastValidator {

    private final MailTemplateMastRepository mailTemplateMastRepository;
    private final MailEventRepository mailEventRepository;
    private final MessageService messageService;

    public MailTemplateMastValidator(MailTemplateMastRepository mailTemplateMastRepository, MailEventRepository mailEventRepository, MessageService messageService) {
        this.mailTemplateMastRepository = mailTemplateMastRepository;
        this.mailEventRepository = mailEventRepository;
        this.messageService = messageService;
    }

    public void validateTemplateMast(MailTemplateMastDto mailTemplateMastDto) {

        if (!mailEventRepository.existsById(mailTemplateMastDto.getEventId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("EVENT_NOT_AVAILABLE"), messageService.getMessage("EVENT_NOT_AVAILABLE"), mailTemplateMastDto);
        }
        if (mailTemplateMastDto.getId() != null && mailTemplateMastDto.getEventId() > 0) {
            if (mailTemplateMastRepository.existsByTemplateNameAndEventIdAndIdNot(mailTemplateMastDto.getTemplateName(), mailTemplateMastDto.getEventId(), mailTemplateMastDto.getId())) {
                throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_EXIST"), messageService.getMessage("TEMPLATE_EXIST"), mailTemplateMastDto);
            }
        } else {
            if (mailTemplateMastRepository.existsByTemplateNameAndEventId(mailTemplateMastDto.getTemplateName(), mailTemplateMastDto.getEventId())) {
                throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_EXIST"), messageService.getMessage("TEMPLATE_EXIST"), mailTemplateMastDto);
            }
        }
    }
}
