package nl.wderoode.assesment.service;

import nl.wderoode.assesment.calculator.SimpleCalculator;
import nl.wderoode.assesment.model.MathExpression;
import nl.wderoode.assesment.web.CalculateListRequest;
import nl.wderoode.assesment.web.CalculateListResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static nl.wderoode.assesment.web.CalculatorController.Operator;

@Service
public class CalculatorService {
    private final SimpleCalculator simpleCalculator;

    public CalculatorService(SimpleCalculator simpleCalculator) {
        this.simpleCalculator = simpleCalculator;
    }

    public double calculate(Integer integer1, Integer integer2, Operator operator) throws ArithmeticException {
        if(operator == null) {
            throw new ArithmeticException("Operator cannot be null");
        }

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

    public CalculateListResponse calculateList(List<MathExpression> mathExpressionList) {
        var results = new ArrayList<CalculateListResponse.Result>(mathExpressionList.size());
        mathExpressionList.forEach(me -> {
            try {
                double result = this.calculate(me.getInteger1(), me.getInteger2(), me.getOperator());
                results.add(mapResult(me, result));
            } catch (ArithmeticException e) {
                results.add(mapResult(me, null));
            }
        });

        return new CalculateListResponse(results);
    }

    private CalculateListResponse.Result mapResult(MathExpression mathExpression, Double result) {
        return new CalculateListResponse.Result(mathExpression, result);
    }
}
