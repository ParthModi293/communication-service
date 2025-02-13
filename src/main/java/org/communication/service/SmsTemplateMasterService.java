package org.communication.service;

import io.micrometer.common.util.StringUtils;
import org.common.common.Pagination;
import org.common.common.ResponseBean;
import org.communication.common.Const;
import org.communication.config.MessageService;
import org.communication.dto.SmsTemplateMasterDto;
import org.communication.dto.SmsTemplateMasterFilterRequest;
import org.communication.entity.SmsTemplateMaster;
import org.communication.repository.SmsTemplateMasterRepository;
import org.communication.validator.SmsTemplateMasterValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class SmsTemplateMasterService {

    private final SmsTemplateMasterRepository smsTemplateMasterRepository;
    private final SmsTemplateMasterValidator smsTemplateMasterValidator;
    private final MessageService messageService;

    public SmsTemplateMasterService(SmsTemplateMasterRepository smsTemplateMasterRepository, SmsTemplateMasterValidator smsTemplateMasterValidator, MessageService messageService) {
        this.smsTemplateMasterRepository = smsTemplateMasterRepository;
        this.smsTemplateMasterValidator = smsTemplateMasterValidator;
        this.messageService = messageService;
    }

    @Transactional
    public ResponseBean<?> addOrUpdateSmsTemplate(SmsTemplateMasterDto smsTemplateMasterDto){

        smsTemplateMasterValidator.validateSmsTemplateRequest(smsTemplateMasterDto);

        SmsTemplateMaster template = new SmsTemplateMaster();
        template.setBody(smsTemplateMasterDto.getBody());
        template.setGovTemplateCode(smsTemplateMasterDto.getGovTemplateCode());
        template.setServiceProviderTemplateCode(smsTemplateMasterDto.getServiceProviderTemplateCode());
        template.setSenderId(smsTemplateMasterDto.getSenderId());
        template.setIsActive(smsTemplateMasterDto.getIsActive());
        template.setSmsMasterId(smsTemplateMasterDto.getSmsMasterId());
        template.setVersion(generateVersion(smsTemplateMasterDto.getServiceProviderTemplateCode(), smsTemplateMasterDto.getVersionType()));
        template.setCreatedAt(Instant.now());
        template.setCreatedBy("1");
       smsTemplateMasterRepository.save(template);

        Map<String,Object> res = new HashMap<>();
        res.put("Id",template.getId());

        return  new ResponseBean<>(HttpStatus.OK, org.common.common.Const.rCode.SUCCESS,messageService.getMessage("SMS_TEMPLATE_ADD"),messageService.getMessage("SMS_TEMPLATE_ADD"),res);

    }

    private String generateVersion(String serviceProviderTemplateCode, String versionType) {
        Optional<SmsTemplateMaster> lastTemplate = smsTemplateMasterRepository.findTopByServiceProviderTemplateCodeOrderByCreatedAtDesc(serviceProviderTemplateCode);
        if (lastTemplate.isEmpty() ) {
            return "1.0.0";
        }
        String[] parts = lastTemplate.get().getVersion().split("\\.");
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = Integer.parseInt(parts[2]);

        if ("MAJOR".equalsIgnoreCase(versionType)) {
            major++;
            minor = 0;
            patch = 0;
        } else if ("MINOR".equalsIgnoreCase(versionType)) {
            if (patch == 9 && minor == 9) {
                major++;
                minor = 0;
                patch = 0;
            } else if (patch == 9) {
                minor++;
                patch = 0;
            } else {
                patch++;
            }
        }

        return major + "." + minor + "." + patch;
    }

    public ResponseBean<?> getAllSmsTemplates(SmsTemplateMasterFilterRequest templateMastFilterRequest) {
        int page = Math.max(0, templateMastFilterRequest.getPage() - 1);
        int size = Math.max(1, templateMastFilterRequest.getSize());
        Pageable pageable = PageRequest.of(page, size);
        Page<SmsTemplateMaster> templateMasts;
        if (StringUtils.isNotBlank(templateMastFilterRequest.getSearchText()) && Pattern.matches(Const.PatternCheck.SearchText, templateMastFilterRequest.getSearchText())) {
            templateMasts = smsTemplateMasterRepository.findByTemplateNameAndBodyLike(templateMastFilterRequest.getSearchText(), pageable);
        }else{
            templateMasts = smsTemplateMasterRepository.findAll(pageable);
        }
        return new ResponseBean<>(HttpStatus.OK, HttpStatus.OK.value(), messageService.getMessage("TEMPLATE_FETCH"), messageService.getMessage("TEMPLATE_FETCH"), templateMasts.getContent(), new Pagination((int) templateMasts.getTotalElements(), templateMastFilterRequest.getPage(), size));

    }

    public ResponseBean<?> findSmsTemplateById(Integer id) {

        smsTemplateMasterValidator.validateSmsTemplateId(id);

        Optional<SmsTemplateMaster> templateMaster = smsTemplateMasterRepository.findById(id);
        if(templateMaster.isPresent()){
            return new ResponseBean<>(HttpStatus.OK, org.common.common.Const.rCode.SUCCESS,messageService.getMessage("TEMPLATE_FETCH"),messageService.getMessage("TEMPLATE_FETCH"),templateMaster.get());
        }
        return new ResponseBean<>(HttpStatus.OK, org.common.common.Const.rCode.SUCCESS,messageService.getMessage("TEMPLATE_FETCH"),messageService.getMessage("TEMPLATE_FETCH"),null);

    }


}
