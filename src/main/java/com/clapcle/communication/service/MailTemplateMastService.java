package com.clapcle.communication.service;

import com.clapcle.communication.common.ConstCommunication;
import com.clapcle.communication.config.CommunicationLocaleService;
import com.clapcle.communication.dto.MailTemplateMastDto;
import com.clapcle.communication.dto.MailTemplateMastFilterRequest;
import com.clapcle.communication.entity.MailTemplateMast;
import com.clapcle.communication.repository.MailTemplateMastRepository;
import com.clapcle.communication.validator.MailTemplateMastValidator;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.common.Pagination;
import com.clapcle.core.common.ResponseBean;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MailTemplateMastService {

    private final MailTemplateMastRepository mailTemplateMastRepository;
    private final MailTemplateMastValidator mailTemplateMastValidator;
    private final CommunicationLocaleService communicationLocaleService;

    public MailTemplateMastService(MailTemplateMastRepository mailTemplateMastRepository, MailTemplateMastValidator mailTemplateMastValidator, CommunicationLocaleService communicationLocaleService) {
        this.mailTemplateMastRepository = mailTemplateMastRepository;
        this.mailTemplateMastValidator = mailTemplateMastValidator;
        this.communicationLocaleService = communicationLocaleService;
    }

    /**
     * @param mailTemplateMastDto
     * @return A ResponseBean containing the saved MailTemplateMast entity along with an HTTP status and success message.
     * @apiNote Creates or updates a template based on the provided MailTemplateMastDto.
     * If the DTO contains an existing template ID, the method attempts to retrieve
     * and update the existing template. Otherwise, a new template is created.
     * @author [Darshit]
     */
    public ResponseBean<MailTemplateMast> createTemplate(MailTemplateMastDto mailTemplateMastDto) {
        mailTemplateMastValidator.validateTemplateMast(mailTemplateMastDto);
        MailTemplateMast template = new MailTemplateMast();
        if (mailTemplateMastDto.getId() != null && mailTemplateMastDto.getId() > 0) {
            Optional<MailTemplateMast> optionalTemplateMast = mailTemplateMastRepository.findById(mailTemplateMastDto.getId());
            if (optionalTemplateMast.isPresent()) {
                template = optionalTemplateMast.get();
            }
        }
        updateTemplateDetails(template, mailTemplateMastDto);
        MailTemplateMast savedTemplate = mailTemplateMastRepository.save(template);
        return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("TEMPLATE_ADD"), communicationLocaleService.getMessage("TEMPLATE_ADD"), savedTemplate);
    }

    private void updateTemplateDetails(MailTemplateMast template, MailTemplateMastDto mailTemplateMastDto) {
        if (mailTemplateMastDto.getTemplateName() != null) {
            template.setTemplateName(mailTemplateMastDto.getTemplateName());
        }
        if (mailTemplateMastDto.getDescription() != null) {
            template.setDescription(mailTemplateMastDto.getDescription());
        }
        if (mailTemplateMastDto.getPriority() != null) {
            template.setPriority(mailTemplateMastDto.getPriority().getValue());
        }
        if (mailTemplateMastDto.getEventId() != null) {
            template.setEventId(mailTemplateMastDto.getEventId());
        }
        if (mailTemplateMastDto.getId() != null && mailTemplateMastDto.getId() > 0) {
            template.setUpdatedAt(LocalDateTime.now().toString());
        } else {
            template.setCreatedAt(LocalDateTime.now().toString());
            template.setUpdatedAt(LocalDateTime.now().toString());
        }

    }

    /**
     * @param mailTemplateMastFilterRequest
     * @return A ResponseBean containing a list of MailTemplateMast entities along with an HTTP status,
     * success message, and pagination details.
     * @apiNote Retrieves a paginated list of templates based on the provided filter criteria.
     * If a search text is provided and matches the required pattern, the method filters
     * templates by event ID and template name. Otherwise, it fetches all templates by event ID.
     * @author [Darshit]
     */
    public ResponseBean<?> getAllTemplates(MailTemplateMastFilterRequest mailTemplateMastFilterRequest) {

        Pageable pageable = PageRequest.of(mailTemplateMastFilterRequest.getPage() - 1, mailTemplateMastFilterRequest.getSize());
        Page<MailTemplateMast> templateMasts;
        if (StringUtils.isNotBlank(mailTemplateMastFilterRequest.getSearchText()) && Pattern.matches(ConstCommunication.PatternCheck.SearchText, mailTemplateMastFilterRequest.getSearchText())) {
            templateMasts = mailTemplateMastRepository.findByEventIdAndTemplateNameLike(mailTemplateMastFilterRequest.getEventId(), mailTemplateMastFilterRequest.getSearchText(), pageable);
        } else {
            templateMasts = mailTemplateMastRepository.findAllByEventId(mailTemplateMastFilterRequest.getEventId(), pageable);
        }

        return new ResponseBean<>(HttpStatus.OK, HttpStatus.OK.value(), communicationLocaleService.getMessage("TEMPLATE_FETCH"), communicationLocaleService.getMessage("TEMPLATE_FETCH"), templateMasts.getContent(), new Pagination((int) templateMasts.getTotalElements(), mailTemplateMastFilterRequest.getPage(), mailTemplateMastFilterRequest.getSize()));
    }

    /**
     * @param id
     * @return A ResponseBean containing the MailTemplateMast entity if found, otherwise a response with null.
     * @apiNote Retrieves a template by its unique ID.
     * If the template exists, it returns the template details; otherwise, it returns a response with a null value.
     * @author [Darshit]
     */
    public ResponseBean<?> getTemplateById(int id) {
        Optional<MailTemplateMast> templateMast = mailTemplateMastRepository.findById(id);
        if (templateMast.isPresent()) {
            return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("TEMPLATE_FETCH"), communicationLocaleService.getMessage("TEMPLATE_FETCH"), templateMast.get());
        }
        return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("TEMPLATE_NOT_AVAILABLE"), communicationLocaleService.getMessage("TEMPLATE_NOT_AVAILABLE"), null);
    }

    public void deleteTemplate(int id) {
        mailTemplateMastRepository.deleteById(id);
    }
}

