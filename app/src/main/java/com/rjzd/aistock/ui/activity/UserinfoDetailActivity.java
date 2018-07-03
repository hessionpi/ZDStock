package com.rjzd.aistock.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.rjzd.aistock.R;
import com.rjzd.aistock.bean.LoginBean;
import com.rjzd.aistock.model.UserInfoCenter;
import com.rjzd.commonlib.imageloader.CircleTransform;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 个人资料详情页
 * <p>
 * Created by Hition on 2017/2/28.
 */

public class UserinfoDetailActivity extends BaseActivity {


    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_cellphone)
    TextView tvCellphone;
    @Bind(R.id.tv_gender)
    TextView tvGender;
    @Bind(R.id.tv_area)
    TextView tvArea;
    @Bind(R.id.iv_avatar)
    ImageView ivAvatar;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserinfoDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        UserInfoCenter center = UserInfoCenter.getInstance();
        LoginBean bean = center.getLoginModel();
        if (bean != null) {
            Glide.with(this).load(bean.getIconurl()).transform(new CircleTransform(this)).placeholder(R.drawable.ic_avatar_default).error(R.drawable.ic_avatar_default).into(ivAvatar);
            tvNickname.setText(bean.getNickname());
            tvGender.setText(bean.getGender());
            tvArea.setText(bean.getLocation());
            if (TextUtils.isEmpty(bean.getCellphone())){
                tvCellphone.setText("未绑定");
                tvCellphone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BindingMobileActivity.startActivityForResult(UserinfoDetailActivity.this);
                    }
                });
            }else {
                tvCellphone.setText(bean.getCellphone());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200){
            String mobile = data.getStringExtra("binding_mobile");
            if(!TextUtils.isEmpty(mobile)){
                tvCellphone.setText(mobile);
            }
        }
    }
}
