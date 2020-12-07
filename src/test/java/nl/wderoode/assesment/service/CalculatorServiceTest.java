package nl.wderoode.assesment.service;

import nl.wderoode.assesment.calculator.SimpleCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static nl.wderoode.assesment.web.CalculatorController.Operator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CalculatorServiceTest {
    private CalculatorService calculatorService;

    @BeforeEach
    void beforeEach() {
        calculatorService = new CalculatorService(new SimpleCalculator());
    }

    @Test
    void testCalculate_WithAdditionOf15And5_Returns20() {
        double result = calculatorService.calculate(15, 5, ADDITION);

        assertThat(result).isEqualTo(20);
    }

    @Test
    void testCalculate_WithSubtractionOf20By35_ReturnsMinus15() {
        double result = calculatorService.calculate(20, 35, SUBTRACTION);

        assertThat(result).isEqualTo(-15);
    }

    @Test
    void testCalculate_WithMultiplicationOf3By15_Returns55() {
        double result = calculatorService.calculate(3, 15, MULTIPLICATION);

        assertThat(result).isEqualTo(45);
    }

    @Test
    void testCalculate_WithDivisionOf45By6_Returns55() {
        double result = calculatorService.calculate(3, 15, DIVISION);

        assertThat(result).isEqualTo(0.2);
    }

    @Test
    void testCalculate_WithDivisionOf0By6_ThrowsArithmicException() {
        assertThatCode(() -> calculatorService.calculate(6, 0, DIVISION))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Cannot divide by zero");
    }
}