package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import view.HQApplacation;

/**
 * Created by wzh on 2016/5/16.
 */
public class BookingCateringActivity extends Activity implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    private TextView tvAdd, tvSub, tvNumber, tvTime;
    private int num = 1;
    private int count, totalPrice;
    private Button btnReserve;
    private TextView tv_title, tvCateringName, tvCateringPrice, tvSubTotal, tvTotalPrice, tvBottomPrice;
    private EditText etName, etPhoNum, etRemark, etRoomNum;
    private RelativeLayout rlExit;
    private String cateringName, cateringPrice, cateringId;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.RESERVE_CATERING:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    Log.e("jsonobject",jsonObject.toString()
                    );
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(BookingCateringActivity.this,"预订成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(BookingCateringActivity.this,"预订失败！",Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.home_booking_catering);
        Intent intent = getIntent();
        cateringName = intent.getStringExtra("cateringName");
        cateringPrice = intent.getStringExtra("cateringPrice");
        cateringId = intent.getStringExtra("cateringId");
        Log.e("intent success", cateringName + cateringPrice);
        initView();
    }

    private void initView() {
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvAdd.setOnClickListener(this);
        tvSub = (TextView) findViewById(R.id.tv_sub);
        tvSub.setOnClickListener(this);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        tvNumber.setText(String.valueOf(num));
        tv_title = (TextView) findViewById(R.id.top_title);
        tv_title.setText(this.getString(R.string.home_btn_reserve_now));
        rlExit = (RelativeLayout) findViewById(R.id.btn_back);
        rlExit.setOnClickListener(this);
        tvCateringName = (TextView) findViewById(R.id.tv_cateringName);
        tvCateringPrice = (TextView) findViewById(R.id.tv_cateringPrice);
        tvCateringName.setText(cateringName);
        tvCateringPrice.setText("¥" + cateringPrice + "/份");
        tvSub = (TextView) findViewById(R.id.tv_subTotal);
        tvSub.setText("¥" + cateringPrice);
        tvTotalPrice = (TextView) findViewById(R.id.tv_totalPrice);
        tvTotalPrice.setText("¥" + cateringPrice);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        tvBottomPrice = (TextView) findViewById(R.id.tv_bottom_total_price);
        tvBottomPrice.setText("¥" + cateringPrice);
        etName = (EditText) findViewById(R.id.et_catering_name);
        etPhoNum = (EditText) findViewById(R.id.et_catering_phoNum);
        etRemark = (EditText) findViewById(R.id.et_remark);
        tvTime = (TextView) findViewById(R.id.tv_catering_time);
        tvTime.setOnClickListener(this);
        etRoomNum = (EditText) findViewById(R.id.et_roomNum);
        btnReserve = (Button) findViewById(R.id.btn_reserve_catering_now);
        btnReserve.setOnClickListener(this);
    }


    private void showTimePopupWindow() {
        View contentView = LayoutInflater.from(BookingCateringActivity.this).inflate(R.layout.catering_popuplayout, null);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_roomCount1);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_roomCount2);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_roomCount3);
        TextView tv4 = (TextView) contentView.findViewById(R.id.pop_roomCount4);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

        View rootview = LayoutInflater.from(BookingCateringActivity.this).inflate(R.layout.home_booking_catering, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                if (num < 9) {
                    num += 1;
                }
//                count = Integer.valueOf(tvNumber.getText().toString()).intValue();
                totalPrice = num * (Integer.valueOf(cateringPrice).intValue());
                tvSub.setText("¥" + totalPrice);
                tvTotalPrice.setText("¥" + totalPrice);
                tvBottomPrice.setText("¥" + totalPrice);
                tvNumber.setText(String.valueOf(num));
                break;
            case R.id.tv_sub:
                if (num > 0) {
                    num -= 1;
                }
//                count = Integer.valueOf(tvNumber.getText().toString()).intValue();
                totalPrice = num * (Integer.valueOf(cateringPrice).intValue());
                tvSub.setText("¥" + totalPrice);
                tvTotalPrice.setText("¥" + totalPrice);
                tvBottomPrice.setText("¥" + totalPrice);
                tvNumber.setText(String.valueOf(num));
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reserve_catering_now:
                String price = tvBottomPrice.getText().toString();
                String name = etName.getText().toString();
                String count = tvNumber.getText().toString();
                String phoNum = etPhoNum.getText().toString();
                String remark = etRemark.getText().toString();
                String time = tvTime.getText().toString();
                String roomNum = etRoomNum.getText().toString();
                if (NetUtil.isConnnected(this)) {
                    new HomepageRequest(this, handler).reserveCatering(HQApplacation.UID, cateringId, roomNum,
                            time, count, "1", phoNum, remark, name, price);
                }
                break;
            case R.id.tv_catering_time:
                showTimePopupWindow();
                break;
            case R.id.pop_roomCount1:
                tvTime.setText("6:00前");
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount2:
                tvTime.setText("8:00前");
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount3:
                tvTime.setText("10:00前");
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount4:
                tvTime.setText("12:00前");
                mPopupWindow.dismiss();
                break;

        }
    }
}
