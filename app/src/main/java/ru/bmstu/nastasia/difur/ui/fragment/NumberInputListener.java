package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import ru.bmstu.nastasia.difur.R;

public class NumberInputListener implements TextWatcher {
    private Context  mContext;
    private EditText mEdittextview;
    private Double   mValue;
    private String   mErrormessage;

    NumberInputListener(Context context, EditText edittextview) {
        this.mContext = context;
        this.mEdittextview = edittextview;
        this.mErrormessage = context.getString(R.string.warning_incorrect);
    }

    boolean checkValue() {
        String src = mEdittextview.getEditableText().toString();
        if (src.isEmpty()) {
            return false;
        }
        try {
            mValue = Double.parseDouble(src);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
//        if (!checkValue()) {
//            mEdittextview.setError(mContext.getString(R.string.warning_number));
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public Double getValue() {
        return checkValue()
                ? mValue
                : null;
    }

    boolean checkAndSetWarning() {
        if (checkValue() == Boolean.TRUE) {
            return true;
        } else {
            mEdittextview.setError(mErrormessage);
            return false;
        }
    }
}