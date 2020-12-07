package nl.wderoode.assesment.service;

import nl.wderoode.assesment.calculator.SimpleCalculator;
import org.springframework.stereotype.Service;

import static nl.wderoode.assesment.web.CalculatorController.Operator;

@Service
public class CalculatorService {
    private final SimpleCalculator simpleCalculator;

    public CalculatorService(SimpleCalculator simpleCalculator) {
        this.simpleCalculator = simpleCalculator;
    }

    public double calculate(Integer integer1, Integer integer2, Operator operator) throws ArithmeticException {
        switch (operator) {
            case ADDITION:
                return simpleCalculator.add(integer1, integer2);
            case SUBTRACTION:
                return simpleCalculator.subtract(integer1, integer2);
            case MULTIPLICATION:
                return simpleCalculator.multiply(integer1, integer2);
            case DIVISION:
                return simpleCalculator.divide(integer1, integer2);
            default:
                throw new ArithmeticException("Unsupported operator in expression");
        }
    }
}
