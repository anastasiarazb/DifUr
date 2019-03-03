package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import ru.bmstu.nastasia.difur.R;

import java.util.ArrayList;

public class InitsAdapter extends RecyclerView.Adapter<InitsAdapter.InputHolder> {
    private int size;

    private ArrayList<String>   src;
    private ArrayList<InputHolder> holders;
    private double[] default_inits;

    public InitsAdapter(int size, @Nullable double[] default_inits) {
        update(size, default_inits);
    }

    public void update(int size, @Nullable double[] default_inits) {
        this.size = size;
        this.default_inits = default_inits;
        src  = new ArrayList<>(size);
        holders = new ArrayList<>(size);
        for (int i = 1; i <= size; ++i) {
            src.add("y" + i + "(x0) = ");
            holders.add(null);
        }

    }

    public void setSize(int size) {
        this.size = size;
    }

    class InputHolder extends RecyclerView.ViewHolder {

        private TextView func_name;
        private EditText value_et;


        InputHolder(View itemView) {
            super(itemView);
            func_name = itemView.findViewById(R.id.adapter_tv_initials);
            value_et = itemView.findViewById(R.id.adapter_et_initials);
        }

        public boolean checkInput() {
            return !value_et.getEditableText().toString().isEmpty();
        }

        public Double getValue() {
            return Double.parseDouble(value_et.getEditableText().toString());
        }

        public boolean isEmpty() {
            return value_et.getText() == null || value_et.getText().toString().isEmpty();
        }

        public void setTitle(String text) {
            func_name.setText(text);
        }
        public void setValue(double val) {
            value_et.setText(String.valueOf(val));
        }
    }

    public double[] getValues() {
        double[] values = new double[holders.size()];
        for (int i = 0; i < holders.size(); ++i) {
            values[i] = holders.get(i).getValue();
        }
        return values;
    }

    public boolean checkInput() {
        boolean res = true;
        for (InputHolder holder: holders) {
            res &= holder.checkInput();
        }
        return res;
    }


    @Override
    @NonNull
    public InputHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForInputItem = R.layout.adapter_init_input;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForInputItem, parent, shouldAttachToParentImmediately);
        InputHolder viewHolder = new InputHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        holder.setTitle(src.get(position));
        if (holder.isEmpty() && default_inits != null && default_inits.length > 0) {
            int n = default_inits.length <= position
                    ? default_inits.length : position;
            holder.setValue(default_inits[n]);
        }
        holders.set(position, holder);
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
