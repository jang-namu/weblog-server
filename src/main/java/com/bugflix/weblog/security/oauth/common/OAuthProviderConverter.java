package com.bugflix.weblog.security.oauth.common;


import org.springframework.core.convert.converter.Converter;

public class OAuthProviderConverter implements Converter<String, OAuthProvider> {
    @Override
    public OAuthProvider convert(String source) {
        return OAuthProvider.fromName(source);
    }
}
