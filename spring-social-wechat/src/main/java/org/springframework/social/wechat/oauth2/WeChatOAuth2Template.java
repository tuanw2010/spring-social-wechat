package org.springframework.social.wechat.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.FormMapHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class WeChatOAuth2Template extends OAuth2Template {

	private String appid;
	private String appsecret;
	
	public WeChatOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		this.appid = clientId;
		this.appsecret=clientSecret;
	}
	
	protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
		String openid = (String) response.get("openid");
		return new WeChatAccessGrant(accessToken, scope, refreshToken, expiresIn, openid);
	}

	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		parameters.add("appid", appid);
		parameters.add("secret", appsecret );
		return super.postForAccessGrant(accessTokenUrl, parameters);
	}

	protected RestTemplate getRestTemplate() {
		final RestTemplate restTemplate = super.getRestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(2);
		converters.add(new FormHttpMessageConverter());
		converters.add(new FormMapHttpMessageConverter());
		//converters.add(new MappingJackson2HttpMessageConverter());
		List<MediaType> mediaTypes = new ArrayList<MediaType>(2);
		mediaTypes.add(MediaType.APPLICATION_JSON);
		mediaTypes.add(MediaType.TEXT_PLAIN);
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(mediaTypes);
		converters.add(converter);
		converters.add(new StringHttpMessageConverter());



		restTemplate.setMessageConverters(converters);

		return  restTemplate;
	}

	public String buildAuthorizeUrl(OAuth2Parameters parameters) {
		return appendAppId(super.buildAuthorizeUrl(parameters));
	}

	public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
		return appendAppId(super.buildAuthorizeUrl(grantType, parameters));
	}

	public String buildAuthenticateUrl(OAuth2Parameters parameters) {
		return appendAppId(super.buildAuthenticateUrl(parameters));
	}

	public String buildAuthenticateUrl(GrantType grantType, OAuth2Parameters parameters) {
		return appendAppId(super.buildAuthenticateUrl(grantType, parameters));
	}


	private String appendAppId(final String url) {
		return url + "&appId=" + formEncode(appid);
	}

	private String formEncode(String data) {
		try {
			return URLEncoder.encode(data, "UTF-8");
		}
		catch (UnsupportedEncodingException ex) {
			// should not happen, UTF-8 is always supported
			throw new IllegalStateException(ex);
		}
	}
}
