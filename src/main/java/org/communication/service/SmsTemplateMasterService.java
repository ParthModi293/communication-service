package org.communication.service;

import org.common.common.ResponseBean;
import org.communication.dto.SmsTemplateMasterDto;
import org.communication.entity.SmsTemplateMaster;
import org.communication.repository.SmsTemplateMasterRepository;
import org.communication.validator.SmsTemplateMasterValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class SmsTemplateMasterService {

    private final SmsTemplateMasterRepository smsTemplateMasterRepository;
    private final SmsTemplateMasterValidator smsTemplateMasterValidator;

    public SmsTemplateMasterService(SmsTemplateMasterRepository smsTemplateMasterRepository, SmsTemplateMasterValidator smsTemplateMasterValidator) {
        this.smsTemplateMasterRepository = smsTemplateMasterRepository;
        this.smsTemplateMasterValidator = smsTemplateMasterValidator;
    }

    @Transactional
    public ResponseBean<SmsTemplateMaster> addOrUpdateSmsTemplate(SmsTemplateMasterDto smsTemplateMasterDto){

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
        template.setUpdatedAt(Instant.now());
        template.setCreatedBy(smsTemplateMasterDto.getCreatedBy());
        template.setUpdatedBy(smsTemplateMasterDto.getUpdatedBy());
        smsTemplateMasterRepository.save(template);
        return new ResponseBean<>(HttpStatus.OK,"Sms template added successfully","Sms template added successfully",template);


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

        if ("major".equalsIgnoreCase(versionType)) {
            major++;   // Increment the major version
            minor = 0;  // Reset the minor version to 0
            patch = 0;  // Reset the patch version to 0
        } else if ("minor".equalsIgnoreCase(versionType)) {
            if (patch == 9 && minor == 9) {
                major++;   // Increment the major version if both patch and minor are 9
                minor = 0; // Reset the minor version to 0
                patch = 0; // Reset the patch version to 0
            } else if (patch == 9) {
                minor++;   // Increment the minor version if only patch is 9
                patch = 0; // Reset the patch version to 0
            } else {
                patch++;   // Otherwise, just increment the patch version
            }
        }

        return major + "." + minor + "." + patch;
    }

}
