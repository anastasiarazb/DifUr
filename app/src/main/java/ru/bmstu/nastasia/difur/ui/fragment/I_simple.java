package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.ui.activity.MainActivity;
import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.Map;


public class I_simple extends Fragment {

    private static final String TAG = I_simple.class.getSimpleName();
    private Button button_res;
    private TextInputEditText input_fxy;
    private EditText input_yx;
    private EditText input_y0;
    private EditText input_x1;
    private EditText input_x2;

    private String res_fxy;
    private Double res_yx;
    private Double res_y0;
    private Double res_x1;
    private Double res_x2;

    private Bundle bundle_inputs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_du_1_simple, container, false);

        initFields(view);


        return view;
    }

    private void initFields(View view) {
        button_res = view.findViewById(R.id.button);
        button_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInput()) {
                    Toast.makeText(getContext(), R.string.warning_fill_all, Toast.LENGTH_LONG).show();
                    return;
                }
                Intent childActivityIntent = new Intent(getActivity(),
                        ru.bmstu.nastasia.difur.solve.TypeChooser.class);
                if (childActivityIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(childActivityIntent, MainActivity.Requests.REQUEST_CODE);
                }
            }
        });
        input_yx = view.findViewById(R.id.input_i_simple_yx);
        input_y0 = view.findViewById(R.id.input_i_simple_y0);
        input_x1 = view.findViewById(R.id.input_i_simple_x1);
        input_x2 = view.findViewById(R.id.input_i_simple_x2);
        input_fxy = view.findViewById(R.id.input_i_simple_fxy);
        input_fxy.setHint("f(x, y)");

        input_yx.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                firstName.setError(null);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                Value= new Function("f(x, y) = x+y");
                input_yx.setError(null);
                input_yx.

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "_____START");

//        updateFilesList();
    }

    private boolean checkInput() {
        res_fxy = input_fxy.getEditableText().toString();
        res_yx = input_yx.getEditableText().toString();
        res_y0 = input_y0.getEditableText().toString();
        res_x1 = input_x1.getEditableText().toString();
        res_x2 = input_x2.getEditableText().toString();
        return    !res_fxy.isEmpty()
                & !res_yx.isEmpty()
                & !res_y0.isEmpty()
                & !res_x1.isEmpty()
                & !res_x2.isEmpty();
    }
}
