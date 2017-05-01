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
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by wzh on 2016/5/22.
 */
public class FacilityListClickAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public FacilityListClickAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.mipmap.default_loading).showImageOnFail(R.mipmap.default_loading)
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
        convertView = layoutInflater.inflate(R.layout.facility_item_click_item, null);
        String picName = arrayList.get(position).get("pic");
        ImageView imageView = (ImageView) convertView.findViewById(R.id.facility_iv_itemPic);
        TextView tvName = (TextView) convertView.findViewById(R.id.facility_tv_itemName);
        TextView tvExcerpt = (TextView) convertView.findViewById(R.id.facility_tv_itemExcerpt);
        tvName.setText(arrayList.get(position).get("name"));
        tvExcerpt.setText(arrayList.get(position).get("excerpt"));
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + picName, imageView, displayImageOptions);
        return convertView;
    }
}
