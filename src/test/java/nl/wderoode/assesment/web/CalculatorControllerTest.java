package nl.wderoode.assesment.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.wderoode.assesment.model.MathExpression;
import nl.wderoode.assesment.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static nl.wderoode.assesment.web.CalculatorController.Operator.ADDITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CalculatorControllerTest {
    private CalculatorController calculatorController;
    private CalculatorService calculatorService;

    @BeforeEach
    void beforeEach() {
        calculatorService = mock(CalculatorService.class);
        calculatorController = new CalculatorController(calculatorService, new ObjectMapper());
    }

    @Test
    void testCalculate_WithValidExpression_ReturnsHttpStatusOk() {
        when(calculatorService.calculate(any())).thenReturn(35.0);

        ResponseEntity<String> response = calculatorController.calculate(15, 25, ADDITION);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // todo: should be tested with jsonassert
        assertThat(response.getBody()).isEqualTo("{ \"result\": {\"mathExpression\":{\"integer1\":15,\"integer2\":25,\"operator\":\"ADDITION\"},\"result\":35.0} }");
    }

    @Test
    void testCalculate_WithInvalidExpression_ReturnsHttpStatusBadRequest() {
        ResponseEntity<String> response = calculatorController.calculate(15, 25, null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // todo: should be tested with jsonassert
        assertThat(response.getBody()).isEqualTo("{ \"error\": Invalid input, need two numbers and an operator }");
    }
}