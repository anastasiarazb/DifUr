package ru.bmstu.nastasia.difur.theory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ru.bmstu.nastasia.difur.R;

public class HelloPage extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theory_hello);

//        textView = (TextView) findViewById(R.id.textView2);
//        editText = (EditText) findViewById(R.id.searchQuery);
//        button   = (Button)   findViewById(R.id.buttonSearch);

        Intent parentIntent = getIntent();

        findViewById(R.id.eq_type1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String query = message.getText().toString();

                Log.d("LOOOOl", "\n\n\nPress\n\n\n\n");
                Intent childActivityIntent = new Intent(ru.bmstu.nastasia.difur.theory.HelloPage.this,
                        ru.bmstu.nastasia.difur.theory.Th1.class);
                if (childActivityIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(childActivityIntent, 0);
                }
            }
        });



//        if (parentIntent.hasExtra(Requests.EXTRA_KEY)) {
//            String value = parentIntent.getStringExtra(Requests.EXTRA_KEY);
//            editText.setHint("Maybe you want to search for:\n" + String.valueOf(value));
//
//        }

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String query = editText.getText().toString();
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY, query);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
////                Intent answer = new Intent()
////                        .putExtra(Requests.ANSWER_KEY, query);
////                setResult(Requests.ANSWER_CODE, answer);
//                finish();
//            }
//        });
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
