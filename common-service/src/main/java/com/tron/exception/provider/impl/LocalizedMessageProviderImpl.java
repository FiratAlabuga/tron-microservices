package com.tron.exception.provider.impl;

import com.tron.exception.provider.LocalizedMessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizedMessageProviderImpl implements LocalizedMessageProvider {

    private final MessageSource messageSource;
    /**
     * Retrieves a localized message based on the provided key, locale, and optional arguments.
     *
     * @param key   The enum key representing the message.
     * @param locale The locale for localization.
     * @param args  Optional arguments for message formatting.
     * @return The localized message as a String.
     */
    @Override
    public String getMessage(Enum<?> key, Locale locale, Object... args) {
        return messageSource.getMessage(key.name(), args, locale);
    }
}
