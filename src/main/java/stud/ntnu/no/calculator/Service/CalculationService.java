package stud.ntnu.no.calculator.Service;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stud.ntnu.no.calculator.DAO.CalculationDAO;
import stud.ntnu.no.calculator.model.Calculation;
import stud.ntnu.no.calculator.model.Result;
@Service
public class CalculationService {
  @Autowired
  CalculationDAO calculationDAO;

  public Result calculate(Calculation calculation) {
    Expression expression = new ExpressionBuilder(calculation.getCalculation()).build();
    return new Result(expression.evaluate());
  }

  public boolean addCalculationToDatabase(Calculation calculation, double result) {
    int rowsAffected = calculationDAO.addNewCalculation(calculation, result);

    return rowsAffected == 1;
  }
}
