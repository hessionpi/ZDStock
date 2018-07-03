package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.ui.views.CircleIndicator;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.vp_splash)
    ViewPager vpSplash;
    List<View> imageList = new ArrayList<>();
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.indicator)
    CircleIndicator indicator;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        getImageList();
        vpSplash.setAdapter(new ViewPagerAdapter(imageList, this));
        indicator.setViewPager(vpSplash);
        //initDot();
        button.setOnClickListener(this);

        vpSplash.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                             @Override
                                             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                             }

                                             @Override
                                             public void onPageSelected(int position) {
                                                 if (position == imageList.size() - 1) {
                                                     button.setVisibility(View.VISIBLE);
                                                 } else {
                                                     button.setVisibility(View.GONE);
                                                 }
                                             }

                                             @Override
                                             public void onPageScrollStateChanged(int state) {

                                             }
                                         }
        );
    }

    //得到图片list
    void getImageList() {
        ImageView image1 = new ImageView(this);
        image1.setBackgroundResource(R.drawable.guide_page1);
        ImageView image2 = new ImageView(this);
        image2.setBackgroundResource(R.drawable.guide_page2);
        ImageView image3 = new ImageView(this);
        image3.setBackgroundResource(R.drawable.guide_page3);
        ImageView image4 = new ImageView(this);
        image4.setBackgroundResource(R.drawable.guide_page4);
        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        imageList.add(image4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                MainActivity.startActivity(GuideActivity.this);
                finish();
                break;
        }

    }

    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> views;

        public ViewPagerAdapter(List<View> views, Context context) {
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() { // 必写的方法 返回当前views的数量
            return this.views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) { //必写的方法 判断当前的view是否是我们需要的对象
            return (view == object);
        }
    }

}
