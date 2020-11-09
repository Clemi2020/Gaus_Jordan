package com.example.gaus_jordan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COLS = "com.example.gaus_jordan.EXTRA_COLS";
    public static final String EXTRA_ROWS = "com.example.gaus_jordan.EXTRA_ROWS";
    private Button button;
    private EditText matrix_cols;
    private EditText matrix_rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        matrix_cols = findViewById(R.id.editTextNumber);
        matrix_rows = findViewById(R.id.editTextNumber2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openInputActivity();
            }
        });
    }
    public void openInputActivity(){
        int cols = Integer.parseInt(matrix_cols.getText().toString());
        int rows = Integer.parseInt(matrix_rows.getText().toString());
        Intent intent = new Intent(this, MatrixActivity.class);

        intent.putExtra(EXTRA_COLS,cols);
        intent.putExtra(EXTRA_ROWS,rows);

        startActivity(intent);
    }
}