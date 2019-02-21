package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.bmstu.nastasia.difur.R;

import java.util.ArrayList;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.InputHolder> {
    private int size;

    private ArrayList<String>   src;
    private ArrayList<FunctionInputListener> input_listeners;

    public FunctionAdapter(int size) {
        update(size);
    }

    public void update(int size) {
        this.size = size;
        src  = new ArrayList<>(size);
        input_listeners = new ArrayList<>(size);
        for (int i = 1; i <= size; ++i) {
            src.add("y" + i + "' = ");
            input_listeners.add(null);
        }

    }

    public void setSize(int size) {
        this.size = size;
    }

    class InputHolder extends RecyclerView.ViewHolder {

        private TextView func_name;
        private TextInputEditText input_func;
        private FunctionInputListener listener;


        InputHolder(View itemView) {
            super(itemView);
            func_name = itemView.findViewById(R.id.adapter_text_view);
            input_func = itemView.findViewById(R.id.adapter_input_fxy);
            listener = new FunctionInputListener(itemView.getContext(), input_func, "f_i(x, y)");
            input_func.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        listener.checkAndSetWarning();
                    }
                }
            });
        }

        boolean checkInput() {
            return listener.checkAndSetWarning();
        }

        FunctionInputListener getListener() {
            return listener;
        }

        public void setTitle(String text) {
            func_name.setText(text);
        }
    }

    public ArrayList<FunctionInputListener> getInputListeners() {
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
        FunctionAdapter.InputHolder viewHolder = new FunctionAdapter.InputHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        holder.setTitle(src.get(position));
        input_listeners.set(position, holder.getListener());
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
