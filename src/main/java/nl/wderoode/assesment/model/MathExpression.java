package nl.wderoode.assesment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static nl.wderoode.assesment.web.CalculatorController.Operator;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MathExpression {
    private Integer integer1;
    private Integer integer2;
    private Operator operator;
}
