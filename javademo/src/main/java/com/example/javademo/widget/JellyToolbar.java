package com.example.javademo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.javademo.Constant;
import com.example.javademo.R;
import com.example.javademo.listener.JellyListener;

/**
 * Created by arvin on 2017-4-10.
 */

public class JellyToolbar extends FrameLayout implements JellyWidget {
    private Toolbar toolbar;
    private JellyView jellyView;
    private ContentLayout contentLayout;
    private int startColor;
    private int endColor;
    private int iconRes;
    private int cancelIconRes;
    private String title;
    private int titleColor;
    private int menuIconRes;
    private JellyListener jellyListener;
    private boolean isExpanded;

    public void setJellyListener(JellyListener jellyListener) {
        this.jellyListener = jellyListener;
    }

    public JellyToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.jelly_toolbar, this);
        toolbar = (Toolbar) findViewById(R.id.defaultToolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        jellyView = (JellyView) findViewById(R.id.jellyView);
        contentLayout = (ContentLayout) findViewById(R.id.contentLayout);
        initAttrs(context, attrs);

        contentLayout.setIconClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expand();
            }
        });

        contentLayout.setCancelIconListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jellyListener != null) {
                    jellyListener.onCancelIconClick();
                }
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.JellyToolbar);
        startColor = array.getColor(R.styleable.JellyToolbar_startColor, 0);
        if (startColor != 0) {
            jellyView.setStartColor(startColor);
        }

        endColor = array.getColor(R.styleable.JellyToolbar_endColor, 0);
        if (endColor != 0) {
            jellyView.setEndColor(endColor);
        }

        iconRes = array.getResourceId(R.styleable.JellyToolbar_icon, 0);
        if (iconRes != 0) {
            contentLayout.setIcon(iconRes);
        }

        cancelIconRes = array.getResourceId(R.styleable.JellyToolbar_cancelIcon, 0);
        if (cancelIconRes != 0) {
            contentLayout.setCancelIcon(cancelIconRes);
        }

        title = array.getString(R.styleable.JellyToolbar_title);
        if (!TextUtils.isEmpty(title)) {
            toolbar.setTitle(title);
        }

        titleColor = array.getColor(R.styleable.JellyToolbar_titleTextColor, 0);
        if (titleColor != 0) {
            toolbar.setTitleTextColor(titleColor);
        }

        menuIconRes = array.getResourceId(R.styleable.JellyToolbar_menuIcon, 0);
        if (menuIconRes != 0) {
            toolbar.setNavigationIcon(menuIconRes);
        }
        array.recycle();
    }

    @Override
    public void collapse() {
        if (!isExpanded) return;
        jellyView.collapse();
        contentLayout.collapse();
        isExpanded = false;
        if (jellyListener != null) {
            jellyListener.onToolbarCollapsingStarted();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    jellyListener.onToolbarCollapsed();
                }
            }, Constant.ANIMATION_DURATION);
        }
    }

    @Override
    public void expand() {
        if (isExpanded) return;
        jellyView.expand();
        contentLayout.expand();
        isExpanded = true;
        if (jellyListener != null) {
            jellyListener.onToolbarExpandingStarted();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    jellyListener.onToolbarExpanded();
                }
            }, Constant.ANIMATION_DURATION);
        }

    }

    @Override
    public void init() {

    }

    @Override
    public void expandImmediately() {

    }

    public void setcontentView(View contentView) {
        if (contentView != null) {
            contentLayout.setContentView(contentView);
        }
    }


    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
