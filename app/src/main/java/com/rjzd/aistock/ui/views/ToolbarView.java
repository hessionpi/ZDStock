package com.rjzd.aistock.ui.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.api.RangeFlag;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hition on 2016/12/26.
 *
 * 顶部标题栏
 */

public class ToolbarView extends FrameLayout {

    @Bind(R.id.iv_left)
    ImageView leftImgAction;
    @Bind(R.id.action_left)
    TextView leftTextAction;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_right)
    Button rightBtnAction;
    @Bind(R.id.iv_right)
    ImageView rightImgAction;

    @Bind(R.id.rg_range)
    RadioGroup mRangeRG;



    //  标题栏文字内容
    private String titleText;

    // 标题栏左侧文字
    private String actionLeftText;
    private int actionLeftBackground;
    // 标题栏右侧文字
    private String actionRightText;
    private int actionRightBackground;

    private OnLeftClickListener leftClickListener;
    private OnRightClickListener rightClickListener;

    public ToolbarView(Context context) {
        this(context, null);
    }

    public ToolbarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolbarView(Context context, AttributeSet attrs,int defStyleAttr) {
        super(context, attrs,defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Toolbar, 0, 0);
        initConfig(typedArray);
        typedArray.recycle();

        View toolbarView = LayoutInflater.from(context).inflate(R.layout.layout_toolbar_view, this);
        ButterKnife.bind(toolbarView);
        initViews();
    }

    /**
     * 读取arrays文件中配置参数
     *
     * @param typedArray
     */
    private void initConfig(TypedArray typedArray) {
        titleText = typedArray.getString(R.styleable.Toolbar_titleText);

        actionLeftText = typedArray.getString(R.styleable.Toolbar_actionLeftText);
        actionLeftBackground = typedArray.getResourceId(R.styleable.Toolbar_actionLeftBackground,R.drawable.image_back);

        actionRightText = typedArray.getString(R.styleable.Toolbar_actionRightText);
        actionRightBackground = typedArray.getResourceId(R.styleable.Toolbar_actionRightBackground,R.drawable.ic_search);
    }

    private void initViews() {
        setCenterShowType(View.VISIBLE,View.GONE);
        leftImgAction.setImageResource(actionLeftBackground);
        leftTextAction.setText(actionLeftText);

        tvTitle.setText(titleText);
        rightBtnAction.setText(actionRightText);
        rightImgAction.setImageResource(actionRightBackground);

        leftImgAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == leftClickListener){
                    ((Activity)getContext()).finish();
                }else{
                    leftClickListener.onLeftImgClick();
                }
            }
        });
        leftTextAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != leftClickListener){
                    leftClickListener.onLeftTextClick();
                }
            }
        });
        rightBtnAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != rightClickListener){
                    rightClickListener.onRightBtnClick();
                }
            }
        });
        rightImgAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != rightClickListener){
                    rightClickListener.onRightImgClick();
                }
            }
        });
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setTitle(int titleResId){
        tvTitle.setText(titleResId);
    }

    public String getTitle(){
        return  tvTitle.getText().toString();
    }

    public void setRaiseOrFallListener(RadioGroup.OnCheckedChangeListener listener){
        mRangeRG.setOnCheckedChangeListener(listener);
    }

    /**
     * 设置左侧文字是否可见
     * @param visible   gone/visible
     */
    public void setLeftTextVisible(int visible){
        leftTextAction.setVisibility(visible);
    }

    /**
     * 设置左侧返回是否可见
     * @param visible   gone/visible
     */
    public void setBackupVisible(int visible){
        leftImgAction.setVisibility(visible);
    }

    /**
     *  设置右侧按钮是否可见
     * @param visible
     */
    public void setRightBtnVisible(int visible){
        rightBtnAction.setVisibility(visible);
    }

    /**
     * 设置右侧刷新是否可见
     * @param visible
     */
    public void setRightImgVisible(int visible){
        rightImgAction.setVisibility(visible);
    }

    /**
     * 设置左边标题栏监听事件
     * @param leftClickListener
     */
    public void setOnLeftClickListener(OnLeftClickListener leftClickListener){
        this.leftClickListener = leftClickListener;
    }

    /**
     * 设置中间显示类型              标题类型/选择类型
     * @param titleVisibility       标题是否显示
     * @param radioVisibility       涨跌幅是否显示
     */
    public void setCenterShowType(int titleVisibility,int radioVisibility){
        tvTitle.setVisibility(titleVisibility);
        mRangeRG.setVisibility(radioVisibility);
    }


    /**
     * 设置右边标题栏监听事件
     * @param rightClickListener
     */
    public void setOnRightClickListener(OnRightClickListener rightClickListener){
        this.rightClickListener = rightClickListener;
    }


    public interface OnLeftClickListener{
        void onLeftImgClick();

        void onLeftTextClick();
    }

    public interface OnRightClickListener{
        void onRightImgClick();

        void onRightBtnClick();
    }
}
