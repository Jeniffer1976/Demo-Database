package sg.edu.rp.c346.id21025290.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetTasks, btnClear;
    TextView tvTasks;
    ListView lvTasks;
    EditText etDesc, etDate;
    ToggleButton tbOrder;
    private static String order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.buttonInsert);
        btnGetTasks = findViewById(R.id.buttonGetTasks);
        tvTasks = findViewById(R.id.textViewTasks);
        lvTasks = findViewById(R.id.listViewTasks);
        btnClear = findViewById(R.id.buttonClear);
        etDesc = findViewById(R.id.editTextDesc);
        etDate = findViewById(R.id.editTextDate);
        tbOrder = findViewById(R.id.toggleButtonOrder);


        DBHelper db = new DBHelper(MainActivity.this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.insertTask(etDesc.getText().toString(), etDate.getText().toString());
            }
        });
        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> data = db.getTaskContent();
                ArrayList<Task> dataList = db.getTasks();
                db.close();

                String txt = "";

                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i + ". " + data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }

                tvTasks.setText(txt);
                ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataList);
                lvTasks.setAdapter(adapter);
            }
        });

        tbOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tbOrder.isChecked()) {
                    order = "ASC";

                } else {
                    order = "DESC";
                }
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearList();
                tvTasks.setText("");

                ArrayList<Task> dataList = db.getTasks();
                ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataList);
                lvTasks.setAdapter(adapter);
                dataList.clear();
                adapter.notifyDataSetChanged();

            }
        });
    }
    public static String getOrder(){
        return order;
    }
}