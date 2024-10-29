package com.example.testjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator;
    private TextView lastOperator;
    private TextView mainText;
    private TextView lastNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculator = new Calculator();
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonClear = findViewById(R.id.button_clear);
        buttonClear.setOnClickListener(v -> {
            calculator.clear();
            lastOperator.setText("");
            mainText.setText("");
            lastNumber.setText("");
            }
        );

        lastOperator = findViewById(R.id.last_operator);
        mainText = findViewById(R.id.main_text);
        lastNumber = findViewById(R.id.last_number);

        initializeButtonOperand();
        initializeButtonOperator();
    }

    public void onButtonClickReturnResult(View view) {
        calculator.addOperand();
        Double result = calculator.calculate();
        lastOperator.setText("");

        if (result != null) {
            mainText.setText(String.valueOf(result));
            lastNumber.setText("");
        } else {
            lastNumber.setText("Ошибка");
            Toast.makeText(this, "Не удалось выполнить расчет", Toast.LENGTH_SHORT).show();
        }

        calculator.clear();
    }

    private void initializeButtonOperator() {
        int[] buttonIds = {
                R.id.button_divide,
                R.id.button_minus,
                R.id.button_multiply,
                R.id.button_plus,
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                if (!calculator.getStringNumber().isEmpty()) {
                    calculator.addOperand();
                    String buttonText = button.getText().toString();
                    calculator.addOperator(buttonText);
                    lastOperator.setText(buttonText);
                    mainText.setText(calculator.returnTextVariant());
                    lastNumber.setText("");
                } else if (!calculator.isLastInputOperator()) {
                    String buttonText = button.getText().toString();
                    calculator.addOperator(buttonText);
                    lastOperator.setText(buttonText);
                    mainText.setText(calculator.returnTextVariant());
                }
            });
        }
    }

    private void initializeButtonOperand() {
        int[] buttonIds = {
                R.id.button_zero,
                R.id.button_one,
                R.id.button_two,
                R.id.button_three,
                R.id.button_four,
                R.id.button_five,
                R.id.button_six,
                R.id.button_seven,
                R.id.button_eight,
                R.id.button_nine
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(v -> {
                String buttonText = ((Button) v).getText().toString();
                calculator.addStringOperands(buttonText);
                lastNumber.setText(calculator.getStringNumber());
                mainText.setText(calculator.returnTextVariant());
            });
        }
    }
}
