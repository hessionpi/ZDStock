package com.rjzd.aistock.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.api.Stock;
import com.rjzd.aistock.api.StockList;

import java.util.List;

/**
 * Created by Hition on 2017/7/24.
 */

public class StockUtils {

    private static List<Stock> allStocks;

    static {
        String json =  FileUtil.readFile(Constants.STOCK_LOCAL_FILE);
        if(!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            StockList data = gson.fromJson(json, StockList.class);
            allStocks = data.get_data();
        }

    }


    /**
     * 根据股票代码识别股票名称
     * @param code                      股票代码
     * @return 股票名称
     */
    public static String getStockNameByCode(String code){
        if(null!= allStocks && !allStocks.isEmpty()){
            for(Stock s:allStocks){
                if(s.get_code().equals(code.trim())){
                    return s.get_name();
                }
            }
        }
        return null;
    }

}
