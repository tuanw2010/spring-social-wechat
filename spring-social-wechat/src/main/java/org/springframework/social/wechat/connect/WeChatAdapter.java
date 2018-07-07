/*
 * Copyright 2011 the original author or authors.
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

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.wechat.api.UserInfo;
import org.springframework.social.wechat.api.WeChat;

public class WeChatAdapter implements ApiAdapter<WeChat> {

	public boolean test(WeChat weChat) {
		try {
			weChat.userOperations().getUserInfo();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	public void setConnectionValues(WeChat weChat, ConnectionValues values) {
		UserInfo userInfo = weChat.userOperations().getUserInfo();
		values.setProviderUserId(userInfo.getUnionid());
		values.setDisplayName(userInfo.getNickname());
		values.setProfileUrl(userInfo.getHeadimgurl());
		values.setImageUrl(userInfo.getHeadimgurl());
	}

	public UserProfile fetchUserProfile(WeChat weChat) {
		UserInfo userInfo = weChat.userOperations().getUserInfo();
		return new UserProfileBuilder().setName(userInfo.getNickname()).setUsername(userInfo.getNickname()).build();
	}

	public void updateStatus(WeChat weChat, String message) {
		throw new UnsupportedOperationException();
	}

}