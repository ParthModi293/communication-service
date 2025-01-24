package org.communication.service;

import org.common.common.ResponseBean;
import org.communication.config.MessageService;
import org.communication.dto.TemplateDetailsDto;
import org.communication.entity.TemplateDetails;
import org.communication.repository.TemplateDetailsRepository;
import org.communication.validator.TemplateDetailsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service

public class TemplateDetailsService {

    private final TemplateDetailsValidator templateDetailsValidator;
    private final TemplateDetailsRepository templateDetailsRepository;
    private final MessageService messageService;

    public TemplateDetailsService(TemplateDetailsValidator templateDetailsValidator, TemplateDetailsRepository templateDetailsRepository, MessageService messageService) {
        this.templateDetailsValidator = templateDetailsValidator;
        this.templateDetailsRepository = templateDetailsRepository;
        this.messageService = messageService;
    }

    public ResponseBean<TemplateDetails> createTemplateDetail(TemplateDetailsDto templateDetailsDto) {
        templateDetailsValidator.validateTemplateDetails(templateDetailsDto);
        double result = 0.0;
        if (templateDetailsDto.getId() > 0) {
            TemplateDetails existTemplateDetails = templateDetailsRepository.findById(templateDetailsDto.getId()).orElse(null);
            result = existTemplateDetails.getVersion();
        }

        TemplateDetails templateDetails = new TemplateDetails(templateDetailsDto.getSubject(), templateDetailsDto.getBody(),
                templateDetailsDto.getTemplateMastId(), generateVersionSeries(result), LocalDateTime.now(), LocalDateTime.now(),
                templateDetailsDto.getIsActive(), templateDetailsDto.getFromEmailId());

        templateDetails = saveTemplateDetails(templateDetails);
        return new ResponseBean<>(HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAILS_ADD"), messageService.getMessage("TEMPLATE_DETAILS_ADD"), templateDetails);
    }

    public TemplateDetails saveTemplateDetails(TemplateDetails templateDetails) {
        return templateDetailsRepository.save(templateDetails);
    }

    public ResponseBean<?> getTemplateDetail(int templateMastId) {
        TemplateDetails templateDetailsByTemplateMastId = getTemplateDetailsByTemplateMastId(templateMastId);
        return new ResponseBean<>(HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAILS_FETCH"), messageService.getMessage("TEMPLATE_DETAILS_FETCH"), templateDetailsByTemplateMastId);
    }

    public TemplateDetails getTemplateDetailsByTemplateMastId(int templateMastId) {
        templateDetailsValidator.validateTemplateMastId(templateMastId);
        return templateDetailsRepository.findFirstByTemplateMastIdOrderByCreatedAtDesc(templateMastId);
    }

    public static double generateVersionSeries(double currentVersion) {
        currentVersion += 0.1;
        currentVersion = Math.round(currentVersion * 10.0) / 10.0;
        return Double.parseDouble(String.format("%.1f", currentVersion));
    }
}
