package com.rjzd.aistock.db;

import com.rjzd.aistock.bean.SearchHistory;
import com.rjzd.aistock.bean.StockBasic;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;

/**
 * Created by Hition on 2017/1/3.
 */

public class DBHelper {
    public static final String DB_NAME = "stock.realm";

    private static DBHelper instnace;

    public static DBHelper getInstance() {
        if (null == instnace) {
            synchronized (DBHelper.class) {
                if (null == instnace) {
                    instnace = new DBHelper();
                }
            }
        }

        return instnace;
    }


    /**
     * 插入数据到历史记录中
     */
    public Observable insert2History(SearchHistory history) {
        history.setCreateTime(System.currentTimeMillis());
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        Observable observable = mRealm.copyToRealmOrUpdate(history).asObservable();
        mRealm.commitTransaction();
        mRealm.close();
        return observable;
    }

    /**
     * 查询所有历史记录
     */
    public List<SearchHistory> getStockHistory() {
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<SearchHistory> stocks = mRealm.where(SearchHistory.class).findAllSorted("createTime", Sort.DESCENDING);
        mRealm.beginTransaction();
        List<SearchHistory> history = mRealm.copyFromRealm(stocks);
        mRealm.commitTransaction();
        mRealm.close();
        return history;
    }

    /**
     * 清除所有历史记录
     */
    public boolean clearHistory() {
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<SearchHistory> stocks = mRealm.where(SearchHistory.class).findAll();
        mRealm.beginTransaction();
        boolean isSuccess = stocks.deleteAllFromRealm();
        mRealm.commitTransaction();
        mRealm.close();
        return isSuccess;
    }

    /**
     * 添加自选股
     */
    public Observable insert2Stock(StockBasic stock) {
        stock.setCreateTime(System.currentTimeMillis());
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        Observable observable = mRealm.copyToRealmOrUpdate(stock).asObservable();
        mRealm.commitTransaction();
        mRealm.close();
        return observable;
    }

    /**
     * 查询所有自选股
     */
    public List<StockBasic> getPortfolio() {
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<StockBasic> stocks = mRealm.where(StockBasic.class).findAllSorted("createTime", Sort.DESCENDING);
        mRealm.beginTransaction();
        List<StockBasic> all = mRealm.copyFromRealm(stocks);
        mRealm.commitTransaction();
        mRealm.close();
        return all;
    }

    /**
     * 根据code查询自选股
     */
    public boolean isZixuan(String code) {
        Realm mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        StockBasic stock = mRealm.where(StockBasic.class).equalTo("code", code).findFirst();
        mRealm.commitTransaction();
        mRealm.close();
        return stock != null;
    }

    /**
     * delete 根据个股code 单删
     */
    public void delete(String code) {
        Realm mRealm = Realm.getDefaultInstance();
        StockBasic stock = mRealm.where(StockBasic.class).equalTo("code", code).findFirst();
        if (null != stock) {
            mRealm.beginTransaction();
            stock.deleteFromRealm();
            mRealm.commitTransaction();
        }
        mRealm.close();
    }


    /**
     * 批量删除自选股
     * @param codes                              股票列表
     */
    public void mutilDelete(String[] codes){
        Realm mRealm = Realm.getDefaultInstance();
        RealmResults<StockBasic> results = mRealm.where(StockBasic.class).in("code",codes).findAll();
        if(null != results && !results.isEmpty()){
            mRealm.beginTransaction();
            results.deleteAllFromRealm();
            mRealm.commitTransaction();
        }
        mRealm.close();
    }

}
