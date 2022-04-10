package com.example.lesson20.logic;

/**
 * @author Bulat Bagaviev
 * @created 05.04.2022
 */

public class Memory {
    private int mValue = 0;

    public void addToValue(int newValue) {
        mValue += newValue;
    }

    public void clearValue() {
        mValue = 0;
    }

    public int getCurrentValue() {
        return mValue;
    }

    public void setNewValue(int memory) {
        mValue = memory;
    }
}
