package com.zc.phoneview.phoneview;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 8/25/14  10:32 AM
 * Created by JustinZhang.
 * 137 5522 0234
 */
public class PhoneFilter implements InputFilter{
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        if(dstart < dend ){
            //增加
            if(dend == 3){
                source = source+" ";
            }
        }

        return source;
    }
}
