package stud.ntnu.no.calculator.model;

public class Calculation {
  private String calculation;
  private String username;

  public Calculation(String calculation, String username) {
    this.calculation = calculation;
    this.username = username;
  }

  public Calculation(){};

  public String getUsername() {
    return username;
  }
  public void setUser(String user) {
    this.username = user;
  }
  public String getCalculation() {
    return calculation;
  }

  public void setCalculation(String calculation) {
    this.calculation = calculation;
  }
}
