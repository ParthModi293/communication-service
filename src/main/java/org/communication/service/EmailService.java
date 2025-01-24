package org.communication.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.common.common.ResponseBean;
import org.communication.bean.EmailBean;
import org.communication.dto.EmailDto;
import org.communication.repository.TemplateDetailsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final TemplateDetailsRepository templateDetailsRepository;

    public EmailService(KafkaTemplate<String, Object> kafkaTemplate, TemplateDetailsRepository templateDetailsRepository) {

        this.kafkaTemplate = kafkaTemplate;
        this.templateDetailsRepository = templateDetailsRepository;
    }


    public ResponseBean<Void> mailSender(EmailBean emailBean) throws Exception {


        Map mailDetails = templateDetailsRepository.getMailDetails(emailBean.getTemplateName(), emailBean.getDbName());
        if (mailDetails == null || mailDetails.isEmpty()) {
            mailDetails = templateDetailsRepository.getMailDetails(emailBean.getTemplateName(), "email");
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

        EmailDto emailDto = EmailDto.builder()
                .from(mailDetails.get("fromEmailId").toString())
                .to(emailBean.getTo())
                .cc(emailBean.getCc())
                .bcc(emailBean.getBcc())
                .subject(subject)
                .body(body)
                .version(mailDetails.get("version").toString())
                .attachments(emailBean.getFile())
                .host(mailDetails.get("host").toString())
                .password(mailDetails.get("password").toString())
                .port(Integer.parseInt(mailDetails.get("port").toString()))
                .build();

        kafkaTemplate.send("email-topic", Integer.parseInt(mailDetails.get("priority").toString()), null, new ObjectMapper().writeValueAsString(emailDto));
        return new ResponseBean<>(HttpStatus.OK, "Mail sender under process", "Mail sender under process", null);
    }


}
