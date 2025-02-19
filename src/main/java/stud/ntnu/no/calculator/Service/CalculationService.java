package stud.ntnu.no.calculator.Service;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stud.ntnu.no.calculator.DAO.CalculationDAO;
import stud.ntnu.no.calculator.controller.CalculatorController;
import stud.ntnu.no.calculator.model.Calculation;
import stud.ntnu.no.calculator.model.Result;

import java.util.Collections;
import java.util.List;

@Service
public class CalculationService {
  @Autowired
  CalculationDAO calculationDAO;
  private static final Logger logger = LogManager.getLogger(CalculationService.class);


  public Result calculate(Calculation calculation) {
    Expression expression = new ExpressionBuilder(calculation.getCalculation()).build();
    return new Result(expression.evaluate());
  }

  public boolean addCalculationToDatabase(Calculation calculation, double result) {
    int rowsAffected = calculationDAO.addNewCalculation(calculation, result);

    return rowsAffected == 1;
  }

  public List<Calculation> get10LatestCalculationsForUser(String username) {
    List<Calculation> calculationList = calculationDAO.getUsersCalculation(username);
    logger.info("getting " + Math.min(calculationList.size(), 10) + " latest calculations");
    Collections.reverse(calculationList);
    return calculationList.subList(0, Math.min(calculationList.size(), 10));
  }
}
