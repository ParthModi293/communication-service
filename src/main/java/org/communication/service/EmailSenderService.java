package org.communication.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


import org.common.common.Const;
import org.common.common.LogUtil;
import org.common.exception.FileNotFoundException;
import org.communication.common.Enum;

import org.communication.config.MessageService;
import org.communication.dto.DynamicMailSender;
import org.communication.dto.EmailDto;
import org.communication.dto.EmailPropertiesDto;
import org.communication.entity.EmailHistory;
import org.communication.repository.EmailHistoryRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class EmailSenderService {

    private final DynamicMailSender dynamicMailSender;
    private final EmailHistoryRepository emailHistoryRepository;
    private final MessageService messageService;


    public EmailSenderService(DynamicMailSender dynamicMailSender, EmailHistoryRepository emailHistoryRepository, MessageService messageService) {
        this.dynamicMailSender = dynamicMailSender;
        this.emailHistoryRepository = emailHistoryRepository;
        this.messageService = messageService;
    }

    /**
     *   * @apiNote Sends an email using a dynamically created {@link JavaMailSender} based on the provided email properties.
     * @param emailDto The {@link EmailDto}
     * @throws FileNotFoundException If any provided attachment file does not exist or is unreadable.
     * @throws Exception             If there is any failure in sending the email.
     * The method records the email details in {@link EmailHistory}
     * @author [Parth]
     */
    public void sendEmail(EmailDto emailDto) throws MessagingException, IOException {
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
                        emailHistory.setStatus(String.valueOf(Enum.STATUS.FAILED));
                        emailHistory.setErrorMessage("File not found: " + filePath);
                        throw new FileNotFoundException(Const.rCode.BAD_REQUEST, HttpStatus.OK, messageService.getMessage("FILE_NOT_FOUND"), messageService.getMessage("FILE_NOT_FOUND"), null);

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
        } catch (Exception e) {
            emailHistory.setStatus(String.valueOf(Enum.STATUS.FAILED));
            emailHistory.setErrorMessage(e.getMessage());
            LogUtil.printErrorStackTraceLog(e);
            throw e;
        } finally {
            emailHistoryRepository.save(emailHistory);
        }

    }


}
