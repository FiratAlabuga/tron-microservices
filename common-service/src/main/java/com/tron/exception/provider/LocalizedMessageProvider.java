package com.tron.exception.provider;

import java.util.Locale;

public interface LocalizedMessageProvider {
    String getMessage(Enum<?> key, Locale locale, Object... args);
}