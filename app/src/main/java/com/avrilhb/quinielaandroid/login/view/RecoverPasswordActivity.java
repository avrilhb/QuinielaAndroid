package com.avrilhb.quinielaandroid.login.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.avrilhb.quinielaandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecoverPasswordActivity extends AppCompatActivity {

    @BindView(R.id.txtTitleTool) TextView txtTitleTool;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        ButterKnife.bind(this);

        showToolbar(true);
        txtTitleTool.setText(getResources().getString(R.string.btnRecoverLogin));
    }

    public void showToolbar(boolean upButton){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }
}
