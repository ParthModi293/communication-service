package com.clapcle.communication.service;


import com.clapcle.communication.dto.EmailDto;
import com.clapcle.communication.model.EmailBean;
import com.clapcle.communication.repository.MailTemplateDetailRepository;
import com.clapcle.core.common.LogUtil;
import com.clapcle.core.common.ResponseBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final MailTemplateDetailRepository templateDetailsRepository;

    @Value("${spring.auth.url}")
    private String authUrl;

    public EmailService(KafkaTemplate<String, Object> kafkaTemplate, MailTemplateDetailRepository templateDetailsRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.templateDetailsRepository = templateDetailsRepository;
    }

    /**
     * @param emailBean
     * @return ResponseBean
     * @throws Exception
     * @author Zeel
     * @apiNote This is api is used to send emails
     * either using rest call (for single email with the highest priority)
     * or kafka (for bulk emails with medium and low priority)
     */
    public ResponseBean<Void> mailSender(EmailBean emailBean) throws Exception {
        EmailDto emailDto = emailDtoMaker(emailBean);
        if (emailDto.getPriority() == 1) {
            try {
                RestTemplate rest = new RestTemplate();
                String path = "email/send";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<EmailDto> requestEntity = new HttpEntity<>(emailDto, headers);
                ResponseEntity<ResponseBean> responseBean = rest.exchange(authUrl + path, HttpMethod.POST,
                        requestEntity, ResponseBean.class);
                return new ResponseBean<>(responseBean.getBody().getRStatus(), responseBean.getBody().getRCode(), responseBean.getBody().getRMsg(), responseBean.getBody().getDisplayMessage(), null);
            } catch (HttpClientErrorException e) {
                LogUtil.printErrorStackTraceLog(e);
                return null;
            }
        } else {
            kafkaTemplate.send("email-topic", emailDto.getPriority(), null, new ObjectMapper().writeValueAsString(emailDto));
            return null;
        }
    }

    /**
     * @param emailBean
     * @return EmailDto
     * @throws Exception
     * @author Zeel
     * @apiNote This function is used to process emailBean and prepare EmailDto (prepare ) which holds all details regarding the email
     */
    public EmailDto emailDtoMaker(EmailBean emailBean) throws Exception {
        Map<String, Object> mailDetails = templateDetailsRepository.getMailDetails(emailBean.getTemplateId(), emailBean.getDbName());
        if (mailDetails == null || mailDetails.isEmpty()) {
            mailDetails = templateDetailsRepository.getMailDetails(emailBean.getTemplateId(), "public");
        }
        if (emailBean.getBcc().size() + emailBean.getTo().size() + emailBean.getCc().size() > Integer.parseInt(mailDetails.get("maxLimit").toString())) {
            throw new Exception("Mail limit exceeded");
        }
        String body = mailDetails.get("body").toString();
        String subject = mailDetails.get("subject").toString();
        for (Map.Entry<String, Object> entry : emailBean.getBodyPlaceHolder().entrySet()) {
            String placeholder = "##" + entry.getKey() + "##";
            body = body.replace(placeholder, entry.getValue().toString());
        }
        for (Map.Entry<String, Object> entry : emailBean.getSubjectPlaceHolder().entrySet()) {
            String placeholder = "##" + entry.getKey() + "##";
            subject = subject.replace(placeholder, entry.getValue().toString());
        }
        Pattern pattern = Pattern.compile("##.*?##");
        Matcher bodyMatcher = pattern.matcher(body);
        Matcher subjectMatcher = pattern.matcher(subject);
        if (bodyMatcher.find()) {
            throw new Exception("Invalid body found");
        }
        if (subjectMatcher.find()) {
            throw new Exception("Invalid subject found");
        }
        EmailDto emailDto = new EmailDto();
        emailDto.setFrom(mailDetails.get("fromEmailId").toString());
        emailDto.setTo(emailBean.getTo());
        emailDto.setSubject(subject);
        emailDto.setBody(body);
        emailDto.setCc(emailBean.getCc());
        emailDto.setBcc(emailBean.getBcc());
        emailDto.setVersion(mailDetails.get("version").toString());
        emailDto.setAttachments(emailBean.getFile());
        emailDto.setHost(mailDetails.get("host").toString());
        emailDto.setPassword(mailDetails.get("password").toString());
        emailDto.setPort(Integer.parseInt(mailDetails.get("port").toString()));
        emailDto.setPriority(Integer.parseInt(mailDetails.get("priority").toString()));
        return emailDto;
    }

}
