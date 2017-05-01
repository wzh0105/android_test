package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.haiqinghotel.R;

/**
 * Created by wzh on 2016/4/28.
 */
public class ContactUsActivity extends Activity implements View.OnClickListener {
    private RelativeLayout btn_back;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_contact_us);
        title = (TextView) findViewById(R.id.top_title);
        title.setText(this.getString(R.string.home_btn_contact_us));
        initView();
    }

    private void initView() {
        btn_back = (RelativeLayout) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
