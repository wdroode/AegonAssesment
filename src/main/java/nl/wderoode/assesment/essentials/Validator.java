package nl.wderoode.assesment.essentials;

import nl.wderoode.assesment.web.CalculateListRequest;

import java.util.Optional;

import static nl.wderoode.assesment.web.CalculatorController.Operator;

public class Validator {
    public static void validateParams(Integer integer1, Integer integer2, Operator operator) {
        if (!validate(integer1, integer2, operator)) {
            throw new ArithmeticException("Invalid input, need two numbers and an operator");
        }
    }

    public static void validateRequest(CalculateListRequest calculateListRequest) {
        Optional<Boolean> result = calculateListRequest.getMathExpressions().stream()
                .map(e -> validate(e.getInteger1(), e.getInteger2(), e.getOperator()))
                .filter(e -> e.equals(false))
                .findFirst();

        result.ifPresent(e -> { throw new ArithmeticException("One or more invalid math expressions"); });
    }

    private static boolean validate(Integer integer1, Integer integer2, Operator operator) {
        return integer1 != null && integer2 != null && operator != null;
    }
}
