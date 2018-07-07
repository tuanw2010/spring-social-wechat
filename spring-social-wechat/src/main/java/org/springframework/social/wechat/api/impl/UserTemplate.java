package org.springframework.social.wechat.api.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.wechat.api.UserInfo;
import org.springframework.social.wechat.api.UserOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class UserTemplate implements UserOperations {

	private static final String AUTH_URL = "https://api.weixin.qq.com/sns/userinfo";
	private String openid;
	private String accessToken;
	private final RestTemplate restTemplate;

	public UserTemplate(RestTemplate restTemplate, String openid, String accessToken) {
		this.openid = openid;
		this.accessToken = accessToken;
		this.restTemplate = restTemplate;
	}

	public UserInfo getUserInfo() {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("openid", openid);
		params.set("access_token", accessToken);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity entity = new HttpEntity(headers);

		URIBuilder builder = URIBuilder.fromUri(AUTH_URL).queryParams(params);

		HttpEntity<UserInfo> userInfo = restTemplate.exchange(
				builder.build(),
				HttpMethod.GET,
				entity,
				UserInfo.class);

		return userInfo.getBody();
	}

}
