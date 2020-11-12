package com.example.gaus_jordan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.util.Size;
import android.util.SizeF;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MatrixActivity extends AppCompatActivity {

    private Button button_calc;
    private Button button_showtheway;
    private int colNum;
    private int rowNum;
    private double[][] solution_matrix;
    private GridLayout gridLayoutMatrix;
    private GridLayout gridLayoutMatrixSolved;
    private ScrollView scrollView;
    private TextView display_text;
    private TextView some_text;
    private int ID_MATRIX_START = 0;
    private int ID_MATRIX_SOLVED_START = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        Intent intent = getIntent();
        colNum = intent.getIntExtra(MainActivity.EXTRA_COLS, 4);
        rowNum = intent.getIntExtra(MainActivity.EXTRA_ROWS, 3);

        gridLayoutMatrix = findViewById(R.id.matrix_input);
        gridLayoutMatrixSolved = findViewById(R.id.matrix_output);
        display_text = findViewById(R.id.textView6);
        scrollView = findViewById(R.id.scrollview);
        some_text = findViewById(R.id.text_some);

        createMatrixLayout(gridLayoutMatrix, ID_MATRIX_START);
        createMatrixSolvedLayout(gridLayoutMatrixSolved, ID_MATRIX_SOLVED_START);


        button_calc = findViewById(R.id.button_calculate);
        button_showtheway = findViewById(R.id.button_rechenweg);
        button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridLayoutMatrixSolved.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.INVISIBLE);
                do_GJA();
                if (solution_matrix != null) {
                    display_text.setText("Hier ist die Lösung:");
                    gridLayoutMatrixSolved.setVisibility(View.VISIBLE);
                    button_showtheway.setEnabled(true);
                }
                else {
                    display_text.setText("LGS konnte nicht gelöst werden!");
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        });
        button_showtheway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gridLayoutMatrixSolved.getVisibility() == View.VISIBLE) {
                    display_text.setText("Hier ist der Rechenweg:");
                    gridLayoutMatrixSolved.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    button_showtheway.setText("Lösung");
                }else{
                    display_text.setText("Hier ist die Lösung:");
                    gridLayoutMatrixSolved.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.INVISIBLE);
                    button_showtheway.setText("Rechenweg");
                }
            }
        });
    }

    private void do_GJA(){
        try{
            double[][] to_solve = getMatrixInput(ID_MATRIX_START);
            GJA gja = new GJA();

            solution_matrix = gja.gaussjordanAlg(to_solve);
            some_text.setText(gja.trace);
            if (solution_matrix != null) {
                setSolvedMatrix(solution_matrix);
                //Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e){
            scrollView.setVisibility(View.VISIBLE);
            gridLayoutMatrixSolved.setVisibility(View.INVISIBLE);
            //some_text.setText(Log.getStackTraceString(e));
        }
    }

    //putting the edit text according to row and column index
    private void setPos(EditText editText, GridLayout gridLayout, int row, int column, int cols, int rows) {
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.width = gridLayout.getLayoutParams().width / cols;
        param.height = gridLayout.getLayoutParams().height / rows;;
        param.setGravity(Gravity.CENTER);
        param.rowSpec = GridLayout.spec(row);
        param.columnSpec = GridLayout.spec(column);
        editText.setLayoutParams(param);
    }
    //putting the edit text according to row and column index
    private void setPos(TextView editText, GridLayout gridLayout, int row, int column, int cols, int rows) {
        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
        param.width = gridLayout.getLayoutParams().width / cols;
        param.height = gridLayout.getLayoutParams().height / rows;;
        param.setGravity(Gravity.CENTER);
        param.rowSpec = GridLayout.spec(row);
        param.columnSpec = GridLayout.spec(column);
        editText.setLayoutParams(param);
    }


    //define how many rows and columns to be used in the layout
    private void createMatrixLayout(GridLayout gridLayoutMatrix, int counter_start){
        int counter = counter_start;
        EditText[][] editTexts = new EditText[rowNum][colNum];
        gridLayoutMatrix.setRowCount(rowNum);
        gridLayoutMatrix.setColumnCount(colNum);
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                editTexts[i][j] = new EditText(this);
                editTexts[i][j].setId(counter++);
                editTexts[i][j].setHint("Zahl");
                editTexts[i][j].setText(Integer.toString(counter));
                editTexts[i][j].setKeyListener(DigitsKeyListener.getInstance("0123456789.-"));
                editTexts[i][j].setSingleLine();
                editTexts[i][j].setTextColor(Color.BLACK);
                setPos(editTexts[i][j], gridLayoutMatrix, i, j, colNum, rowNum);
                gridLayoutMatrix.addView(editTexts[i][j]);

                if (j == colNum-1){
                    editTexts[i][j].setBackgroundColor(Color.LTGRAY);
                }
            }
        }
    }

    private void createMatrixSolvedLayout(GridLayout gridLayoutMatrix, int counter_start){
        int counter = counter_start;
        int max_rows = rowNum;
        if (rowNum > colNum-1){
            max_rows = colNum-1;
        }
        TextView[][] editTexts = new TextView[max_rows][colNum];
        gridLayoutMatrix.setRowCount(rowNum);
        gridLayoutMatrix.setColumnCount(colNum);
        for (int i = 0; i < max_rows; i++) {
            for (int j = 0; j < colNum; j++) {
                editTexts[i][j] = new TextView(this);
                editTexts[i][j].setId(counter++);
                editTexts[i][j].setTextColor(Color.BLACK);
                editTexts[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                setPos(editTexts[i][j], gridLayoutMatrix, i, j, colNum, rowNum);
                gridLayoutMatrix.addView(editTexts[i][j]);

                if (j == colNum-1){
                    editTexts[i][j].setBackgroundColor(Color.LTGRAY);
                }
            }
        }
    }


    private void setSolvedMatrix(double[][] matrix_solved){
        int counter = ID_MATRIX_SOLVED_START;
        int max_rows = rowNum;
        if (rowNum > colNum-1){
            max_rows = colNum-1;
        }
        for (int i = 0; i < max_rows; i++) {
            for (int j = 0; j < colNum; j++) {
                TextView editText_Matrix = findViewById(counter);
                editText_Matrix.setText(Double.toString(matrix_solved[i][j]));
                counter++;
            }
        }
    }

    public double[][] getMatrixInput(int counter_start){
        int counter = counter_start;
        double[][] to_solve = new double[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                EditText editText_Matrix = findViewById(counter);
                to_solve[i][j] = Double.parseDouble(editText_Matrix.getText().toString());
                counter++;
            }
        }
        return to_solve;
    }
}