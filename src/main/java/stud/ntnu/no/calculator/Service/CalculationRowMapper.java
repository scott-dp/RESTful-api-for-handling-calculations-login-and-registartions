package stud.ntnu.no.calculator.Service;

import stud.ntnu.no.calculator.model.Calculation;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalculationRowMapper implements RowMapper<Calculation> {
  @Override
  public Calculation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new Calculation(
        resultSet.getString("calculation"),
        resultSet.getString("username")
    );
  }
}
