package com.rjzd.aistock.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AIAttention;
import com.rjzd.aistock.api.AIInfo;
import com.rjzd.aistock.api.AIList;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.AIPresenter;
import com.rjzd.aistock.ui.activity.AIActivity;
import com.rjzd.aistock.ui.activity.InviteFriendsActivity;
import com.rjzd.aistock.ui.activity.LoginActivity;
import com.rjzd.aistock.ui.activity.MyPointsActivity;
import com.rjzd.aistock.ui.adapters.AiAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.CircleIndicator;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.PixelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.rjzd.aistock.Constants.DS_TAG_AI_ATTENTION_GET;


/**
 * Created by Hition on 2016/12/22.
 * <p>
 * AI投顾  fragment
 */

public class AIFragment extends BaseFragment {

    public static final String TAG = "AIFragment";

    @Bind(R.id.rl_ai)
    RecyclerView rlAi;
    List<View> imageList = new ArrayList<>();
    private AIPresenter aiPresenter;
    private AiAdapter adapter;
    private int currentItem; //当前页面
    private ScheduledExecutorService scheduledExecutorService;
    LinearLayoutManager linearLayoutManager;
    ViewPager vpAi;
    int userId;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //设置当前页面
            vpAi.setCurrentItem(currentItem);
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ai, container, false);
        ButterKnife.bind(this, view);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //每隔5秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);
        aiPresenter = new AIPresenter(this);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        userId = UserInfoCenter.getInstance().getUserId();
        if (userId == 0) {
            getAIData();
        } else {
            aiPresenter.getAttentionAIs(userId);
            LogUtil.e("guanzhi");
        }
    }

    private void getAIData() {
        if (NetWorkUtil.isNetworkConnected(activity)) {
            aiPresenter.getAllRobots();
            showLoadingDialog();
        } else {
            activity.showToast(R.string.no_network);
        }
    }

    private void init() {


        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        rlAi.setLayoutManager(linearLayoutManager);
        adapter = new AiAdapter(activity);
        AIHeaderView headerView = new AIHeaderView();
        adapter.addHeader(headerView);
        AIFooterView footerView = new AIFooterView();
        adapter.addFooter(footerView);
        rlAi.setAdapter(adapter);

        adapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                    AIInfo mInfo = adapter.getItem(position);
                    StringBuilder characters = new StringBuilder();
                    if (!mInfo.get_characteristics().isEmpty()) {
                        for (String cha : mInfo.get_characteristics()) {
                            characters.append(cha + "     ");
                        }
                    }
                    AIActivity.startActivity(activity, mInfo.get_id(), mInfo.get_avatar(), mInfo.get_aliasName(),mInfo.get_name(), mInfo.get_onlineTime(), characters.toString(), mInfo.get_introduce());
            }
        });


    }

    //得到图片list
    void getImageList() {
        ImageView image1 = new ImageView(activity);
        image1.setBackgroundResource(R.drawable.ai_banner1);
        ImageView image2 = new ImageView(activity);
        image2.setBackgroundResource(R.drawable.ai_banner2);
        imageList.add(image1);
        imageList.add(image2);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {

            if (userId == 0) {
                getAIData();
            } else {
                aiPresenter.getAttentionAIs(userId);
            }
        }
    }

    @Override
    public void setStatistical() {
        statistical = "ai_fragment";
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch (dsTag) {
            case Constants.DS_TAG_AI_INFO:
                AIList aiInfoList = (AIList) data;
                List<AIInfo> aiList = aiInfoList.get_data();
                adapter.setData(aiList);
                adapter.notifyDataSetChanged();

                break;
            case DS_TAG_AI_ATTENTION_GET:
                AIAttention aiAttention = (AIAttention) data;
                List<String> idList = aiAttention.get_data();
                adapter.setIdList(idList);
                getAIData();
                break;
            default:
                if (null == data) {
                    showFailedView();
                }
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        aiPresenter.onUnsubscribe();
    }

    private class AIHeaderView implements XMBaseAdapter.ItemView {

        @Override
        public View onCreateView(ViewGroup parent) {
            View header = LayoutInflater.from(activity).inflate(R.layout.layout_ai_header, null);
            vpAi = (ViewPager) header.findViewById(R.id.vp_ai);
            CircleIndicator indicator = (CircleIndicator) header.findViewById(R.id.indicator);
            getImageList();
            vpAi.setAdapter(new ViewPagerAdapter(imageList, activity));
            indicator.setViewPager(vpAi);
            return header;

        }

        @Override
        public void onBindView(View footerView) {

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
            public Object instantiateItem(ViewGroup container, final int position) {
                container.addView(views.get(position));
                View view=views.get(position);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      LoginBean login = UserInfoCenter.getInstance().getLoginModel();
                        if (position==0){
                            if (null == login) {
                                LoginActivity.startActivity(activity);
                            } else {
                                InviteFriendsActivity.startActivity(activity);
                            }
                        }else{
                            if (null == login) {
                                LoginActivity.startActivity(activity);
                            } else {
                                MyPointsActivity.startActivity(activity, MyPointsActivity.MY_TASK);
                            }

                        }
                    }
                });




                return view;
            }

            @Override
            public int getCount() {
                return this.views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) { //必写的方法 判断当前的view是否是我们需要的对象
                return (view == object);
            }
        }

    }

    private class AIFooterView implements XMBaseAdapter.ItemView {

        @Override
        public View onCreateView(ViewGroup parent) {
            TextView footer = new TextView(activity);
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, PixelUtil.dp2px(41));
            footer.setLayoutParams(params);
            footer.setGravity(Gravity.CENTER);
            footer.setText(R.string.ai_explain);
            footer.setTextSize(10);
            footer.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
            return footer;
        }

        @Override
        public void onBindView(View footerView) {

        }
    }

    //切换图片
    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageList.size();
            handler.obtainMessage().sendToTarget();
        }

    }
}
