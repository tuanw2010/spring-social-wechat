package org.springframework.social.wechat.api.impl;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.wechat.api.UserOperations;
import org.springframework.social.wechat.api.WeChat;

import java.util.ArrayList;
import java.util.List;

public class WeChatTemplate extends AbstractOAuth2ApiBinding implements WeChat {
	
	private String openid;
	private String accessToken;
	private UserOperations userOperations;
	
    public WeChatTemplate(String accessToken, String openid) {
        super(accessToken);
        this.openid = openid;
        this.accessToken = accessToken;
        initialize();
    }

	public UserOperations userOperations() {
		return userOperations;
	}

    public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
    }

    @Override
    protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<MediaType>(2);
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(mediaTypes);

        return converter;
    }

    private void initialize() {
        super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(this.getRestTemplate().getRequestFactory()));
        this.initSubApis();
    }

    private void initSubApis() {
        this.userOperations = new UserTemplate(getRestTemplate(), this.openid, this.accessToken);
    }

}
