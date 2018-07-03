package com.rjzd.aistock.ui.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rjzd.aistock.R;
import com.rjzd.aistock.utils.FontUtil;
import com.rjzd.commonlib.utils.PixelUtil;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 底栏图标VIew
 */
public class TabIconView extends LinearLayout {

    public static final String TAG = "TabIconView";

    /**
     * Icon状态
     */
    public static final int CHOSEN = 0;
    public static final int UNCHOSEN = 1;
    /**
     * icon状态
     */
    int status = UNCHOSEN;
    /**
     * 有没有按下效果
     */
    boolean hasPressEffect;

    int tabIconImageSelector;
    int tabIconImageDefault;
    int tabIconImagePressed;
    int tabBackgroundImageDefault;
    int tabBackgroundImagePressed;
    int tabBackgroundImageSelector;

    ColorStateList textSelector;
    int textColorDefault, textColorPressed;
    /**
     * 字体大小
     */
    float textSize;
    /**
     * logo大小
     */
    int imageSize;
    /**
     * icon的水平、垂直padding
     */
    int paddingVertical, paddingHorizontal;
    /**
     * icon和text之间的间距
     */
    int iconTextMargin;
    /**
     * icon的文字
     */
    String text;

    @Bind(R.id.tvTabText)
    TextView tvText;
    @Bind(R.id.ivTabIconLogo)
    ImageView ivLogo;
    /*@Bind(R.id.haveMessageIcon)
    ImageView haveMessageIcon;*/

    /**
     * 框架属性
     */
    LayoutParams containerParams, logoParams;


    public TabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //读取自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TabIconView, 0, 0);
        readInParams(typedArray);
        typedArray.recycle();

        //填充View
        LayoutInflater inflater = LayoutInflater.from(context);
        View tabView = inflater.inflate(R.layout.view_tab_icon, this, true);
        ButterKnife.bind(tabView);

        //初始化属性变量
        containerParams = (LayoutParams) this.getLayoutParams();
        logoParams = new LayoutParams(imageSize, imageSize);

        //初始化控件
        initViews();
    }

    private void initViews() {
        //修改容器属性
        this.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
        this.setBackgroundResource(hasPressEffect ? tabBackgroundImageSelector : tabBackgroundImageDefault);
        /*this.setBackgroundDrawable((hasPressEffect && tabBackgroundImageSelector != null)
                ? tabBackgroundImageSelector : tabBackgroundImageDefault);*/

        //修改Logo属性
        ivLogo.setBackgroundResource(hasPressEffect ? tabIconImageSelector : tabIconImageDefault);
        /*ivLogo.setBackgroundDrawable((hasPressEffect && tabIconImageSelector != null)
                ? tabIconImageSelector : tabIconImageDefault);*/
        logoParams.bottomMargin = iconTextMargin;

        if(TextUtils.isEmpty(text)){
//            logoParams = new LayoutParams(70, 70);
//            ivLogo.setLayoutParams(logoParams);
            tvText.setVisibility(GONE);
        }else{
            tvText.setVisibility(VISIBLE);
//            ivLogo.setLayoutParams(logoParams);
        }

        //修改文字属性
        tvText.setText(text);
        //此处巨坑……SP读取过来默认是PX，而setTextSize的默认是SP，必须自己设定设置字体大小的类型。
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (hasPressEffect && textSelector != null) {
            tvText.setTextColor(textSelector);
        } else {
            tvText.setTextColor(textColorDefault);
        }

        //默认隐藏
//        haveMessageIcon.setVisibility(View.GONE);
    }

    public int getIconStatus() {
        return this.status;
    }

    public void setIconStatus(int status) {
        this.status = status;
        if (status == CHOSEN) {
//            ivLogo.setBackgroundDrawable(tabIconImagePressed);
            ivLogo.setBackgroundResource(tabIconImagePressed);
            tvText.setTextColor(textColorPressed);
        } else {
            if (hasPressEffect) {
//                ivLogo.setBackgroundDrawable(tabIconImageSelector);
                ivLogo.setBackgroundResource(tabIconImageSelector);
                tvText.setTextColor(textSelector);
            } else {
//                ivLogo.setBackgroundDrawable(tabIconImageDefault);
                ivLogo.setBackgroundResource(tabIconImageDefault);
                tvText.setTextColor(textColorDefault);
            }
        }
    }

    /**
     * 读取自定义属性
     *
     * @param typedArray
     */
    private void readInParams(TypedArray typedArray) {
        tabBackgroundImageDefault = typedArray.getResourceId(R.styleable.TabIconView_backgroundDefault,tabBackgroundImageDefault);
        tabBackgroundImagePressed = typedArray.getResourceId(R.styleable.TabIconView_backgroundPressed,tabBackgroundImagePressed);
        tabBackgroundImageSelector = typedArray.getResourceId(R.styleable.TabIconView_backgroundSelector,tabBackgroundImageSelector);
        tabIconImageDefault = typedArray.getResourceId(R.styleable.TabIconView_iconLogoDefault,tabIconImageDefault);
        tabIconImagePressed = typedArray.getResourceId(R.styleable.TabIconView_iconLogoPressed,tabIconImagePressed);
        tabIconImageSelector = typedArray.getResourceId(R.styleable.TabIconView_iconLogoSelector,tabIconImageSelector);
        imageSize = typedArray.getDimensionPixelSize(R.styleable.TabIconView_imageSize,PixelUtil.dp2px(30));
        text = typedArray.getString(R.styleable.TabIconView_text);
        textColorDefault = typedArray.getColor(R.styleable.TabIconView_textColorDefault,0);
        textColorPressed = typedArray.getColor(R.styleable.TabIconView_textColorPressed,0);
        textSelector = typedArray.getColorStateList(R.styleable.TabIconView_textColorSelector);
        textSize = typedArray.getDimensionPixelSize(R.styleable.TabIconView_textSize, PixelUtil.sp2px(12));
        paddingHorizontal = typedArray.getDimensionPixelSize(R.styleable.TabIconView_iconPaddingHorizontal, PixelUtil.dp2px(5));
        paddingVertical = typedArray.getDimensionPixelSize(R.styleable.TabIconView_iconPaddingVertical, PixelUtil.dp2px(5));
        iconTextMargin = typedArray.getDimensionPixelSize(R.styleable.TabIconView_iconTextMargin, PixelUtil.dp2px(5));
        hasPressEffect = typedArray.getBoolean( R.styleable.TabIconView_pressEffect, false);

    }

    /**
     * 设置小红点 显隐
     * @param visibility
     */
    /*public void setHaveMessageIconVisibility(int visibility){
        haveMessageIcon.setVisibility(visibility);
    }*/

}
