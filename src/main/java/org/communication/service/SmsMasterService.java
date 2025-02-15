package org.communication.service;

import org.common.common.Const;
import org.common.common.ResponseBean;
import org.common.exception.ValidationException;
import org.communication.config.MessageService;
import org.communication.dto.SmsMasterDto;
import org.communication.entity.SmsMaster;
import org.communication.repository.SmsMasterRepository;
import org.communication.validator.SmsMasterValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SmsMasterService {

    private final SmsMasterValidator smsMasterValidator;
    private final SmsMasterRepository smsMasterRepository;
    private final MessageService messageService;

    public SmsMasterService(SmsMasterValidator smsMasterValidator, SmsMasterRepository smsMasterRepository, MessageService messageService) {
        this.smsMasterValidator = smsMasterValidator;
        this.smsMasterRepository = smsMasterRepository;
        this.messageService = messageService;
    }

    /**
     * @apiNote  Creates or updates an SMS Master record.
     * If `id` is provided, it updates the existing record; otherwise, a new record is created.
     * Ensures `templateName` is unique before saving.
     * @param requestDTO {@link SmsMasterDto}
     * @return {@link ResponseBean} with the saved record ID.
     * @throws ValidationException If `id` is not found when updating or `templateName` already exists.
     * @author [Parth]
     */
    @Transactional
    public ResponseBean<Map<String,Object>>    createOrUpdateSmsMaster(SmsMasterDto requestDTO) {

        smsMasterValidator.validateSmsMaster(requestDTO);
        SmsMaster smsMaster;
        if (requestDTO.getId() != null && requestDTO.getId() > 0) {
            Optional<SmsMaster> optionalSmsMaster = smsMasterRepository.findById(requestDTO.getId());
            if (optionalSmsMaster.isEmpty()) {
                throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("SMS_MASTER_ID_NOT_AVAILABLE"), messageService.getMessage("SMS_MASTER_ID_NOT_AVAILABLE"), null);

            }
            smsMaster = optionalSmsMaster.get();
            smsMaster.setUpdatedAt(Instant.now());
            smsMaster.setUpdatedBy("1");
        } else {
            smsMaster = new SmsMaster();
            smsMaster.setCreatedAt(Instant.now());
            smsMaster.setCreatedBy("1");
        }

        Optional<SmsMaster> existingTemplate = smsMasterRepository.findByTemplateName((requestDTO.getTemplateName()));
        if (existingTemplate.isPresent() && !existingTemplate.get().getId().equals(requestDTO.getId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK,  messageService.getMessage("TEMPLATE_NAME_EXISTS"), messageService.getMessage("TEMPLATE_NAME_EXISTS"), null);
        }

        smsMaster.setTemplateName(requestDTO.getTemplateName());
        smsMaster.setEventId(requestDTO.getEventId());
        smsMaster.setPriority(requestDTO.getPriority().getValue());

        smsMasterRepository.save(smsMaster);

        Map<String,Object> res = new HashMap<>();
        res.put("Id",smsMaster.getId());

        return new ResponseBean<>(HttpStatus.OK,Const.rCode.SUCCESS, messageService.getMessage("SMS_MASTER_ADD"), messageService.getMessage("SMS_MASTER_ADD"),res);


    }
}
