package nl.wderoode.assesment.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.wderoode.assesment.model.MathExpression;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalculateListResponse {
    private List<Result> results;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private MathExpression mathExpression;
        private Double result;
    }
}
