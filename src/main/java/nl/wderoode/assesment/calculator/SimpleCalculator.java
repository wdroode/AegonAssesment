package nl.wderoode.assesment.calculator;

import org.springframework.stereotype.Component;

@Component
public class SimpleCalculator {
    public double add(int a, int b) {
        return a + b;
    }

    public double subtract(int a, int b) {
        return a - b;
    }

    public double multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }

        return ((double) a) / b;
    }
}
