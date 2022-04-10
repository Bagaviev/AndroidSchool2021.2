package com.example.lesson20.logic;

/**
 * @author Bulat Bagaviev
 * @created 05.04.2022
 */

public class Calculator {
    private final Memory mMemory;

    public Calculator(Memory calculatorWithMemory) {
        mMemory = calculatorWithMemory;
    }

    public int addition(int number) {
        addNumberToMemory(number);
        return getNumberFromMemory();
    }

    public int tripleAddition(int number) {
        addition(number);
        addition(number);
        return addition(number);
    }

    private void addNumberToMemory(int number) {
        mMemory.addToValue(number);
    }

    private int getNumberFromMemory() {
        return mMemory.getCurrentValue();
    }
}
