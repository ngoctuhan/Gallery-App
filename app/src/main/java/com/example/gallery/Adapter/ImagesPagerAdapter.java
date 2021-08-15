package com.example.gallery.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gallery.Model.Picture;
import com.example.gallery.R;

import java.util.List;

public class ImagesPagerAdapter extends PagerAdapter {

    Context context;
    private List<Picture> allImages;

    public ImagesPagerAdapter(  Context context, List<Picture> allImages) {
        this.context = context;
        this.allImages = allImages;
    }

    @Override
    public int getCount() {
        return allImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup containerCollection, int position) {
        LayoutInflater layoutinflater = (LayoutInflater) containerCollection.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutinflater.inflate(R.layout.picture_browser_pager,null);
        ImageView image = view.findViewById(R.id.image);

        image.setImageBitmap(BitmapFactory.decodeFile(allImages.get(position).getPicturePath()));

//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(indicatorRecycler.getVisibility() == View.GONE){
//                    indicatorRecycler.setVisibility(View.VISIBLE);
//                }else{
//                    indicatorRecycler.setVisibility(View.GONE);
//                }
//
//            }
//        });
        ((ViewPager) containerCollection).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup containerCollection, int position, Object view) {
        ((ViewPager) containerCollection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }
}

