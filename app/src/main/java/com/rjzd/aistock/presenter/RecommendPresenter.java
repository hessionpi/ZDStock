package com.rjzd.aistock.presenter;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.PlateRecommendationData;
import com.rjzd.aistock.api.StockRecommendationData;
import com.rjzd.aistock.model.imp.RecommendModel;
import com.rjzd.aistock.view.IFillDataView;

/**
 * Created by Hition on 2017/8/9.
 */

public class RecommendPresenter extends BasePresenter {

    private IFillDataView mView;
    private RecommendModel model;

    public RecommendPresenter(IFillDataView view){
        this.mView = view;
        model = new RecommendModel(this);
    }

    public void getPlateRecommendation(int pageIndex,int perPage){
        addSubscription(model.getPlateRecommendation(pageIndex,perPage));
    }

    public void getStockRecommendation(int pageIndex,int perPage){
        addSubscription(model.getStockRecommendation(pageIndex,perPage));
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof PlateRecommendationData){
            mView.fillData(data, Constants.DS_TAG_RECOMMEND_PLATE);
        }else if(data instanceof StockRecommendationData){
            mView.fillData(data, Constants.DS_TAG_RECOMMEND_STOCK);
        }else{
            mView.fillData(data, Constants.DS_TAG_DEFAULT);
        }
    }

    @Override
    public void onFailed(Throwable e) {
        super.onFailed(e);
        mView.showFailedView();
    }
}
