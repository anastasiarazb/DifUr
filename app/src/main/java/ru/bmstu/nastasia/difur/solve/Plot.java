package ru.bmstu.nastasia.difur.solve;


import android.graphics.Color;
import android.support.annotation.Nullable;

import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;

import java.util.Arrays;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import ru.bmstu.nastasia.difur.R;

public class Plot extends AppCompatActivity {

    public static class ParamNames {
        final public static String y = "y array";
        final public static String x = "x array";
    }

    private XYPlot plot;

    Double[] y;
    Double[] x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_plot);

        Bundle b = this.getIntent().getExtras();

        if (b == null || !b.containsKey(ParamNames.x) || ! b.containsKey(ParamNames.y)) {
            throw new Error("solve.Plot: Not x or y array are found");
        }
        x = (Double[])b.get(ParamNames.x);
        y = (Double[])b.get(ParamNames.y);
        plot = (XYPlot)findViewById(R.id.XYPlot);

        // create a couple arrays of y-values to plot:
//        Number[] domainLabels = x;
        Number[] series1Numbers = y;

        Number[] domainLabels = new Number[x.length];

        double precision = 100.;

        for (int i = 0; i < x.length; ++i) {
            domainLabels[i] = Math.round(x[i].floatValue() * precision) / precision;
        }

        // turn the above arrays into XYSeries': (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getBaseContext().getString(R.string.plot_series_solution));

        // create formatters to use for drawing a series using LineAndPointRenderer and configure them from xml:
        PixelUtils.init(getBaseContext());
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);
        series1Format.setPointLabelFormatter(null);

        // (optional) add some smoothing to the lines: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));


        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        plot.setDomainBoundaries(x[0], x[x.length-1], BoundaryMode.FIXED);
        plot.getGraph().getDomainGridLinePaint().setColor(Color.TRANSPARENT);
        plot.getGraph().getDomainSubGridLinePaint().setColor(Color.TRANSPARENT);
        plot.getGraph().getGridBackgroundPaint().setColor(Color.WHITE);
        plot.getGraph().position(
                0, HorizontalPositioning.ABSOLUTE_FROM_LEFT,
                0, VerticalPositioning.ABSOLUTE_FROM_TOP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
