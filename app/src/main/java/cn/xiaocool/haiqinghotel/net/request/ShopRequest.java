package cn.xiaocool.haiqinghotel.net.request;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.haiqinghotel.dao.CommunalInterfaces;
import cn.xiaocool.haiqinghotel.net.constant.WebAddress;

/**
 * Created by wzh on 2016/5/7.
 */
public class ShopRequest {
    private Context mContext;
    private Handler handler;

    public ShopRequest(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.handler = handler;
    }

    //获取商城列表
    public void shopList() {
        new Thread() {
            Message msg = Message.obtain();

            public void run() {
                String data = "";
                String result_data = NetUtil.getResponse(WebAddress.SHOP_LIST, data);
                try {
                    JSONObject obj = new JSONObject(result_data);
                    Log.e("shoplist is",result_data);
                    msg.what = CommunalInterfaces.SHOP_LIST;
                    msg.obj = obj;
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
}
