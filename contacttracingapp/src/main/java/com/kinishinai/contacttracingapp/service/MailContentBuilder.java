package com.kinishinai.contacttracingapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
@Data
public class MailContentBuilder {
    private final TemplateEngine TEMPLATEENGINE;

    String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return TEMPLATEENGINE.process("mailTemplaate", context);

    }
}
