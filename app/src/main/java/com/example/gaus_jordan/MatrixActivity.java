package com.example.gaus_jordan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import com.example.gaus_jordan.GJA;

import static android.widget.RelativeLayout.*;

public class MatrixActivity extends AppCompatActivity {

    private Button button_calc;
    int colNum;
    int rowNum;
    GridLayout gridLayout;
    TextView display_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        Intent intent = getIntent();
        colNum = intent.getIntExtra(MainActivity.EXTRA_COLS, 3) +1;
        rowNum = intent.getIntExtra(MainActivity.EXTRA_ROWS, 3);


        EditText[][] editTexts = new EditText[rowNum][colNum];
        gridLayout = findViewById(R.id.matrix_input);

        //define how many rows and columns to be used in the layout
        gridLayout.setRowCount(rowNum);
        gridLayout.setColumnCount(colNum);
        int counter = 0;
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                editTexts[i][j] = new EditText(this);
                editTexts[i][j].setId(counter++);
                editTexts[i][j].setText(Integer.toString(counter));
                setPos(editTexts[i][j], i, j, colNum, rowNum);
                gridLayout.addView(editTexts[i][j]);
                if (j == colNum-1){
                    editTexts[i][j].setBackgroundColor(Color.LTGRAY);
                }
            }
        }
        TextView display_text = findViewById(R.id.text_some);
        display_text.setMovementMethod(new ScrollingMovementMethod());
        display_text.setText("w: " + gridLayout.getLayoutParams().width + " h: " + gridLayout.getLayoutParams().height
         + "\n rows: " + rowNum + " cols: " + colNum
        );

        button_calc = findViewById(R.id.button_calculate);
        button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_GJA();
            }
        });
    }

    private void do_GJA(){
        TextView display_text = findViewById(R.id.text_some);
        int counter = 0;
        double[][] to_solve = new double[rowNum][colNum];
        try{
            for (int i = 0 ; i < rowNum; i++){
                for (int j = 0; j < colNum; j++){
                    EditText editText = findViewById(counter);
                    to_solve[i][j] = Double.parseDouble(editText.getText().toString());
                    counter++;
                }
            }
            GJA gja = new GJA();
            gja.gaussjordanAlg(to_solve);

            if(gja.trace == null) {
                Toast.makeText(getApplicationContext(),"Failed...",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_SHORT).show();

                display_text.setText(gja.trace);

            }
        } catch (Exception e){
            display_text.setText(Log.getStackTraceString(e));
        }
    }


    //putting the edit text according to row and column index
    private void setPos(EditText editText, int row, int column, int cols, int rows) {
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.width = gridLayout.getLayoutParams().width / cols;
        param.height = gridLayout.getLayoutParams().height / rows;;
        param.setGravity(Gravity.CENTER);
        param.rowSpec = GridLayout.spec(row);
        param.columnSpec = GridLayout.spec(column);
        editText.setLayoutParams(param);
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789.-"));
        editText.setSingleLine();
    }
}