package stud.ntnu.no.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import stud.ntnu.no.calculator.Service.UserService;
import stud.ntnu.no.calculator.model.Calculation;
import stud.ntnu.no.calculator.model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stud.ntnu.no.calculator.model.User;

@org.springframework.web.bind.annotation.RestController
public class CalculatorController {
  @Autowired
  UserService userService;
  private static final Logger logger = LogManager.getLogger(CalculatorController.class);
  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/calculate")
  public ResponseEntity<Result> calculateResult(@RequestBody Calculation calculation) {
    logger.info("Requested calculation: " + calculation.getCalculation());
    Result result;
    try {
      result = calculation.calculate();
      logger.info("calculation successful, result: " + result.getResult());
    } catch (Exception e) {
      logger.error("Error: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(result);
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/login")
  public ResponseEntity<?> loginToCalculator(@RequestBody User user) {
    logger.info("Trying to log in username: " + user.getUsername() + " password: " + user.getPassword());
    if (!userService.areUserCredentialsValid(user)) {
      logger.error("Couldnt find user with the given credentials");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    logger.info("Successfully logged in to user " + user.getUsername());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
