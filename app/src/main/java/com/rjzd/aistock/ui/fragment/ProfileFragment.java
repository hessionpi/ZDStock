package com.rjzd.aistock.ui.fragment;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.CompanyProfile;
import com.rjzd.aistock.api.Dividends;
import com.rjzd.aistock.api.Executive;
import com.rjzd.aistock.api.NameValuePairs;
import com.rjzd.aistock.api.Shareholder;
import com.rjzd.aistock.api.ShareholderInfo;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.commonlib.utils.NetWorkUtil;
import com.rjzd.commonlib.utils.PixelUtil;

import java.util.List;

import butterknife.Bind;

/**
 * 公司概况
 * Created by Hition on 2017/3/20.
 */

public class ProfileFragment extends LazyF10Fragment implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.rg_profile)
    RadioGroup mProfileRG;
    @Bind(R.id.profile_layout)
    LinearLayout mProfileLayout;
    @Bind(R.id.shareholder_layout)
    LinearLayout mShareHolderLayout;

    @Bind(R.id.basic_info_container)
    LinearLayout mBasicInfoLayout;
    @Bind(R.id.executive_container)
    LinearLayout mExecutiveLayout;
    @Bind(R.id.dividends_container)
    LinearLayout mDividendsLayout;
    @Bind(R.id.capital_structure_container)
    LinearLayout mCapitalStructureLayout;
    @Bind(R.id.circulation_container)
    LinearLayout mCirculationLayout;
    @Bind(R.id.organization_container)
    LinearLayout mOrganizationLayout;

    @Bind(R.id.report_date_circulation)
    TextView mCircuReportdate;
    @Bind(R.id.tv_report_date_organization)
    TextView mOrganizationReportDate;

    private boolean isShareHolderLoad ;

    @Override
    protected int createView() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initView() {
        mProfileRG.setOnCheckedChangeListener(this);
    }

    @Override
    public void setStatistical() {
        statistical = "company_profile";
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch (dsTag){
            case Constants.DS_TAG_COMPANY_PROFILE:
                CompanyProfile profile = (CompanyProfile) data;
                if(StatusCode.OK.getValue() == profile.get_status().getValue()){
                    mHasLoadedOnce = true;
                    List<NameValuePairs> basicInfos = profile.get_basicInfo();
                    List<Executive> executives = profile.get_executiveList();
                    List<Dividends> dividendsList = profile.get_dividendsList();

                    if(!basicInfos.isEmpty()){
                        for(NameValuePairs nvp:basicInfos){
                            setBasicInfoView(nvp);
                        }
                    }
                    if(!executives.isEmpty()){
                        for(Executive et : executives){
                            setExecutiveView(et);
                        }
                    }
                    if(!dividendsList.isEmpty()){
                        for(Dividends dividends : dividendsList){
                            setDividends(dividends);
                        }
                    }
                }
                break;

            case Constants.DS_TAG_SHARE_HOLDER:
                ShareholderInfo shInfo = (ShareholderInfo) data;
                if(StatusCode.OK.getValue() == shInfo.get_status().getValue()){
                    isShareHolderLoad = true;

                    mCircuReportdate.setText(shInfo.get_circulationDate());
                    mOrganizationReportDate.setText(shInfo.get_organizationDate());
                    List<NameValuePairs> capitalStructures = shInfo.get_capitalStructure();
                    List<Shareholder> circulateList = shInfo.get_circulationShareholderOf10();
                    List<Shareholder> organizationList = shInfo.get_organizationShareholders();

                    if(!capitalStructures.isEmpty()){
                        for(NameValuePairs nvp : capitalStructures){
                            setCapitalStructureView(nvp);
                        }
                    }
                    if(!circulateList.isEmpty()){
                        for(Shareholder sh : circulateList){
                            setShareholder(sh,1);
                        }
                    }
                    if(!organizationList.isEmpty()){
                        for(Shareholder sh : organizationList){
                            setShareholder(sh,2);
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
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        if (NetWorkUtil.isNetworkConnected(activity)) {
            // 获取数据
            presenter.getCompanyProfile(stockCode);
            showLoadingDialog();
        }else{
            activity.showToast(R.string.no_network);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rb_profile:
                mProfileLayout.setVisibility(View.VISIBLE);
                mShareHolderLayout.setVisibility(View.GONE);
                break;

            case R.id.rb_share_holder:
                if(!isShareHolderLoad){
                    presenter.getShareholder(stockCode);
                    showLoadingDialog();
                }
                mProfileLayout.setVisibility(View.GONE);
                mShareHolderLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 动态添加公司基本资料
     * @param basicInfo   基本资料
     */
    private void setBasicInfoView(NameValuePairs basicInfo){
        LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        // 属性名
        TextView mNameView = new TextView(activity);
        LinearLayout.LayoutParams nameLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(80), LinearLayout.LayoutParams.WRAP_CONTENT);
        nameLP.setMargins(0,PixelUtil.dp2px(10),PixelUtil.dp2px(40),PixelUtil.dp2px(12));
        mNameView.setLayoutParams(nameLP);
        mNameView.setTextColor(Color.parseColor("#555555"));
        mNameView.setTextSize(13);
        mNameView.setText(basicInfo.get_name());

        // 属性值
        TextView mValueView = new TextView(activity);
        LinearLayout.LayoutParams valueLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        valueLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(12));
        mValueView.setLayoutParams(valueLP);
        mValueView.setTextColor(Color.parseColor("#808080"));
        mValueView.setTextSize(13);
        mValueView.setText(basicInfo.get_value()+basicInfo.get_unit());

        layout.addView(mNameView);
        layout.addView(mValueView);
                mBasicInfoLayout.addView(layout);


    }
    /**
     * 股本结构
     * @param basicInfo   基本资料
     */
    private void setCapitalStructureView(NameValuePairs basicInfo){
        LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        // 属性名
        TextView mNameView = new TextView(activity);
        LinearLayout.LayoutParams nameLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(80), LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        nameLP.gravity = Gravity.LEFT;
        nameLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(12));
        mNameView.setLayoutParams(nameLP);
        mNameView.setTextColor(Color.parseColor("#555555"));
        mNameView.setTextSize(13);
        mNameView.setText(basicInfo.get_name());

        // 属性值
        TextView mValueView = new TextView(activity);
        LinearLayout.LayoutParams valueLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        valueLP.gravity = Gravity.RIGHT;
        valueLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(12));
        mValueView.setLayoutParams(valueLP);
        mValueView.setTextColor(Color.parseColor("#808080"));
        mValueView.setGravity(Gravity.RIGHT);
        mValueView.setTextSize(13);
        mValueView.setText(basicInfo.get_value()+basicInfo.get_unit());

        layout.addView(mNameView);
        layout.addView(mValueView);
                mCapitalStructureLayout.addView(layout);
    }
    /**
     * 动态添加公司高管信息
     * @param et  高管信息
     */
    private void setExecutiveView(Executive et){
        LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        // 姓名
        TextView mNameView = new TextView(activity);
        LinearLayout.LayoutParams nameLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(48), LinearLayout.LayoutParams.WRAP_CONTENT,0.2f);
        nameLP.setMargins(0,PixelUtil.dp2px(10),PixelUtil.dp2px(30),PixelUtil.dp2px(12));
        mNameView.setLayoutParams(nameLP);
        mNameView.setTextColor(Color.parseColor("#555555"));
        mNameView.setTextSize(13);
        mNameView.setText(et.get_name());
        // 职位
        TextView mPositionView = new TextView(activity);
        LinearLayout.LayoutParams positionLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(100), LinearLayout.LayoutParams.WRAP_CONTENT,0.3f);
        positionLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(12));
        mPositionView.setLayoutParams(positionLP);
        mPositionView.setTextColor(Color.parseColor("#555555"));
        mPositionView.setTextSize(13);
        mPositionView.setText(et.get_position());
        // 开始时间
        TextView mStartDateView = new TextView(activity);
        LinearLayout.LayoutParams startDateLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(75), LinearLayout.LayoutParams.WRAP_CONTENT,0.5f);
        startDateLP.setMargins(0,PixelUtil.dp2px(10),PixelUtil.dp2px(10),PixelUtil.dp2px(12));
        mStartDateView.setLayoutParams(startDateLP);
        mStartDateView.setTextColor(Color.parseColor("#555555"));
        mStartDateView.setTextSize(13);
        mStartDateView.setText(et.get_startDate());
        // 结束时间
        TextView mEndDateView = new TextView(activity);
        LinearLayout.LayoutParams endDateLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(75), LinearLayout.LayoutParams.WRAP_CONTENT,0.5f);
        endDateLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(12));
        mEndDateView.setLayoutParams(endDateLP);
        mEndDateView.setGravity(Gravity.CENTER_HORIZONTAL);
        mEndDateView.setTextColor(Color.parseColor("#555555"));
        mEndDateView.setTextSize(13);
        String endDate = et.get_endDate();
        if(TextUtils.isEmpty(endDate)){
            endDate = "--";
        }
        mEndDateView.setText(endDate);

        layout.addView(mNameView);
        layout.addView(mPositionView);
        layout.addView(mStartDateView);
        layout.addView(mEndDateView);
        mExecutiveLayout.addView(layout);
    }

    /**
     * 动态添加分红送配
     */
    private void setDividends(Dividends ds){
        LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        // 年度
        TextView mYearView = new TextView(activity);
        LinearLayout.LayoutParams yearLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(66), LinearLayout.LayoutParams.WRAP_CONTENT);
        yearLP.setMargins(0,PixelUtil.dp2px(10),PixelUtil.dp2px(40),PixelUtil.dp2px(14));
        mYearView.setLayoutParams(yearLP);
        mYearView.setTextColor(Color.parseColor("#555555"));
        mYearView.setTextSize(13);
        mYearView.setText(ds.get_year());
        // 方案
        TextView mPlanView = new TextView(activity);
        LinearLayout.LayoutParams planLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(200), LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        planLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(14));
        mPlanView.setLayoutParams(planLP);
        mPlanView.setTextColor(Color.parseColor("#555555"));
        mPlanView.setTextSize(13);
        mPlanView.setText(ds.get_plan());
        // 除权日
        TextView mXRDateView = new TextView(activity);
        LinearLayout.LayoutParams xrDateLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(75), LinearLayout.LayoutParams.WRAP_CONTENT);
        xrDateLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(14));
        mXRDateView.setLayoutParams(xrDateLP);
        mXRDateView.setTextColor(Color.parseColor("#555555"));
        mXRDateView.setTextSize(13);
        mXRDateView.setText(ds.get_xrDate());

        layout.addView(mYearView);
        layout.addView(mPlanView);
        layout.addView(mXRDateView);
        mDividendsLayout.addView(layout);
    }

    /**
     * 动态设置十大流通股东和机构持股信息
     * @param sh           股东信息
     * @param shType       股东类型    1 十大流通股     2 机构持股
     */
    private void setShareholder(Shareholder sh,int shType){
        LinearLayout layout = new LinearLayout(activity);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        // 股东
        TextView shareHolderView = new TextView(activity);
        LinearLayout.LayoutParams shLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(150), LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        shLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(12));
        shareHolderView.setLayoutParams(shLP);
        shareHolderView.setTextColor(Color.parseColor("#555555"));
        shareHolderView.setTextSize(13);
        shareHolderView.setText(sh.get_name());
        // 占比
        TextView mProportionView = new TextView(activity);
        LinearLayout.LayoutParams proportionLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(40), LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        if(1 == shType){
            proportionLP.setMargins(0,PixelUtil.dp2px(10),PixelUtil.dp2px(10),PixelUtil.dp2px(12));
        }else{
            proportionLP.setMargins(0,PixelUtil.dp2px(10),PixelUtil.dp2px(30),PixelUtil.dp2px(12));
        }

        mProportionView.setLayoutParams(proportionLP);
        mProportionView.setGravity(Gravity.RIGHT);
        mProportionView.setTextColor(Color.parseColor("#555555"));
        mProportionView.setTextSize(13);
        mProportionView.setText(sh.get_proportion());
        // 变动
        TextView mChangeView = new TextView(activity);
        mChangeView.setGravity(Gravity.RIGHT);
        LinearLayout.LayoutParams changeLP = new LinearLayout.LayoutParams(PixelUtil.dp2px(60), LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        changeLP.setMargins(0,PixelUtil.dp2px(10),0,PixelUtil.dp2px(12));
        mChangeView.setLayoutParams(changeLP);
        if(1 == shType){
            mChangeView.setText(sh.get_change());
        }else {
            mChangeView.setText(sh.get_quantity());
        }
        mChangeView.setTextColor(Color.parseColor("#555555"));
        mChangeView.setTextSize(13);



        layout.addView(shareHolderView);
        layout.addView(mProportionView);
        layout.addView(mChangeView);
        switch(shType){
            case 1:
                mCirculationLayout.addView(layout);
                break;

            case 2:
                mOrganizationLayout.addView(layout);
                break;
        }
    }



}
