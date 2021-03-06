package com.jackie.flash_buy.ui.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.jackie.flash_buy.R;
import com.jackie.flash_buy.model.Allergen;
import com.jackie.flash_buy.views.setting.SettingActivity;
import com.jackie.flash_buy.views.setting.SettingActivity;


/**
 *  ViewHolder
 */
public class AllergenHolder extends ChildViewHolder {
    private Allergen mAllergen;
    private TextView mIngredientTextView;
    private ImageView mImageView;


    public AllergenHolder(View itemView) {
        super(itemView);
        mIngredientTextView = (TextView) itemView.findViewById(R.id.allergen_textview);
        mImageView = (ImageView) itemView.findViewById(R.id.allergen_check);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.isChanged = true;  //更改
                
                if(mAllergen.isChosed){
                    mAllergen.isChosed = false;
                    


                    mImageView.setImageResource(R.drawable.ic_cancel_black_24dp);
                }else {
                    mAllergen.isChosed = true;
                    //加入过敏源中
                    SettingActivity.Allergens = mAllergen;
                    mImageView.setImageResource(R.drawable.ic_done_black_18dp);
                }
            }
        });


        mIngredientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
   //             Log.i("Test","你点击了"+mIngredientTextView.getText().toString());
            }
        });
    }

    public void bind(Allergen allergen) {
        mAllergen = allergen;
        if(mAllergen.isChosed)
            mImageView.setImageResource(R.drawable.ic_done_black_18dp);
        else
            mImageView.setImageResource(R.drawable.ic_cancel_black_24dp);
        mIngredientTextView.setText(allergen.getName());
    }
}
