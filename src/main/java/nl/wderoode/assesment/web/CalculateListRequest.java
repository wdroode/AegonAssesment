package nl.wderoode.assesment.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.wderoode.assesment.model.MathExpression;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalculateListRequest {
    private List<MathExpression> mathExpressions;
}
