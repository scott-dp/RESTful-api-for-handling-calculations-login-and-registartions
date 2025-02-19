package stud.ntnu.no.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stud.ntnu.no.calculator.Service.CalculationService;
import stud.ntnu.no.calculator.Service.UserService;
import stud.ntnu.no.calculator.model.Calculation;
import stud.ntnu.no.calculator.model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stud.ntnu.no.calculator.model.User;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class CalculatorController {
  @Autowired
  UserService userService;
  @Autowired
  CalculationService calculationService;

  private static final Logger logger = LogManager.getLogger(CalculatorController.class);
  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/calculate")
  public ResponseEntity<Result> calculateResultAndStoreInDatabase(@RequestBody Calculation calculation) {
    logger.info("Requested calculation: " + calculation.getCalculation() + " for user " + calculation.getUsername());
    Result result;
    boolean wasCalculationAdded;
    try {
      result = calculationService.calculate(calculation);
      logger.info("calculation successful, result: " + result.getResult());
      //Store in db using CalculationService
      logger.info("Storing calculation in database");
      wasCalculationAdded = calculationService.addCalculationToDatabase(calculation, result.getResult());
    } catch (Exception e) {
      logger.error("Error: " + e.getMessage());
      return ResponseEntity.badRequest().build();
    }
    if (!wasCalculationAdded) {
      logger.error("Couldn't add calculation to database");
    } else {
      logger.info("Calculation added to database");
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

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/register")
  public ResponseEntity<?> registerNewUser(@RequestBody User user) {
    logger.info("Trying to create new user " + user.getUsername());
    boolean userWasAdded = userService.setNewUser(user);
    if (userWasAdded) {
      logger.info("User successfully added");
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      logger.error("User with given username already exists");
      return new ResponseEntity<>(HttpStatus.CONFLICT);  //If username already exist in database
    }
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/calculations/{username}")
  public ResponseEntity<?> getCalculationsOfUser(@PathVariable String username) {
    logger.info("Fetching all calculations of user " + username);
    try {
      List<Calculation> calculationList = calculationService.get10LatestCalculationsForUser(username);
      return ResponseEntity.ok(calculationList);
    } catch (Exception e) {
      logger.error("Error: " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
