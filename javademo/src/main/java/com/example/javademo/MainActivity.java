package com.example.javademo;

import android.database.sqlite.SQLiteBlobTooBigException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.example.javademo.listener.JellyListener;
import com.example.javademo.widget.ContentLayout;
import com.example.javademo.widget.JellyToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        final ContentLayout contentLayout = (ContentLayout) findViewById(R.id.test);
        contentLayout.setIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentLayout.expand();
            }
        });

        contentLayout.setCancelIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentLayout.collapse();
            }
        });



        final JellyToolbar toolbar = (JellyToolbar) findViewById(R.id.toolbar);

        final AppCompatEditText editText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        toolbar.setcontentView(editText);
        toolbar.setJellyListener(new JellyListener() {
            @Override
            public void onCancelIconClick() {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    editText.setText("");
                } else {
                    toolbar.collapse();
                }
            }
        });
    }
}
