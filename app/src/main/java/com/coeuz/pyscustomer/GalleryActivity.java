package com.coeuz.pyscustomer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import junit.framework.Assert;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class GalleryActivity extends AppCompatActivity {
    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";

    private ArrayList<Bitmap> _images =new ArrayList<>();

    @InjectView(R.id.pager)
    ViewPager _pager;
    @InjectView(R.id.thumbnails) LinearLayout _thumbnails;
    @InjectView(R.id.btn_close) ImageButton _closeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.inject(this);
        byte[] byteArray = getIntent().getByteArrayExtra("image");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        _images.add(bmp);
      // _images= getIntent().getParcelableArrayListExtra(EXTRA_NAME);

     // _images = (ArrayList<Integer>) getIntent().getSerializableExtra(EXTRA_NAME);
        Assert.assertNotNull(_images);
      //  _images.add();

        GalleryPagerAdapter _adapter = new GalleryPagerAdapter(this);
        _pager.setAdapter(_adapter);
        _pager.setOffscreenPageLimit(6); // how many images to load into memory

        _closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    class GalleryPagerAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;

        GalleryPagerAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return _images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ( object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            // Get the border size to show around each image
            int borderSize = _thumbnails.getPaddingTop();
            
            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize*2);
            
            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);

            // You could also set like so to remove borders
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            //        ViewGroup.LayoutParams.WRAP_CONTENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);
            
            final ImageView thumbView = new ImageView(_context);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // Set the pager position when thumbnail clicked
                    _pager.setCurrentItem(position);
                }
            });
            _thumbnails.addView(thumbView);
            final SubsamplingScaleImageView imageView = itemView.findViewById(R.id.image);

            // Asynchronously load the image and set the thumbnail and pager view
            Glide.with(_context)
                    .load(_images.get(position))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                            thumbView.setImageBitmap(bitmap);
                        }
                    });

            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
