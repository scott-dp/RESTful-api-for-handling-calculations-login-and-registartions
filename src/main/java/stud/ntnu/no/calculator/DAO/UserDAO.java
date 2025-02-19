package stud.ntnu.no.calculator.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stud.ntnu.no.calculator.controller.CalculatorController;
import stud.ntnu.no.calculator.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDAO {
  @Autowired
  private JdbcTemplate jdbcTemplate;
  private static final Logger logger = LogManager.getLogger(UserDAO.class);

  public Optional<User> getUserByUsername(String username) {
    logger.info("Trying to execute sql to get user with username " + username);
    String sql = "SELECT * FROM users WHERE username=?";
    Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, username);
    logger.info("Rows found: " + resultMap.size());
    logger.info("Found password: " + resultMap.get("password"));
    if (resultMap.isEmpty()) {
      return Optional.empty();
    }
    User user = new User(username, (String) resultMap.get("password"));
    return Optional.of(user);
  }

  /**
   * Sets a new user in the database, comes from a person registering a new account
   *
   * @param user the user to add to db
   * @return 1 if row was inserted, 0 if row wasn't inserted
   */
  public int setNewUser(User user) {
    logger.info("Trying to set new user in database with username " + user.getUsername());
    String sql = "INSERT IGNORE INTO users (username, password) VALUES(?,?)"; //Ignores the insert if primary key already exists
    return jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
  }

}
