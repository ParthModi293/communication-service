package org.communication.service;

import jakarta.mail.internet.MimeMessage;

import org.common.common.Const;
import org.communication.common.Enum;
import org.communication.dto.AttachmentDto;
import org.communication.dto.DynamicMailSender;
import org.communication.dto.EmailDto;
import org.communication.dto.EmailPropertiesDto;
import org.communication.entity.EmailHistory;
import org.communication.repository.EmailHistoryRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmailSenderService {

    private final DynamicMailSender dynamicMailSender;
    private final EmailHistoryRepository emailHistoryRepository;

    public EmailSenderService(DynamicMailSender dynamicMailSender, EmailHistoryRepository emailHistoryRepository) {
        this.dynamicMailSender = dynamicMailSender;
        this.emailHistoryRepository = emailHistoryRepository;
    }

    public void sendEmail(EmailDto emailDto) {
        EmailPropertiesDto emailProperties = EmailPropertiesDto.builder()
                .username(emailDto.getFrom())
                .password(emailDto.getPassword())
                .host(emailDto.getHost())
                .port(emailDto.getPort())
                .build();

        JavaMailSender mailSender = this.dynamicMailSender.createMailSender(emailProperties);

        EmailHistory emailHistory = new EmailHistory();
        emailHistory.setFromAddress(emailProperties.getUsername());
        emailHistory.setToAddresses(String.join(",", emailDto.getTo()));
        emailHistory.setCcAddresses(emailDto.getCc() != null ? String.join(",", emailDto.getCc()) : null);
        emailHistory.setBccAddresses(emailDto.getBcc() != null ? String.join(",", emailDto.getBcc()) : null);
        emailHistory.setSubject(emailDto.getSubject());
        emailHistory.setBody(emailDto.getBody());
        emailHistory.setVersion(emailDto.getVersion());
        emailHistory.setTimestamp(LocalDateTime.now());

        List<AttachmentDto> attachments = emailDto.getAttachments().stream()
                .map(filePath -> new AttachmentDto(filePath, extractFileName(filePath)))
                .collect(Collectors.toList());
        emailHistory.setAttachments(attachments);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailProperties.getUsername());
            helper.setTo(emailDto.getTo().toArray(new String[0]));
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getBody(), true);
            if (emailDto.getCc() != null && !emailDto.getCc().isEmpty()) {
                helper.setCc(emailDto.getCc().toArray(new String[0]));
            }

            if (emailDto.getBcc() != null && !emailDto.getBcc().isEmpty()) {
                helper.setBcc(emailDto.getBcc().toArray(new String[0]));
            }
            List<String> attachmentFile = emailDto.getAttachments();
            if(attachmentFile != null && !attachmentFile.isEmpty()){
                for(String attachment : attachmentFile){
                    FileSystemResource file = new FileSystemResource(new File(attachment));
                    helper.addAttachment(attachment, file);
                }
            }


            mailSender.send(message);
            emailHistory.setStatus(String.valueOf(Enum.STATUS.SUCCESS));
        } catch (Exception e) {
            emailHistory.setStatus(String.valueOf(Enum.STATUS.FAILED));
            emailHistory.setErrorMessage(e.getMessage());
        } finally {
            this.emailHistoryRepository.save(emailHistory);
        }

    }

    private String extractFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

}
