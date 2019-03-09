package ru.bmstu.nastasia.difur.ui.listeners;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import ru.bmstu.nastasia.difur.R;
import org.mariuszgromada.math.mxparser.Function;

public class FunctionInputListener implements TextWatcher {

    private Context  mContext;
    private TextInputEditText mEdittextview;
    private String value;
    private Function mFunction;
    private String   mErrormessage;
    private String innerFuncName;
    private String func_name;

    public FunctionInputListener(Context context, TextInputEditText edittextview, @Nullable String func_headline) {
        this.mContext = context;
        this.mEdittextview = edittextview;
        this.mErrormessage = context.getString(R.string.warning_incorrect);
        this.innerFuncName = func_headline != null
                ? func_headline
                : "f(x)";
    }

    public void setFuncName(String name) {
        func_name = name;
    }

    public FunctionInputListener(Context context, TextInputEditText edittextview, int arg_num) {
        this.mContext = context;
        this.mEdittextview = edittextview;
        this.mErrormessage = context.getString(R.string.warning_incorrect);
        StringBuilder sb = new StringBuilder();
        sb.append("f(x");
        for (int i = 1; i <= arg_num; ++i) {
            sb.append(", y").append(i);
        }
        sb.append(')');
        this.innerFuncName = sb.toString();
    }

    public Boolean checkVal() {
        value = mEdittextview.getEditableText().toString().replace('÷', '/');
        if (value.trim().isEmpty()) {
            return null;
        }
        mFunction = new Function(innerFuncName + "=" + value);
        return mFunction.checkSyntax();
    }

    @Override
    public void afterTextChanged(Editable s) {
//        if (checkVal() == Boolean.FALSE) {
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

    public Function getFunction() {
        return checkVal()
                ? mFunction
                : null;
    }

    public String getFunctionString() {
        return func_name + " = " + value;
    }

    public boolean checkAndSetWarning() {
        if (checkVal() == Boolean.TRUE) {
            return true;
        } else {
            mEdittextview.setError(mErrormessage);
            return false;
        }
    }
}