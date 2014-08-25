package com.zc.phoneview.phoneview;

import android.content.Context;
import android.text.*;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * 8/25/14  9:42 AM
 * Created by JustinZhang.
 * 格式自动变为
 * 137 1100 1100
 */
public class PhoneView extends EditText {
    private static final String TAG = "PhoneView";
    private Context mContext;
    private OnPhoneChangeListener mListener;

    public interface OnPhoneChangeListener{
        void onChange(String s);
    }

    public PhoneView(Context context) {
        super(context);
        shareConstruction(context);
    }

    public PhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        shareConstruction(context);
    }

    public PhoneView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        shareConstruction(context);
    }

    private void shareConstruction(Context context) {
        mContext = context;
        setInputType(InputType.TYPE_CLASS_PHONE);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        addTextChangedListener(new PhoneTextWather());
    }

    public void setTextListener(OnPhoneChangeListener mListener) {
        this.mListener = mListener;
    }

    private class PhoneTextWather implements TextWatcher {
        private boolean delete = false;
        private int beforeCount = 0;
        private int afterCount = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            beforeCount = s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            afterCount = s.length();
            Log.e(TAG, "before: " + beforeCount + "  after: " + afterCount);
            if (beforeCount > afterCount) {
                delete = true;
            } else {
                delete = false;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s.toString())) {
                if(mListener!=null){
                    mListener.onChange("");
                }
                return;
            }
            if (!delete) {
                String str = s.toString();

                for (int i = 0; i < str.length(); i++) {
                    if (i == 3 || i == 8) {
                        if (str.charAt(i) != ' ') {
                            s.insert(i, " ");
                        }
                    }
                }
            }
            if(mListener!=null){
                mListener.onChange(removeSpace(s.toString()));
            }
        }
    }

    private String removeSpace(String s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);
            if(c!=' '){
                sb.append(c);
            }
        }
        
        return sb.toString();
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if(!(watcher instanceof PhoneTextWather)){
            throw new RuntimeException("Can not add TextWatcher");
        }
        super.addTextChangedListener(watcher);
    }

    @Override
    public void setInputType(int type) {
        type = InputType.TYPE_CLASS_PHONE;
        super.setInputType(type);
    }
}
