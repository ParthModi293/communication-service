package com.clapcle.communication.service;

import com.clapcle.communication.dto.SmsDetailsDto;
import com.clapcle.communication.dto.SmsDto;
import com.clapcle.core.common.ConstCore;
import com.clapcle.core.common.ResponseBean;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SmsSendService {

    /**
     * @param smsDetails
     * @param smsDto
     * @return ResponseBean
     * @author Zeel
     * @apiNote This function is used to send SMS request to the service provider through the rest call (works for both single and bulk SMS send)
     */
    public ResponseBean sendSms(SmsDetailsDto smsDetails, SmsDto smsDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.set("authkey", smsDetails.getApiKey());
            HttpEntity<SmsDto> requestEntity = new HttpEntity<>(smsDto, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(smsDetails.getUrl(), HttpMethod.POST,
                    requestEntity, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
            if (jsonObject.get("type").equals("error")) {
                return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.BAD_REQUEST, "Invalid request", "Invalid request", jsonObject);
            } else {
                return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.SUCCESS, "Executed successfully", "Executed successfully", jsonObject);
            }
        } catch (HttpClientErrorException e) {
            return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.BAD_REQUEST, "Error during MSG91 rest call", "Error during MSG91 rest call", null);
        } catch (Exception e) {
            return new ResponseBean<>(HttpStatus.OK, ConstCore.rCode.BAD_REQUEST, "Invalid request", "Invalid request", null);
        }
    }

}
