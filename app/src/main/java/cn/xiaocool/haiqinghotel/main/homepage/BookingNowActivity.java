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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import view.HQApplacation;

/**
 * Created by wzh on 2016/5/8.
 */
public class BookingNowActivity extends Activity implements View.OnClickListener {
    private PopupWindow mPopupWindow;
    private LinearLayout llRoomNum, llArriveTime;
    private TextView tvTitle, tvRoomCount, tvArriveTime;
    private String roomCount, arriveTime, goodId;
    private TextView tvIntro, tvCheckIn, tvCheckOUt,tvDayCount;
    private RelativeLayout rlExit;
    private String bedSize, network, checkIn, checkOut;
    private long msInDay, msOutDay;
    private Button btnBookNow;
    private EditText etName, etPhoNum, etRemark;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.RESERVE_ROOM_NOW:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    try {
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(BookingNowActivity.this, "预订酒店房间成功！", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(BookingNowActivity.this,"预定失败！",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    private long dayCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_booking_now);
        initView();
    }

    private void initView() {
        tvDayCount = (TextView) findViewById(R.id.tv_dayCount);
        llRoomNum = (LinearLayout) findViewById(R.id.ll_room_num);
        llRoomNum.setOnClickListener(this);
        llArriveTime = (LinearLayout) findViewById(R.id.ll_arrive_time);
        llArriveTime.setOnClickListener(this);
        tvRoomCount = (TextView) findViewById(R.id.tv_roomCount);
        tvArriveTime = (TextView) findViewById(R.id.tv_arriveTime);
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.home_btn_reserve_now));
        rlExit = (RelativeLayout) findViewById(R.id.btn_back);
        rlExit.setOnClickListener(this);
        tvCheckIn = (TextView) findViewById(R.id.tv_check_in);
        tvCheckOUt = (TextView) findViewById(R.id.tv_check_out);
        tvIntro = (TextView) findViewById(R.id.tv_introduce);
        btnBookNow = (Button) findViewById(R.id.btn_booking_now);
        btnBookNow.setOnClickListener(this);
        etName = (EditText) findViewById(R.id.et_name);
        etPhoNum = (EditText) findViewById(R.id.et_phoNum);
        etRemark = (EditText) findViewById(R.id.et_remark);
        Intent intent = getIntent();
        Log.e("bedsize network", bedSize + network);
        bedSize = intent.getStringExtra("bedsize");
        network = intent.getStringExtra("network");
        checkIn = intent.getStringExtra("textCheckIn");
        checkOut = intent.getStringExtra("textCheckOut");
        dayCount = intent.getLongExtra("dayCount",1);
        Log.e("check in day is ",checkIn + checkOut);
        goodId = intent.getStringExtra("roomId");
        Log.e("roomId here is  ",goodId);
        //取到毫秒值
        msInDay = intent.getLongExtra("msInDay", 0);
        msOutDay = intent.getLongExtra("msOutDay", 0);
        Log.e("ms time", msInDay + "aaaa" + msOutDay);
        tvCheckIn.setText(checkIn);
        tvCheckOUt.setText(checkOut);
        //介绍行
        tvIntro.setText(bedSize + this.getString(R.string.space) + network);
        tvDayCount.setText("共" + dayCount + "晚");
    }


    private void showRoomPopupWindow() {
        View contentView = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.room_popuplayout, null);
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

        View rootview = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.home_booking_now, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    private void showTimePopupWindow() {
        View contentView = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.time_popuplayout, null);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        TextView tv1 = (TextView) contentView.findViewById(R.id.pop_time0);
        TextView tv2 = (TextView) contentView.findViewById(R.id.pop_time1);
        TextView tv3 = (TextView) contentView.findViewById(R.id.pop_time2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        View rootview = LayoutInflater.from(BookingNowActivity.this).inflate(R.layout.home_booking_now, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_room_num:
                showRoomPopupWindow();
                break;
            case R.id.ll_arrive_time:
                showTimePopupWindow();
                break;
            case R.id.pop_roomCount1:
                tvRoomCount.setText("1间");
                roomCount = "1";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount2:
                tvRoomCount.setText("2间");
                roomCount = "2";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount3:
                tvRoomCount.setText("3间");
                roomCount = "3";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_roomCount4:
                tvRoomCount.setText("4间");
                roomCount = "4";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_time0:
                tvArriveTime.setText("12:00前");
                arriveTime = "12:00前";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_time1:
                tvArriveTime.setText("18:00前");
                arriveTime = "18:00前";
                mPopupWindow.dismiss();
                break;
            case R.id.pop_time2:
                tvArriveTime.setText("24:00前");
                arriveTime = "24:00前";
                mPopupWindow.dismiss();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_booking_now:
                String name = etName.getText().toString();
                String phoNum = etPhoNum.getText().toString();
                String remark = etRemark.getText().toString();
                Log.e("入参数据",goodId + arriveTime + phoNum + remark);
                if (NetUtil.isConnnected(this)) {
                    //发起预定房间请求
                    new HomepageRequest(this, handler).reserveNow(HQApplacation.UID, goodId, msInDay, msOutDay, arriveTime,
                            "2", "1", phoNum, remark);
                }else{
                    Toast.makeText(this,"无网络连接！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
