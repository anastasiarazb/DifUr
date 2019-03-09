package ru.bmstu.nastasia.difur.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.common.PlotDataContainer;
import ru.bmstu.nastasia.difur.common.PlotDataContainer.ParamNames;
import ru.bmstu.nastasia.difur.ui.adapters.PlotAdapter;

public class SystemPlotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlotAdapter  adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_system_plot);


        Bundle b = this.getIntent().getExtras();

        if (b == null || !b.containsKey(ParamNames.x) || ! b.containsKey(ParamNames.y)) {
            throw new Error("solve.PlotActivity: Not x or y array are found");
        }

        ArrayList<PlotDataContainer> data = PlotDataContainer.genArray(b);

        adapter = new PlotAdapter(data);
        recyclerView = findViewById(R.id.system_plots_rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
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
