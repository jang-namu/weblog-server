package com.bugflix.weblog.security.oauth.client;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.FailedFetchResourceException;
import com.bugflix.weblog.common.exception.FailedTokenExchangeException;
import com.bugflix.weblog.security.oauth.common.config.GoogleOAuthConfiguration;
import com.bugflix.weblog.security.oauth.dto.response.GoogleOAuthMemberResponse;
import com.bugflix.weblog.security.oauth.dto.response.GoogleOAuthTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleOAuthClient implements OAuthClient {

    private final GoogleOAuthConfiguration googleOAuthConfiguration;

    @Override
    public GoogleOAuthTokenResponse fetchToken(String code) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GoogleOAuthTokenResponse> responseEntity = restTemplate.postForEntity(
                googleOAuthConfiguration.getTokenUri(),
                tokenRequestParams(code), GoogleOAuthTokenResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        throw new FailedTokenExchangeException(Errors.TOKEN_EXCHANGE);
    }

    @Override
    public GoogleOAuthMemberResponse fetchMember(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<GoogleOAuthMemberResponse> responseEntity = restTemplate.exchange(
                googleOAuthConfiguration.getUserInfoUri(), HttpMethod.GET, request, GoogleOAuthMemberResponse.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        }
        throw new FailedFetchResourceException(Errors.FETCH_SOCIAL_RESOURCE);
    }

    private MultiValueMap<String, String> tokenRequestParams(String authCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        params.add("client_id", googleOAuthConfiguration.getClientId());
        params.add("redirect_uri", googleOAuthConfiguration.getRedirectUri());
        params.add("code", authCode);
        params.add("client_secret", googleOAuthConfiguration.getClientSecret());
        return params;
    }
}
