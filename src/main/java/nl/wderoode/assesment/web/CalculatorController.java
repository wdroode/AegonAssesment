package nl.wderoode.assesment.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.wderoode.assesment.essentials.Validator;
import nl.wderoode.assesment.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final ObjectMapper objectMapper;

    public CalculatorController(CalculatorService calculatorService, ObjectMapper objectMapper) {
        this.calculatorService = calculatorService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/api/v1/calculate")
    public ResponseEntity<String> calculate(@RequestParam Integer integer1, Integer integer2, Operator operator) {
        try {
            Validator.validateParams(integer1, integer2, operator);

            double result = calculatorService.calculate(integer1, integer2, operator);

            return okResponse("" + result);
        } catch (ArithmeticException e) {
            return badResponse(e.getMessage());
        }
    }

    @SneakyThrows
    @GetMapping("/api/v1/calculatelist")
    public ResponseEntity<String> calculateList(@RequestBody CalculateListRequest requestBody) {
        try {
            Validator.validateRequest(requestBody);

            var result = calculatorService.calculateList(requestBody.getMathExpressions());

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
