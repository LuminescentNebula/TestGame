package com.example.testgame.other;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.example.testgame.R;

public class StatBar extends ConstraintLayout {

    public static final int Left=1;
    public static final int Right=2;
    public static final int Center=3;

    private static final int red=0xFFFF2A00;
    private static final int green=0xFF5DDB6E;


    private float maxAmount=100;
    private int amount;

    MagicTextView magicTextView;
    ImageView bar_minus,bar_plus;

    public StatBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.stat,this);
        amount=100;
        magicTextView=findViewById(R.id.amount);
        bar_minus =findViewById(R.id.bar_minus);
        bar_plus=findViewById(R.id.bar_plus);
    }

    public void rotate(int i){
        if (i == Right){
            findViewById(R.id.back).setRotationY(180);
            findViewById(R.id.bar_minus).setRotationY(180);
            findViewById(R.id.bar_plus).setRotationY(180);
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int n){
        amount= (int) Math.min(maxAmount,n);
        magicTextView.setText(String.valueOf(amount));
        magicTextView.setStroke(3,Color.BLACK);
        ConstraintLayout.LayoutParams  layoutParams= (LayoutParams) bar_minus.getLayoutParams();
        layoutParams.matchConstraintPercentWidth=(maxAmount-amount)/100f;
        //TODO:Анимация измнения значения
        bar_minus.setLayoutParams(layoutParams);
        bar_plus.setBackgroundTintList(ColorStateList.valueOf(ColorUtils.blendARGB(red,green,
                (float) Math.min(1,amount/maxAmount+0.3))));
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
}
