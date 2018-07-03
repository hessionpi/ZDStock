package com.rjzd.aistock.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rjzd.aistock.Constants;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.IsSuccess;
import com.rjzd.aistock.api.Privilege;
import com.rjzd.aistock.api.PrivilegeData;
import com.rjzd.aistock.api.StatusCode;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.aistock.presenter.PointsPresenter;
import com.rjzd.aistock.ui.PointsChangedListener;
import com.rjzd.aistock.ui.activity.InviteFriendsActivity;
import com.rjzd.aistock.utils.view.DialogManager;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.rjzd.aistock.R.id.tv_unlock_stock;

/**
 * 积分特权
 *
 * Created by Hition on 2017/7/20.
 */

public class PrivilegeFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.cost_best_stock)
    TextView mCostBestStock;
    @Bind(R.id.cost_best_plate)
    TextView mCostBestPlate;

    @Bind(tv_unlock_stock)
    TextView mUnlockStock;
    @Bind(R.id.tv_unlock_plate)
    TextView mUnlockPlate;
    @Bind(R.id.tv_unlock_vip)
    TextView mUnlockVIP;

    private int userId;
    private PointsPresenter presenter;
    private List<String> privilegeList = new ArrayList<>();
    private String bestStockPrivilegeId;
    private String bestPlatePrivilegeId;

    private int unlockBestStockNeedCost;
    private int unlockBestPlateNeedCost;

    private UnlockListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_privilege,container,false);
        ButterKnife.bind(this,mView);
        initView();

        presenter = new PointsPresenter(this);
        return mView;
    }

    private void initView() {
        Bundle bundle = getArguments();
        if(null != bundle){
            userId = bundle.getInt("user_id");
        }
        privilegeList.add(Constants.PRIVILEGE_NIUGU);
        privilegeList.add(Constants.PRIVILEGE_NIUBAN);
        privilegeList.add(Constants.PRIVILEGE_ZHOUBAO);

        LoginBean loginBean =  UserInfoCenter.getInstance().getLoginModel();
        if(loginBean.getLevel()>0){
            mUnlockVIP.setBackgroundResource(R.drawable.points_unlock_shape);
            mUnlockVIP.setText(R.string.finish_today);
        }else{
            mUnlockVIP.setOnClickListener(this);
        }
        mUnlockStock.setOnClickListener(this);
        mUnlockPlate.setOnClickListener(this);

        listener = new UnlockListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getPrivilegeStatus(userId,privilegeList);
    }

    @Override
    public void setStatistical() {
        statistical = "积分特权";
    }

    @Override
    public void onClick(View v) {
        String unlockMsg = getString(R.string.unlock_msg);

        switch(v.getId()){
            case tv_unlock_stock:
                listener.setType(0);
                DialogManager.showSelectDialogWithTitle2(activity, "解锁每日金股",
                        String.format(unlockMsg,unlockBestStockNeedCost),
                        R.string.unlock_now,
                        R.string.unlock_not,
                        true, listener);
                break;
            case R.id.tv_unlock_plate:
                listener.setType(1);
                DialogManager.showSelectDialogWithTitle2(activity,"解锁每日牛版",
                        String.format(unlockMsg,unlockBestPlateNeedCost),
                        R.string.unlock_now,
                        R.string.unlock_not,
                        true, listener);
                break;

            case R.id.tv_unlock_vip:
                InviteFriendsActivity.startActivity(activity);
                break;
        }
    }

    @Override
    public void fillData(Object data, int dsTag) {
        super.fillData(data, dsTag);
        switch (dsTag){
            case Constants.DS_TAG_PRIVILEGESTATUS:
                PrivilegeData privilege = (PrivilegeData) data;
                if(StatusCode.OK.getValue() == privilege.get_status().getValue()){
                    List<Privilege> privileges = privilege.get_data();
                    if(null == privileges || privileges.isEmpty()){
                        return ;
                    }

                    bestStockPrivilegeId = privileges.get(0).get_privilegeId();
                    bestPlatePrivilegeId = privileges.get(1).get_privilegeId();

                    unlockBestStockNeedCost = privileges.get(0).get_costPoints();
                    unlockBestPlateNeedCost = privileges.get(1).get_costPoints();

                    mCostBestStock.setText(String.valueOf(unlockBestStockNeedCost));
                    if(1 == privileges.get(0).get_unlock()){
                        mUnlockStock.setText(R.string.unlocked);
                        mUnlockStock.setBackgroundResource(R.drawable.points_unlock_shape);
                       mUnlockStock.setClickable(false);
                    }else if(0 == privileges.get(0).get_unlock()){
                        mUnlockStock.setText(R.string.unlock);
                        mUnlockStock.setBackgroundResource(R.drawable.points_lock_shape);
                        mUnlockStock.setClickable(true);
                    }

                    mCostBestPlate.setText(String.valueOf(unlockBestPlateNeedCost));
                    if(1 == privileges.get(1).get_unlock()){
                        mUnlockPlate.setText(R.string.unlocked);
                        mUnlockPlate.setBackgroundResource(R.drawable.points_unlock_shape );
                        mUnlockPlate.setClickable(false);
                    }else if(0 == privileges.get(1).get_unlock()){
                        mUnlockPlate.setText(R.string.unlock);
                        mUnlockPlate.setBackgroundResource(R.drawable.points_lock_shape);
                        mUnlockPlate.setClickable(true);
                    }
                }
                break;

            case Constants.DS_TAG_UNLOCKPRIVILEGE:
                IsSuccess isSuccess = (IsSuccess) data;
                if(StatusCode.OK.getValue() == isSuccess.get_status().getValue()){
                    showToast("解锁成功");
                    if(listener.getType() == 0){
                        mUnlockStock.setText(R.string.unlocked);
                        mUnlockStock.setBackgroundResource(R.drawable.points_unlock_shape);
                        mUnlockStock.setClickable(false);
                    }else if(listener.getType() == 1){
                        mUnlockPlate.setText(R.string.unlocked);
                        mUnlockPlate.setBackgroundResource(R.drawable.points_unlock_shape );
                        mUnlockPlate.setClickable(false);
                    }

                    // 通知MyPointsActivity 更新积分数据
                    PointsChangedListener listener = (PointsChangedListener) activity;
                    listener.onPointsChanged();
                }else{
                    showToast(isSuccess.get_msg());
                }
                break;

        }

    }

    @Override
    public void showFailedView() {
        super.showFailedView();
    }

    private class UnlockListener implements DialogManager.DialogListener{

        private int type ;

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        @Override
        public void onNegative() {

        }

        @Override
        public void onPositive() {
            switch(type){
                case 0:
                    presenter.unlockPrivilege(userId,bestStockPrivilegeId);
                    break;

                case 1:
                    presenter.unlockPrivilege(userId,bestPlatePrivilegeId);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onUnsubscribe();
    }
}
