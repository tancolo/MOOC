
package com.shrimpcolo.johnnytam.ishuying.utils;

import android.widget.Toast;

import com.shrimpcolo.johnnytam.ishuying.IShuYingApplication;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class ToastUtils {

	private ToastUtils() {
	}

	/**
	 * long toast
	 * 
	 * @param id
	 */
	public static void showLongToast(final int id) {
		Observable.just(id)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(integer -> makeToast(id, Toast.LENGTH_LONG));
	}

	/**
	 * long toast
	 * 
	 * @param msg
	 */
	public static void showLongToast(final String msg) {

		Observable.just(msg)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(integer -> makeToast(msg, Toast.LENGTH_LONG));
	}

	/**
	 * short toast
	 * 
	 * @param id
	 */
	public static void showShortToast(final int id) {

		Observable.just(id)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(integer -> makeToast(id, Toast.LENGTH_SHORT));
	}

	/**
	 * short toast
	 * 
	 * @param msg
	 */
	public static void showShortToast(final String msg) {

		Observable.just(msg)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(integer -> makeToast(msg, Toast.LENGTH_SHORT));

	}

	private static void makeToast(int id, int duration) {
		Toast toast = Toast.makeText(IShuYingApplication.getInstance(), id, duration);
		toast.show();
	}

	private static void makeToast(String msg, int duration) {
		Toast toast = Toast.makeText(IShuYingApplication.getInstance(), msg, duration);
		toast.show();
	}
}