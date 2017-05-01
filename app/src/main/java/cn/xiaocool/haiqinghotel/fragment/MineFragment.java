package cn.xiaocool.haiqinghotel.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.main.mine.MineSettingActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyOrderActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyRoomOrderActivity;
import cn.xiaocool.haiqinghotel.main.mine.MyShopOrderActivity;
import cn.xiaocool.haiqinghotel.utils.IntentUtils;

/**
 * Created by wzh on 2016/4/28.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private LinearLayout llRoomOrder,llShopOrder;
    private Context context;
    private ImageView ivSetting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
    }

    private void initView() {
        llRoomOrder = (LinearLayout) getView().findViewById(R.id.ll_mine_room_order);
        llRoomOrder.setOnClickListener(this);
        llShopOrder = (LinearLayout) getView().findViewById(R.id.ll_mine_shop_order);
        llShopOrder.setOnClickListener(this);
        ivSetting = (ImageView) getView().findViewById(R.id.mine_btn_setting);
        ivSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mine_room_order:
                IntentUtils.getIntent((Activity) context, MyOrderActivity.class);
                break;
            case R.id.ll_mine_shop_order:
                IntentUtils.getIntent((Activity) context, MyShopOrderActivity.class);
                break;
            case R.id.mine_btn_setting:
                IntentUtils.getIntent((Activity) context, MineSettingActivity.class);
                break;
        }
    }
}
