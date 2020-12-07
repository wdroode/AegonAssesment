package nl.wderoode.assesment.essentials;

import nl.wderoode.assesment.web.CalculatorController;

public class Validator {
    public static void validateParams(Integer integer1, Integer integer2, CalculatorController.Operator operator) {
        if (integer1 == null
                || integer2 == null
                || operator == null) {
            throw new ArithmeticException("Invalid input, need two numbers and an operator");
        }
    }
}
