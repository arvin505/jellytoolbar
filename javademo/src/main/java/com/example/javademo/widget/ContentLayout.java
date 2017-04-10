package com.example.javademo.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.javademo.Constant;
import com.example.javademo.R;
import com.example.javademo.interpolator.BounceInterpolator;
import com.example.javademo.listener.AnimationListener;

/**
 * Created by arvin on 2017-4-10.
 */

public class ContentLayout extends RelativeLayout implements JellyWidget {
    private boolean isInitialized;
    private float startPosition;
    private float endPosition;
    private boolean isExpanded;

    private AppCompatImageView icon;
    private AppCompatImageView cancelIcon;

    private FrameLayout container;
    private float iconFullSize = getDimen(R.dimen.icon_full_size);


    public ContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_content, this);
        icon = (AppCompatImageView) findViewById(R.id.icon);
        cancelIcon = (AppCompatImageView) findViewById(R.id.cancelIcon);
        container = (FrameLayout) findViewById(R.id.container);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        init();
    }

    public void setIcon(int iconRes) {
        this.icon.setBackgroundResource(iconRes);
    }

    public void setCancelIcon(int iconRes) {
        this.cancelIcon.setBackgroundResource(iconRes);
    }

    public void setContentView(View view) {
        container.removeAllViews();
        container.addView(view);
    }

    @Override
    public void collapse() {
        if (!isExpanded) return;
        setTranslationX(endPosition);
        final ValueAnimator animator = ValueAnimator.ofFloat(endPosition, startPosition).setDuration(Constant.ANIMATION_DURATION / 3);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setTranslationX((Float) animation.getAnimatedValue());
                icon.setAlpha(0.5f + 0.5f * animation.getAnimatedFraction());
                cancelIcon.setTranslationX(endPosition - (Float) animation.getAnimatedValue());
                cancelIcon.setAlpha(1f - animation.getAnimatedFraction());
                cancelIcon.setScaleY(1f - animation.getAnimatedFraction());
                cancelIcon.setScaleX(1f - animation.getAnimatedFraction());
                cancelIcon.setRotation(360 * animation.getAnimatedFraction());
            }
        });

        animator.addListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isExpanded = false;
            }
        });
        animator.start();
    }

    @Override
    public void expand() {
        if (isExpanded) return;
        setTranslationX(startPosition);
        ValueAnimator animator = ValueAnimator.ofFloat(startPosition, endPosition).setDuration(Constant.ANIMATION_DURATION / 3);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                icon.setAlpha(1f);
                setTranslationX((Float) animation.getAnimatedValue());
                cancelIcon.setRotationX(0);
                cancelIcon.setAlpha(1f);
                cancelIcon.setTranslationX(0);
                cancelIcon.setScaleX(1);
                cancelIcon.setScaleY(1);
            }
        });


        animator.addListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isExpanded = true;
            }
        });

        animator.start();

    }

    @Override
    public void init() {
        if (!isInitialized) {
            isInitialized = true;
            setTranslationX(getWidth() - iconFullSize);
            startPosition = getWidth() - iconFullSize;
            endPosition = 0;
        }
    }

    @Override
    public void expandImmediately() {

    }

    private float getDimen(int res) {
        return getResources().getDimensionPixelOffset(res);
    }

    public void setIconClickListener(View.OnClickListener listener) {
        icon.setOnClickListener(listener);
    }

    public void setCancelIconListener(View.OnClickListener listener) {
        cancelIcon.setOnClickListener(listener);
    }

}
