package com.example.gaus_jordan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MatrixActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        Intent intent = getIntent();
        int cols = intent.getIntExtra(MainActivity.EXTRA_COLS, 0);
        int rows = intent.getIntExtra(MainActivity.EXTRA_ROWS, 0);

    }
}