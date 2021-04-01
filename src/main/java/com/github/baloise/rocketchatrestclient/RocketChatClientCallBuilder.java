package com.github.baloise.rocketchatrestclient;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * The call builder for the {@link RocketChatClient} and is only supposed to be used internally.
 *
 * @author Bradley Hilton (graywolf336)
 * @version 0.0.1
 * @since 0.1.0
 */
public class RocketChatClientCallBuilder {
	
	public static final String CALL_METHOD_NAME_ARGUMENTS_KEY = "methodNameArgs";
	
    private final ObjectMapper objectMapper;
    private final String serverUrl;
    private final String user;
    private final String password;
    private String sha256password;
    private String authToken;
    private String userId;

    //- http://localhost:3000
    //- http://localhost:3000/
    //- http://localhost:3000/api
    //- http://localhost:3000/api/
    protected RocketChatClientCallBuilder(String serverUrl, String user, String password) {
        //I am not the greatest with if statements like these, so feel free to submit
        //a pull request to fix this XD
        if (serverUrl.endsWith("/")) {
            this.serverUrl = serverUrl + (serverUrl.endsWith("api/") ? "" : "api/");
        } else {
            this.serverUrl = serverUrl + (serverUrl.endsWith("api") ? "/" : "/api/");
        }

        this.user = user;
        this.password = password;
        this.authToken = "";
        this.userId = "";
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    protected RocketChatClientResponse buildCall(RocketChatRestApiV1 call) throws IOException {
        return this.buildCall(call, null, null);
    }

    protected RocketChatClientResponse buildCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams) throws IOException {
        return this.buildCall(call, queryParams, null);
    }

    protected RocketChatClientResponse buildCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams, Object body) throws IOException {
        if (call.requiresAuth() && (authToken.isEmpty() || userId.isEmpty())) {
        	login();
        	try {
				byte[] digest = MessageDigest.getInstance("SHA-256").digest(password.trim().getBytes());
				sha256password = bytesToHex(digest);
			} catch (NoSuchAlgorithmException e) {
				throw new IOException("Could not generate sha256 password digest", e);
			}
        }

        switch (call.getHttpMethod()) {
            case GET:
                return this.buildGetCall(call, queryParams);
            case POST:
                return this.buildPostCall(call, queryParams, body);
            default:
                throw new IOException("Http Method " + call.getHttpMethod().toString() + " is not supported.");
        }
    }
    
	/**
	 * @param hash
	 * @return get the hashed value in hexadecimal
	 * @see https://www.baeldung.com/sha-256-hashing-java
	 */
	private String bytesToHex(byte[] hash){
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

    protected void logout() throws IOException {
        try {
            Unirest.post(serverUrl + "v1/logout").asString();
            this.authToken = "";
            this.userId = "";
        } catch (UnirestException e) {
            throw new IOException(e);
        }
    }

    private void login() throws IOException {
        HttpResponse<JsonNode> loginResult;
        String[] ldap_options = {};

        try {
            loginResult = Unirest.post(serverUrl + "v1/login").field("username", user).field("ldapPass", password).field("ldap", true).field("ldapOptions", ldap_options).asJson();
        } catch (UnirestException e) {
            throw new IOException(e);
        }

        if (loginResult.getStatus() == 401)
            throw new IOException("The username and password provided are incorrect.");

        
		if (loginResult.getStatus() != 200)
			throw new IOException("The login failed with a result of: " + loginResult.getStatus()
				+ " (" + loginResult.getStatusText() + ")");
		
        JSONObject data = loginResult.getBody().getObject().getJSONObject("data");
        this.authToken = data.getString("authToken");
        this.userId = data.getString("userId");
    }

    private RocketChatClientResponse buildGetCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams) throws IOException { 	
    	String methodName = prepareCallMethodName(call, queryParams);
    	GetRequest req = Unirest.get(serverUrl + methodName);

        if (call.requiresAuth()) {
            req.header("X-Auth-Token", authToken);
            req.header("X-User-Id", userId);
            req.header("x-2fa-method", "password");
            req.header("x-2fa-code", sha256password);
        }

        if (queryParams != null && queryParams.get() != null && !queryParams.isEmpty()) {
            for (Entry<? extends String, ? extends String> e : queryParams.get().entrySet()) {
                req.queryString(e.getKey(), e.getValue());
            }
        }

        try {
            HttpResponse<String> res = req.asString();

            return objectMapper.readValue(res.getBody(), RocketChatClientResponse.class);
        } catch (UnirestException e) {
            throw new IOException(e);
        }
    }

	private RocketChatClientResponse buildPostCall(RocketChatRestApiV1 call, RocketChatQueryParams queryParams, Object body) throws IOException {
		String methodName = prepareCallMethodName(call, queryParams);
		HttpRequestWithBody req = Unirest.post(serverUrl + methodName).header("Content-Type", "application/json");

        if (call.requiresAuth()) {
            req.header("X-Auth-Token", authToken);
            req.header("X-User-Id", userId);
            req.header("x-2fa-method", "password");
            req.header("x-2fa-code", sha256password);
        }

        if (queryParams != null && queryParams.get() != null && !queryParams.isEmpty()) {
            for (Entry<? extends String, ? extends String> e : queryParams.get().entrySet()) {
                req.queryString(e.getKey(), e.getValue());
            }
        }

        if (body != null) {
            req.body(objectMapper.writeValueAsString(body));
        }

        try {
            HttpResponse<String> res = req.asString();

            return objectMapper.readValue(res.getBody(), RocketChatClientResponse.class);
        } catch (UnirestException e) {
            throw new IOException(e);
        }
    }
	
	/**
	 * If variables like {0} are used within
	 * {@link RocketChatRestApiV1#getMethodName()}, this method replaces the
	 * respective variables with the values found in
	 * {@link RocketChatQueryParams}#<code>methodNameArgs</code>.
	 * 
	 * @param call
	 * @param queryParams
	 * @return
	 */
	private String prepareCallMethodName(RocketChatRestApiV1 call, RocketChatQueryParams queryParams) {
		String methodName = call.getMethodName();
		if (methodName.contains("{") && methodName.contains("}")) {
			String[] methodArguments = queryParams.get().get(CALL_METHOD_NAME_ARGUMENTS_KEY).split(",");
			methodName = MessageFormat.format(methodName, (Object[]) methodArguments);
			queryParams.get().remove(CALL_METHOD_NAME_ARGUMENTS_KEY);
		} else {
			methodName = call.getMethodName();
		}
		return methodName;
	}
}
