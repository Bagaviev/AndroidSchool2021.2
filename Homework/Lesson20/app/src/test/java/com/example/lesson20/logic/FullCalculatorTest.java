package com.example.lesson20.logic;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

/**
 * @author Bulat Bagaviev
 * @created 05.04.2022
 */

public class FullCalculatorTest {
    private static final int ARGUMENT = 10;
    private static final int ADDITION_RESULT = 110;
    private static final int ADDITION_RESULT_TRIPLE = 130;
    private Calculator mCalculator;

    private Memory mMemory;

    // @Mock
    // private Memory mMemory;

    @Before
    public void setUp() {
        // MockitoAnnotations.openMocks(this);

        mMemory = Mockito.mock(Memory.class);
        mCalculator = new Calculator(mMemory);
    }

    @Test
    public void testAddition() {

        // Arrange
        Mockito.when(mMemory.getCurrentValue()).thenReturn(ADDITION_RESULT);

        int expectedResult = ADDITION_RESULT;

        // Act
        int actualResult = mCalculator.addition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        Mockito.verify(mMemory, times(1)).addToValue(ARGUMENT);
    }

    @Test
    public void testTripleAddition() {

        // Arrange
        Mockito.when(mMemory.getCurrentValue()).thenReturn(ADDITION_RESULT_TRIPLE);

        int expectedResult = ADDITION_RESULT_TRIPLE;

        // Act
        int actualResult = mCalculator.tripleAddition(ARGUMENT);

        // Assert
        Truth.assertThat(actualResult).isEqualTo(expectedResult);
        Mockito.verify(mMemory, times(3)).addToValue(ARGUMENT);
    }
}
