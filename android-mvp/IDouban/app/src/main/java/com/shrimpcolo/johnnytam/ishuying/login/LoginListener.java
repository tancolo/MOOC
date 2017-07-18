package com.shrimpcolo.johnnytam.ishuying.login;

/**
 * 登录 观察者，观察LoginListenerObservable的实现者HomeActivity
 * AboutmeFramgent等会实现该接口
 */
public interface LoginListener {

	void onLoginSuccess();

	void onLogoutSuccess();
}