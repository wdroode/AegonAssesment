package nl.wderoode.assesment.service;

import nl.wderoode.assesment.calculator.SimpleCalculator;
import nl.wderoode.assesment.model.MathExpression;
import nl.wderoode.assesment.repository.CalculationRepository;
import nl.wderoode.assesment.web.CalculateListResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static nl.wderoode.assesment.web.CalculateListResponse.Result;

@Service
public class CalculatorService {
    private final SimpleCalculator simpleCalculator;
    private final CalculationRepository calculationRepository;

    public CalculatorService(SimpleCalculator simpleCalculator, CalculationRepository calculationRepository) {
        this.simpleCalculator = simpleCalculator;
        this.calculationRepository = calculationRepository;
    }

    public double calculate(MathExpression mathExpression) throws ArithmeticException {
        if (mathExpression.getOperator() == null) {
            throw new ArithmeticException("Operator cannot be null");
        }

        double result;
        switch (mathExpression.getOperator()) {
            case ADDITION:
                result = simpleCalculator.add(mathExpression.getInteger1(), mathExpression.getInteger2());
                break;
            case SUBTRACTION:
                result = simpleCalculator.subtract(mathExpression.getInteger1(), mathExpression.getInteger2());
                break;
            case MULTIPLICATION:
                result = simpleCalculator.multiply(mathExpression.getInteger1(), mathExpression.getInteger2());
                break;
            case DIVISION:
                result = simpleCalculator.divide(mathExpression.getInteger1(), mathExpression.getInteger2());
                break;
            default:
                throw new ArithmeticException("Unsupported operator in expression");
        }

        calculationRepository.insertCalculationResult(mathExpression, result);
        return result;
    }

    public CalculateListResponse calculateList(List<MathExpression> mathExpressionList) {
        var results = new ArrayList<Result>(mathExpressionList.size());
        mathExpressionList.forEach(mathExpression -> {
            try {
                double result = this.calculate(mathExpression);
                results.add(mapResult(mathExpression, result));
            } catch (ArithmeticException e) {
                results.add(mapResult(mathExpression, null));
            }
        });

        return new CalculateListResponse(results);
    }

    private Result mapResult(MathExpression mathExpression, Double result) {
        return new Result(mathExpression, result);
    }

    public List<Result> getCalculations() {
        return calculationRepository.selectResults();
    }
}
