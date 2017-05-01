package cn.xiaocool.haiqinghotel.main.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/5/8.
 */
public class RoomIntroActivity extends Activity implements View.OnClickListener {
    private TextView tvTitle;
    private RelativeLayout btnExit;
    private LinearLayout llDateChoose;
    private String roomId, roomName;
    private String price, oPrice, network, window, peopleNum, bathRoom, roomFloor, roomArea, bedSize;
    private String textChechIn,textCheckOut;
    private long msInDay,msOutDay;
    private Context context;
    private final int CODE = 1;
    private TextView tvOPrice, tvPrice, tvNetwork, tvWindow, tvPeopleNum, tvBathroom,
            tvRoomFloor, tvRoomArea, tvBedSize, tvTotalPrice, tvInday, tvOutday, tvDayCount;
    private Button btnReserve;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ROOM_DETAILS:
                    if (NetUtil.isConnnected(context)) {
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            Log.e("room details status is", status);
                            if (status.equals("success")) {
                                JSONObject dataObject = (JSONObject) jsonObject.get("data");
                                oPrice = dataObject.getString("oprice");
                                price = dataObject.getString("price");
                                network = dataObject.getString("network");
                                window = dataObject.getString("window");
                                peopleNum = dataObject.getString("peoplenumber");
                                bathRoom = dataObject.getString("bathroom");
                                roomFloor = dataObject.getString("floor");
                                roomArea = dataObject.getString("acreage");
                                bedSize = dataObject.getString("bedsize");
                                Log.e("", "oprice " + oPrice + "price" + price + "network" + network + "window" + window
                                        + "peopleNum" + peopleNum + "bathroom" + bathRoom + "floor" + roomFloor + "roomarea"
                                        + roomArea + "bedsize" + bedSize);
                                tvOPrice.setText(oPrice);
                                tvPrice.setText(price);
                                tvNetwork.setText(network);
                                tvWindow.setText(window);
                                tvPeopleNum.setText(peopleNum);
                                tvBathroom.setText(bathRoom);
                                tvRoomFloor.setText(roomFloor);
                                tvRoomArea.setText(roomArea);
                                tvBedSize.setText(bedSize);
                                tvTotalPrice.setText("¥" + price);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
            }

        }
    };
    private long dayCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_room_intro);
        context = this;
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        roomName = intent.getStringExtra("roomName");
        Log.e("roomId/Name is ", roomId + roomName);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            String indayMonNum = data.getStringExtra("inDayMonth1");
            String indayDayNum = data.getStringExtra("inDayNum0");
            String outdayMonNum = data.getStringExtra("outMonth1");
            String outdayDayNum = data.getStringExtra("outDayNum0");
            dayCount = data.getLongExtra("dayCount",0);
            msInDay = data.getLongExtra("msInDate",0);
            msOutDay = data.getLongExtra("msOutDate",0);
            Log.e("ms in out day is", String.valueOf(msInDay + "bbb" + msOutDay));
            Log.e("五个数：", indayMonNum + indayDayNum + outdayMonNum + outdayDayNum + "ccc" + dayCount);
            textChechIn = "入住：" + indayMonNum + "月" + indayDayNum + "日";
            tvInday.setText(textChechIn);
            textCheckOut = "离店：" + outdayMonNum + "月" + outdayDayNum + "日";
            tvOutday.setText(textCheckOut);
            tvDayCount.setText("共" + dayCount + "晚");
            int intPrice = Integer.parseInt(price);
            int count = (int) (dayCount * intPrice);
            Log.e("count is", String.valueOf(count));
            tvTotalPrice.setText("¥" + count);
        }
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.top_title);
        tvTitle.setText(roomName);
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        tvOPrice = (TextView) findViewById(R.id.tv_oprice);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvNetwork = (TextView) findViewById(R.id.tv_network);
        tvWindow = (TextView) findViewById(R.id.tv_window);
        tvPeopleNum = (TextView) findViewById(R.id.tv_prople_num);
        tvBathroom = (TextView) findViewById(R.id.tv_bathroom);
        tvRoomFloor = (TextView) findViewById(R.id.tv_floor);
        tvRoomArea = (TextView) findViewById(R.id.tv_area);
        tvBedSize = (TextView) findViewById(R.id.tv_bedsize);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        btnReserve = (Button) findViewById(R.id.btn_reserve_now);
        btnReserve.setOnClickListener(this);
        llDateChoose = (LinearLayout) findViewById(R.id.reserve_choose_date);
        llDateChoose.setOnClickListener(this);
        //日期
        tvInday = (TextView) findViewById(R.id.tv_inday);
        tvOutday = (TextView) findViewById(R.id.tv_outday);
        tvDayCount = (TextView) findViewById(R.id.tv_day_count);
        new HomepageRequest(this, handler).roomDetails(roomId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_reserve_now:
                Intent reserveIntent = new Intent(this, BookingNowActivity.class);
                reserveIntent.putExtra("bedsize",bedSize);
                reserveIntent.putExtra("network",network);
                reserveIntent.putExtra("textCheckIn",textChechIn);
                reserveIntent.putExtra("textCheckOut",textCheckOut);
                reserveIntent.putExtra("msInDay",msInDay);
                reserveIntent.putExtra("msOutDay",msOutDay);
                Log.e("room id isisisis",roomId);
                reserveIntent.putExtra("roomId",roomId);
                reserveIntent.putExtra("dayCount",dayCount);
                startActivity(reserveIntent);
                break;
            case R.id.reserve_choose_date:
                Intent intent = new Intent(this, CalenderMainActivity.class);
                startActivityForResult(intent, CODE);
                break;
        }
    }
}