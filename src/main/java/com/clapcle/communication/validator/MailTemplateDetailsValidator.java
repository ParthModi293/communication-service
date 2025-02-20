package com.clapcle.communication.validator;

import com.clapcle.communication.config.MessageService;
import com.clapcle.communication.dto.MailTemplateDetailsDto;
import com.clapcle.communication.repository.MailTemplateDetailRepository;
import com.clapcle.communication.repository.MailTemplateMastRepository;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.exception.ValidationException;
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
public class MailTemplateDetailsValidator {

    private final MailTemplateMastRepository mailTemplateMastRepository;
    private final MailTemplateDetailRepository mailTemplateDetailRepository;
    private final MessageService messageService;

    public MailTemplateDetailsValidator(MailTemplateMastRepository mailTemplateMastRepository, MailTemplateDetailRepository mailTemplateDetailRepository, MessageService messageService) {
        this.mailTemplateMastRepository = mailTemplateMastRepository;
        this.mailTemplateDetailRepository = mailTemplateDetailRepository;
        this.messageService = messageService;
    }

    public void validateTemplateDetails(MailTemplateDetailsDto mailTemplateDetailsDto) {

        if (!mailTemplateMastRepository.existsById(mailTemplateDetailsDto.getTemplateMastId())) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("EVENT_TEMPLATE_NOT_AVAILABLE"), messageService.getMessage("EVENT_TEMPLATE_NOT_AVAILABLE"), null);
        }
        validateHtml(mailTemplateDetailsDto.getBody());
    }

    public void validateHtml(String templateDetailsDto) {
        try {
            Document doc = Jsoup.parse(URLDecoder.decode(templateDetailsDto, StandardCharsets.UTF_8.toString()));

            List<String> MALICIOUS_TAGS = Arrays.asList("script", "iframe", "object", "embed", "applet", "form", "link", "meta", "style", "base");

            for (String tag : MALICIOUS_TAGS) {
                if ((!doc.select(tag).isEmpty())) {
                    throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAIL_BODY"), messageService.getMessage("TEMPLATE_DETAIL_BODY"), null);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_DETAIL_BODY"), messageService.getMessage("TEMPLATE_DETAIL_BODY"), null);
        }
    }

    public void validateTemplateMastId(int templateMastId) {

        if (templateMastId <= 0) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_MAST_ID_NOT_VALID"), messageService.getMessage("TEMPLATE_MAST_ID_NOT_VALID"), null);
        }

        if (!mailTemplateDetailRepository.existsByTemplateMastId(templateMastId)) {
            throw new ValidationException(ConstCore.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("TEMPLATE_MAST_TEMPLATE_DETAIL_NOT_AVAILABLE"), messageService.getMessage("TEMPLATE_MAST_TEMPLATE_DETAIL_NOT_AVAILABLE"), null);
        }
    }

}
