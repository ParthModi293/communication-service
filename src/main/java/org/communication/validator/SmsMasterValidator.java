package org.communication.validator;

import org.common.common.Const;
import org.common.exception.ValidationException;
import org.communication.dto.SmsMasterDto;
import org.communication.entity.SmsMaster;
import org.communication.repository.SmsMasterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class SmsMasterValidator {

    private final SmsMasterRepository smsMasterRepository;

    public SmsMasterValidator(SmsMasterRepository smsMasterRepository) {
        this.smsMasterRepository = smsMasterRepository;
    }

    public void validateSmsMaster(SmsMasterDto dto) {

        if (!StringUtils.hasText(dto.getTemplateName())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Template name cannot be empty!", "Template name cannot be empty!", null);
        }

        if(dto.getTemplateName() !=null && smsMasterRepository.existsByTemplateName((dto.getTemplateName()))){
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Template name already exists!", "Provider name already exists!", null);

        }
        if (dto.getEventId() == null || dto.getEventId() <= 0 || ObjectUtils.isEmpty(dto.getEventId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Event ID is required!", "Event ID is required!", null);
        }
        if(dto.getEventId()!=null && !smsMasterRepository.existsById(dto.getEventId())) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Event ID is not available!", "Event ID is not available!", null);
        }

    }
}
