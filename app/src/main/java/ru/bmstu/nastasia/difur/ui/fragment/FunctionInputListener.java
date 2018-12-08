package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import ru.bmstu.nastasia.difur.R;
import org.mariuszgromada.math.mxparser.Function;

public class FunctionInputListener implements TextWatcher {

    private Context  mContext;
    private TextInputEditText mEdittextview;
    private String value;
    private Function mFunction;
    private String   mErrormessage;

    FunctionInputListener(Context context, TextInputEditText edittextview) {
        this.mContext = context;
        this.mEdittextview = edittextview;
        this.mErrormessage = context.getString(R.string.warning_incorrect);
    }

    Boolean checkVal() {
        value = mEdittextview.getEditableText().toString();
        if (value.isEmpty()) {
            return null;
        }
        mFunction = new Function("f(x, y)=" + value);
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

    boolean checkAndSetWarning() {
        if (checkVal() == Boolean.TRUE) {
            return true;
        } else {
            mEdittextview.setError(mErrormessage);
            return false;
        }
    }
}