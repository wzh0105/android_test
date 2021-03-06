package cn.xiaocool.haiqinghotel.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.adapter.ShopListAdapter;
import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.request.NetUtil;
import cn.xiaocool.haiqinghotel.net.request.ShopRequest;

/**
 * Created by wzh on 2016/4/28.
 */
public class EcshopFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private TextView tvTitle;
    private ListView listView;
    private ShopListAdapter shopListAdapter;
    private String [] name,id,price,pic,description;
    private ArrayList<HashMap<String, String>> arrayList;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommunalInterfaces.SHOP_LIST:
                    if (NetUtil.isConnnected(context)){
                        JSONObject jsonObject = (JSONObject) msg.obj;
                        try {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.e("jsonArray",jsonArray.toString());
                                int length = jsonArray.length();
                                name = new String[length];
                                id = new String[length];
                                price = new String[length];
                                pic = new String[length];
                                description = new String[length];
                                JSONObject dataObject;
                                for (int i = 0;i <length; i++){
                                    dataObject = (JSONObject) jsonArray.get(i);
                                    name[i] = dataObject.getString("name");
                                    id[i] = dataObject.getString("id");
                                    price[i] = dataObject.getString("price");
                                    pic[i] = dataObject.getString("picture");
                                    description[i] = dataObject.getString("description");
                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put("name",name[i]);
                                    hashMap.put("id",id[i]);
                                    hashMap.put("price",price[i]);
                                    hashMap.put("pic",pic[i]);
                                    hashMap.put("description",description[i]);
                                    arrayList.add(hashMap);
                                }
                                shopListAdapter = new ShopListAdapter(context,arrayList);
                                listView.setAdapter(shopListAdapter);
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
        View view = inflater.inflate(R.layout.fragment_ecshop, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
    }

    private void initView() {
        tvTitle = (TextView) getView().findViewById(R.id.top_title);
        tvTitle.setText(this.getString(R.string.shop_title));
        listView = (ListView) getView().findViewById(R.id.shop_good_list);
        new ShopRequest(context,handler).shopList();
        arrayList = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }
}

