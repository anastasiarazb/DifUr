package ru.bmstu.nastasia.difur.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.Function;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.ui.listeners.FunctionInputListener;

import java.util.ArrayList;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.InputHolder> {

    private int size;

    public enum FunctionType {
        DIFF_EQUATION,
        Y_FROM_X
    }

    private ArrayList<String>      src;
    private ArrayList<InputHolder> input_holders;
    private String[]     default_functions;
    private String       funcname_formatstring;
    private String       func_argnames;
    private FunctionType func_type;

    public FunctionAdapter(int size, @NonNull FunctionType func_type, @Nullable String funcname_formatstring,
                           @Nullable String[] default_functions) {
        if (funcname_formatstring == null) {
            funcname_formatstring = "f%d";
        }
        this.funcname_formatstring = funcname_formatstring;
        this.func_type = func_type;
        update(size, default_functions);
    }

    public void update(int size, @Nullable String[] default_functions) {
        this.size = size;
        this.default_functions = default_functions;
        switch (func_type) {
            case DIFF_EQUATION:
                this.func_argnames = FunctionInputListener.HeadlineGenerator.f_x_y1_yn(size);
                break;
            case Y_FROM_X:
                this.func_argnames = FunctionInputListener.HeadlineGenerator.f_x;
        }
        src  = new ArrayList<>(size);
        input_holders = new ArrayList<>(size);

        for (int i = 1; i <= size; ++i) {
            src.add(String.format(funcname_formatstring, i) + " = ");
            input_holders.add(null);
        }

    }

    public class InputHolder extends RecyclerView.ViewHolder {

        private TextView func_name;
        private TextInputEditText input_func;
        private FunctionInputListener listener;


        InputHolder(View itemView, String func_argnames) {
            super(itemView);
            func_name = itemView.findViewById(R.id.adapter_text_view);
            input_func = itemView.findViewById(R.id.adapter_input_fxy);
            listener = new FunctionInputListener(itemView.getContext(), input_func, func_argnames);
            input_func.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        listener.checkAndSetWarning();
                    }
                }
            });
        }

        public boolean checkInputAndSetWarning() {
            return listener.checkAndSetWarning();
        }

        FunctionInputListener getListener() {
            return listener;
        }

        public void setTitle(String text) {
            func_name.setText(text);
            listener.setFuncName(text.replaceAll("=","").trim());
        }

        public void setValue(String text) {
            input_func.setText(text);
        }

        public boolean isEmpty() {
            return input_func.getText() == null || input_func.getText().toString().trim().isEmpty();
        }
    }

    public boolean checkInputAndSetWarnings() {
        boolean res = true;
        for (InputHolder holder: input_holders) {
            res &= holder.checkInputAndSetWarning();
        }
        return res;
    }

    public ArrayList<Function> getFunctions() {
        ArrayList<Function> functions = new ArrayList<>(size);
        for (InputHolder holder: input_holders) {
            functions.add(holder.listener.getFunction());
        }
        return functions;
    }

    public String[] getFunctionsStrings() {
        String[] sources = new String[size];
        for (int i = 0; i < size; ++i) {
            InputHolder holder = input_holders.get(i);
            sources[i] = holder.listener.getFunctionString();
        }
        return sources;
    }

    public boolean hasEmpty() {
        boolean res = false;
        for (InputHolder holder: input_holders) {
            res |= holder.isEmpty();
        }
        return res;
    }



    public ArrayList<FunctionInputListener> getInputListeners() {
        ArrayList<FunctionInputListener> input_listeners = new ArrayList<>(size);
        for (InputHolder holder: input_holders) {
            input_listeners.add(holder.getListener());
        }
        return input_listeners;
    }

    @Override
    @NonNull
    public InputHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForInputItem = R.layout.adapter_func_input;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForInputItem, parent, shouldAttachToParentImmediately);
        FunctionAdapter.InputHolder viewHolder = new FunctionAdapter.InputHolder(view, func_argnames);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        holder.setTitle(src.get(position));
        if (holder.isEmpty() && default_functions != null && default_functions.length > 0) {
            int n = default_functions.length <= position
                    ? default_functions.length : position;
            holder.setValue(default_functions[n]);
            Log.i("FuncAd.onBindViewHolder", position + ' ' + default_functions[n]);
        }
        input_holders.set(position, holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return size;
    }

}
