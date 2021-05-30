package com.kinishinai.contacttracingapp.twilio;

public interface SmsSender {
    void sendSms(SmsRequest smsRequest);
}
