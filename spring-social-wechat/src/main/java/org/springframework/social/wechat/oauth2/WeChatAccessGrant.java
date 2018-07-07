package org.springframework.social.wechat.oauth2;

import org.springframework.social.oauth2.AccessGrant;

public class WeChatAccessGrant extends AccessGrant {

	private String openid;

	private static final long serialVersionUID = -1611426559782895128L;

	public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openid) {
		super(accessToken, scope, refreshToken, expiresIn);
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}