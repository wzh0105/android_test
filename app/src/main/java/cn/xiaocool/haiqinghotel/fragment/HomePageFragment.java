package cn.xiaocool.haiqinghotel.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.HomeOnsaleListAdapter;
import cn.xiaocool.haiqinghotel.main.homepage.CalenderMainActivity;
import cn.xiaocool.haiqinghotel.main.homepage.CateringIntroActivity;
import cn.xiaocool.haiqinghotel.main.homepage.ContactUsActivity;
import cn.xiaocool.haiqinghotel.main.homepage.HomeReserveNowActivity;
import cn.xiaocool.haiqinghotel.main.homepage.RoomIntroActivity;
import cn.xiaocool.haiqinghotel.net.request.HomepageRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;

/**
 * Created by wzh on 2016/4/28.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout btnLocation, btnContact, btn_Details, rlInday, rlOutday;
    private Context context;
    private ListView onsaleList;
    private Button btnReserveNow;
    private TextView tvInday, tvOutday;
    private HomeOnsaleListAdapter homeOnsaleListAdapter;
    private String[] picName, name, intro, price, id, type;
    private long msInDay, msOutDay;
    private ArrayList<HashMap<String, Object>> arrayList;
    private String textInday;
    private String textOutday;
//    private long dayCount;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.ONSALE_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    Log.e("jsonObject 获取成功", jsonObject.toString());
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                                JSONObject object;
                                int length = jsonArray.length();
                                picName = new String[length];
                                name = new String[length];
                                intro = new String[length];
                                price = new String[length];
                                id = new String[length];
                                type = new String[length];
                                for (int i = 0; i < length; i++) {
                                    object = (JSONObject) jsonArray.get(i);
                                    picName[i] = object.getString("picture");
                                    name[i] = object.getString("name");
                                    intro[i] = object.getString("type");
                                    price[i] = object.getString("price");
                                    id[i] = object.getString("id");
                                    type[i] = object.getString("type");
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("picName", picName[i]);
                                    hashMap.put("name", name[i]);
                                    hashMap.put("intro", intro[i]);
                                    hashMap.put("price", price[i]);
                                    hashMap.put("id", id[i]);
                                    hashMap.put("type", type[i]);
                                    arrayList.add(hashMap);
                                }
                                homeOnsaleListAdapter = new HomeOnsaleListAdapter(context, arrayList);
                                onsaleList.setAdapter(homeOnsaleListAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "网络连接有问题", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    private long dayCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        new HomepageRequest(context, handler).onsaleList();
        setItemOnclick();
    }

    private void setItemOnclick() {
        onsaleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> intentMap = (HashMap<String, Object>) homeOnsaleListAdapter.getItem(position);
                String goodId = (String) intentMap.get("id");
                String goodName = (String) intentMap.get("name");
                String type = (String) intentMap.get("type");
                if (type.equals("1")) {
                    Intent intent = new Intent();
                    intent.setClass(context, RoomIntroActivity.class);
                    intent.putExtra("roomId", goodId);
                    intent.putExtra("roomName", goodName);
                    startActivity(intent);
                } else {
//                    Toast.makeText(context,"跳转餐饮",Toast.LENGTH_SHORT).show();//此处须跳转餐饮activity
                    if (type.equals("2")) {
                        Intent intent = new Intent();
                        intent.setClass(context, CateringIntroActivity.class);
                        intent.putExtra("cateringId", goodId);
                        intent.putExtra("cateringName", goodName);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void initView() {
        btnLocation = (RelativeLayout) getView().findViewById(R.id.home_btn_location);
        btnLocation.setOnClickListener(this);
        btnContact = (RelativeLayout) getView().findViewById(R.id.home_btn_contact_us);
        btnContact.setOnClickListener(this);
        btn_Details = (RelativeLayout) getView().findViewById(R.id.home_btn_details);
        btn_Details.setOnClickListener(this);
        onsaleList = (ListView) getView().findViewById(R.id.home_onsale_list);
        arrayList = new ArrayList<>();
        btnReserveNow = (Button) getView().findViewById(R.id.home_btn_reserve_now);
        btnReserveNow.setOnClickListener(this);
        rlInday = (RelativeLayout) getView().findViewById(R.id.home_rl_inputDate);
        rlInday.setOnClickListener(this);
        rlOutday = (RelativeLayout) getView().findViewById(R.id.home_rl_outputDate);
        rlOutday.setOnClickListener(this);
        tvInday = (TextView) getView().findViewById(R.id.tv_inday);
        tvOutday = (TextView) getView().findViewById(R.id.tv_outday);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.e("this daycount", "111111");
            String indayMonNum = data.getStringExtra("inDayMonth1");
            String indayDayNum = data.getStringExtra("inDayNum0");
            String outdayMonNum = data.getStringExtra("outMonth1");
            String outdayDayNum = data.getStringExtra("outDayNum0");
//            dayCount = data.getLongExtra("dayCount",1);
            dayCount = data.getLongExtra("dayCount",1);
            Log.e("this daycount", String.valueOf(dayCount));
            msInDay = data.getLongExtra("msInDate", 0);
            msOutDay = data.getLongExtra("msOutDate", 0);
            Log.e("ms in out day is", String.valueOf(msInDay + "bbb" + msOutDay));
            Log.e("五个数：", indayMonNum + indayDayNum + outdayMonNum + outdayDayNum + "ccc" + dayCount);
            textInday = indayMonNum + "月" + indayDayNum + "日";
            textOutday = outdayMonNum + "月" + outdayDayNum + "日";
            tvInday.setText(textInday);
            tvOutday.setText(textOutday);
//            textChechIn = "入住：" + indayMonNum + "月" + indayDayNum + "日";
//            tvInday.setText(textChechIn);
//            textCheckOut = "离店：" + outdayMonNum + "月" + outdayDayNum + "日";
//            tvOutday.setText(textCheckOut);
//            tvDayCount.setText("共" + dayCount + "晚");
//            int intPrice = Integer.parseInt(price);
//            int count = (int) (dayCount * intPrice);
//            Log.e("count is", String.valueOf(count));
//            tvTotalPrice.setText("" + count);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_contact_us:
                IntentUtils.getIntent((Activity) context, ContactUsActivity.class);
                break;
            case R.id.home_btn_reserve_now:
                Intent intent2 = new Intent();
                intent2.setClass(context,HomeReserveNowActivity.class);
                intent2.putExtra("msInDay", msInDay);
                intent2.putExtra("msOutDay", msOutDay);
                intent2.putExtra("textCheckIn","入住：" + textInday);
                intent2.putExtra("textCheckOut","离店" + textOutday);
                intent2.putExtra("roomId",id);
                intent2.putExtra("dayCount",dayCount);
                startActivity(intent2);
//                IntentUtils.getIntent((Activity) context, HomeReserveNowActivity.class);
                break;
            case R.id.home_rl_inputDate:
                Intent intent0 = new Intent(context, CalenderMainActivity.class);
                startActivityForResult(intent0, 1);
                break;
            case R.id.home_rl_outputDate:
                Intent intent1 = new Intent();
        }
    }
}
