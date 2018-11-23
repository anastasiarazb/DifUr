package ru.bmstu.nastasia.difur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mariuszgromada.math.mxparser.*;

public class MainActivity extends AppCompatActivity {

    class Requests {
        public static final int REQUEST_CODE = 0;
        public static final int ANSWER_CODE  = 1;
        public static final int MAP_REQ_CODE = 2;
        public static final int MAP_ANSW_CODE = 3;
        public static final String EXTRA_KEY   = "ValueToPrint";
        public static final String ANSWER_KEY  = "string";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)findViewById(R.id.Tv);
//        Function f = new Function("f(x, y) = x+y");
//        tv.setText(String.valueOf(f.calculate(1, 2)));
//        Button button_theory =  (Button) findViewById(R.id.button_theory_part);
//        button_theory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String query = message.getText().toString();
//
//                Log.d("LOOOOl", "\n\n\nPress\n\n\n\n");
//                Intent childActivityIntent = new Intent(MainActivity.this,
//                        ru.bmstu.nastasia.difur.theory.HelloPage.class);
////                        .putExtra(Requests.EXTRA_KEY, query);
//                if (childActivityIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(childActivityIntent, Requests.REQUEST_CODE);
//                }
//            }
//        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent childActivityIntent = new Intent(MainActivity.this,
                        ru.bmstu.nastasia.difur.solve.TypeChooser.class);
                if (childActivityIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(childActivityIntent, Requests.REQUEST_CODE);
                }
            }
        });
    }
}
