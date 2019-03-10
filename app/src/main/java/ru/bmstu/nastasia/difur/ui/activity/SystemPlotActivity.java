package ru.bmstu.nastasia.difur.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.common.PlotDataContainer;
import ru.bmstu.nastasia.difur.ui.adapters.PlotAdapter;

public class SystemPlotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlotAdapter  adapter;
    private Toolbar toolbar;
    private Bundle parent_bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_system_plot);
        toolbar = findViewById(R.id.toolbar_system_plots);
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

        adapter = new PlotAdapter(data);
        recyclerView = findViewById(R.id.system_plots_rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_switch2one_plot:
                Intent childActivityIntent = new Intent(this,
                        SystemOnePlotActivity.class)
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
        getMenuInflater().inflate(R.menu.menu_system_plots, menu);
        return true;
    }
}
