package ru.bmstu.nastasia.difur.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.bmstu.nastasia.difur.R;

import java.util.ArrayList;

public class PlotAdapter extends RecyclerView.Adapter<PlotAdapter.PlotHolder>  {

    public PlotAdapter(ArrayList<>)

    class PlotHolder extends RecyclerView.ViewHolder {
        PlotHolder(View itemView) {
            super(itemView);
        }


    }

    @Override
    @NonNull
    public PlotAdapter.PlotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForInputItem = R.layout.adapter_init_input;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForInputItem, parent, shouldAttachToParentImmediately);
        PlotAdapter.PlotHolder viewHolder = new PlotAdapter.PlotHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlotAdapter.PlotHolder holder, int position) {
        /*initialisation*/
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
