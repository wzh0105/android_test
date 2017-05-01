package cn.xiaocool.haiqinghotel.main.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/5/22.
 */
public class MineSettingActivity extends Activity implements View.OnClickListener {
    private RelativeLayout btnExit;
    private TextView tvTitle;
    private LinearLayout llPassword,llClean,llAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_setting);
        initView();
    }



    private void initView() {
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.mine_title_setting));
        llPassword = (LinearLayout) findViewById(R.id.mine_ll_password);
        llPassword.setOnClickListener(this);
        llClean = (LinearLayout) findViewById(R.id.mine_ll_clean);
        llClean.setOnClickListener(this);
        llAbout = (LinearLayout) findViewById(R.id.mine_ll_about);
        llAbout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_ll_password:
                IntentUtils.getIntent(this,ChangePasswordActivity.class);
                break;
            case R.id.mine_ll_clean:
                break;
            case R.id.mine_ll_about:
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}