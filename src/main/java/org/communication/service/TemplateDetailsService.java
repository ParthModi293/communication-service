package org.communication.service;

import org.common.common.ResponseBean;
import org.communication.config.MessageService;
import org.communication.dto.TemplateDetailsDto;
import org.communication.entity.TemplateDetail;
import org.communication.repository.TemplateDetailRepository;
import org.communication.validator.TemplateDetailsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TemplateDetailsService {

    private final TemplateDetailsValidator templateDetailsValidator;
    private final TemplateDetailRepository templateDetailRepository;
    private final MessageService messageService;

    public TemplateDetailsService(TemplateDetailsValidator templateDetailsValidator, TemplateDetailRepository templateDetailRepository, MessageService messageService) {
        this.templateDetailsValidator = templateDetailsValidator;
        this.templateDetailRepository = templateDetailRepository;
        this.messageService = messageService;
    }

    /**
     * @apiNote Creates and saves a new TemplateDetail entity based on the provided TemplateDetailsDto.
     * If a template with the same templateMastId already exists, the new template's version is incremented accordingly.
     *
     * @param templateDetailsDto
     * @return A ResponseBean containing the created TemplateDetail entity along with an HTTP status
     *         and success messages.
     * @author [Darshit]
     */
    public ResponseBean<TemplateDetail> createTemplateDetail(TemplateDetailsDto templateDetailsDto) {
        templateDetailsValidator.validateTemplateDetails(templateDetailsDto);
        double result = 0.0;
        TemplateDetail existTemplateDetails = getTemplateDetailsByTemplateMastId(templateDetailsDto.getTemplateMastId());
        if (existTemplateDetails.getId() > 0) {
            result = existTemplateDetails.getVersion();
        }
        TemplateDetail templateDetails = new TemplateDetail(templateDetailsDto.getSubject(), templateDetailsDto.getBody(),
                templateDetailsDto.getTemplateMastId(), generateVersionSeries(result), LocalDateTime.now(), LocalDateTime.now(),
                templateDetailsDto.getIsActive(), templateDetailsDto.getFromEmailId());

        templateDetails = saveTemplateDetails(templateDetails);
        return new ResponseBean<>(HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAILS_ADD"), messageService.getMessage("TEMPLATE_DETAILS_ADD"), templateDetails);
    }

    public TemplateDetail saveTemplateDetails(TemplateDetail templateDetails) {
        return templateDetailRepository.save(templateDetails);
    }

    /**
     * @apiNote  Retrieves the template details associated with the given template master ID.
     *
     * @param templateMastId
     * @return A ResponseBean containing the retrieved TemplateDetail entity along with
     *         an HTTP status and success message.
     * @author [Darshit]
     */
    public ResponseBean<?> getTemplateDetail(int templateMastId) {
        TemplateDetail templateDetailsByTemplateMastId = getTemplateDetailsByTemplateMastId(templateMastId);
        return new ResponseBean<>(HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAILS_FETCH"), messageService.getMessage("TEMPLATE_DETAILS_FETCH"), templateDetailsByTemplateMastId);
    }

    public TemplateDetail getTemplateDetailsByTemplateMastId(int templateMastId) {
        templateDetailsValidator.validateTemplateMastId(templateMastId);
        return templateDetailRepository.findFirstByTemplateMastIdOrderByCreatedAtDesc(templateMastId);
    }

    /**
     * @apiNote Retrieves all template details associated with the given template master ID.
     *
     * @param templateMastId
     * @return A ResponseBean containing a list of TemplateDetail entities along with
     *         an HTTP status and success message.
     * @author [Darshit]
     */
    public ResponseBean<?> getAllTemplateDetail(int templateMastId) {
        List<TemplateDetail> templateDetailsByTemplateMastId = getAllTemplateDetailsByTemplateMastId(templateMastId);
        return new ResponseBean<>(HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAILS_FETCH"), messageService.getMessage("TEMPLATE_DETAILS_FETCH"), templateDetailsByTemplateMastId);
    }

    public List<TemplateDetail> getAllTemplateDetailsByTemplateMastId(int templateMastId) {
        templateDetailsValidator.validateTemplateMastId(templateMastId);
        return templateDetailRepository.findAllByTemplateMastIdOrderByCreatedAtDesc(templateMastId);
    }

    public static double generateVersionSeries(double currentVersion) {
        currentVersion += 0.1;
        currentVersion = Math.round(currentVersion * 10.0) / 10.0;
        return Double.parseDouble(String.format("%.1f", currentVersion));
    }
}

