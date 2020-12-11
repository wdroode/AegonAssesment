package nl.wderoode.assesment.essentials;

import nl.wderoode.assesment.model.MathExpression;
import nl.wderoode.assesment.web.CalculateListRequest;
import nl.wderoode.assesment.web.CalculateListResponse;
import nl.wderoode.assesment.web.CalculatorController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.wderoode.assesment.web.CalculatorController.Operator.ADDITION;
import static nl.wderoode.assesment.web.CalculatorController.Operator.DIVISION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ValidatorTest {

    @Test
    void testValidateParams_WithValidParams_DoesNotThrowException() {
        assertThatCode(() -> Validator.validateParams(5, 5, DIVISION))
                .doesNotThrowAnyException();
    }

    @Test
    void testValidateParams_WithInvalidParams_ThrowsException() {
        assertThatCode(() -> Validator.validateParams(null, 5, ADDITION))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Invalid input, need two numbers and an operator");
    }

    @Test
    void testValidateRequest_WithValidReuest_DoesNotThrowException() {
        var request = List.of(new MathExpression(5, 5, ADDITION));

        assertThatCode(() -> Validator.validateRequest(request))
                .doesNotThrowAnyException();
    }

    @Test
    void testValidateRequest_WithInvalidReuest_ThrowsException() {
        var request = List.of(new MathExpression(null, 5, ADDITION));

        assertThatCode(() -> Validator.validateRequest(request))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("One or more invalid math expressions");
    }
}