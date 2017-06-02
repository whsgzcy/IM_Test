package com.iwant.im.imwanttest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by Administrator on 2017/4/28 0028.
 */

public class LoginActivity extends Activity {

    private static final String TAG = "whsgzcy";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Log.d(TAG,"LoginActivity ->" + "onCreate()");
        doLogin();
    }

    private void doLogin(){
        LoginInfo loginInfo = new LoginInfo("whsgzcy01","whsgzcy520");
        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                Log.d(TAG,"LoginActivity ->" + "onSuccess()");
                startActivity(LoginActivity.this);
            }

            @Override
            public void onFailed(int i) {
                Log.d(TAG,"LoginActivity -> " + "onFailed() code = " + i);
            }

            @Override
            public void onException(Throwable throwable) {
                Log.d(TAG,"LoginActivity ->" + "onException()");
            }
        };

        NIMClient.getService(AuthService.class).login(loginInfo)
                .setCallback(callback);
    }

    private void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        finish();
    }
}
