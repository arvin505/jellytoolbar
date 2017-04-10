package com.example.javademo.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.javademo.Constant;
import com.example.javademo.R;
import com.example.javademo.interpolator.BounceInterpolator;
import com.example.javademo.interpolator.JellyInterpolator;
import com.example.javademo.listener.AnimationListener;


/**
 * Created by xiaoyi on 2017/4/10.
 */

public class JellyView extends View implements JellyWidget {
    private boolean isInitialized;
    private Path path;
    private Paint paint;
    private int startColor = Color.RED;
    private int endColor = Color.BLUE;
    private LinearGradient gradient;
    private float startPosition;
    private float endPosition;
    private boolean isExpanded;
    private float jellyViewSize = getDimen(R.dimen.jelly_view_size);
    private float jellyViewWidth = getDimen(R.dimen.jelly_view_width);
    private float jellyViewOffset = getDimen(R.dimen.jelly_view_offset);
    private float difference;


    public JellyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInitialized) {
            init();
        }
        redraw(canvas);
    }

    private void redraw(Canvas canvas) {
        paint.setShader(gradient);
        path.moveTo(jellyViewWidth, 0);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(jellyViewWidth, getHeight());
        path.quadTo(jellyViewWidth - difference, getHeight() / 2, jellyViewWidth, 0);
        canvas.drawPath(path, paint);
        path.reset();
        path.close();

    }


    @Override
    public void collapse() {
        isExpanded = false;
        animateJellyCollapsing();
        moveBack();
    }

    private void moveBack() {
        ValueAnimator animator = ValueAnimator.ofFloat(endPosition, startPosition);
        animator.setDuration(Constant.ANIMATION_DURATION / 3);
        setTranslationX(endPosition);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void animateJellyCollapsing() {
        animaeJelly(1, false, Constant.ANIMATION_DURATION);
    }

    @Override
    public void expand() {
        isExpanded = true;
        animateJellyExpanding();
        moveForward(true);
    }

    private void moveForward(boolean moveOffset) {
        float endPosition = this.endPosition;
        if (moveOffset) {
            endPosition += jellyViewOffset;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(startPosition, endPosition);
        animator.setDuration(Constant.ANIMATION_DURATION / 3);
        setTranslationX(startPosition);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void animateJellyExpanding() {
        animaeJelly(-1, true, Constant.ANIMATION_DURATION);
    }

    private void animaeJelly(final int coefficient, final boolean moveOffset, long animationDuration) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, jellyViewWidth / 2).setDuration(animationDuration);
        animator.setInterpolator(new JellyInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                difference = coefficient * (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                difference = 0f;
                invalidate();

                if (moveOffset && isExpanded) {
                    moveOffset();
                }
            }
        });

        animator.start();
    }

    private void moveOffset() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, jellyViewOffset);
        animator.setDuration(150);

        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float transX = getTranslationX();
                transX -= (Float) animation.getAnimatedValue();
                setTranslationX(transX);
            }
        });

        animator.start();
    }


    @Override
    public void init() {
        path = new Path();
        paint = new Paint();
        setLayoutParams(new FrameLayout.LayoutParams((int) (getWidth() + jellyViewWidth * 2), getHeight()));
        setTranslationX(getWidth() - jellyViewSize);
        startPosition = getTranslationX();
        endPosition = -jellyViewWidth;
        isInitialized = true;
        gradient = new LinearGradient(0, 0, getWidth(), 0, startColor, endColor, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
    }

    @Override
    public void expandImmediately() {

    }

    private float getDimen(int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }
}
