package com.shrimpcolo.johnnytam.ishuying.login;

/**
 * 登录被观察者，实现类是HomeActivity， 其他的Activity/Fragment 如AboutFragment等，需要时刻监听
 * HomeActivity中登录状态的改变
 */
public interface LoginListenerObservable {

	void addListener(LoginListener listener);

	void removeListener(LoginListener listener);

	void onLoginSuccess();//通知其他的观察者 登录成功

	void onLogoutSuccess();//通知其他的观察者 退出成功

}