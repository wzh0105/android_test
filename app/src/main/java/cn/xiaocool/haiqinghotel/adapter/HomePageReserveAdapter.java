package cn.xiaocool.haiqinghotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.HashMap;

import cn.xiaocool.haiqinghotel.R;
import cn.xiaocool.haiqinghotel.main.homepage.BookingNowActivity;
import cn.xiaocool.haiqinghotel.net.constant.NetBaseConstant;

/**
 * Created by wzh on 2016/5/18.
 */
public class HomePageReserveAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, String>> arrayList;
    private DisplayImageOptions displayImageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;
    private String bedsize;
    private String repast;
    private String acreage;
    private String name;
    private String price;
    private String oprice;
    private String adtitle;
    private String inDay,outDay;
    private long msInDay,msOutDay,dayCount;
    private String id;

    public HomePageReserveAdapter(Context context, ArrayList<HashMap<String, String>> arrayList,
                                  String inDay,String outDay,long msInDay,long msOutDay,long dayCount) {
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.context = context;
        this.inDay = inDay;
        this.outDay = outDay;
        this.msInDay = msInDay;
        this.msOutDay = msOutDay;
        this.dayCount = dayCount;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.homepage_reserve_item, null);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
        holder.btnHomeReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("11111","2222");
                getBundle(position,"id",BookingNowActivity.class,"客房预订");

            }
        });




        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_home_reserve);
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_home_roomName);
        TextView tvRepast = (TextView) convertView.findViewById(R.id.tv_home_repast);
        TextView tvInfor = (TextView) convertView.findViewById(R.id._tv_home_reserve_infor);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_home_reserve_price);
        TextView tvOprice = (TextView) convertView.findViewById(R.id.tv_home_reserve_oprice);
        String pic = arrayList.get(position).get("pic").toString();
        name = arrayList.get(position).get("name");
        id = arrayList.get(position).get("id");
        price = arrayList.get(position).get("price");
        oprice = arrayList.get(position).get("oprice");
        acreage = arrayList.get(position).get("acreage");
        repast = arrayList.get(position).get("repast");
        bedsize = arrayList.get(position).get("bedsize");
        adtitle = arrayList.get(position).get("adtitle");
        String floor = arrayList.get(position).get("floor");
        tvName.setText(name);
        tvPrice.setText(price);
        tvOprice.setText(oprice);
        tvInfor.setText(acreage + bedsize + adtitle + floor);
        if (repast.equals("1")) {
            tvRepast.setText("有早餐");
        } else if (repast.equals("2")) {
            tvRepast.setText("无早餐");
        }
        imageLoader.displayImage(NetBaseConstant.NET_PIC_PREFIX + pic, imageView, displayImageOptions);
        return convertView;
    }
    @SuppressWarnings("rawtypes")
    public void getBundle(final int position, String key, Class clazz, String str) {
//        Car.CarData cardataInfo = carDataList.get(position);
        Bundle bundle = new Bundle();
//        bundle.putSerializable(key, cardataInfo);
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        intent.putExtra("bedsize",bedsize);
        intent.putExtra("network","wifi");
        intent.putExtra("name",name);
        intent.putExtra("price",price);
        intent.putExtra("textCheckIn",inDay);
        intent.putExtra("textCheckOut",outDay);
        intent.putExtra("roomId",id);
        intent.putExtra("msInDay",id);
        intent.putExtra("msOutDay",id);
        intent.putExtra("dayCount",dayCount);
        context.startActivity(intent);
    }
    class ViewHolder{
        //            TextView car_number,car_brand;
        Button btnHomeReserve;
        //            ImageView car_editor;
        public ViewHolder(View convertView) {
            btnHomeReserve = (Button) convertView.findViewById(R.id.home_item_btn_reserve);
        }
    }
}
