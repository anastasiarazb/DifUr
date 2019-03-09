package ru.bmstu.nastasia.difur.solve;


import android.graphics.Color;
import android.support.annotation.Nullable;

import android.util.Log;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;

import java.util.Arrays;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import io.github.kexanie.library.MathView;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.common.Arrays2Strings;

public class SystemPlot extends AppCompatActivity {

    public static class ParamNames {
        final public static String y = "y array";
        final public static String x = "x array";
        final public static String y2 = "y2 array";
        final public static String equation = "equation";
        final public static String user_solution = "user solution";
    }

    private XYPlot plot;

    private Double[][] y;
    private Double[] x;

    private static final Integer[] colors = {
            Color.GREEN,
            Color.BLUE,
            Color.GRAY,
            Color.MAGENTA,
            Color.YELLOW,
            Color.CYAN
    };

    void addSeries(Double[] x, Double[] y, @Nullable String label, Integer color) {
        XYSeries series = new SimpleXYSeries(Arrays.asList(x), Arrays.asList(y), label);
        LineAndPointFormatter seriesFormat =
                new LineAndPointFormatter(color, Color.DKGRAY, Color.BLUE, null);
        seriesFormat.configure(this, R.xml.line_point_formatter_no_color);
        Log.i("SystemPlot.ADD_SERIES", Arrays2Strings.arr1DtoString(y) + label);
        seriesFormat.setPointLabelFormatter(null);
        // (optional) add some smoothing to the lines: http://androidplot.com/smooth-curves-and-androidplot/
        seriesFormat.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        plot.addSeries(series, seriesFormat);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_system_plot);


        Bundle b = this.getIntent().getExtras();

        if (b == null || !b.containsKey(ParamNames.x) || ! b.containsKey(ParamNames.y)) {
            throw new Error("solve.PlotActivity: Not x or y array are found");
        }

        x = (Double[])b.get(ParamNames.x);
        y = (Double[][])b.get(ParamNames.y);
        plot = (XYPlot)findViewById(R.id.system_XYPlot);

        // create a couple arrays of y-values to plot:
//        Number[] domainLabels = x;
        Number[][] series1Numbers = y;

        Number[] domainLabels = new Number[x.length];

        double precision = 100.;

        for (int i = 0; i < x.length; ++i) {
            domainLabels[i] = Math.round(x[i].floatValue() * precision) / precision;
        }

        // turn the above arrays into XYSeries': (Y_VALS_ONLY means use the element index as the x value)
//        XYSeries series1 = new SimpleXYSeries(
//                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getBaseContext().getString(R.string.plot_series_solution));

        // create formatters to use for drawing a series using LineAndPointRenderer and configure them from xml:
        PixelUtils.init(getBaseContext());

//        addSeries(y[1], "1", R.xml.line_point_formatter_with_labels);

        for (int i = 0; i < y.length; ++i) {
            String name = "y" + (i+1);

            addSeries(x, y[i], name, colors[i % colors.length]);
        }

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
