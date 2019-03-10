package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.support.v7.widget.RecyclerView;
import org.mariuszgromada.math.mxparser.Function;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.common.PlotDataContainer;
import ru.bmstu.nastasia.difur.solve.SystemRungeKutta;
import ru.bmstu.nastasia.difur.ui.activity.SystemOnePlotActivity;
import ru.bmstu.nastasia.difur.ui.activity.SystemPlotActivity;
import ru.bmstu.nastasia.difur.ui.activity.MainActivity;
import ru.bmstu.nastasia.difur.examples.*;
import ru.bmstu.nastasia.difur.ui.adapters.FunctionAdapter;
import ru.bmstu.nastasia.difur.ui.adapters.InitsAdapter;
import ru.bmstu.nastasia.difur.ui.listeners.FunctionInputListener;

import java.util.ArrayList;

public class System_simple extends Fragment {

    private Button button_solve;
    private Button button_ok;
    private CheckBox check_box;
    private CardView solutions_cv;
    private EditText row_number_et;
    private EditText x1_et;
    private EditText x2_et;
    private RecyclerView rows_rv;
    private RecyclerView solutions_rv;
    private RecyclerView inits_rv;
    private FunctionAdapter function_adapter;
    private FunctionAdapter solutions_adapter;
    private InitsAdapter inits_adapter;
    private int rows_number;
    private Context context;

    private final String Y_EQ_NAME = "y%d'";
    private final String Y_RES_NAME = "y_res%d";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_system_simple, container, false);

        initFields(view);


        return view;
    }

    private void initFields(View view) {
        context = getContext();

        EquationSystem default_data = EquationSystem.TEST2;

        solutions_cv = view.findViewById(R.id.system_solution_cv);
        solutions_cv.setVisibility(View.GONE);

        x1_et = view.findViewById(R.id.system_et_x1);
        x1_et.setText(String.valueOf(default_data.x1));
        x2_et = view.findViewById(R.id.system_et_x2);
        x2_et.setText(String.valueOf(default_data.x2));
        row_number_et = view.findViewById(R.id.system_row_number_et);
        rows_number = 2;  // default

        function_adapter = new FunctionAdapter(rows_number, default_data.func_raws, Y_EQ_NAME);
        rows_rv = view.findViewById(R.id.system_simple_rv);
        rows_rv.setAdapter(function_adapter);
        rows_rv.setLayoutManager(new LinearLayoutManager(this.context));

        solutions_adapter = new FunctionAdapter(rows_number, default_data.results_raws, Y_RES_NAME);
        solutions_rv = view.findViewById(R.id.system_solutions_rv);
        solutions_rv.setAdapter(solutions_adapter);
        solutions_rv.setLayoutManager(new LinearLayoutManager(this.context));


        inits_adapter = new InitsAdapter(rows_number, default_data.inits);
        inits_rv = view.findViewById(R.id.system_inits_rv);
        inits_rv.setAdapter(inits_adapter);
        inits_rv.setLayoutManager(new LinearLayoutManager(this.context));

        check_box = view.findViewById(R.id.solution_cb_n);

        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    solutions_cv.setVisibility(View.VISIBLE);
                } else {
                    solutions_cv.setVisibility(View.GONE);
                }
            }
        });

        button_ok = view.findViewById(R.id.system_ok_button);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRowsNumber();
                function_adapter.update(rows_number, null, Y_EQ_NAME);
                function_adapter.notifyDataSetChanged();
                solutions_adapter.update(rows_number, null, Y_RES_NAME);
                solutions_adapter.notifyDataSetChanged();
                inits_adapter.update(rows_number, null);
                inits_adapter.notifyDataSetChanged();

            }
        });

        button_solve = view.findViewById(R.id.system_solve_button);
        button_solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkFunctions()) {
                    Toast.makeText(context, R.string.warning_incorrect, Toast.LENGTH_SHORT).show();
                    return;
                }
//                ArrayList<FunctionInputListener> listeners = function_adapter.getInputListeners();
//                boolean is_ok = true;
//                ArrayList<Function> functions = new ArrayList<>(listeners.size());
//                String[] func_strings = new String[listeners.size()];
//                for (int i = 0; i < listeners.size(); ++i) {
//                    FunctionInputListener listener = listeners.get(i);
//                    Boolean val = listener.checkVal();
//                    if (val != null) {
//                        is_ok &= val;
//                        functions.add(listener.getFunction());
//                        func_strings[i] = listener.getFunctionString();
//                    }
//                }
                ArrayList<Function> functions    = function_adapter.getFunctions();
                String[]            func_strings = function_adapter.getFunctionsStrings();

                double[] inits = inits_adapter.getValues();
                double x1 = Double.parseDouble(x1_et.getText().toString());
                double x2 = Double.parseDouble(x2_et.getText().toString());
//                if (!is_ok) {
//                    Toast.makeText(context, R.string.warning_incorrect, Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                StringBuilder sb = new StringBuilder();
//                for (Function f: functions) {
//                    sb.append(f.getFunctionExpressionString()).append('\n');
//                }
//                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
//                sb = new StringBuilder();
//                for (double d: inits) {
//                    sb.append(d).append('\n');
//                }
//                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();

                SystemRungeKutta solver = new SystemRungeKutta(functions, inits, x1, x2, 100);
                Intent childActivityIntent = new Intent(getActivity(),
//                        SystemOnePlotActivity.class)
                        SystemPlotActivity.class)
                        .putExtra(PlotDataContainer.ParamNames.x, solver.getX())
                        .putExtra(PlotDataContainer.ParamNames.y, solver.getY())
                        .putExtra(PlotDataContainer.ParamNames.equation, func_strings);

                if (childActivityIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(childActivityIntent, MainActivity.Requests.REQUEST_CODE);
                }


            }
        });


        if (context == null) {
            throw new RuntimeException("I_simple.initFields: Null context");
        }

    }

    private int updateRowsNumber() {
        rows_number = Integer.parseInt(row_number_et.getText().toString());
        return rows_number;
    }

    private boolean checkFunctions() {
        boolean res = true;
        res &= function_adapter.checkInputAndSetWarnings();
        res &= inits_adapter.checkInput();
        res &= !x1_et.getText().toString().isEmpty();
        res &= !x2_et.getText().toString().isEmpty();
        res &= (!check_box.isChecked()
                || solutions_adapter.checkInputAndSetWarnings());
        return res;
    }

}
