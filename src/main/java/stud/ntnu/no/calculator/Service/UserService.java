package stud.ntnu.no.calculator.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stud.ntnu.no.calculator.DAO.UserDAO;
import stud.ntnu.no.calculator.controller.CalculatorController;
import stud.ntnu.no.calculator.model.User;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserDAO userDAO;
  private static final Logger logger = LogManager.getLogger(UserService.class);


  public boolean areUserCredentialsValid(User user) {
    Optional<User> foundUser = userDAO.getUserByUsername(user.getUsername());
    if (foundUser.isEmpty()) {
      logger.error("No user found");
      return false;
    }
    String storedPassword = foundUser.get().getPassword();
    String givenPassword = user.getPassword();
    logger.info("Stored password: "+storedPassword+". Given password: " + givenPassword);

    return givenPassword.equals(storedPassword);
  }

  public boolean setNewUser(User user) {
    int rowsAffected = userDAO.setNewUser(user);

    return rowsAffected == 1;
  }
}
