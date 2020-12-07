package nl.wderoode.assesment.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.wderoode.assesment.essentials.Validator;
import nl.wderoode.assesment.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/api/v1/calculate")
    public ResponseEntity<String> calculate(@RequestParam Integer integer1, Integer integer2, Operator operator) {
        try {
            Validator.validateParams(integer1, integer2, operator);

            double result = calculatorService.calculate(integer1, integer2, operator);

            return new ResponseEntity<>(String.format("{ \"result\": %f }", result), HttpStatus.OK);
        } catch (ArithmeticException e) {
            return new ResponseEntity<>(String.format("{ \"error\": %s }", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Operator {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION;
    }
}
