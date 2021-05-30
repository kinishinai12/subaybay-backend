package com.kinishinai.contacttracingapp.service;

import com.kinishinai.contacttracingapp.twilio.SmsRequest;
import com.kinishinai.contacttracingapp.twilio.SmsSender;
import com.kinishinai.contacttracingapp.twilio.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    private final SmsSender smsSender;

    @Autowired
    public TwilioService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }
}
