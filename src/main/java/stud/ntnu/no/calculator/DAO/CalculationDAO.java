package stud.ntnu.no.calculator.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stud.ntnu.no.calculator.Service.CalculationRowMapper;
import stud.ntnu.no.calculator.model.Calculation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CalculationDAO {
  @Autowired
  JdbcTemplate jdbcTemplate;
  private static final Logger logger = LogManager.getLogger(CalculationDAO.class);

  public int addNewCalculation(Calculation calculation, double result) {
    String sql = "INSERT INTO calculation (username, calculation, result) VALUES(?,?,?)";
    return jdbcTemplate.update(sql, calculation.getUsername(), calculation.getCalculation(), result);
  }

  public List<Calculation> getUsersCalculation(String username) {
    logger.info("Trying to execute sql to find calculations of user " + username);
    String sql = "SELECT * FROM calculation WHERE username=?";
    List<Calculation> calculationList = jdbcTemplate.query(sql, new CalculationRowMapper(), username);
    logger.info("Found " + calculationList.size() + " rows");
    return calculationList;
  }


}
