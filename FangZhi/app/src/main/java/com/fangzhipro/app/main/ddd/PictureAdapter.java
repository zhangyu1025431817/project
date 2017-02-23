package com.fangzhipro.app.main.ddd;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fangzhipro.app.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PictureAdapter extends StaticPagerAdapter {

    private Context ctx;
    private ArrayList<Map<String,String>> list;
    List<ImageView> listView;
       public PictureAdapter(Context ctx , ArrayList<Map<String,String>>  list
       ,List<ImageView> listView){
           this.ctx = ctx;
           this.list = list;
           this.listView = listView;
        }
        @Override
        public View getView(ViewGroup container, final int position) {
//            View view =  LayoutInflater.from(ctx).inflate(R.layout.item_2d_picture,container,false);
            Map<String,String> map = list.get(position);
//            final ScaleImageView imageView = (ScaleImageView) view.findViewById(R.id.iv_image);
//            Glide.with(ctx)
//                    .load(map.get("url"))
//                    .asBitmap()
//                    .placeholder(R.drawable.bg_image_placeholder)
//                    .fitCenter()
//                    .into(imageView);
            ImageView imageView = listView.get(position);

            return imageView;
        }

        @Override
        public int getCount() {
            return list.size();
        }

    @Override
    public void onBind(View view, int position) {
        Map<String,String> map = list.get(position);
//        final ScaleImageView imageView = (ScaleImageView) view.findViewById(R.id.iv_image);
        Glide.with(ctx)
                .load(map.get("url"))
                .asBitmap()
                .placeholder(R.drawable.bg_image_placeholder)
                .fitCenter()
                .into((ImageView)view);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      //  container.removeView((View)object);
      //  unbindDrawables(container);
    }



    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
