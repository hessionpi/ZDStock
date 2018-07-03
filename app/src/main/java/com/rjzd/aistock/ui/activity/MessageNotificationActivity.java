package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.Announcement;
import com.rjzd.aistock.api.AnnouncementData;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.NotificationPresenter;
import com.rjzd.aistock.ui.adapters.MessageNotificationAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.pull2refresh.PullToRefreshView;
import com.rjzd.aistock.view.IFillDataView;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息通知
 *
 * @author Hition at 2017-08-07
 */
public class MessageNotificationActivity extends BaseActivity implements IFillDataView,XMBaseAdapter.OnLoadMoreListener{

    @Bind(R.id.refresh_view)
    PullToRefreshView mRefreshView;
    @Bind(R.id.message_list)
    RecyclerView messageList;

    private NotificationPresenter presenter;
    private MessageNotificationAdapter adapter;
    private int userId;
    private int pageIndex = 0;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MessageNotificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notification);
        ButterKnife.bind(this);
        init();
        presenter = new NotificationPresenter(this);
        getNews();
    }

    private void init() {
        userId = UserInfoCenter.getInstance().getUserId();

        LinearLayoutManager layoutmanager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        messageList.setLayoutManager(layoutmanager1);
        adapter = new MessageNotificationAdapter(this);
        adapter.setMore(R.layout.view_recyclerview_more, this);
        adapter.setNoMore(R.layout.view_recyclerview_nomore);
        adapter.setError(R.layout.view_recyclerview_error);
        messageList.setAdapter(adapter);

        mRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 0;
                getNews();
            }
        });

        adapter.setOnItemClickListener(new XMBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Announcement announcement = adapter.getItem(position);
                if(0 == announcement.get_hasRead()){
                    announcement.set_hasRead(1);
                    adapter.notifyItemChanged(position);
                    presenter.setRead(userId,announcement.get_newsId());
                }
                WebActivity.startActivity(MessageNotificationActivity.this,announcement.get_url());
            }
        });
    }

    public void getNews(){
        presenter.getAnnouncement(userId,pageIndex,Constants.DEFAULT_PER_PAGE);
    }

    @Override
    public void fillData(Object data, int dsTag) {
        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
        }

        switch(dsTag){
            case Constants.DS_TAG_NOTIFICATION_ANNOUNCEMENT:
                AnnouncementData announcementData = (AnnouncementData) data;
                int totalPage = announcementData.get_totalPage();

                if(pageIndex < totalPage && StatusCode.OK.getValue() == announcementData.get_status().getValue()){
                    List<Announcement> announcements = announcementData.get_data();
                    if(null != announcements && !announcements.isEmpty()){
                        if(1 == totalPage){
                            adapter.clear();
                        }
                        adapter.addAll(announcements);
                    }
                }else{
                    adapter.stopMore();
                }
                break;
        }
    }

    @Override
    public void showFailedView() {

    }

    @Override
    public void onLoadMore() {
        pageIndex ++ ;
        getNews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
