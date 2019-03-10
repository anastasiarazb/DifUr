package ru.bmstu.nastasia.difur.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import com.androidplot.ui.HorizontalPositioning;
import com.androidplot.ui.VerticalPositioning;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.*;

import java.util.ArrayList;
import java.util.Arrays;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import io.github.kexanie.library.MathView;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.common.Arrays2Strings;
import ru.bmstu.nastasia.difur.common.PlotDataContainer;
import ru.bmstu.nastasia.difur.common.PlotDataContainer.ParamNames;

public class SystemOnePlotActivity extends AppCompatActivity {

    private XYPlot plot;
    private String[] equations;
    private MathView equations_tex;
    private Bundle parent_bundle;
    Toolbar toolbar;

    private Double[] x;

    private static final Integer[] colors = {
            Color.GREEN,
            Color.BLUE,
            Color.GRAY,
            Color.MAGENTA,
            Color.YELLOW,
            Color.CYAN
    };

    String prettyEquationsOutput() {
        StringBuilder sb = new StringBuilder();
        for (String s: equations) {
            sb.append("$$").append(s).append("$$\n");
        }
        return sb.toString();
    }

    void addSeries(Double[] x, Double[] y, @Nullable String label, Integer color) {
        XYSeries series = new SimpleXYSeries(Arrays.asList(x), Arrays.asList(y), label);
        LineAndPointFormatter seriesFormat =
                new LineAndPointFormatter(color, Color.DKGRAY, Color.BLUE, null);
        seriesFormat.configure(this, R.xml.line_point_formatter_no_color);
        Log.i("SystemPlotAct.addSeries", Arrays2Strings.arr1DtoString(y) + label);
        seriesFormat.setPointLabelFormatter(null);
        // (optional) add some smoothing to the lines: http://androidplot.com/smooth-curves-and-androidplot/
        seriesFormat.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
        plot.addSeries(series, seriesFormat);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_system_one_plot);

        toolbar = findViewById(R.id.toolbar_system_one_plot);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Решение");
        }


        parent_bundle = this.getIntent().getExtras();

        if (parent_bundle == null) {
            throw new Error("solve.PlotActivity: null Bundle");
        }

        ArrayList<PlotDataContainer> data = PlotDataContainer.genArray(parent_bundle);
        equations = parent_bundle.getStringArray(ParamNames.equation);
        equations_tex = findViewById(R.id.tex_all_solutions);
        equations_tex.setText(prettyEquationsOutput());
        plot = findViewById(R.id.system_XYPlot);
        // create formatters to use for drawing a series using LineAndPointRenderer and configure them from xml:
        PixelUtils.init(getBaseContext());
        x = data.get(0).getX();
        for (int i = 0; i < data.size(); ++i) {
            String name = "y" + (i+1);
            addSeries(x, data.get(i).getY(), name, colors[i % colors.length]);
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

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_switch2multiple_plots:
                Intent childActivityIntent = new Intent(this,
                        SystemPlotActivity.class)
                        .putExtras(parent_bundle);

                if (childActivityIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(childActivityIntent, MainActivity.Requests.REQUEST_CODE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_system_one_plot, menu);
        return true;
    }
}