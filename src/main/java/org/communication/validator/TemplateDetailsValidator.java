package org.communication.validator;

import org.common.common.ResponseBean;
import org.communication.dto.TemplateDetailsDto;
import org.communication.repository.TemplateDetailsRepository;
import org.communication.repository.TemplateMastRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class TemplateDetailsValidator {

    private final TemplateMastRepository templateMastRepository;
    private final TemplateDetailsRepository templateDetailsRepository;

    public TemplateDetailsValidator(TemplateMastRepository templateMastRepository, TemplateDetailsRepository templateDetailsRepository) {
        this.templateMastRepository = templateMastRepository;
        this.templateDetailsRepository = templateDetailsRepository;
    }

    public ResponseBean<Void> templateDetailsValidation(TemplateDetailsDto templateDetailsDto) {

        if (!templateMastRepository.existsById(templateDetailsDto.getTemplateMastId())) {
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, "This Event template is not available", "This Event template is not available", null);
        }
        if (templateDetailsDto.getId() > 0) {
            if (!templateDetailsRepository.existsById(templateDetailsDto.getId())) {
                return new ResponseBean<>(HttpStatus.BAD_REQUEST, "This template Detail is not available", "This template Detail is not available", null);
            }
        }
//        Document doc = Jsoup.parse(URLDecoder.decode(tncBean.getTncText(), StandardCharsets.UTF_8.toString()));
//        System.out.println("before:" + doc);
//        if ((!doc.select("script").isEmpty())) {
//            return new ResponseBean<>(HttpStatus.BAD_REQUEST, "Enter valid text for terms & conditions",
//                    "Data of Terms and Conditions text contains malicious elements.", null);
//        }
        return new ResponseBean<>(HttpStatus.OK, "OK");
    }

    public ResponseBean<Void> templateMastIdValidation(int templateMastId) {

        if (templateMastId <= 0) {
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, "Template mast id is not valid", "Template mast id is not valid", null);
        }

        if (!templateDetailsRepository.existsByTemplateMastId(templateMastId)) {
            return new ResponseBean<>(HttpStatus.BAD_REQUEST, "On this template mast id template details is not available", "On this template mast id template details is not available", null);
        }
        return new ResponseBean<>(HttpStatus.OK, "OK");
    }

}
