package com.rjzd.aistock.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.api.AITransfer;
import com.rjzd.aistock.api.DateTransfer;
import com.rjzd.aistock.api.TransferType;
import com.rjzd.aistock.ui.activity.StockDetailsActivity;
import com.rjzd.aistock.ui.adapters.recycleadapter.BaseViewHolder;
import com.rjzd.aistock.ui.adapters.recycleadapter.XMBaseAdapter;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DateUtil;
import com.rjzd.aistock.utils.view.DialogManager;
import com.rjzd.commonlib.utils.PixelUtil;

import java.util.List;



/**
 * Created by Administrator on 2017/3/17.
 */

public class AIAdjustdynamicAdapter extends XMBaseAdapter<DateTransfer> {

    public AIAdjustdynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new AIAdjustdynamicHolder(parent, R.layout.item_recycle_ai_adjustdynamic);
    }

    private class AIAdjustdynamicHolder extends BaseViewHolder<DateTransfer> {

        private TextView tv_time;
        private LinearLayout ll_adjustdynamic;

        public AIAdjustdynamicHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);

            tv_time = $(R.id.tv_time);
            ll_adjustdynamic = $(R.id.ll_adjustdynamic);
        }

        @Override
        public void setData(final DateTransfer data) {
            tv_time.setText(data.get_actionTime());
            List<AITransfer> transferActionList = data.get_transfers();
            for(final AITransfer transferAction: transferActionList){
                LinearLayout hlayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams hlayoutLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,PixelUtil.dp2px(48));
                hlayout.setLayoutParams(hlayoutLP);
                hlayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout vlayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams vlayoutLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,PixelUtil.dp2px(47));
                vlayoutLP.gravity=Gravity.CENTER_VERTICAL;
                vlayout.setLayoutParams(vlayoutLP);

                //图片,时间
                LinearLayout imageLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams imageLLLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageLLLP.setMargins(PixelUtil.dp2px(10),0,0,0);
                imageLLLP.gravity= Gravity.CENTER_VERTICAL;
                imageLayout.setLayoutParams(imageLLLP);
                imageLayout.setOrientation(LinearLayout.VERTICAL);


                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams imgLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imgLP.setMargins(0,0,0,PixelUtil.dp2px(5));
                imgLP.gravity= Gravity.CENTER_HORIZONTAL;
                imageView.setLayoutParams(imgLP);
                if (transferAction.get_type() == TransferType.SELL){
                    imageView.setImageResource(R.drawable.ai_sell);
                }else {
                    imageView.setImageResource(R.drawable.ai_buy);
                }

                TextView timeView = new TextView(mContext);
                LinearLayout.LayoutParams timeLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                timeLP.setMargins(0,0,0,0);
                timeView.setLayoutParams(timeLP);
                timeView.setTextColor(Color.parseColor("#999999"));
                timeView.setTextSize(9);
                timeView.setText(DateUtil.getShortDateJustHour(DateUtil.parser24(transferAction.get_actionTime()).getTime()/1000));

                imageLayout.addView(imageView);
                imageLayout.addView(timeView);


                //名称,代码
                LinearLayout nameLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams nameLLLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(80), LinearLayout.LayoutParams.WRAP_CONTENT);
                nameLLLP.setMargins(PixelUtil.dp2px(10),0,0,0);
                nameLLLP.gravity= Gravity.CENTER_VERTICAL;
                nameLayout.setLayoutParams(nameLLLP);
                nameLayout.setOrientation(LinearLayout.VERTICAL);

                TextView nameView = new TextView(mContext);
                LinearLayout.LayoutParams nameLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                nameLP.setMargins(0,0,0,PixelUtil.dp2px(5));
                nameView.setLayoutParams(nameLP);
                nameView.setTextColor(Color.parseColor("#333333"));
                nameView.setTextSize(14);
                nameView.setText(transferAction.get_stockName());

                TextView codeView = new TextView(mContext);
                LinearLayout.LayoutParams codeLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                codeLP.setMargins(0,0,0,0);
                codeView.setLayoutParams(codeLP);
                codeView.setTextColor(Color.parseColor("#888888"));
                codeView.setTextSize(10);
                codeView.setText(transferAction.get_stockCode());

                nameLayout.addView(nameView);
                nameLayout.addView(codeView);

                //价格，比例
                LinearLayout priceLayout = new LinearLayout(mContext);
                LinearLayout.LayoutParams priceLLLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(60), LinearLayout.LayoutParams.WRAP_CONTENT,1f);
                priceLLLP.setMargins(0,0,0,0);
                priceLLLP.gravity= Gravity.CENTER_VERTICAL;
                priceLayout.setLayoutParams(priceLLLP);
                priceLayout.setOrientation(LinearLayout.VERTICAL);
                priceLayout.setGravity(Gravity.CENTER_VERTICAL);

                TextView priceView = new TextView(mContext);
                LinearLayout.LayoutParams priceLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                priceLP.setMargins(0,0,0,0);
                priceView.setLayoutParams(priceLP);
                priceView.setTextColor(Color.parseColor("#333333"));
                priceView.setTextSize(14);
                if (transferAction.get_type() == TransferType.SELL){
                    priceView.setText(transferAction.get_sellPrice()+"成交");
                }else {
                    priceView.setText(transferAction.get_buyPrice()+"成交");
                }


                TextView rangeView = new TextView(mContext);
                LinearLayout.LayoutParams rangeLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rangeLP.setMargins(0,0,0,0);
                rangeView.setLayoutParams(rangeLP);
                rangeView.setTextColor(Color.parseColor("#888888"));
                rangeView.setTextSize(8);
                if (transferAction.get_type() == TransferType.SELL){
                    rangeView.setText(String.format("%.1f", transferAction.get_positionPercentage()) + "%—>0%");
                }else {
                    rangeView.setText("0%—>" + String.format("%.1f", transferAction.get_positionPercentage()) + "%");
                }
                priceLayout.addView(priceView);
                priceLayout.addView(rangeView);

                // 盈亏
                TextView mProfitLossView = new TextView(mContext);
                LinearLayout.LayoutParams profitLossLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
                profitLossLP.gravity=Gravity.CENTER_VERTICAL;
                mProfitLossView.setLayoutParams(profitLossLP);
                mProfitLossView.setText(transferAction.get_profitPercentage()+"%");
                if (transferAction.get_profitPercentage()<0){
                    mProfitLossView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_119d3e));
                }else{
                    mProfitLossView.setTextColor(ContextCompat.getColor(mContext,R.color.cl_f95555));
                }

                //策略
                TextView reasonView = new TextView(mContext);
                LinearLayout.LayoutParams reasonLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(40),PixelUtil.dp2px(20),1f);
                reasonLP.gravity=Gravity.RIGHT|Gravity.CENTER_VERTICAL;
                reasonLP.setMargins(0,0,PixelUtil.dp2px(10),0);
                reasonView.setLayoutParams(reasonLP);
                reasonView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_blue_bg));
                reasonView.setTextColor(Color.parseColor("#1e82d2"));
                reasonView.setGravity(Gravity.CENTER);
                reasonView.setTextSize(11);
                reasonView.setText("调仓策略");
                reasonView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogManager.showAiStrategyDialog(mContext, "      "+transferAction.get_actionReason(), true);
                    }
                });

                vlayout.addView(imageLayout);
                vlayout.addView(nameLayout);
                vlayout.addView(priceLayout);
                vlayout.addView(mProfitLossView);
                vlayout.addView(reasonView);
                hlayout.addView(vlayout);

                //分割线
                View view = new View(mContext);
                LinearLayout.LayoutParams viewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,PixelUtil.dp2px(0.5f));
                view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.cl_e6e6e6));
                if(transferActionList.indexOf(transferAction) < transferActionList.size()-1){
                    viewLP.setMargins(PixelUtil.dp2px(10),0,PixelUtil.dp2px(10),0);
                }
                view.setLayoutParams(viewLP);
                hlayout.addView(view);
                ll_adjustdynamic.addView(hlayout);
                hlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StockDetailsActivity.startActivity(mContext,transferAction.get_stockCode(),transferAction.get_stockName());
                    }
                });
            }

        }

    }
}


