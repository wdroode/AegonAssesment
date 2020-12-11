package nl.wderoode.assesment.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.wderoode.assesment.essentials.Validator;
import nl.wderoode.assesment.model.MathExpression;
import nl.wderoode.assesment.service.CalculatorService;
import nl.wderoode.assesment.web.CalculateListResponse.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final ObjectMapper objectMapper;

    public CalculatorController(CalculatorService calculatorService, ObjectMapper objectMapper) {
        this.calculatorService = calculatorService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @GetMapping("/api/v1/calculate")
    public ResponseEntity<String> calculate(@RequestParam Integer integer1, Integer integer2, Operator operator) {
        try {
            Validator.validateParams(integer1, integer2, operator);

            var expression = new MathExpression(integer1, integer2, operator);

             var result = new Result(expression, calculatorService.calculate(expression));

            return okResponse("" + objectMapper.writeValueAsString(result));
        } catch (ArithmeticException e) {
            return badResponse(e.getMessage());
        }
    }

    @SneakyThrows
    @GetMapping("/api/v1/calculations")
    public ResponseEntity<String> caluclations() {
        return okResponse(objectMapper.writeValueAsString(calculatorService.getCalculations()));
    }

    @SneakyThrows
    @PostMapping("/api/v1/calculatelist")
    public ResponseEntity<String> calculateList(@RequestBody List<MathExpression> requestBody) {
        try {
            Validator.validateRequest(requestBody);

            var result = calculatorService.calculateList(requestBody);

            return okResponse(objectMapper.writeValueAsString(result));
        } catch (ArithmeticException e) {
            return badResponse(e.getMessage());
        }
    }

    // todo: should have own class, so does the enum
    private ResponseEntity<String> okResponse(String message) {
        return response(String.format("{ \"result\": %s }", message), HttpStatus.OK);
    }

    private ResponseEntity<String> badResponse(String message) {
        return response(String.format("{ \"error\": %s }", message), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> response(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(message, httpStatus);
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
