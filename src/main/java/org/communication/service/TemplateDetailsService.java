package org.communication.service;

import org.common.common.ResponseBean;
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

    public TemplateDetailsService(TemplateDetailsValidator templateDetailsValidator, TemplateDetailsRepository templateDetailsRepository) {
        this.templateDetailsValidator = templateDetailsValidator;
        this.templateDetailsRepository = templateDetailsRepository;
    }

    public ResponseBean<?> createTemplateDetail(TemplateDetailsDto templateDetailsDto) {

        ResponseBean<Void> responseBean = templateDetailsValidator.templateDetailsValidation(templateDetailsDto);
        if (responseBean.getRStatus() != HttpStatus.OK) {
            return responseBean;
        }
        Double result = 0.0;
        if (templateDetailsDto.getId() > 0) {
            TemplateDetails existTemplateDetails = templateDetailsRepository.findById(templateDetailsDto.getId()).orElse(null);
            result = existTemplateDetails.getVersion();
        }

        TemplateDetails templateDetails = new TemplateDetails();
        templateDetails.setVersion(generateVersionSeries(result));
        templateDetails.setTemplateMastId(templateDetailsDto.getTemplateMastId());
        templateDetails.setSubject(templateDetailsDto.getSubject());
        templateDetails.setBody(templateDetailsDto.getBody());
        templateDetails.setCreatedAt(LocalDateTime.now());
        templateDetails.setUpdatedAt(LocalDateTime.now());
        templateDetails.setIsActive(templateDetailsDto.getIsActive());
        templateDetails.setFromEmailId(templateDetailsDto.getFromEmailId());

        templateDetails = templateDetailsRepository.save(templateDetails);
        return new ResponseBean<>(HttpStatus.OK, "Template added successfully", "Template added successfully", templateDetails);
    }

    public ResponseBean<?> getTemplateDetail(int templateMastId) {

        ResponseBean<Void> responseBean = templateDetailsValidator.templateMastIdValidation(templateMastId);
        if (responseBean.getRStatus() != HttpStatus.OK) {
            return responseBean;
        }

        TemplateDetails templateDetails = templateDetailsRepository.findFirstByTemplateMastIdOrderByCreatedAtDesc(templateMastId);
        return new ResponseBean<>(HttpStatus.OK, "Template added successfully", "Template added successfully", templateDetails);
    }

    public static double generateVersionSeries(double currentVersion) {
        currentVersion += 0.1;
        currentVersion = Math.round(currentVersion * 10.0) / 10.0;
        return Double.parseDouble(String.format("%.1f", currentVersion));
    }
}
