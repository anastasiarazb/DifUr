package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.solve.Plot;
import ru.bmstu.nastasia.difur.solve.RungeKutta;
import ru.bmstu.nastasia.difur.ui.activity.MainActivity;

import org.mariuszgromada.math.mxparser.Function;


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
    private NumberInputListener listener_y1;
    private NumberInputListener listener_y2;
//    private NumberInputListener listener_x1;
    private NumberInputListener listener_x2;



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

                Function fxy = listener_fxy.getFunction();
                Double y1 = listener_y1.getValue();
                Double y2 = listener_y2.getValue();
//                Double x1 = listener_x1.getValue();
                Double x2 = listener_x2.getValue();

                RungeKutta solver = new RungeKutta(fxy, y1, y2, x2);
                Intent childActivityIntent = new Intent(getActivity(),
                        ru.bmstu.nastasia.difur.solve.Plot.class)
                        .putExtra(Plot.ParamNames.x, solver.getX())
                        .putExtra(Plot.ParamNames.y, solver.getY());
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

        context = getContext();

        if (context == null) {
            throw new RuntimeException("I_simple.initFields: Null context");
        }

        listener_fxy = new FunctionInputListener(context, input_fxy);
        listener_y1 = new NumberInputListener(context, input_y1);
        listener_y2 = new NumberInputListener(context, input_y2);
//        listener_x1 = new NumberInputListener(context, input_x1);
        listener_x2 = new NumberInputListener(context, input_x2);

        input_fxy.addTextChangedListener(listener_fxy);
        input_y1.addTextChangedListener(listener_y1);
        input_y2.addTextChangedListener(listener_y2);
//        input_x1.addTextChangedListener(listener_x1);
        input_x2.addTextChangedListener(listener_x2);

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
                & listener_x2.checkAndSetWarning();
    }
}
