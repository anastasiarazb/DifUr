package ru.bmstu.nastasia.difur.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;
import io.github.kexanie.library.MathView;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.common.Arrays2Strings;
import ru.bmstu.nastasia.difur.common.PlotDataContainer;

import java.util.ArrayList;
import java.util.Arrays;

public class PlotAdapter extends RecyclerView.Adapter<PlotAdapter.PlotHolder>  {

    private ArrayList<PlotDataContainer> data;

    public PlotAdapter(@NonNull ArrayList<PlotDataContainer> data) {
        this.data = data;
    }

    class PlotHolder extends RecyclerView.ViewHolder {

        private XYPlot plot;

        private Double[] y;
        private Double[] y2;
        private Double[] x;

        private MathView tex_equation;
        private MathView tex_user_solution;

        private Context context;

        private int formatter = R.xml.line_point_formatter_with_labels;

        PlotHolder(View itemView) {
            super(itemView);
            tex_equation = itemView.findViewById(R.id.tex_solution_1);
            tex_user_solution = itemView.findViewById(R.id.tex_solution_user_1);
            plot = itemView.findViewById(R.id.XYPlot);
            context = itemView.getContext();
        }

        void setData(PlotDataContainer data, int number) {
            tex_equation.setText("$$" + data.getEquation() + "$$");
            x = data.getX();
            y = data.getY();
            y2 = data.getY2();
            PixelUtils.init(context);
            String name = data.getEquation().split("=")[0].trim().replaceAll("'", "");
            addSeries(x, y, name, R.xml.line_point_formatter_with_labels);
            if (data.hasUserSolution()) {
                tex_user_solution.setText("$$" + data.getUserSolution() + "$$");
                itemView.findViewById(R.id.user_solution_container).setVisibility(View.VISIBLE);
                addSeries(x, y2, "y" + number+ "_user", formatter);
            } else {
                itemView.findViewById(R.id.user_solution_container).setVisibility(View.GONE);
            }
            plot.setDomainBoundaries(x[0], x[x.length-1], BoundaryMode.FIXED);
            plot.getGraph().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
            plot.getGraph().getDomainSubGridLinePaint().setColor(Color.TRANSPARENT);
            plot.getGraph().getGridBackgroundPaint().setColor(Color.WHITE);
            plot.getGraph().position(
                    0, HorizontalPositioning.ABSOLUTE_FROM_LEFT,
                    0, VerticalPositioning.ABSOLUTE_FROM_TOP);

        }

        void addSeries(Double[] x, Double[] y, @Nullable String label, int formatter) {
            XYSeries series = new SimpleXYSeries(
                    Arrays.asList(x), Arrays.asList(y), label);
            LineAndPointFormatter seriesFormat =
                    new LineAndPointFormatter(context, formatter);
            seriesFormat.setPointLabelFormatter(null);
            // (optional) add some smoothing to the lines: http://androidplot.com/smooth-curves-and-androidplot/
            seriesFormat.setInterpolationParams(
                    new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
            plot.addSeries(series, seriesFormat);

        }


    }

    @Override
    @NonNull
    public PlotAdapter.PlotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForInputItem = R.layout.adapter_solve_plot;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForInputItem, parent, shouldAttachToParentImmediately);
        PlotAdapter.PlotHolder viewHolder = new PlotAdapter.PlotHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlotAdapter.PlotHolder holder, int position) {
        int array_index_to_natural_number = position + 1;
        holder.setData(data.get(position), array_index_to_natural_number);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
