package com.kinishinai.contacttracingapp.twilio;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TwilioConfiguration {
    @Value("${account_sid}")
    private String accountSid;
    @Value("${auth_token}")
    private String authToken;
    @Value("${trial_number}")
    private String trialNumber;


}
