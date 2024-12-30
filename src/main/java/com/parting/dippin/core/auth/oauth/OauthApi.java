package com.parting.dippin.core.auth.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OauthApi {

    String getToken(String code) throws JsonProcessingException;

    String getUser(String token) throws JsonProcessingException;
}
