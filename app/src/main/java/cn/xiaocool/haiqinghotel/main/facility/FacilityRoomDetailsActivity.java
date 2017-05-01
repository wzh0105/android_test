package cn.xiaocool.haiqinghotel.main.facility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.FacilityListClickAdapter;
import cn.xiaocool.haiqinghotel.net.request.FacilityRequest;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;

/**
 * Created by wzh on 2016/5/22.
 */
public class FacilityRoomDetailsActivity extends Activity implements View.OnClickListener {
    private String[] name, excerpt, pic;
    private FacilityListClickAdapter facilityListClickAdapter;
    private TextView tvTitle;
    private RelativeLayout btnExit;
    private ListView listView;
    private ArrayList<HashMap<String, String>> arrayList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.FACILITY_CLICK_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(FacilityRoomDetailsActivity.this)) {
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                int length = jsonArray.length();
                                JSONObject dataObject;
                                name = new String[length];
                                excerpt = new String[length];
                                pic = new String[length];
                                for (int i = 0; i < length; i++) {
                                    dataObject = (JSONObject) jsonArray.get(i);
                                    name[i] = dataObject.getString("title");
                                    excerpt[i] = dataObject.getString("excerpt");
                                    pic[i] = dataObject.getString("photo");
                                    Log.e("pic[]",pic[i]);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("name", name[i]);
                                    hashMap.put("excerpt", excerpt[i]);
                                    hashMap.put("pic", pic[i]);
                                    arrayList.add(hashMap);
                                }
                                facilityListClickAdapter = new FacilityListClickAdapter(FacilityRoomDetailsActivity.this
                                        , arrayList);
                                listView.setAdapter(facilityListClickAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.facility_item_click_);
        listView = (ListView) findViewById(R.id.facility_click_list);
        tvTitle = (TextView) findViewById(R.id.top_title);
        btnExit = (RelativeLayout) findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        String facilityId = intent.getStringExtra("facilityId");
        String name = intent.getStringExtra("name");
        tvTitle.setText(name);
        new FacilityRequest(this, handler).facilityClickList(facilityId);
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
