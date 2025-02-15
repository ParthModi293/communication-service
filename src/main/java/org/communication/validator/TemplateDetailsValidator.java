package org.communication.validator;

import org.common.common.Const;
import org.common.exception.ValidationException;
import org.communication.config.MessageService;
import org.communication.dto.TemplateDetailsDto;
import org.communication.repository.TemplateDetailRepository;
import org.communication.repository.TemplateMastRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


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
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("EVENT_TEMPLATE_NOT_AVAILABLE"), messageService.getMessage("EVENT_TEMPLATE_NOT_AVAILABLE"), null);
        }
        validateHtml(templateDetailsDto.getBody());
    }

    public void validateHtml(String templateDetailsDto) {
        try {
            Document doc = Jsoup.parse(URLDecoder.decode(templateDetailsDto, StandardCharsets.UTF_8.toString()));

            List<String> MALICIOUS_TAGS = Arrays.asList("script", "iframe", "object", "embed", "applet", "form", "link", "meta", "style", "base");

            for (String tag : MALICIOUS_TAGS) {
                if ((!doc.select(tag).isEmpty())) {
                    throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAIL_BODY"), messageService.getMessage("TEMPLATE_DETAIL_BODY"), null);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAIL_BODY"), messageService.getMessage("TEMPLATE_DETAIL_BODY"), null);
        }
    }

    public void validateTemplateMastId(int templateMastId) {

        if (templateMastId <= 0) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_MAST_ID_NOT_VALID"), messageService.getMessage("TEMPLATE_MAST_ID_NOT_VALID"), null);
        }

        if (!templateDetailRepository.existsByTemplateMastId(templateMastId)) {
            throw new ValidationException(Const.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_MAST_TEMPLATE_DETAIL_NOT_AVAILABLE"), messageService.getMessage("TEMPLATE_MAST_TEMPLATE_DETAIL_NOT_AVAILABLE"), null);
        }
    }

}
