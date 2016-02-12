package com.kiwoong.cmssign;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BannerDialog extends Dialog {

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.banner_dialog);

        setLayout();
        setTitle(mTitle);
        setContent(mContent, mUrl);
        setClickListener(mLeftClickListener , mRightClickListener);
    }

    public BannerDialog(Context context) {
        // Dialog 배경을 투명 처리 해준다.
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        mContext = context;
    }

    public BannerDialog(Context context , String title ,
                        View.OnClickListener singleListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mLeftClickListener = singleListener;
        mContext = context;
    }

    public BannerDialog(Context context , String title , String content , String url,
                        View.OnClickListener leftListener ,	View.OnClickListener rightListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mUrl = url;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
        mContext = context;
    }

    private void setTitle(String title){
        mTitleView.setText(title);
    }

    private void setContent(String content, final String url){
        //mContentView.setText(content);
        Picasso.with(mContext).load("http://ah-freecar.com"+content)//.fit()
                .into(mBannerView);

        mBannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //ComponentName comp = new ComponentName("com.android.browser","com.android.browser.BrowserActivity");
                //intent.setComponent(comp);
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
            }
        });
    }

    private void setClickListener(View.OnClickListener left , View.OnClickListener right){
        if(left!=null && right!=null){
            mLeftButton.setOnClickListener(left);
            mRightButton.setOnClickListener(right);
        }else if(left!=null && right==null){
            mLeftButton.setOnClickListener(left);
        }else {

        }
    }

    /*
     * Layout
     */
    private TextView mTitleView;
    private ImageView mBannerView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mContent;
    private String mUrl;


    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    /*
     * Layout
     */
    private void setLayout(){
        mTitleView = (TextView) findViewById(R.id.tv_title);
        mBannerView = (ImageView) findViewById(R.id.img_banner);
        mLeftButton = (Button) findViewById(R.id.bt_left);
        mRightButton = (Button) findViewById(R.id.bt_right);
    }
}
