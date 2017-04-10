package com.example.javademo.interpolator;

import android.view.animation.Interpolator;

/**
 * Created by xiaoyi on 2017/4/10.
 */

public class BounceInterpolator implements Interpolator {
    float MOVE_TIME = 0.46667f;
    float FIRST_BOUNCE_TIME = 0.26666f;

    @Override
    public float getInterpolation(float t) {

        if (t < MOVE_TIME)
            return move(t);
        else if (t < MOVE_TIME + FIRST_BOUNCE_TIME)
            return firstBounce(t);
        else
            return secondBounce(t);
    }

    private float move(float t) {
        return 4.592f * t * t;
    }

    private float firstBounce(float t) {
        return 2.5f * t * t - 3f * t + 1.85556f;
    }

    private float secondBounce(float t) {
        return 0.625f * t * t - 1.083f * t + 1.458f;
    }
}
