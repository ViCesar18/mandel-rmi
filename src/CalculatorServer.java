import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CalculatorServer {
  CalculatorServer() {
    try{
      LocateRegistry.createRegistry(2020);
      Calculator c = new CalculatorImple();
      Naming.rebind("//localhost:2020/CalculatorService", c);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new CalculatorServer();

    System.out.println("Server iniciado.");
  }
}
