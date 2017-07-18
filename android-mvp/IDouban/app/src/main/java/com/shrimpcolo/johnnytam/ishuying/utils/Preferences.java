package com.shrimpcolo.johnnytam.ishuying.utils;

public class Preferences {

	private Preferences() {
	}

	public static final String APP_SHARED_PREFERENCE = "ishuying_sp";

	/**
	 * 自动登录
	 */
	public static final String PREFERENCE_AUTO_LOGIN = "auto_login";

	/**
	 * 登录类型: 1为QQ用户, 2为新浪用户, 3为微信用户, 4为自建账户用户
	 * 
	 */
	public static final String PREFERENCE_LOGIN_TYPE = "login_type";

	/**
	 * 是否要显示启动页
	 * 
	 */
	public static final String PREFERENCE_SHOW_SPLASH = "show_splash";
}
