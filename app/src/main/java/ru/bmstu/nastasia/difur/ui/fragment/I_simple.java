package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.ui.activity.PlotActivity;
import ru.bmstu.nastasia.difur.solve.RungeKutta;
import ru.bmstu.nastasia.difur.ui.activity.MainActivity;

import org.mariuszgromada.math.mxparser.Function;
import ru.bmstu.nastasia.difur.ui.listeners.FunctionInputListener;
import ru.bmstu.nastasia.difur.ui.listeners.NumberInputListener;
import ru.bmstu.nastasia.difur.common.PlotDataContainer.ParamNames;



public class I_simple extends Fragment {

    private static final String TAG = I_simple.class.getSimpleName();
    private Button button_res;
    private TextInputEditText input_fxy;
    private EditText input_y1;
    private EditText input_y2;
//    private EditText input_x1;
    private EditText input_x2;

    private Context context;

    private FunctionInputListener listener_fxy;
    private FunctionInputListener listener_solution;
    private NumberInputListener listener_y1;
    private NumberInputListener listener_y2;
//    private NumberInputListener listener_x1;
    private NumberInputListener listener_x2;
    private CheckBox input_solution_cb;
    private TextInputEditText input_solution;
    private CardView solution_cv;


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
        solution_cv = view.findViewById(R.id.solution_cv_1);
        solution_cv.setVisibility(View.GONE);
        input_solution_cb = view.findViewById(R.id.solution_cb_1);
        input_solution_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    solution_cv.setVisibility(View.VISIBLE);
                } else {
                    solution_cv.setVisibility(View.GONE);
                }
            }
        });
        button_res = view.findViewById(R.id.button);
        button_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInput()) {
                    Toast.makeText(getContext(), R.string.warning_fill_all, Toast.LENGTH_LONG).show();
                    return;
                }

                Function fxy = listener_fxy.getFunction();
                Double y1 = listener_y1.getValue();
                Double y2 = listener_y2.getValue();
//                Double x1 = listener_x1.getValue();
                Double x2 = listener_x2.getValue();

                RungeKutta solver = new RungeKutta(fxy, y1, y2, x2);
                Intent childActivityIntent = new Intent(getActivity(),
                        PlotActivity.class)
                        .putExtra(ParamNames.x, solver.getX())
                        .putExtra(ParamNames.y, solver.getY())
                        .putExtra(ParamNames.equation, input_fxy.getEditableText().toString());

                if (input_solution_cb.isChecked()
                        && (listener_solution.checkVal() != null)) {
                    // Generate user solution points
                    Function solution = listener_solution.getFunction();
                    Double[] x = solver.getX();
                    Double[] y = new Double[x.length];
                    for (int i = 0; i < y.length; ++i) {
                        y[i] = solution.calculate(x[i]);
                    }
                    childActivityIntent
                            .putExtra(ParamNames.y2, y)
                            .putExtra(ParamNames.user_solution, input_solution.getEditableText().toString());
                }

                if (childActivityIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(childActivityIntent, MainActivity.Requests.REQUEST_CODE);
                }
            }
        });

        input_fxy = view.findViewById(R.id.input_i_simple_fxy);
        input_fxy.setHint("f(x, y)");
        input_y1 = view.findViewById(R.id.input_i_simple_y1);
        input_y2 = view.findViewById(R.id.input_i_simple_y2);
//        input_x1 = view.findViewById(R.id.input_i_simple_x1);
        input_x2 = view.findViewById(R.id.input_i_simple_x2);
        input_solution = view.findViewById(R.id.input_solution_1);

        context = getContext();

        if (context == null) {
            throw new RuntimeException("I_simple.initFields: Null context");
        }

        listener_fxy = new FunctionInputListener(context, input_fxy, "f(x, y)");
        listener_solution = new FunctionInputListener(context, input_solution, "y(x)");
        listener_y1 = new NumberInputListener(context, input_y1);
        listener_y2 = new NumberInputListener(context, input_y2);
//        listener_x1 = new NumberInputListener(context, input_x1);
        listener_x2 = new NumberInputListener(context, input_x2);

        input_fxy.addTextChangedListener(listener_fxy);
        input_y1.addTextChangedListener(listener_y1);
        input_y2.addTextChangedListener(listener_y2);
//        input_x1.addTextChangedListener(listener_x1);
        input_x2.addTextChangedListener(listener_x2);

        input_solution = view.findViewById(R.id.input_solution_1);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "_____START");

    }

    private boolean checkInput() {
        return listener_fxy.checkAndSetWarning()
                & listener_y1.checkAndSetWarning()
                & listener_y2.checkAndSetWarning()
//                & listener_x1.checkAndSetWarning()
                & listener_x2.checkAndSetWarning()
                & (!input_solution_cb.isChecked()
                    || (listener_solution.checkVal() == null) // empty optional string, pass
                    || listener_solution.checkAndSetWarning());
    }
}
