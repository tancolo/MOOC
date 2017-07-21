/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shrimpcolo.johnnytam.ishuying.ui.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.util.List;

/**
 * refer to https://github.com/ianhanniballake/cheesesquare/tree/scroll_aware_fab and
 * https://github.com/makovkastar/FloatingActionButton
 * I combined with their respective characteristics to achieve
 * two different up and down sliding hidden FAB effect
 */
public class FABAwareScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {

    private static final String TAG = FABAwareScrollingViewBehavior.class.getSimpleName();

    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private static final int TRANSLATE_DURATION_MILLIS = 300;

    private boolean mVisible;

    private FloatingActionButton mFloatingActionBar;

    public FABAwareScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        mVisible = true;
        mFloatingActionBar = null;
        //Log.e(TAG, "FABAwareScrollingViewBehavior() ");
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //Log.e(TAG, "layoutDependsOn() ");
        return super.layoutDependsOn(parent, child, dependency) ||
                dependency instanceof FloatingActionButton;
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final View child,
            final View directTargetChild, final View target, final int nestedScrollAxes) {
        //Log.e(TAG, "onStartNestedScroll() ");
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final View child,
            final View target, final int dxConsumed, final int dyConsumed,
            final int dxUnconsumed, final int dyUnconsumed) {

        //Log.e(TAG, "onNestedScroll() ");
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0) {
            // User scrolled down -> hide the FAB
            List<View> dependencies = coordinatorLayout.getDependencies(child);
            dependencies.stream().filter(view -> view instanceof FloatingActionButton).forEach(view -> {
                //((FloatingActionButton) view).hide(); //scaling animation
                toggle(false, true, view);
            });
        } else if (dyConsumed < 0) {
            // User scrolled up -> show the FAB
            List<View> dependencies = coordinatorLayout.getDependencies(child);
            dependencies.stream()
                    .filter(view -> view instanceof FloatingActionButton)
                    .forEach(view -> {
                        //((FloatingActionButton) view).show();
                        toggle(true, true, view);
                    });
        }
    }

    private void toggle(final boolean visible, final boolean animate, final View view) {
        mFloatingActionBar = (FloatingActionButton) view; //vertical transform animation
        //Log.e(TAG, "toggle() visible = " + visible + ",  fab instance : " + mFloatingActionBar);
        if (mVisible != visible) {
            mVisible = visible;
            int height = mFloatingActionBar.getHeight();

            int translationY = visible ? 0 : height + getMarginBottom();
            if (animate) {
                mFloatingActionBar.animate().setInterpolator(mInterpolator)
                        .setDuration(TRANSLATE_DURATION_MILLIS)
                        .translationY(translationY);
            } else {
                //ViewHelper.setTranslationY(this, translationY);
            }

            mFloatingActionBar.setClickable(visible);
        }
    }

    private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = mFloatingActionBar.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }
}
