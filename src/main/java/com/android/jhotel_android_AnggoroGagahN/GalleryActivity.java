package com.android.jhotel_android_AnggoroGagahN;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mindorks.placeholderview.PlaceHolderView;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);
        PlaceHolderView mGalleryView = findViewById(R.id.galleryView);
        mGalleryView
                .addView(new GalleryItem(getResources().getDrawable(R.drawable.img_3,null)))
                .addView(new GalleryItem(getResources().getDrawable(R.drawable.img_4,null)))
                .addView(new GalleryItem(getResources().getDrawable(R.drawable.img_5,null)))
                .addView(new GalleryItem(getResources().getDrawable(R.drawable.img_6,null)))
                .addView(new GalleryItem(getResources().getDrawable(R.drawable.img_7,null)))
                .addView(new GalleryItem(getResources().getDrawable(R.drawable.img_8,null)))
                .addView(new GalleryItem(getResources().getDrawable(R.drawable.img_9,null)));
    }
}
