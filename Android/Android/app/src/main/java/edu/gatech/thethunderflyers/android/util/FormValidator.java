package edu.gatech.thethunderflyers.android.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public class FormValidator implements TextWatcher, View.OnFocusChangeListener {
    private TextView tv;
    private String name;

    public FormValidator(TextView tv, String name) {
        this.tv = tv;
        this.name = name;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        String text = tv.getText().toString();
        if (TextUtils.isEmpty(text)) {
            tv.setError(name + " cannot be empty!");
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (!b) {
            afterTextChanged(((TextView) view).getEditableText());
        }
    }
}