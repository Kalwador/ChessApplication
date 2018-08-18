package com.chess.spring.services.register;

import com.chess.spring.dto.AccountDTO;
import com.chess.spring.models.register.AccessToken;
import com.chess.spring.models.register.AccessTokenData;
import com.chess.spring.models.register.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@Service
public class FacebookConnector {
    private RestTemplate restTemplate;

    private final String REDIRECT_URI;
    private final String APP_ID;
    private final String APP_SECRET;

    public FacebookConnector(
            @Value("${REDIRECT_URI}") String REDIRECT_URI,
            @Value("${APP_ID}") String APP_ID,
            @Value("${APP_SECRET}") String APP_SECRET) {
        this.restTemplate = new RestTemplate();
        this.REDIRECT_URI = REDIRECT_URI;
        this.APP_ID = APP_ID;
        this.APP_SECRET = APP_SECRET;
    }

    public AccessTokenData inspectAccessToken(String accessToken, String appAccessToken) {
        Map<String, String> urlparams = new HashMap<>();
        urlparams.put("input_token", accessToken);
        urlparams.put("access_token", appAccessToken);
        try {
            AccessTokenData accessTokenData = restTemplate.getForObject(
                    "https://graph.facebook.com/debug_token?input_token={input_token}&access_token={access_token}",
                    Data.class, urlparams).getData();
            return accessTokenData;
        } catch (HttpStatusCodeException exception) {
            log.warn(exception.getResponseBodyAsString());
            throw new RuntimeException(String.valueOf(exception.getStatusCode()));
        }
    }

    public AccessToken getAccessTokenFromCode(String code) {
        Map<String, String> urlparams = new HashMap<>();
        urlparams.put("client_id", APP_ID);
        urlparams.put("redirect_uri", REDIRECT_URI);
        urlparams.put("client_secret", APP_SECRET);
        urlparams.put("code", code);

        try {
            AccessToken accessToken = restTemplate.getForObject(
                    "https://graph.facebook.com/oauth/access_token?client_id={client_id}&code={code}&client_secret"
                            + "={client_secret}&redirect_uri={redirect_uri}",
                    AccessToken.class, urlparams);
            return accessToken;
        } catch (HttpStatusCodeException exception) {
            log.warn(exception.getResponseBodyAsString());
            throw new RuntimeException(String.valueOf(exception.getStatusCode()));
        }
    }

    public AccountDTO getUserDetailsFromAccessToken(String accessToken) {

        Map<String, String> urlparams = new HashMap<>();
        urlparams.put("access_token", accessToken);
        urlparams.put("fields", "id,email");
        log.info("Retrieving user details with {} and {}", accessToken, urlparams);
        try {
            AccountDTO accountDTO = restTemplate
                    .getForObject("https://graph.facebook.com/v2.9/me/?access_token={access_token}&fields={fields}",
                            AccountDTO.class, urlparams);
            return accountDTO;
        } catch (HttpStatusCodeException exception) {
            log.warn(exception.getResponseBodyAsString());
            throw new RuntimeException(String.valueOf(exception.getStatusCode()));
        }
    }

    public String getAppAccessToken() {
        Map<String, String> urlparams = new HashMap<>();
        urlparams.put("client_id", APP_ID);
        urlparams.put("client_secret", APP_SECRET);
        log.info("Retrieving app access token");

        try {
            String json = restTemplate.getForObject(
                    "https://graph.facebook.com/oauth/access_token?client_id={client_id}&client_secret={client_secret"
                            + "}&grant_type=client_credentials",
                    String.class, urlparams);
            String acces_token = new JSONObject(json).getString("access_token");
            return acces_token;
        } catch (HttpStatusCodeException exception) {
            log.warn(exception.getResponseBodyAsString());
            throw new RuntimeException(String.valueOf(exception.getStatusCode()));
        }
    }
}