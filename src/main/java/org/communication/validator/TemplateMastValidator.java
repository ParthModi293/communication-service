package org.communication.validator;

import org.communication.Exception.TemplateMastException;
import org.communication.dto.TemplateMastDto;
import org.communication.repository.MailEventRepository;
import org.communication.repository.TemplateMastRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TemplateMastValidator {

    private final TemplateMastRepository templateMastRepository;
    private final MailEventRepository mailEventRepository;

    public TemplateMastValidator(TemplateMastRepository templateMastRepository, MailEventRepository mailEventRepository) {
        this.templateMastRepository = templateMastRepository;
        this.mailEventRepository = mailEventRepository;
    }

    public void validateTemplateMast(TemplateMastDto templateMastDto) {

        if (!mailEventRepository.existsById(templateMastDto.getEventId())) {
            throw new TemplateMastException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "This Event is not available", "This Event is not available", templateMastDto);
        }
        if (templateMastDto.getId() != null && templateMastDto.getEventId() > 0) {
            if (templateMastRepository.existsByTemplateNameAndEventIdAndIdNot(templateMastDto.getTemplateName(), templateMastDto.getEventId(), templateMastDto.getId())) {
                throw new TemplateMastException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Template name is already exist on this event", "Template name is already exist on this event", templateMastDto);
            }
        } else {
            if (templateMastRepository.existsByTemplateNameAndEventId(templateMastDto.getTemplateName(), templateMastDto.getEventId())) {
                throw new TemplateMastException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Template name is already exist on this event", "Template name is already exist on this event", templateMastDto);
            }
        }
    }
}
