package cn.xiaocool.haiqinghotel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by wzh on 2016/5/8.
 */
public class HomeOnsaleListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, Object>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HomeOnsaleListAdapter(Context context, ArrayList<HashMap<String, Object>> arrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
//        displayImageOptions = new DisplayImageOptions.Builder()
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
//                .cacheInMemory(true).cacheOnDisc(true).build();
        displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_loading)
                .showImageOnFail(R.mipmap.default_loading)
                .cacheInMemory(true).cacheOnDisc(true).build();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局,绑定组件
        convertView = layoutInflater.inflate(R.layout.home_onsale_list_item, null);
        ImageView onsalePic = (ImageView) convertView.findViewById(R.id.home_onsale_pic);
        TextView onsaleName = (TextView) convertView.findViewById(R.id.home_onsale_name);
//        TextView onsaleIntro = (TextView) convertView.findViewById(R.id.home_onsale_intro);
        TextView onsalePrice = (TextView) convertView.findViewById(R.id.home_onsale_price);
        String picName = arrayList.get(position).get("picName").toString();
        onsaleName.setText(arrayList.get(position).get("name").toString());
//        onsaleIntro.setText(arrayList.get(position).get("intro").toString());
        onsalePrice.setText(arrayList.get(position).get("price").toString());
//        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picName, onsalePic, displayImageOptions);
        return convertView;
    }
}
