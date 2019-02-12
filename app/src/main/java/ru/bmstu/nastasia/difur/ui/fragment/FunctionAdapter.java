package ru.bmstu.nastasia.difur.ui.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.Function;
import ru.bmstu.nastasia.difur.R;

import java.util.ArrayList;
import java.util.List;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.InputHolder> {
    private int id;

    private int size;

//    private ArrayList<Function> data;
    private ArrayList<String>   src;

    public FunctionAdapter(int size) {
//        data = new ArrayList<>(size);
        src  = new ArrayList<>(size);
        this.size = size;
        for (int i = 1; i <= size; ++i) {
            src.add("y" + i + "' = ");
        }
    }

    public void setSize(int size) {
        this.size = size;
    }

    class InputHolder extends RecyclerView.ViewHolder {

        //    ImageView sign;
        private TextView func_name;
        private TextInputEditText input_func;
        private FunctionInputListener listener;


        public InputHolder(View itemView) {
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

        public boolean checkInput() {
            return listener.checkAndSetWarning();
        }

        public void getFunction() {
            listener.getFunction();
        }

        public void setTitle(String text) {
            func_name.setText(text);
        }
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
