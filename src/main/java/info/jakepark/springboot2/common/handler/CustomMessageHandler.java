package info.jakepark.springboot2.common.handler;

import info.jakepark.springboot2.common.response.ResponseType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomMessageHandler {

    private final MessageSource messageSource;

    public String getMessage(ResponseType responseType) {
        return messageSource.getMessage(responseType.getMessage(), null, LocaleContextHolder.getLocale());
    }

    public String getMessage(ResponseType responseType, Object... args) {
        return messageSource.getMessage(responseType.getMessage(), args, LocaleContextHolder.getLocale());
    }

    public String getMessage(ResponseType responseType, Locale locale) {
        return messageSource.getMessage(responseType.getMessage(), null, locale);
    }

    public String getMessage(ResponseType responseType, Locale locale, Object... args) {
        return messageSource.getMessage(responseType.getMessage(), args, locale);
    }

}