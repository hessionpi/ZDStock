package com.rjzd.aistock.ui.adapters;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.StockBasic;
import com.rjzd.aistock.ui.activity.BaseActivity;
import com.rjzd.aistock.ui.activity.ExponentDetailsActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/23.
 */

public class ExponentPagerAdapter extends PagerAdapter {

    private List<StockBasic> zixuans;
    private BaseActivity activity;

    public ExponentPagerAdapter(List<StockBasic> zixuanList, BaseActivity activity) {
        zixuans = zixuanList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_exponent, null);
        setView(view, position);
        container.addView(view);
        return view;
    }

    private void setView(View view, int position) {
        LinearLayout exponent1 = ButterKnife.findById(view, R.id.exponent1);
        TextView exponentName1 = ButterKnife.findById(view, R.id.exponent_name1);
        TextView price1 = ButterKnife.findById(view, R.id.price1);
        TextView range1 = ButterKnife.findById(view, R.id.range1);
        TextView rangeRate1 = ButterKnife.findById(view, R.id.range_rate1);
        exponentName1.setText(zixuans.get(position % 2 * 3).getName());
        if(zixuans.get(position % 2 * 3).getLatestPrice()>0){
            price1.setText(String.format("%.2f", zixuans.get(position % 2 * 3).getLatestPrice()));
            if(zixuans.get(position % 2 * 3).getOpen()>0){
                double rangeCount1 = zixuans.get(position % 2 * 3).getLatestPrice() * zixuans.get(position % 2 * 3).getRange();
                range1.setText(String.format("%.2f", rangeCount1));
               // range1.setText(zixuans.get(position % 2 * 3).getRange()+"%");
            }else{
                range1.setText("0.00");
            }
            rangeRate1.setText(String.format("%.2f", zixuans.get(position % 2 * 3).getRange() * 100) + "%");

        }else{
            price1.setText("——");
            range1.setText("——");
            rangeRate1.setText("——");
        }


        if (zixuans.get(position % 2 * 3).getRange() >=0) {
            exponent1.setBackground(ContextCompat.getDrawable(activity,R.drawable.zixuan_stock_raise_bg));
          /*  price1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_raise, 0, 0, 0);
            price1.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
            range1.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
            rangeRate1.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));*/
        } else if(zixuans.get(position % 2 * 3).getRange() <0){
            exponent1.setBackground(ContextCompat.getDrawable(activity,R.drawable.zixuan_stock_fall_bg));
          /*  price1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fall, 0, 0, 0);
            price1.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
            range1.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
            rangeRate1.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));*/
        }
        final StockBasic stoc = zixuans.get(position % 2 * 3);
        exponent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ExponentDetailsActivity.class);
                // intent.putExtra("index", position);
                intent.putExtra("stock", stoc);
                activity.startActivity(intent);
            }
        });
        LinearLayout exponent2 = ButterKnife.findById(view, R.id.exponent2);
        TextView exponentName2 = ButterKnife.findById(view, R.id.exponent_name2);
        TextView price2 = ButterKnife.findById(view, R.id.price2);
        TextView range2 = ButterKnife.findById(view, R.id.range2);
        TextView rangeRate2 = ButterKnife.findById(view, R.id.range_rate2);

        exponentName2.setText(zixuans.get(1 + position % 2 * 3).getName());
        if (zixuans.get(1 + position % 2 * 3).getLatestPrice()>0){
            price2.setText(String.format("%.2f", zixuans.get(1 + position % 2 * 3).getLatestPrice()));
            if(zixuans.get(1 + position % 2 * 3).getOpen()>0){
                double rangeCount2 = zixuans.get(1 + position % 2 * 3).getLatestPrice() * zixuans.get(1 + position % 2 * 3).getRange();
                range2.setText(String.format("%.2f", rangeCount2));
                //  range2.setText(zixuans.get(1 + position % 2 * 3).getRange()+"%");
            }else{
                range2.setText("0.00");
            }
            rangeRate2.setText(String.format("%.2f", zixuans.get(1 + position % 2 * 3).getRange() * 100) + "%");
        }else {
            price2.setText("——");
            rangeRate2.setText("——");
            range2.setText("——");

        }
        if (zixuans.get(1 + position % 2 * 3).getRange() >= 0) {
            exponent2.setBackground(ContextCompat.getDrawable(activity,R.drawable.zixuan_stock_raise_bg));
         /*   price2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_raise, 0, 0, 0);
            price2.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
            range2.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
            rangeRate2.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));*/
        } else if(zixuans.get(1+position % 2 * 3).getRange() <0){
            exponent2.setBackground(ContextCompat.getDrawable(activity,R.drawable.zixuan_stock_fall_bg));
       /*     price2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fall, 0, 0, 0);
            price2.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
            range2.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
            rangeRate2.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));*/
        }
        final StockBasic stoc2 = zixuans.get(1 + position % 2 * 3);
        exponent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ExponentDetailsActivity.class);
                // intent.putExtra("index", position);
                intent.putExtra("stock", stoc2);
                activity.startActivity(intent);
            }
        });
        LinearLayout exponent3 = ButterKnife.findById(view, R.id.exponent3);
        TextView exponentName3 = ButterKnife.findById(view, R.id.exponent_name3);
        TextView price3 = ButterKnife.findById(view, R.id.price3);
        TextView range3 = ButterKnife.findById(view, R.id.range3);
        TextView rangeRate3 = ButterKnife.findById(view, R.id.range_rate3);
        exponentName3.setText(zixuans.get(2 + position % 2 * 3).getName());
        if(zixuans.get(2 + position % 2 * 3).getLatestPrice()>0){
            price3.setText(String.format("%.2f", zixuans.get(2 + position % 2 * 3).getLatestPrice()));
            if(zixuans.get(2 + position % 2 * 3).getOpen()>0){
                double rangeCount3 = zixuans.get(2 + position % 2 * 3).getLatestPrice() * zixuans.get(2 + position % 2 * 3).getRange();
                range3.setText(String.format("%.2f", rangeCount3));
               // range3.setText(zixuans.get( + position % 2 * 3).getRange()+"%");
            }else{
                range2.setText("0.00");
            }

            rangeRate3.setText(String.format("%.2f", zixuans.get(2 + position % 2 * 3).getRange() * 100) + "%");
        }else {
            price3.setText("——");
            range3.setText("——");
            rangeRate3.setText("——");

        }
        if (zixuans.get(2 + position % 2 * 3).getRange() >= 0) {
            exponent3.setBackground(ContextCompat.getDrawable(activity,R.drawable.zixuan_stock_raise_bg));
          /*  price3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_raise, 0, 0, 0);
            price3.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
            range3.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));
            rangeRate3.setTextColor(ContextCompat.getColor(activity, R.color.cl_d63535));*/
        } else if(zixuans.get(2+position % 2 * 3).getRange() <0){
            exponent3.setBackground(ContextCompat.getDrawable(activity,R.drawable.zixuan_stock_fall_bg));
          /*  price3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fall, 0, 0, 0);
            price3.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
            range3.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));
            rangeRate3.setTextColor(ContextCompat.getColor(activity, R.color.cl_119d3e));*/
        }
        final StockBasic stoc3 = zixuans.get(2 + position % 2 * 3);
        exponent3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ExponentDetailsActivity.class);
                // intent.putExtra("index", position);
                intent.putExtra("stock", stoc3);
                activity.startActivity(intent);
            }
        });

    }

}
