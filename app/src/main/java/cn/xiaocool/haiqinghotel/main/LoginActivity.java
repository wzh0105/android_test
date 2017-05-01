package cn.xiaocool.haiqinghotel.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.homepage.ContactUsActivity;
import cn.xiaocool.haiqinghotel.net.request.MainRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;
import view.HQApplacation;

/**
 * Created by wzh on 2016/5/13.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private Button btnLogin;
    private TextView tvTitle;
    private RelativeLayout rlRegister;
    private EditText etPhoNUm, etPassword;
    private CheckBox cbRember;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.LOGIN:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            JSONObject dataObject = jsonObject.getJSONObject("data");
                            String id = dataObject.getString("id");
                            Log.e("id is ", id);
                            //写入用户id
                            HQApplacation.UID = Integer.parseInt(id);
                            //记住账号密码
                            if (cbRember.isChecked()) {
                                String userName = etPhoNUm.getText().toString();
                                String userPass = etPassword.getText().toString();
                                if ((userName != null) && (userPass != null)) {
                                    editor.putString("userName", userName);
                                    editor.putString("userPass", userPass);
                                    editor.commit();
                                }
                            }
                            Toast.makeText(LoginActivity.this, "登陆成功，正在跳转主界面！", Toast.LENGTH_SHORT).show();
                            IntentUtils.getIntent(LoginActivity.this, MainActivity.class);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "密码错误！，请重试！", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_login);
        sharedPreferences = getSharedPreferences("userNamePass", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView();
        String userName = sharedPreferences.getString("userName", null);
        String userPass = sharedPreferences.getString("userPass", null);
        Log.e("sharedpreference", userName + userPass);

        etPhoNUm.setText(userName);
        etPassword.setText(userPass);
    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        etPhoNUm = (EditText) findViewById(R.id.et_phoNum);
        etPassword = (EditText) findViewById(R.id.et_password);
        rlRegister = (RelativeLayout) findViewById(R.id.rl_register);
        rlRegister.setOnClickListener(this);
        cbRember = (CheckBox) findViewById(R.id.cb_remember);
        cbRember.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.btn_login));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (NetUtil.isConnnected(this)) {
                    String phoNum = etPhoNUm.getText().toString();
                    String password = etPassword.getText().toString();
                    new MainRequest(this, handler).login(phoNum, password);
                } else {
                    Toast.makeText(this, "请检查网络！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_register:
                IntentUtils.getIntent(this, RegisterActivity.class);
                break;
            case R.id.cb_remember:

        }
    }
}
