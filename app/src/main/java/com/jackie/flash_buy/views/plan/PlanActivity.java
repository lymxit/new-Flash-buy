package com.jackie.flash_buy.views.plan;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jackie.flash_buy.BaseActivity;
import com.jackie.flash_buy.R;
import com.jackie.flash_buy.model.Market;
import com.jackie.flash_buy.presenters.plan.PlanPresenter;
import com.jackie.flash_buy.utils.RoundedTransformation;
import com.jackie.flash_buy.utils.activity.ActivityUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Jack on 2016/11/1.
 */
public class PlanActivity  extends BaseActivity{
    private android.widget.ImageView ivMarketPhoto;
    private android.widget.TextView tvMarketName;
    private android.widget.TextView tvMarketDescri;
    private android.widget.Button btnFollow;
    private android.widget.LinearLayout llMarketDetail;
    private android.widget.LinearLayout llMarketRoot;
    private android.widget.ImageView ivLogo;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.design.widget.CollapsingToolbarLayout collapsingtoolbar;
    private android.support.design.widget.AppBarLayout appBarLayout;
    private android.widget.FrameLayout planContentFrame;



    private PlanFragment mPlanFragment;
    private PlanPresenter mPlanPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置UI界面绑定
        setUI();
        //初始化UI界面数据
        initUI();
        mPlanFragment = (PlanFragment) (getSupportFragmentManager().findFragmentById(R.id.planContentFrame));
        if(mPlanFragment ==null){
            mPlanFragment = PlanFragment.GetInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mPlanFragment, R.id.planContentFrame);
        }

        // Create the presenter
        mPlanPresenter = PlanPresenter.GetInstance(mPlanFragment);

    }

    private void initUI() {
        //获取上个ACTIVITY给的数据
        if(getIntent().getExtras().getParcelable("market") != null) {
            Market market = getIntent().getExtras().getParcelable("market");
            tvMarketDescri.setText(market.getDesri());
            tvMarketName.setText(market.getName());
            if(!market.getLogo().equals(""))
                Picasso.with(this)
                        .load(market.getLogo())
                        .transform( new RoundedTransformation())
                        .into(ivMarketPhoto);

        }
    }

    private void setUI() {
        this.appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        this.collapsingtoolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

   //     this.ivLogo = (ImageView) findViewById(R.id.ivLogo);
        this.llMarketRoot = (LinearLayout) findViewById(R.id.llMarketRoot);
        this.llMarketDetail = (LinearLayout) findViewById(R.id.llMarketDetail);
        this.btnFollow = (Button) findViewById(R.id.btnFollow);
        this.tvMarketDescri = (TextView) findViewById(R.id.tvMarketDescri);
        this.tvMarketName = (TextView) findViewById(R.id.tvMarketName);
        this.ivMarketPhoto = (ImageView) findViewById(R.id.ivMarketPhoto);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 不要在onDestroy中释放资源，不安全,
     * 比如说更高权限的APP需要资源时候，不会调用Destroy,或者直接杀死进程也不会
     */
    @Override
    public void onStop(){
        super.onStop();
        PlanFragment.endFragment();
        mPlanPresenter.end();
    }
}
