package com.example.testjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Calculator {
    private List<Double> operands;
    private List<String> operators;
    private String temp_number;
    private Map<String, BiFunction<Double, Double, Double>> operations;
    private StringBuilder textVariant;
    private boolean lastInputIsOperator;

    public Calculator() {
        operands = new ArrayList<>();
        operators = new ArrayList<>();
        operations = new HashMap<>();
        temp_number = "";

        operations.put("*", (a, b) -> a * b);
        operations.put("/", (a, b) -> a / b);
        operations.put("+", (a, b) -> a + b);
        operations.put("-", (a, b) -> a - b);

        textVariant = new StringBuilder();
        lastInputIsOperator = false;
    }

    public void addStringOperands(String number) {
        temp_number += number;
        lastInputIsOperator = false;
    }

    public void addOperand() {
        if (!temp_number.isEmpty()) {
            textVariant.append(temp_number);
            operands.add(Double.parseDouble(temp_number));
            temp_number = "";
        }
    }

    public void addOperator(String operator) {
        if (!lastInputIsOperator) {
            addOperand();
            textVariant.append(operator);
            operators.add(operator);
            lastInputIsOperator = true;
        }
    }

    public boolean isLastInputOperator() {
        return lastInputIsOperator;
    }

    public double calculate() {
        if (operands.isEmpty()) {
            return 0;
        }

        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            BiFunction<Double, Double, Double> operation = operations.get(operator);

            if (operation != null && i + 1 < operands.size()) {
                double currentOperand = operands.get(i);
                double nextOperand = operands.get(i + 1);

                double result = operation.apply(currentOperand, nextOperand);
                operands.set(i, result);
                operands.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }

        return operands.get(0);
    }

    public void clear() {
        operands.clear();
        operators.clear();
        temp_number = "";
        textVariant.setLength(0);
        lastInputIsOperator = false;
    }

    public String returnTextVariant() {
        return textVariant.toString();
    }

    public String getStringNumber() {
        return temp_number;
    }
}
