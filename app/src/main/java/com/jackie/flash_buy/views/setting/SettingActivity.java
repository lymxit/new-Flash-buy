package com.jackie.flash_buy.views.setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jackie.flash_buy.BaseActivity;
import com.jackie.flash_buy.R;
import com.jackie.flash_buy.adapter.ExpandViewAdapter;
import com.jackie.flash_buy.bus.InternetEvent;
import com.jackie.flash_buy.model.Aller_father;
import com.jackie.flash_buy.model.Allergen;
import com.jackie.flash_buy.utils.Constant;
import com.jackie.flash_buy.utils.activity.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jack on 2016/11/3.
 */
public class SettingActivity extends BaseActivity {

    TextView tvAler;


    private RecyclerView mRecyclerView;
    private ExpandViewAdapter mAdapter;

    public static Allergen Allergens;
    public static boolean isChanged = false;

    Context mContext;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting);
        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);

        mContext = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("用户设置");   //设置标题
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mRecyclerView = (RecyclerView) findViewById(R.id.aler_recyview);
        init();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //瀑布流设置
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));



    }
    private  void init(){
        Allergen allergen1 = new Allergen("牛奶",11);
        Allergen allergen2 = new Allergen("鸡蛋",12);

        Aller_father aller_father1 = new Aller_father("奶制品",1, Arrays.asList(allergen1,allergen2));

        Allergen allergen3 = new Allergen("花生",71);
        Allergen allergen4 = new Allergen("杏仁",72);

        Aller_father aller_father2 = new Aller_father("坚果",7,Arrays.asList(allergen3,allergen4));

        mAdapter = new ExpandViewAdapter(mContext, Arrays.asList(aller_father1,aller_father2));
    }


    @Override
    public void onStop(){
        super.onStop();
        if(isChanged) {
            EventBus.getDefault().post(new InternetEvent("过敏源", Constant.POST_Aller));
        }
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
}
