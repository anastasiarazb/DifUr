package ru.bmstu.nastasia.difur.solve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.github.kexanie.library.MathView;
import ru.bmstu.nastasia.difur.R;
import ru.bmstu.nastasia.difur.ui.activity.PlotActivity;

public class SolveHello extends AppCompatActivity {

    TextView input;
    Button button_submit;
    Button button_solve;
    MathView tex_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solve_hello);
        input = (TextView)findViewById(R.id.input);
        button_submit = (Button)findViewById(R.id.button_submit);
        button_solve = (Button)findViewById(R.id.button_solve);
        tex_input = (MathView)findViewById(R.id.tex_input);
//        tex_input.setText("$$j$$");
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String formula = input.getText().toString();
                Log.e("EEEEEEEE", "EEEE");
                tex_input.setText("$$"+formula+"$$");
                tex_input.setVisibility(View.GONE);
                tex_input.setVisibility(View.VISIBLE);
//                tex_input.reload();
//                SolveHello.this.setContentView(R.layout.solve_hello);
//                SolveHello.this.recreate();
            }
        });
        button_solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent childActivityIntent = new Intent(ru.bmstu.nastasia.difur.solve.SolveHello.this,
                        PlotActivity.class);
                if (childActivityIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(childActivityIntent, 0);
                }
            }
        });

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
