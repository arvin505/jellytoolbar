package com.example.javademo.interpolator;

import android.view.animation.Interpolator;

/**
 * Created by xiaoyi on 2017/4/10.
 */

public class JellyInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float t) {
        return (float) Math.min(1.0, Math.sin(28 * t - 6.16) / (5 * t - 1.1));
    }
}
