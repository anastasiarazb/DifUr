package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import org.mariuszgromada.math.mxparser.Function;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.solve.SystemRungeKutta;
import ru.bmstu.nastasia.difur.solve.SystemPlot;
import ru.bmstu.nastasia.difur.ui.activity.MainActivity;
import ru.bmstu.nastasia.difur.examples.*;

import java.util.ArrayList;

public class System_simple extends Fragment {

    private Button button_solve;
    private Button button_ok;
    private EditText row_number_et;
    private EditText x1_et;
    private EditText x2_et;
    private RecyclerView rows_rv;
    private RecyclerView inits_rv;
    private FunctionAdapter function_adapter;
    private InitsAdapter inits_adapter;
    private int rows_number;
    private Context context;

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

        x1_et = view.findViewById(R.id.system_et_x1);
        x1_et.setText(String.valueOf(default_data.x1));
        x2_et = view.findViewById(R.id.system_et_x2);
        x2_et.setText(String.valueOf(default_data.x2));
        row_number_et = view.findViewById(R.id.system_row_number_et);
        rows_number = 2;  // default

        function_adapter = new FunctionAdapter(rows_number, default_data.func_raws);
        rows_rv = view.findViewById(R.id.system_simple_rv);
        rows_rv.setAdapter(function_adapter);
        rows_rv.setLayoutManager(new LinearLayoutManager(this.context));


        inits_adapter = new InitsAdapter(rows_number, default_data.inits);
        inits_rv = view.findViewById(R.id.system_inits_rv);
        inits_rv.setAdapter(inits_adapter);
        inits_rv.setLayoutManager(new LinearLayoutManager(this.context));

        button_ok = view.findViewById(R.id.system_ok_button);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRowsNumber();
                function_adapter.update(rows_number, null);
                function_adapter.notifyDataSetChanged();
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
                ArrayList<FunctionInputListener> listeners = function_adapter.getInputListeners();
                boolean is_ok = true;
                ArrayList<Function> functions = new ArrayList<>(listeners.size());
                for (FunctionInputListener listener: listeners) {
                    Boolean val = listener.checkVal();
                    if (val != null) {
                        is_ok &= val;
                        functions.add(listener.getFunction());
                    }
                }
                double[] inits = inits_adapter.getValues();
                double x1 = Double.parseDouble(x1_et.getText().toString());
                double x2 = Double.parseDouble(x2_et.getText().toString());
                if (!is_ok) {
                    Toast.makeText(context, R.string.warning_incorrect, Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (Function f: functions) {
                    sb.append(f.getFunctionExpressionString()).append('\n');
                }
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
                sb = new StringBuilder();
                for (double d: inits) {
                    sb.append(d).append('\n');
                }
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();

                SystemRungeKutta solver = new SystemRungeKutta(functions, inits, x1, x2, 100);
                Intent childActivityIntent = new Intent(getActivity(),
                        ru.bmstu.nastasia.difur.solve.SystemPlot.class)
                        .putExtra(SystemPlot.ParamNames.x, solver.getX())
                        .putExtra(SystemPlot.ParamNames.y, solver.getY());

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
        for (int i = 0; i < rows_number; ++i) {
            res &= ((FunctionAdapter.InputHolder)rows_rv.findViewHolderForAdapterPosition(i)).checkInput();
        }
        res &= inits_adapter.checkInput();
        res &= !x1_et.getText().toString().isEmpty();
        res &= !x2_et.getText().toString().isEmpty();
        return res;
    }

}
