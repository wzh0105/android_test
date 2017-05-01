package cn.xiaocool.haiqinghotel.fragment;

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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.FacilityListAdapter;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.main.facility.FacilityRoomDetailsActivity;
import cn.xiaocool.haiqinghotel.net.request.FacilityRequest;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;

/**
 * Created by wzh on 2016/4/28.
 */
public class FacilityFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ListView listView;
    private ArrayList<HashMap<String, String>> arrayList;
    private TextView tvTitle;
    private RelativeLayout btnExit;
    private String[]  id ,name, pic;
    private FacilityListAdapter facilityListAdapter;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.FACILITY_LIST:
                    JSONObject jsonObject = (JSONObject) msg.obj;
                    if (NetUtil.isConnnected(context)) {
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                int length = jsonArray.length();
                                JSONObject dataObject;
                                name = new String[length];
                                pic = new String[length];
                                id = new String[length];
                                for (int i = 0; i < length; i++) {
                                    dataObject = (JSONObject) jsonArray.get(i);
                                    name[i] = dataObject.getString("name");
                                    pic[i] = dataObject.getString("picture");
                                    id[i] = dataObject.getString("id");
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("name", name[i]);
                                    hashMap.put("pic", pic[i]);
                                    hashMap.put("id", id[i]);
                                    arrayList.add(hashMap);
                                }
                                facilityListAdapter = new FacilityListAdapter(context, arrayList);
                                listView.setAdapter(facilityListAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facility, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        new FacilityRequest(context, handler).facilityList();
        setOnItemClick();
    }

    private void setOnItemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap= (HashMap<String, Object>) facilityListAdapter.getItem(position);
                String facilityId = (String) hashMap.get("id");
                String name = (String) hashMap.get("name");
                Intent intent = new Intent();
                intent.putExtra("facilityId",facilityId);
                intent.putExtra("name",name);
                Log.e("facility id is ",facilityId);
                intent.setClass(context, FacilityRoomDetailsActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initView() {
        arrayList = new ArrayList<>();
        listView = (ListView) getView().findViewById(R.id.facility_listview);
        tvTitle = (TextView) getView().findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.facility_title));
        btnExit = (RelativeLayout) getView().findViewById(R.id.btn_back);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
