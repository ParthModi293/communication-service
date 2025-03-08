package com.clapcle.communication.service;

import com.clapcle.communication.config.CommunicationLocaleService;
import com.clapcle.communication.dto.MailTemplateDetailsDto;
import com.clapcle.communication.entity.MailTemplateDetail;
import com.clapcle.communication.repository.MailTemplateDetailRepository;
import com.clapcle.communication.validator.MailTemplateDetailsValidator;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.common.ResponseBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MailTemplateDetailsService {

    private final MailTemplateDetailsValidator mailTemplateDetailsValidator;
    private final MailTemplateDetailRepository mailTemplateDetailRepository;
    private final CommunicationLocaleService communicationLocaleService;

    public MailTemplateDetailsService(MailTemplateDetailsValidator mailTemplateDetailsValidator, MailTemplateDetailRepository mailTemplateDetailRepository, CommunicationLocaleService communicationLocaleService) {
        this.mailTemplateDetailsValidator = mailTemplateDetailsValidator;
        this.mailTemplateDetailRepository = mailTemplateDetailRepository;
        this.communicationLocaleService = communicationLocaleService;
    }

    /**
     * @param mailTemplateDetailsDto
     * @return A ResponseBean containing the created MailTemplateDetail entity along with an HTTP status
     * and success messages.
     * @apiNote Creates and saves a new MailTemplateDetail entity based on the provided MailTemplateDetailsDto.
     * If a template with the same templateMastId already exists, the new template's version is incremented accordingly.
     * @author [Darshit]
     */
    public ResponseBean<MailTemplateDetail> createTemplateDetail(MailTemplateDetailsDto mailTemplateDetailsDto) {
        mailTemplateDetailsValidator.validateTemplateDetails(mailTemplateDetailsDto);
        double result = 0.0;
        MailTemplateDetail existMailTemplateDetails = getTemplateDetailsByTemplateMastId(mailTemplateDetailsDto.getTemplateMastId());
        if (existMailTemplateDetails.getId() > 0) {
            result = existMailTemplateDetails.getVersion();
        }
        MailTemplateDetail mailTemplateDetails = new MailTemplateDetail(mailTemplateDetailsDto.getSubject(), mailTemplateDetailsDto.getBody(),
                mailTemplateDetailsDto.getTemplateMastId(), generateVersionSeries(result), LocalDateTime.now(), LocalDateTime.now(),
                mailTemplateDetailsDto.getIsActive(), mailTemplateDetailsDto.getFromEmailId());

        mailTemplateDetails = saveTemplateDetails(mailTemplateDetails);
        return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("TEMPLATE_DETAILS_ADD"), communicationLocaleService.getMessage("TEMPLATE_DETAILS_ADD"), mailTemplateDetails);
    }

    public MailTemplateDetail saveTemplateDetails(MailTemplateDetail mailTemplateDetails) {
        return mailTemplateDetailRepository.save(mailTemplateDetails);
    }

    /**
     * @param templateMastId
     * @return A ResponseBean containing the retrieved MailTemplateDetail entity along with
     * an HTTP status and success message.
     * @apiNote Retrieves the template details associated with the given template master ID.
     * @author [Darshit]
     */
    public ResponseBean<?> getTemplateDetail(int templateMastId) {
        MailTemplateDetail templateDetailsByMailTemplateMastId = getTemplateDetailsByTemplateMastId(templateMastId);
        return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("TEMPLATE_DETAILS_FETCH"), communicationLocaleService.getMessage("TEMPLATE_DETAILS_FETCH"), templateDetailsByMailTemplateMastId);
    }

    public MailTemplateDetail getTemplateDetailsByTemplateMastId(int templateMastId) {
        mailTemplateDetailsValidator.validateTemplateMastId(templateMastId);
        return mailTemplateDetailRepository.findFirstByTemplateMastIdOrderByCreatedAtDesc(templateMastId);
    }

    /**
     * @param templateMastId
     * @return A ResponseBean containing a list of MailTemplateDetail entities along with
     * an HTTP status and success message.
     * @apiNote Retrieves all template details associated with the given template master ID.
     * @author [Darshit]
     */
    public ResponseBean<?> getAllTemplateDetail(int templateMastId) {
        List<MailTemplateDetail> templateDetailsByMailTemplateMastId = getAllTemplateDetailsByTemplateMastId(templateMastId);
        return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, communicationLocaleService.getMessage("TEMPLATE_DETAILS_FETCH"), communicationLocaleService.getMessage("TEMPLATE_DETAILS_FETCH"), templateDetailsByMailTemplateMastId);
    }

    public List<MailTemplateDetail> getAllTemplateDetailsByTemplateMastId(int templateMastId) {
        mailTemplateDetailsValidator.validateTemplateMastId(templateMastId);
        return mailTemplateDetailRepository.findAllByTemplateMastIdOrderByCreatedAtDesc(templateMastId);
    }

    public static double generateVersionSeries(double currentVersion) {
        currentVersion += 0.1;
        currentVersion = Math.round(currentVersion * 10.0) / 10.0;
        return Double.parseDouble(String.format("%.1f", currentVersion));
    }
}

