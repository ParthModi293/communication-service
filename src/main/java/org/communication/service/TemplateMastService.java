package org.communication.service;

import io.micrometer.common.util.StringUtils;
import org.common.common.Pagination;
import org.common.common.ResponseBean;
import org.communication.common.Const;
import org.communication.dto.TemplateMastDto;
import org.communication.dto.TemplateMastFilterRequest;
import org.communication.entity.TemplateMast;
import org.communication.repository.TemplateMastRepository;
import org.communication.validator.TemplateMastValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class TemplateMastService {

    private final TemplateMastRepository templateMastRepository;
    private final TemplateMastValidator templateMastValidator;

    public TemplateMastService(TemplateMastRepository templateMastRepository, TemplateMastValidator templateMastValidator) {
        this.templateMastRepository = templateMastRepository;
        this.templateMastValidator = templateMastValidator;
    }

    public ResponseBean<TemplateMast> createTemplate(TemplateMastDto templateMastDto) {

        templateMastValidator.validateTemplateMast(templateMastDto);

        TemplateMast template = new TemplateMast();

        if (templateMastDto.getId() != null && templateMastDto.getId() > 0) {

            Optional<TemplateMast> optionalTemplateMast = templateMastRepository.findById(templateMastDto.getId());
            if (optionalTemplateMast.isPresent()) {
                template = optionalTemplateMast.get();
            } else {
                return new ResponseBean<>(HttpStatus.NOT_FOUND, "Template not found", "Template with given ID does not exist", null);
            }
        }
        updateTemplateDetails(template, templateMastDto);
        TemplateMast savedTemplate = templateMastRepository.save(template);
        return new ResponseBean<>(HttpStatus.OK, "Template added successfully", "Template added successfully", savedTemplate);
    }

    private void updateTemplateDetails(TemplateMast template, TemplateMastDto templateMastDto) {
        if (templateMastDto.getTemplateName() != null) {
            template.setTemplateName(templateMastDto.getTemplateName());
        }
        if (templateMastDto.getDescription() != null) {
            template.setDescription(templateMastDto.getDescription());
        }
        if (templateMastDto.getPriority() != null) {
            template.setPriority(templateMastDto.getPriority().getValue());
        }
        if (templateMastDto.getEventId() != null) {
            template.setEventId(templateMastDto.getEventId());
        }
        if (templateMastDto.getId() != null && templateMastDto.getId() > 0) {
            template.setUpdatedAt(LocalDateTime.now());
        } else {
            template.setCreatedAt(LocalDateTime.now());
            template.setUpdatedAt(LocalDateTime.now());
        }

    }

    public ResponseBean<?> getAllTemplates(TemplateMastFilterRequest templateMastFilterRequest) {

//        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(templateMastFilterRequest.getPage() - 1, templateMastFilterRequest.getSize());
        Page<TemplateMast> templateMasts;
        if (StringUtils.isNotBlank(templateMastFilterRequest.getSearchText()) && Pattern.matches(Const.PatternCheck.SearchText, templateMastFilterRequest.getSearchText())) {
            templateMasts = templateMastRepository.findByEventIdAndTemplateNameLike(templateMastFilterRequest.getEventId(), templateMastFilterRequest.getSearchText(), pageable);
        } else {
            templateMasts = templateMastRepository.findAllByEventId(templateMastFilterRequest.getEventId(), pageable);
        }
        templateMasts.getContent();
        return new ResponseBean<>(HttpStatus.OK, HttpStatus.OK.value(), "Template fetch successfully", "Template fetch successfully", templateMasts.getContent(), new Pagination((int) templateMasts.getTotalElements(), templateMastFilterRequest.getPage(), templateMastFilterRequest.getSize()));
    }

    public ResponseBean<?> getTemplateById(int id) {
        Optional<TemplateMast> templateMast = templateMastRepository.findById(id);
        if (templateMast.isPresent()) {
            return new ResponseBean<>(HttpStatus.OK, "Template fetch successfully", "Template fetch successfully", templateMast.get());
        }
        return new ResponseBean<>(HttpStatus.OK, "Template fetch successfully", "Template fetch successfully", null);

    }

    public void deleteTemplate(int id) {
        templateMastRepository.deleteById(id);
    }
}

