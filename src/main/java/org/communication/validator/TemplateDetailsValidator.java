package org.communication.validator;

import org.common.exception.ValidationException;
import org.communication.config.MessageService;
import org.communication.dto.TemplateDetailsDto;
import org.communication.repository.TemplateDetailRepository;
import org.communication.repository.TemplateMastRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class TemplateDetailsValidator {

    private final TemplateMastRepository templateMastRepository;
    private final TemplateDetailRepository templateDetailRepository;
    private final MessageService messageService;

    public TemplateDetailsValidator(TemplateMastRepository templateMastRepository, TemplateDetailRepository templateDetailRepository, MessageService messageService) {
        this.templateMastRepository = templateMastRepository;
        this.templateDetailRepository = templateDetailRepository;
        this.messageService = messageService;
    }

    public void validateTemplateDetails(TemplateDetailsDto templateDetailsDto) {

        if (!templateMastRepository.existsById(templateDetailsDto.getTemplateMastId())) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, messageService.getMessage("EVENT_TEMPLATE_NOT_AVAILABLE"), messageService.getMessage("EVENT_TEMPLATE_NOT_AVAILABLE"), null);
        }
        if (templateDetailsDto.getId() > 0) {
            if (!templateDetailRepository.existsById(templateDetailsDto.getId())) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, messageService.getMessage("TEMPLATE_DETAIL_NOT_AVAILABLE"), messageService.getMessage("TEMPLATE_DETAIL_NOT_AVAILABLE"), null);
            }
        }
//        Document doc = Jsoup.parse(URLDecoder.decode(tncBean.getTncText(), StandardCharsets.UTF_8.toString()));
//        System.out.println("before:" + doc);
//        if ((!doc.select("script").isEmpty())) {
//            return new ResponseBean<>(HttpStatus.BAD_REQUEST, "Enter valid text for terms & conditions",
//                    "Data of Terms and Conditions text contains malicious elements.", null);
//        }
    }

    public void validateTemplateMastId(int templateMastId) {

        if (templateMastId <= 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, messageService.getMessage("TEMPLATE_MAST_ID_NOT_VALID"), messageService.getMessage("TEMPLATE_MAST_ID_NOT_VALID"), null);
        }

        if (!templateDetailRepository.existsByTemplateMastId(templateMastId)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, messageService.getMessage("TEMPLATE_MAST_TEMPLATE_DETAIL_NOT_AVAILABLE"), messageService.getMessage("TEMPLATE_MAST_TEMPLATE_DETAIL_NOT_AVAILABLE"), null);
        }
    }

}
