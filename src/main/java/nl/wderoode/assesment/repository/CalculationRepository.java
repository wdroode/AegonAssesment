package nl.wderoode.assesment.repository;

import nl.wderoode.assesment.model.MathExpression;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static nl.wderoode.assesment.web.CalculateListResponse.Result;
import static nl.wderoode.assesment.web.CalculatorController.Operator;

@Service
public class CalculationRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CalculationRepository(@Qualifier("postgresNamedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public boolean insertCalculationResult(MathExpression mathExpression, double result) {
        String sql = "INSERT INTO calculations (integer1, integer2, operator, result) " +
                "VALUES (:integer1, :integer2, :operator, :result)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("integer1", mathExpression.getInteger1());
        params.addValue("integer2", mathExpression.getInteger2());
        params.addValue("operator", mathExpression.getOperator().toString());
        params.addValue("result", result);

        int rows = namedParameterJdbcTemplate.update(sql, params);

        return rows != 1;
    }

    public List<Result> selectResults() {
        String sql = "SELECT * FROM calculations";

        return namedParameterJdbcTemplate.query(sql, resultMapper);
    }

    private RowMapper<Result> resultMapper = (rs, rowNum) -> {
        MathExpression mathExpression = new MathExpression(
                rs.getInt("integer1"),
                rs.getInt("integer2"),
                Operator.valueOf(rs.getString("operator")));
        return new Result(mathExpression, rs.getDouble("result"));
    };
}
