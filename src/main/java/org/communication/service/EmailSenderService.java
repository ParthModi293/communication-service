package org.communication.service;

import jakarta.mail.internet.MimeMessage;


import org.communication.common.Enum;

import org.communication.dto.DynamicMailSender;
import org.communication.dto.EmailDto;
import org.communication.dto.EmailPropertiesDto;
import org.communication.entity.EmailHistory;
import org.communication.repository.EmailHistoryRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class EmailSenderService {

    private final DynamicMailSender dynamicMailSender;
    private final EmailHistoryRepository emailHistoryRepository;

    public EmailSenderService(DynamicMailSender dynamicMailSender, EmailHistoryRepository emailHistoryRepository) {
        this.dynamicMailSender = dynamicMailSender;
        this.emailHistoryRepository = emailHistoryRepository;
    }

    /**
     * Sends an email using dynamically created JavaMailSender based on the provided email properties.
     * method take emailDto request and set value to EmailHistory For record . After
     *
     * @param emailDto The {@code EmailDto} object containing email details such as recipients,
     *                 subject, body,cc,bcc,attachments,version.
     *                 The email history is recorded in the database with status updates.
     */
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

        emailHistory.setAttachments(emailDto.getAttachments() != null ? Collections.singletonList(String.join(",", emailDto.getAttachments())) : null);

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

            List<String> attachmentPaths = emailDto.getAttachments();
            if (attachmentPaths != null && !attachmentPaths.isEmpty()) {
                for (String filePath : attachmentPaths) {
                    File file = new File(filePath);
                    if (!file.exists() || !file.canRead()) {
                        System.out.println("File not found:================ " + filePath);
                        throw new FileNotFoundException("Attachment not found or unreadable: " + filePath);

                    }
                    String fileName = file.getName();
                    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

                    if ("pdf".equalsIgnoreCase(fileExtension)) {
                        byte[] fileBytes = Files.readAllBytes(file.toPath());
                        helper.addAttachment(fileName, new ByteArrayResource(fileBytes), "application/pdf");
                    } else {
                        FileSystemResource fileResource = new FileSystemResource(file);
                        helper.addAttachment(fileResource.getFilename(), fileResource);
                    }
                }
            }
            mailSender.send(message);
            emailHistory.setStatus(String.valueOf(Enum.STATUS.SUCCESS));
        } catch (FileNotFoundException e) {
            emailHistory.setStatus(String.valueOf(Enum.STATUS.FAILED));
            emailHistory.setErrorMessage("Attachment error: " + e.getMessage());
        } catch (Exception e) {
            emailHistory.setStatus(String.valueOf(Enum.STATUS.FAILED));
            emailHistory.setErrorMessage(e.getMessage());
        } finally {
            this.emailHistoryRepository.save(emailHistory);
        }

    }


}
