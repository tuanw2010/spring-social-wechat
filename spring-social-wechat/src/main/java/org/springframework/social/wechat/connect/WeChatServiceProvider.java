/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.wechat.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.wechat.api.WeChat;
import org.springframework.social.wechat.api.impl.WeChatTemplate;
import org.springframework.social.wechat.oauth2.WeChatOAuth2Template;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WeChatServiceProvider extends AbstractOAuth2ServiceProvider<WeChat> {

	private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

	public WeChatServiceProvider(OAuth2Operations oauth2Operations) {
		super(oauth2Operations);
	}

	public WeChatServiceProvider(String appId, String appSecret) {
		super(getOAuth2Template(appId, appSecret));
	}

	private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
		OAuth2Template oAuth2Template = new WeChatOAuth2Template(appId, appSecret,
				AUTHORIZE_URL, ACCESS_TOKEN_URL);
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	public WeChat getApi(String accessToken, String openid) {
		return new WeChatTemplate(accessToken, openid);
	}

	@Override
	public WeChat getApi(String accessToken) {
		return new WeChatTemplate(accessToken, null);
	}

}