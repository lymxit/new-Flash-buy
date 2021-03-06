package com.jackie.flash_buy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackie.flash_buy.R;
import com.jackie.flash_buy.contracts.plan.TypeListener;
import com.jackie.flash_buy.model.LineItem;
import com.squareup.picasso.Picasso;


import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Jack on 2016/10/19.
 */
public class GoodsAdapter extends ArrayAdapter<LineItem> implements StickyListHeadersAdapter {
    private List<LineItem> dataList;
    private Context mContext;
    private int resId;
    private LayoutInflater mInflater;

    private TypeListener mListener;  //外部调用接口


    public GoodsAdapter (Context mContext, int resId, List<LineItem> dataList,TypeListener m) {
        super(mContext,resId,dataList);
        this.dataList = dataList;
        this.mContext = mContext;
        this.resId = resId;
        this.mListener = m;
       // Log.e("test1",mListener.toString());

        mInflater = LayoutInflater.from(mContext);
    }

  /*  public GoodsAdapter (Context mContext, int resId, List<LineItem> dataList) {
        super(mContext,resId,dataList);
        this.dataList = dataList;
        this.mContext = mContext;
        this.resId = resId;
        mInflater = LayoutInflater.from(mContext);
    }*/

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_header_view, parent, false);
        }
        //设置每个Header的头
        ((TextView)(convertView)).setText(dataList.get(position).getItem().getPid());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final LineItem LineItem = getItem(position); //得到LineItem
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = mInflater.inflate(resId,parent,false);
            viewHolder = new ViewHolder(view,mListener);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.bindData(LineItem);
        return view;
    }

    class ViewHolder implements View.OnClickListener{
        private TextView name,price,tvAdd,tvMinus,tvCount,tvDetails;
        private LineItem lineItem;
        private ImageView mImageView;


        public ViewHolder(View LineItemView,TypeListener mFragment){
            mImageView = (ImageView) LineItemView.findViewById(R.id.img);
            name = (TextView) LineItemView.findViewById(R.id.tvName);
            price = (TextView) LineItemView.findViewById(R.id.tvPrice);
            tvCount = (TextView) LineItemView.findViewById(R.id.count);
            tvMinus = (TextView) LineItemView.findViewById(R.id.tvMinus);
            tvAdd = (TextView) LineItemView.findViewById(R.id.tvAdd);
            tvDetails = (TextView) LineItemView.findViewById(R.id.tvDetails);
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
            mImageView.setOnClickListener(this);
        }

        /**
         * 绑定数据
         * @param lineItem
         */
        public void bindData(LineItem lineItem){
            this.lineItem = lineItem;
            name.setText(lineItem.getItem().getName());
            //LineItem.count = mContext.getSelectedLineItemCountById(LineItem.id);
            //tvCount.setText(String.valueOf(LineItem.count));
            Picasso.with(mContext)
                    .load(lineItem.getItem().getImage())
                    .into(mImageView);
            tvDetails.setText(lineItem.getItem().getSize());
            price.setText(lineItem.getItem().getPrice()+"元");
            if(lineItem.getNum() < 1){
                tvCount.setVisibility(View.GONE);
                tvMinus.setVisibility(View.GONE);
            }else{
                tvCount.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            //PlanFragment fragment = mListener;
            switch (v.getId()){
                case R.id.tvAdd:{
                    if(lineItem.getNum() < 1){
                        tvMinus.setAnimation(getShowAnimation());
                        tvMinus.setVisibility(View.VISIBLE);
                        tvCount.setVisibility(View.VISIBLE);
                    }
                    tvCount.setText(String.valueOf(lineItem.getNum() + 1));  //数量变化
                    mListener.add(lineItem,false);
                }
                break;
                case R.id.tvMinus:{
                    if(lineItem.getNum() < 2){
                        tvMinus.setAnimation(getHiddenAnimation());
                        tvMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    }
                    tvCount.setText(String.valueOf(lineItem.getNum() - 1));  //数量变化
                    mListener.removeItem(lineItem,false);
                }
                break;
                default:
                  //  Log.d("Item",lineItem.getItem().getName());
                    //打开详情列表，应该交给外部处理
                    mListener.openItemActivity(lineItem.getItem());
                    break;
            }
        }
    }

    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f, RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                , TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
}
