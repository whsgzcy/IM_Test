package com.iwant.im.imwanttest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "whsgzcy";

    private EditText mContentEd;
    private Button mSendMessageBtn;
    private Button mMediaMessageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentEd = (EditText) findViewById(R.id.content_ed);
        mSendMessageBtn = (Button) findViewById(R.id.send_btn);
        mSendMessageBtn.setOnClickListener(this);
        mMediaMessageBtn = (Button) findViewById(R.id.media_btn);
        mMediaMessageBtn.setOnClickListener(this);

        Observer<List<IMMessage>> incomingMessageObserver =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                      for(IMMessage im:messages){
                          String sender = im.getSessionId();
                          String content = im.getContent();
                          Log.d(TAG,"MainAcitvity-> " + "recevice " + sender+ " message");
                          Log.d(TAG,"MainAcitvity-> " + "content " + content);
                      }
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);

    }

    @Override
    public void onClick(View v) {
        IMMessage message;
        switch (v.getId()) {
            case R.id.send_btn:
                String content = mContentEd.getText().toString().trim();
                if(content.equals("")){
                    return;
                }
                // 创建文本消息
                message = MessageBuilder.createTextMessage(
                        "whsgzcy02", // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                        SessionTypeEnum.P2P, // 聊天类型，单聊或群组
                        content // 文本内容
                );
                Log.d(TAG,"MainAcitvity-> " + "send " + content);
                NIMClient.getService(MsgService.class).sendMessage(message,true).setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mContentEd.setText("");
                        Log.d(TAG,"MainAcitvity-> " + "send success");
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.d(TAG,"MainAcitvity-> " + "send failed");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.d(TAG,"MainAcitvity-> " + "send exception");
                    }
                });
                break;
            case R.id.media_btn:

              break;
        }

    }
}
