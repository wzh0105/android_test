package cn.xiaocool.haiqinghotel.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.bean.UserInfo;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.request.MainRequest;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/5/7.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private RelativeLayout btnBack;
    private Button btnSendCode;
    private ImageView btnRegister;
    private EditText edName, edPhoNum, edCode, edPassword;
    private TextView tvTitle;
    private String phoneNumber, code;
    private CheckBox cbAgree;
    private static int second;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SEND_CODE:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(RegisterActivity.this, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                            String data = jsonObject.getString("data");
                            JSONObject joCode = new JSONObject(data);
                            code = joCode.getString("code");
                            Log.e("code is ",code);
                        } else {
                            Toast.makeText(RegisterActivity.this, "验证码获取失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                case CommunalInterfaces.BTN_UNTOUCH:
                    //按钮不可用，开始倒计时
                    btnSendCode.setClickable(false);
                    btnSendCode.setText(second + "s");
                case CommunalInterfaces.BTN_TOUCH:
                    btnSendCode.setText("发送验证码");
                    btnSendCode.setClickable(true);
                    second = 30;
                case CommunalInterfaces.REGISTER:
                    JSONObject registerObj = (JSONObject) msg.obj;
//                    try {
//                        String regStatus = registerObj.getString("status");
//                        Log.e("status is this",regStatus);
//                        if (regStatus.equals("success")) {
//                            String userNum = registerObj.getString("data");
//                            new UserInfo(RegisterActivity.this).userUID(userNum);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_register);
        initView();
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        btnSendCode = (Button) findViewById(R.id.send_code);
        btnSendCode.setOnClickListener(this);
        btnRegister = (ImageView) findViewById(R.id.register);
        btnRegister.setOnClickListener(this);
        cbAgree = (CheckBox) findViewById(R.id.main_cb_agree);
        cbAgree.setOnClickListener(this);
        edName = (EditText) findViewById(R.id.main_ed_name);
        edPhoNum = (EditText) findViewById(R.id.main_ed_phonum);
        edCode = (EditText) findViewById(R.id.main_ed_code);
        edPassword = (EditText) findViewById(R.id.main_ed_password);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.register));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.send_code:
                sendCode();
                break;
            case R.id.register:
                register();
                break;

        }
    }


    private void sendCode() {
        phoneNumber = edPhoNum.getText().toString();
        Log.e("phone number is", phoneNumber);
        if (phoneNumber.length() == 11) {
            new MainRequest(this, handler).sendCode(phoneNumber);
            new Thread() {
                public void run() {
                    for (int i = 0; i < 30; i++) {
                        try {
                            handler.sendEmptyMessage(CommunalInterfaces.BTN_UNTOUCH);
                            Thread.sleep(1000);
                            second--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendEmptyMessage(CommunalInterfaces.BTN_TOUCH);
                }
            }.start();
        } else {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
        }
    }


    private void register() {
        String verifyCode = edCode.getText().toString();
        String userName = edName.getText().toString();
        String userPass = edPassword.getText().toString();
        phoneNumber = edPhoNum.getText().toString();
        if (verifyCode.equals(code)) {
            if ((userName.length() != 0) && (userPass.length() != 0)) {
                new MainRequest(this, handler).register(userName, phoneNumber, userPass, verifyCode);
                IntentUtils.getIntent(this, MainActivity.class);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
        }
    }
}
