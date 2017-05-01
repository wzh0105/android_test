package cn.xiaocool.haiqinghotel.bean;

import android.content.Context;
import android.util.Log;

/**
 * Created by wzh on 2016/5/7.
 */
public class UserInfo {
    String userID;
    private Context context;
    public UserInfo(Context context){
        super();
        this.context = context;
    }
    public void userUID(String id) {
        this.userID = id;
        Log.e("userUID write success",this.userID);
    }
}