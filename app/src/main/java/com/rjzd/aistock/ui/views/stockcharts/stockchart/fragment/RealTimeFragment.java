package com.rjzd.aistock.ui.views.stockcharts.stockchart.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.ZdStockApp;
import com.rjzd.aistock.api.KData;
import com.rjzd.aistock.api.Point;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.cache.ACache;
import com.rjzd.aistock.presenter.RealTimePresenter;
import com.rjzd.aistock.ui.activity.BaseActivity;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.adapter.FenshiAdapter;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.CMinute;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.FenshiDataResponse;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.bean.FenshiParam;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.utils.DateUtil;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.BaseRealTimeView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.CrossView;
import com.rjzd.aistock.ui.views.stockcharts.stockchart.view.FenshiView;
import com.rjzd.commonlib.utils.LogUtil;
import com.rjzd.commonlib.utils.MathDataUtil;
import com.rjzd.commonlib.utils.SerializeTools;
import com.rjzd.commonlib.utils.StockTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.rjzd.aistock.Constants.DS_TAG_DEFAULT;


/**
 * Created by Administrator on 2016/10/25.
 */
public class RealTimeFragment extends LineBaseFragment implements BaseRealTimeView {

    @Bind(R.id.cff_msg)
    TextView msgText;
    @Bind(R.id.cff_fenshiview)
    FenshiView fenshiView;
    @Bind(R.id.cff_cross)
    CrossView crossView;
    @Bind(R.id.cfb_index_tab)
    TabLayout cfbIndexTab;
   /* @Bind(R.id.cff_recycle)
    RecyclerView recyclerView;
    @Bind(R.id.ll)
    LinearLayout ll;*/

    //滑动十字线时，显示对应点详情的地方
    private TimerTask refreshFiveTask;
    private Timer timer;

