package stud.ntnu.no.calculator.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stud.ntnu.no.calculator.controller.CalculatorController;
import stud.ntnu.no.calculator.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAO {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  private static final Logger logger = LogManager.getLogger(UserDAO.class);

  public int addUser(User user) {
    return jdbcTemplate.update("INSERT INTO users VALUES (?, ?)", user.getUsername(), user.getPassword());
  }

  public Optional<User> getUserByUsername(String username) {
    logger.info("Trying to execute sql");
    String sql = "SELECT * FROM users WHERE username=?";
    Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, username);
    logger.info("Sql executed");
    if (resultMap.isEmpty()) {
      logger.info("No rows found");
      return Optional.empty();
    }
    logger.info("Rows found");
    User user = new User(username, (String) resultMap.get(username));
    return Optional.of(user);
  }

}
