package exemplo;

import java.rmi.Naming;

public class CalculatorClient {

  public static void main(String[] args) {
    try{
      Calculator c = (Calculator) Naming.lookup("//localhost:2020/CalculatorService");
      System.out.println("10 + 10 = " + c.add(10, 10));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