    final static int SUCCESS = 1;
    private TimerTask refreshFenshiTask;
    BaseActivity activity;
    FenshiAdapter adapter;
    //是否全屏
    // private boolean isPause;
    public ACache mCache;
    private RealTimePresenter presenter;
    KData ckData;
    boolean   isTrade;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_fenshi_frag, null);
        ButterKnife.bind(this, view);
        presenter = new RealTimePresenter(this);
        mCache = ACache.get(getActivity(), stockCode + "realtime");

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.isTradeDate();
        fenshiView.setUsedViews(crossView, msgText);
        indexTab.setOnTabSelectedListener(this);
        for (String s : INDEX_FENSHI_TAB) {
            indexTab.addTab(indexTab.newTab().setText(s));
        }
        activity = (BaseActivity) getActivity();
        adapter = new FenshiAdapter(getActivity());
        // SerializeTools.serialization(getFileDir() ,null);
        // presenter.getRealmTime(stackcode, codeType);


        // LogUtil.e("ZSJ",fivePositions+"++++++++++++++++++++++++++++++++++++++++");
        //  LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //  recyclerView.setLayoutManager(LayoutManager);
        // recyclerView.addItemDecoration(new ChartItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL, 2, Color.parseColor("#CED3D6")));
       /* List<Deal> deals = new ArrayList<>();
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));
        deals.add(new Deal("卖1", "13.77", "234"));*/

        // recyclerView.setAdapter(adapter);
        //ll.setVisibility(View.GONE);

    }

    @Override
    public void fillData(Object data, int dsTag) {
        switch (dsTag) {
        case  Constants.DS_TAG_IS_TRADE_DATE:
        if (data instanceof Boolean) {
            isTrade = (boolean) data;

            startTimer();
        }
        break;
            case DS_TAG_DEFAULT:
                KData kData = (KData) data;
               // LogUtil.e(kData.get_data().size() + "++++++++++++++++++++++++++++++");
                List<Point> points = new ArrayList<>();

                if (null == ckData || ckData.get_data().isEmpty()) {
                    points = kData.get_data();
                    if ( kData.get_data().isEmpty()|| kData.get_data().size() == 0 || kData.get_endTime().isEmpty() || "".equals(kData.get_endTime())) {
                    } else {
                        ckData = kData;
                    }
                    SerializeTools.serialization(getFileDir(), ckData);
                } else {
                    List<Point> cMinuteList = ckData.get_data();
                    List<Point> MinuteList = kData.get_data();
                    cMinuteList.remove(cMinuteList.size() - 1);
            cMinuteList.addAll(MinuteList);
            points.addAll(cMinuteList);
            // ckData.clear();
            ckData.set_data(points);
            if ("".equals(kData.get_endTime()) || kData.get_endTime().isEmpty()) {
            } else {
                ckData.set_endTime(kData.get_endTime());
            }
            if (MinuteList.size() == 0) {
            } else {
                SerializeTools.serialization(getFileDir(), ckData);
            }
        }

                // mCache.put(codeType+stockCode+"realtime",ckData);
                if (ckData != null) {
                    FenshiDataResponse fdata = new FenshiDataResponse();
                    String start = ckData.get_startTime();
                    String end = ckData.get_endTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        Date startDate = sdf.parse(start);
                        Date endDate = sdf.parse(end);
                        long startTime = startDate.getTime() / 1000;
                        long endTime = endDate.getTime() / 1000;
                        long openTime = startTime;
                        List<CMinute> MinuteList = new ArrayList<>();
                        long leadTime = 60;
                        if (null != points && !points.isEmpty()) {
                            for (Point p : points) {
                                CMinute cMinute = new CMinute();
                                if ( !kData.get_data().isEmpty()&&kData.get_data().size() != 0 && kData.get_data().get(0).get_open() == 0) {
                                    cMinute.setPrice(p.get_close());
                                } else {
                                    cMinute.setPrice(p.get_price());
                                }
                                cMinute.setRate(p.get_range());
                                cMinute.setAverage(p.get_average());
                                cMinute.setCount(p.get_volume());
                                cMinute.setTime(openTime);
                                cMinute.setMoney(p.get_price() * p.get_volume());
                                openTime += leadTime;
                                MinuteList.add(cMinute);
                            }
                            FenshiParam param = new FenshiParam();
                            param.setDuration("9:30-11:30|13:00-15:00");
                            param.setUntil(endTime);
                            if ( !kData.get_data().isEmpty()&&kData.get_data().size() != 0 && kData.get_data().get(0).get_open() == 0) {
                                param.setLength(points.size());
                            } else {
                                param.setLength(242);
                            }

                            param.setLast(points.get(0).get_close());
                            fdata.setParam(param);
                            fdata.setData(MinuteList);
                            fdata.setMsg(kData.get_msg());
                            // 如果code为数字则是股票，否则为指数  sz sc hs300

                        }

                        if (kData.get_status().getValue() == StatusCode.WAITING_FOR_PRICE.getValue() | kData.get_status().getValue() == StatusCode.WAITING_FOR_OPEN.getValue()) {
                            cancelStockViewTimer();
                            fenshiView.setDataAndInvalidate(null, true);
                        } else {
                            if (MathDataUtil.isInteger(stockCode.trim())) {
                                fenshiView.setDataAndInvalidate(fdata, true);
                            } else {
                                fenshiView.setDataAndInvalidate(fdata, false);
                            }
                          /*  if (kData.get_data().get(0).get_open() == 0) {
                                fenshiView.setDataAndInvalidate(fdata, false);
                            }*/
                            if (kData.get_status().getValue() == StatusCode.NON_TRADE_TIME.getValue()) {
                                cancelStockViewTimer();
                            }

                        }
                    } catch (ParseException e) {
                        LogUtil.e(e.getMessage());
                    }
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //isPause = false;

    }

    @Override
    public void onPause() {
        super.onPause();
        // isPause = true;
    }

    @Override
    public void setStatistical() {
        statistical = "real_time";
    }

    private void addListener() {
        fenshiView.setOnDoubleTapListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 启动计时器
     */
    private void startTimer() {
        timer = new Timer();

        //  刷新自选股定时器
        refreshFenshiTask = new TimerTask() {
            @Override
            public void run() {
                // DateUtil.getHms(System.currentTimeMillis();
                String startMinute = "9:30";
                if (isTrade) {
                    //  获取缓存数据
                    Object obj = null;
                    try {
                        obj = SerializeTools.deserialization(getFileDir());
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }

                    if (obj instanceof KData) {
                        ckData = (KData) obj;
                    }
                    if (null != ckData && !ckData.get_data().isEmpty()) {
                        Date now = new Date();
                        Long nowTime = now.getTime() / 1000;
                        String end = ckData.get_endTime();
                        Date endDate = DateUtil.parser24NoSecond(end);
                        long endTime = endDate.getTime()/1000;
                      // String endDay = DateUtil.shortDate(endDate);
           /*             Calendar c = new GregorianCalendar();
                        c.set(Calendar.HOUR_OF_DAY, 8);
                        c.set(Calendar.MINUTE, 45);
                        c.set(Calendar.SECOND, 0);
                        long nineTime = c.getTimeInMillis() / 1000;
                        //昨天的时间
                        Date getYesterdayDate = StockTimeUtils.getYesterdayTime();
                        String Yesterday = DateUtil.shortDate(getYesterdayDate);
                        if (endDay.equals(Yesterday) && nowTime > nineTime) {
                            ckData.clear();
                            startMinute = DateUtil.formatDate(new Date(), "9:30");
                        } else {
                            startMinute = DateUtil.formatDate(DateUtil.parser24NoSecond(end), "HH:mm");
                        }*/

                    Calendar c1 = new GregorianCalendar();
                    c1.set(Calendar.HOUR_OF_DAY, 0);
                    c1.set(Calendar.MINUTE, 0);
                    c1.set(Calendar.SECOND, 0);
                    long time = c1.getTimeInMillis() / 1000;
                    if (nowTime > StockTimeUtils.getEightMillis() && nowTime < StockTimeUtils.getEightMillis()) {
                        ckData.clear();
                        startMinute = DateUtil.formatDate(new Date(), "9:30");
                    } else if (endTime < time&&nowTime > StockTimeUtils.getEightMillis() ) {
                        ckData.clear();
                        startMinute = DateUtil.formatDate(new Date(), "9:30");
                    } else {
                        startMinute = DateUtil.formatDate(DateUtil.parser24NoSecond(end), "HH:mm");
                    }
                    }
                }
                presenter.getRealmTime(stockCode, startMinute);
            }
        };

        //  启动定时器
        timer.schedule(refreshFenshiTask, 0, Constants.PERIOD_REFRESH);
    }

    private String getFileDir() {
        return ZdStockApp.getAppContext().getFilesDir().toString() + stockCode;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case INDEX_VOL:
                fenshiView.showVOL();
                break;
        /*    case INDEX_ZJ:
                //TODO 显示指标资金
                fenshiView.showZJ();
                break;*/
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
        presenter.onUnsubscribe();
        cancelStockViewTimer();
    }

    @Override
    public void fillDataAndInvalidate(FenshiDataResponse d) {
      /*  if(CodeType.STOCK == codeType){
            fenshiView.setDataAndInvalidate(d,true);
        }else if(CodeType.EXPONENT == codeType){
            fenshiView.setDataAndInvalidate(d,false);
        }
        mCache.put(stockCode, d);*/
    }

    @Override
    public void cancelStockViewTimer() {
        if (timer != null) {
            timer.cancel();
        }
        if (refreshFenshiTask != null) {
            refreshFenshiTask.cancel();
        }
    }

}
