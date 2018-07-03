package com.rjzd.aistock.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.PointsRecord;
import com.rjzd.aistock.api.PointsRecordData;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.presenter.PointsPresenter;
import com.rjzd.aistock.ui.adapters.PointsRecordAdapter;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.MyItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 积分记录
 * <p>
 * Created by Hition on 2017/7/20.
 */

public class RecordFragment extends BaseFragment implements XMBaseAdapter.OnLoadMoreListener {

    @Bind(R.id.rv_record)
    RecyclerView mRecord;
    @Bind(R.id.tv_empty)
    TextView mEmptyView;


    private int userId;
    private PointsPresenter presenter;
    private int pageIndex = 0;
    private int totalPage = 0;
    private PointsRecordAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_record, container, false);
        ButterKnife.bind(this, mView);
        initView();
        presenter = new PointsPresenter(this);
        return mView;
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (null != bundle) {
            userId = bundle.getInt("user_id");
        }

        MyItemDecoration decoration = new MyItemDecoration(activity, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#e6e6e6"));
        mRecord.addItemDecoration(decoration);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mRecord.setLayoutManager(layoutmanager);
        adapter = new PointsRecordAdapter(activity);
        mRecord.setAdapter(adapter);
        adapter.setMore(R.layout.view_recyclerview_more, this);
        adapter.setNoMore(R.layout.view_recyclerview_nomore);
        adapter.setError(R.layout.view_recyclerview_error);

    }

    @Override
    public void onResume() {
        super.onResume();
        pageIndex = 0;
        getData(pageIndex);
    }

    public void getData(int pageNo) {
        showLoadingDialog();
        presenter.getPointsRecord(userId, pageNo, Constants.DEFAULT_PER_PAGE);
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch (dsTag) {
            case Constants.DS_TAG_POINTS_RECORD:
                PointsRecordData recordData = (PointsRecordData) data;
                if (StatusCode.OK.getValue() == recordData.get_status().getValue()) {
                    List<PointsRecord> pointsRecords = recordData.get_data();
                    if (null != pointsRecords || !pointsRecords.isEmpty()) {
                        totalPage = recordData.get_totalPage();
                        if (totalPage == 0) {
                            mEmptyView.setVisibility(View.VISIBLE);
                        } else {
                            mEmptyView.setVisibility(View.GONE);
                            if (totalPage<pageIndex+1){
                                pointsRecords=null;
                            }
                            adapter.addAll(pointsRecords);
                        }
                    }
                }
                break;

            default:

                break;
        }

    }

    @Override
    public void showFailedView() {
        super.showFailedView();

    }

    @Override
    public void setStatistical() {
        statistical = "积分记录";
    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        getData(pageIndex);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }


}
