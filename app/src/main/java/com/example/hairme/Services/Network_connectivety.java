package com.example.hairme.Services;

import static android.provider.Settings.ACTION_WIFI_SETTINGS;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;
import androidx.core.content.ContextCompat;

import com.example.hairme.R;

public class Network_connectivety extends Dialog implements AppCompatCallback {

    Context mContext;
    private AppCompatDelegate mDelegate;

    public Network_connectivety(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_connectivety);

        findViewById(R.id.settings).setOnClickListener(v -> ContextCompat.startActivity(mContext, new Intent(ACTION_WIFI_SETTINGS), null));
        findViewById(R.id.dismess).setOnClickListener(v -> dismiss());
    }


    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        this.dismiss();
        // There is no onDestroy in Dialog, so we simulate it from dismiss()
        getDelegate().onDestroy();
    }

    public AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, this);
        }
        return mDelegate;
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

}