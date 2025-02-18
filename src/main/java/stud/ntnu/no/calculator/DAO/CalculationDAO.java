package stud.ntnu.no.calculator.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stud.ntnu.no.calculator.model.Calculation;

@Repository
public class CalculationDAO {
  @Autowired
  JdbcTemplate jdbcTemplate;
  private static final Logger logger = LogManager.getLogger(CalculationDAO.class);

  public int addNewCalculation(Calculation calculation, double result) {
    String sql = "INSERT INTO calculation (username, calculation, result) VALUES(?,?,?)";
    return jdbcTemplate.update(sql, calculation.getUsername(), calculation.getCalculation(), result);
  }

}
